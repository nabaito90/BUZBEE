package bit.spring4.buzbee.util;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler {
	  private static Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	  private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	 
	  // Ŭ���̾�Ʈ�� ���� ���Ŀ� ����Ǵ� �޼ҵ�
	  @Override
	  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	    sessionList.add(session);
	    logger.info("{} �����", session.getId());
	  }
	 
	  // Ŭ���̾�Ʈ�� ������ �޽����� �������� �� ����Ǵ� �޼ҵ�
	  @Override
	  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	    logger.info("{}�� ���� {} ����", session.getId(), message.getPayload());
	    for (WebSocketSession sess : sessionList) {
	      sess.sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
	    }
	  }
	 
	  // Ŭ���̾�Ʈ�� ������ ������ �� ����Ǵ� �޼ҵ�
	  @Override
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	    sessionList.remove(session);
	    logger.info("{} ���� ����", session.getId());
	  }
	}

