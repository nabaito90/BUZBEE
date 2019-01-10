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
		  String msg = message.getPayload();
		  msg = msg.replaceAll("\\n", "</br>");
		  System.out.println(msg);
		  logger.info("{}로 부터 {} 받음", session.getPrincipal().getName(), msg);

		  long m_no = loginService.selectM_NOByIdService(session.getPrincipal().getName()); 
		  long b_no = boardService.insertService(m_no, msg); // DB에 메세지 insert
		  msg += "$" + b_no;
		  for (WebSocketSession sess : sessionList) {
			  if(sess.getPrincipal().getName().equals(session.getPrincipal().getName())) {
				  sess.sendMessage(new TextMessage(msg));
			  }
		  }
	  }
	 
	  // 클라이언트와 연결을 끊었을 때 실행되는 메소드
	  @Override
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		  sessionList.remove(session);
		  // logger.info("{} 연결 끊김", session.getPrincipal().getName());
	  }
}

