package game.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import game.vo.Data;
import game.vo.RoomInfo;
import game.vo.User;

public class ClientListenerThread implements Runnable{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	static ArrayList<User> connectedUserList = new ArrayList<>(); //서버에 접속된 클라이언트, 각 클라이언트의 ObjectOutputStream이 저정되어 있음
	static HashMap<String, RoomInfo> gameRoomList = new HashMap<>();
	private Data data;
	private User user;
	private RoomInfo ri;
	private String roomTitle;
	private Socket socket;
	private boolean exit;
	
	
	public ClientListenerThread(Socket socket, User user) throws IOException {
		super();
		this.user = user;
		this.socket = socket;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		exit = false;
	}

	@Override
	public void run() {
		System.out.println("리스너 스레드 접속");
		//접속하자마자 접속자 리스트 불러올 command 보냄 (Command만 들어있음)
		data.setCommand(Data.CONNECTION);
		try {
			oos.writeObject(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(!exit){
		try {
			data = (Data)ois.readObject();
			switch(data.getCommand()){
			case Data.CONNECTION: //전체 접속자 정보 받아와서 ui에 띄워주기
				connectedUserList = data.getUserList(); 
				//TODO:userList 갱신 메소드 만들기 
				renewalConUserList();
				break;
			case Data.SIGNIN:
				/** 회원가입 버튼 눌렀을 때 UI 액션리스너에서 서버에 oos로 넘길 내용
				 * TODO: id, pw, em, pfimg 선택(랜덤), 받아서 User 객체 만들기
				 * 하나라도 입력 안하면 오류남! 에러 처리 해줄것!
				 * 서버에 user Data.SIGNIN setCommand 해서 넘겨주기
				 * TODO: 서버에서 boolean(database 등록되었는지 안되었는지 받음)
				 * 결과에 따라 
				 */
				boolean addrs = data.isUserAddrs();
				if(addrs){ //TODO:등록되었을 경우 - 메세지창:"등록 완료되었습니다." 
					
				}else{ 
				// TODO:등록되지 않았을 경우 - 메세지창: "이미 등록된 아이디입니다. 다른 아이디를 입력해 주세요"
				// 회원가입 textfield 아이디 창만 리셋해주기
				}
				break;
			case Data.LOGIN:
				/**로그인 버튼
				 * 
				 */
				break;
			case Data.JOIN:
				break;
			case Data.MAKE_ROOM:
				break;
			case Data.GAME_READY:
				break;
			case Data.GAME_START:
				break;
			case Data.SEND_TURN:
				break;
			case Data.SEND_WINNING_RESULT:
				break;
			case Data.CHAT_MESSAGE:
				break;
			case Data.EXIT:
				break;
			default:
				break;
				
		}//switch
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			exit = true; //오류나면 socket , while 문 종료
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}//catch
		}//while
	}

	private void renewalConUserList() {
		// TODO: connectedUserList 전체 접속자 리스트에 띄우기
		
	}

}
