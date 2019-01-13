package bit.spring4.buzbee.util;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import bit.spring4.buzbee.board.model.service.BoardService;
import bit.spring4.buzbee.login.model.service.LoginService;

public class EchoHandler extends TextWebSocketHandler {
	  private static Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	  private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	  @Autowired
	  private BoardService boardService;
	  @Autowired
	  private LoginService loginService;
	 
	  // 클라이언트와 연결 이후에 실행되는 메소드
	  @Override
	  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		  sessionList.add(session);
		  logger.info("{} 연결됨", session.getPrincipal().getName());
	  }
	 
	  // 클라이언트가 서버로 메시지를 전송했을 때 실행되는 메소드
	  @Override
	  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		  int set = -1;
		  long b_no = -1;
		  List<String> follower = null;
		  HashMap<String, Long> ids = new HashMap<String, Long>();
		  String msg = message.getPayload();
		  msg = msg.replaceAll("\\n", "</br>");
		  String m_content = msg.substring(msg.indexOf(":") + 1);
		  m_content = m_content.trim();
		  
		  if(!m_content.contains("@")) {
			  long m_no = loginService.selectM_NOByIdService(session.getPrincipal().getName()); 
			  b_no = boardService.insertService(m_no, m_content); // DB에 메세지 insert
			  follower = boardService.followerOnlineService(m_no);
			  set = 1;
		  } else {
			  ArrayList<String> idList = pickIds(m_content);
			  idList.add(session.getPrincipal().getName());
			  for(String id : idList) {
				  long m_no = loginService.selectM_NOByIdService(id); 
				  if(m_no != -1) {
					  b_no = boardService.insertService(m_no, m_content);
					  ids.put(id, b_no);
				  }
			  }
			  set = 2;
		  }
		  
		  for (WebSocketSession sess : sessionList) {
			  if(sess.getPrincipal().getName().equals(session.getPrincipal().getName())) {
				  if(set == 1) sess.sendMessage(new TextMessage(session.getPrincipal().getName() + "/" + msg + "$" + b_no));
				  else if(set == 2) sess.sendMessage(new TextMessage(session.getPrincipal().getName() + "/" + msg + "$" + ids.get(session.getPrincipal().getName())));
			  } else {
				  if(set == 1) {
					  if(follower.contains(sess.getPrincipal().getName())) {
						  sess.sendMessage(new TextMessage(session.getPrincipal().getName() + "/" + msg + "$" + b_no));
					  }
				  } else if(set == 2) {
					  if(ids.containsKey(sess.getPrincipal().getName())) {
						  sess.sendMessage(new TextMessage(session.getPrincipal().getName() + "/" + msg + "$" + ids.get(sess.getPrincipal().getName())));
					  }
				  }
			  }
		  }
	  }
	 
	  // 클라이언트와 연결을 끊었을 때 실행되는 메소드
	  @Override
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		  sessionList.remove(session);
		  // logger.info("{} 연결 끊김", session.getPrincipal().getName());
	  }
	  
	  private ArrayList<String> pickIds(String msg) {
		  String[] ids = msg.split("@");
		  ArrayList<String> idsList = new ArrayList<String>();
		  if(ids == null) return null;
		  else {
			  for(int i = 1; i < ids.length; i++) {
				  String id = null;
				  try {
					  id = ids[i].substring(0, ids[i].indexOf(" "));
				  } catch (Exception e) {
					  id = ids[i].substring(0);
				  }
				  idsList.add(id);
			  }
		  }
		  return idsList;
	  }
}

