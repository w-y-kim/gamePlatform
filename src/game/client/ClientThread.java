package game.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import game.client.data.ClientData;

public class ClientThread implements Runnable {

	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public ClientThread(ObjectOutputStream oos, ObjectInputStream ois){
		this.oos = oos;
		this.ois = ois; 
	
		boolean exit = false;
		while(!exit){
			try {
				ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				exit = true; 
				JOptionPane.showMessageDialog(null, "서버와의 연결이 끊겼습니다.", "접속종료 안내창", JOptionPane.INFORMATION_MESSAGE);

			} 
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//리스너구현

	}

	public void SendData(ClientData data){
		try {
			oos.writeObject(data);
		} catch (IOException e) {
			System.out.println("CleintThread : SendData >> 스트림전송실패");
		}
	}
}
