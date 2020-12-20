package whiteboard;

import java.util.*;
import java.net.*;

public class WhiteboardServer {
	private Vector handlers;
	
	public WhiteboardServer (int port) {
		try {
			ServerSocket server = new ServerSocket (port); 	//바인드
////////////위에 소스 생성 과 바인드////////////
			handlers = new Vector();
			System.out.println("WhiteboardServer is ready");
			while (true) {
				Socket s = server.accept();			//어셉트를 만듬 = 정보가 클라이언트 소켓으로 들어감 
//				소켓이 접속 완료된 부분
				WhiteboardHandler c = new WhiteboardHandler (this, s);
				c.start ();
			}
		} catch(Exception e) {
			PrintDebugMessage.print(e);
		}
	}
	
	public Object getHandler(int index) {
		return handlers.elementAt(index);
	}
	
	public void register(WhiteboardHandler c) {
		handlers.addElement(c);
	}
	
	public void unregister(Object o) {
		handlers.removeElement(o);
	}
	
	public void broadcast (String msg) {
		synchronized (handlers) {
			int n = handlers.size();
			for(int i=0; i<n; i++) {
				WhiteboardHandler c = (WhiteboardHandler) handlers.elementAt(i);
				try {
					c.println(msg);
				} catch(Exception ex) {
					PrintDebugMessage.print(ex);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		WhiteboardServer server = new WhiteboardServer(9850);
	}
}
