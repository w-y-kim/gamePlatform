package game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {//

	private static Socket socket;

	public static void main(String[] args) {

		ServerSocket ss;
		try {
			ss = new ServerSocket(7582);
			System.out.println("접속대기중");
			while (!ss.isClosed()) {
				socket = ss.accept();
				System.out.println("=============================================");
				System.out.println(socket.getInetAddress()+"로부터 새로운 클라이언트 접속");

				ServerThread gst = new ServerThread(socket);
				Thread thread = new Thread(gst);
				thread.start();
				System.out.println("=============================================");
			} // while
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("서버종료");
		}

	}

}

