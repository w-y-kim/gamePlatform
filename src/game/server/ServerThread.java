package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import game.dao.Database;
import game.errors.DuplicateIDException;
import game.errors.RecordNotFoundException;
import game.vo.ClientMessage;
import game.vo.Data;
import game.vo.Friend;
import game.vo.GameInfo;
import game.vo.GameRoom;
import game.vo.User;

public class ServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	static ArrayList<User> connectedUserList = new ArrayList<>(); // 서버에 접속된
																	// 클라이언트, 각
																	// 클라이언트의
																	// ObjectOutputStream이
																	// 저정되어 있음
	static HashMap<String, GameRoom> gameRoomList = new HashMap<>();
	private Data data;
	private User loginUser;
	private GameRoom ri;
	private String roomTitle;
	private Socket socket;
	private boolean exit; // exit이 true 면 나가기
	private Database db = new Database();
	private ArrayList<Friend> friendList;

	public ServerThread(Socket socket) {
		super();
		this.socket = socket;
		exit = false;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Data Command 별 분기처리 broadCasting 메소드 만들기
	 */
	@Override
	public void run() {
		System.out.println(socket.getPort() + "번 서버스레드 시작");

		while (!exit) {
			try {
				data = (Data) ois.readObject();
				switch (data.getCommand()) {
				case Data.CONNECTION: // 전체 접속자 정보 보냄
					data.setUserList(connectedUserList);
					data.setAllUserIds(db.getAllUserId());
					oos.writeObject(data); // 자신한테만 보내는 것이라서 broadcasting 안씀
					oos.reset();
					System.out.println(connectedUserList + "받아온 접속자 목록");
					break;
				case Data.SIGNUP:
					/*
					 * 유저 정보 받아서 DB에 저장 가입 허가 넘겨주기
					 */
					//
					boolean addrs = false;
					loginUser = data.getUser();
					System.out.println(loginUser);
					addrs = db.addUser(loginUser); // database에서 등록됐으면 true,
													// 안됐으면 false 넘김
					data.setUserAddrs(addrs);
					oos.writeObject(data);
					oos.reset();
					break;
				case Data.LOGIN:
					User user = data.getUser();
					System.out.println(user+" 서버: 로그인 명령한 유저(ID체크전)");
					user = db.loginCheck(user); // pw와 일치하면 완전한 유저 정보가 담김, pw일치
												// 안하면 user null
					System.out.println(user+" 서버: 로그인 명령한 유저(ID체크후)");
					if (user != null) {
						//FIXME connectedUserList.add(user); 
						/*클라에서 로긴 시 컨리스트를 담아보낼 필요가 없다. 
						 *로그인이 정말 되는지는 서버를 거쳐서 결정되기 때문에
						 *대신 클라 리스너에서는 로그인 결과 출력할 때 필요하다.
						 *그래서 서버에서 담아줌  
						 */
						System.out.println("접속 유저 있는데 왜 안들어와");
						user.setOos(oos);
						connectedUserList.add(user);
						System.out.println(connectedUserList+"서버에서 추가한 유저리스트");
						friendList = db.getFriendList(user.getId());
						data.setFriendList(friendList);
						data.setUser(user);
						data.setUserList(connectedUserList);
						System.out.println(data+"데이터");
						System.out.println(data.getUserList()+"유저리스트");
					} else {
					}

					oos.writeObject(data);
					oos.reset();
					break;
				case Data.ADDFRIEND:
					Friend fr = data.getFriend();
					boolean faddrs = db.addFriend(fr); // friend add result
					break;
				case Data.SEND_NOTE:
					/*- 받은 message db에 저장
					 *- 특정 아이디의 oos에만 보내는 메소드 만들기 
					 * 1. 받는 user가 로그아웃 중일 때는 db에만 저장
					 * 2. 로그인 중일 때는 ClientMessage의 id의 oos로 보내기
					 */
					ClientMessage cm = data.getClientMessage();
					db.saveMessage(cm);
					String rcid = cm.getId(); // 받는 유저 아이디
					boolean ss = false;
					User rcUser = null;
					for (int i = 0; i < connectedUserList.size(); i++) {
						if (connectedUserList.get(i).equals(rcid)) {
							ss = true;
							rcUser = connectedUserList.get(i);
						}
					}
					if (ss) { // 그인 중일 때는 ClientMessage의 id의 oos로 보내기
						sendMemo(rcUser);
					}
					// ss가 false면 안보냄.

					break;
				case Data.CHAT_MESSAGE:
					break;
				case Data.JOIN:
					break;
				case Data.MAKE_ROOM:
					break;
				case Data.GAME_READY:
					break;
				case Data.GAME_START:
					GameInfo ginfo = data.getGameInfo();
					System.out.println(ginfo+" ginfo");
					User painter = data.getUser();
//					connectedUserList.add(painter);//로그인시 들어가니까 안넣어도 됨 
					data.setUserList(connectedUserList);
					broadCasting(data); 
					break;
				case Data.SEND_TURN:
					break;
				case Data.SEND_WINNING_RESULT:
					break;

				case Data.EXIT:// 로그 아웃 currentUserList에서 빼고 갱신된 유저리스트 브로드캐스팅
								// 해주기.
					String id = data.getUser().getId();
					for (int i = 0; i < connectedUserList.size(); i++) {
						if (connectedUserList.get(i).getId().equals(id)) {
							connectedUserList.remove(i);
						}
					}
					data.setUserList(connectedUserList);
					broadCasting(data);
					break;
				default:
					break;

				}// switch

			} catch (ClassNotFoundException | IOException e) {
				System.out.println(socket.getPort() + "번 서버스레드 종료");
				exit = true;
			} // catch
			catch (DuplicateIDException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}
		} // while
		System.out.println("=============================================");
		System.out.println("=============================================");
	}// run

	public void sendMemo(User rcUser) throws IOException { // 받는 사람 user id
		// 로그인한 유저에게 쪽지 보냄
		rcUser.getOos().writeObject(data);
	}

	public void sendDataRoommate(Data data) {

	}

	public void broadCasting(Data data) throws IOException {
		for (User userr : connectedUserList) {
			System.out.println("broadcasting");
			System.out.println(data.getGameInfo()+"좌표방송");
			System.out.println(data.getUser()+"그린이");
			System.out.println(connectedUserList+"서버유저목록");
			System.out.println(userr+"방송보낸대상");
			System.out.println(data+"데이터");
			userr.getOos().writeObject(data);
			userr.getOos().reset();
		}
	}

}