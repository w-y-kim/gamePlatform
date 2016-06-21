package game.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;



public class Data implements Serializable {
	private String message; // 채팅 대화내용
	private String error;
	private int command; // 요청 명령 상수
	private GameRoom gameRoom; // 게임방 정보
	private GameInfo gameInfo; // 게임 정보
	private User user; // Data객체를 발송하는 사용자 정보
	private boolean userAddrs; // user 가입 결과 보내주는 정보
	private ArrayList<Friend> friendList;
	private ArrayList<User> userList; // 서버에 접속되어있는 사용자 목록
	private HashMap<String, GameRoom> roomList; // 게설된 방 목록
	private ClientMessage clientMessage;
	private Friend friend;
	private MainInfo mainInfo;
	private ArrayList<String> allUserIds;
	private int gameType;//게임선택 

	public static final int GAME_FIRST = 300; // 게임방 생성
	public static final int GAME_SECOND = 350; // 게임방 생성
	public static final int GAME_THIRD = 400; // 게임방 생성

	public static final int MAKE_ROOM = 20; // 게임방 생성
	public static final int JOIN = 30; // 게임방 입장
	
	public static final int CHAT_MESSAGE = 40; // 게임방 내 채팅
	public static final int SEND_NOTE = 41;
	public static final int SEND_BINGO_DATA = 50; // 선택한 빙고 단어 전송
	public static final int SEND_WINNING_RESULT = 60; // 5개의 빙고가 완성됐을 시 승리결과 전송
	public static final int GAME_READY = 70; // 25개 빙고단어 입력 완료 후 준비
	public static final int GAME_START = 80; // 방에 참가한 모든 유저가 준비상태가 되어 게임시작을 알림
	public static final int EXIT = -10; // 프로그램 종료
	public static final int SEND_TURN = 90;
	public static final int CONNECTION = 11;
	public static final int SIGNUP = 10;// 로그인
	public static final int LOGIN = 0;
	public static final int ADDFRIEND = 12;
	public static final int DRAW_READY = 100;
	public static final int DRAW_START = 150;
	public static final int CLEAR_CANVAS = 200;
	public static final int SELECT_GAME =500;

	public Data(int command) {
		this.command = command;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ArrayList<String> getAllUserIds() {
		return allUserIds;
	}

	public void setAllUserIds(ArrayList<String> allUserIds) {
		this.allUserIds = allUserIds;
	}

	public ArrayList<Friend> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<Friend> friendList) {
		this.friendList = friendList;
	}

	public boolean isUserAddrs() {
		return userAddrs;
	}

	public void setUserAddrs(boolean userAddrs) {
		this.userAddrs = userAddrs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public HashMap<String, GameRoom> getRoomList() {
		return roomList;
	}

	public void setRoomList(HashMap<String, GameRoom> roomList) {
		this.roomList = roomList;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public ClientMessage getClientMessage() {
		return clientMessage;
	}

	public void setClientMessage(ClientMessage clientMessage) {
		this.clientMessage = clientMessage;
	}

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public MainInfo getMainInfo() {
		return mainInfo;
	}

	public void setMainInfo(MainInfo mainInfo) {
		this.mainInfo = mainInfo;
	}

	
	
	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	@Override
	public String toString() {
		return "Data [message=" + message + ", command=" + command + ", gameRoom=" + gameRoom + ", gameInfo=" + gameInfo
				+ ", user=" + user + ", userList=" + userList + ", roomList=" + roomList + ", clientMessage="
				+ clientMessage + ", friend=" + friend + ", mainInfo=" + mainInfo + "]";
	}

}