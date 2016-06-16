package game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {

	public static void main(String[] args) {

		ServerSocket ss;
		try{
			ss =	new ServerSocket(7582);
		System.out.println("서버 시작");
		while(!ss.isClosed()){
			System.out.println("waiting...");	
			
			Socket socket = ss.accept();
			System.out.println("New User accepted!");
			
			System.out.println("New User accepted!");
		ServerThread gst = new ServerThread(socket);
		Thread thread = new Thread(gst);
		thread.start();
		
		}//while
	} catch (IOException e) {
		e.printStackTrace();
	}

		
	}

}
