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
	 
	  // Ŭ���̾�Ʈ�� ���� ���Ŀ� ����Ǵ� �޼ҵ�
	  @Override
	  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		  sessionList.add(session);
		  logger.info("{} �����", session.getPrincipal().getName());
	  }
	 
	  // Ŭ���̾�Ʈ�� ������ �޽����� �������� �� ����Ǵ� �޼ҵ�
	  @Override
	  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		  String msg = message.getPayload();
		  msg = msg.replaceAll("\\n", "</br>");
		  System.out.println(msg);
		  logger.info("{}�� ���� {} ����", session.getPrincipal().getName(), msg);

		  long m_no = loginService.selectM_NOByIdService(session.getPrincipal().getName()); 
		  long b_no = boardService.insertService(m_no, msg); // DB�� �޼��� insert
		  msg += "$" + b_no;
		  for (WebSocketSession sess : sessionList) {
			  if(sess.getPrincipal().getName().equals(session.getPrincipal().getName())) {
				  sess.sendMessage(new TextMessage(msg));
			  }
		  }
	  }
	 
	  // Ŭ���̾�Ʈ�� ������ ������ �� ����Ǵ� �޼ҵ�
	  @Override
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		  sessionList.remove(session);
		  // logger.info("{} ���� ����", session.getPrincipal().getName());
	  }
}

