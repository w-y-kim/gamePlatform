package game.vo;

import java.io.Serializable;
import java.util.ArrayList;



public class GameRoom implements Serializable{

	private User user; //방장
	private String title; 
	private String roomPw; 
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<String> words;
	
	private User turnUser;
	private int pointer = 0;
	private ArrayList<String> turnUserList = new ArrayList<>();
	private int nowUserNum;
	
	
	public GameRoom(User loginUser, String roomId, String roomPw) {
		this.user = loginUser; 
		this.title = roomId; 
		this.roomPw = roomPw; 
	}

	//게임 시작 시 실행해 주세요
	public void turnUserSet(){
		for (User user1 : userList) {
			turnUserList.add(user1.getId());
		}
		nowUserNum = userList.size();
	}
	
	public void setTurnUser(){
		int index = pointer % nowUserNum; //0, 1,2 를 반복적으로 가져와 다음 턴 유저를 가져옴
		pointer++;
		String userID = turnUserList.get(index);
		turnUser = userList.get(index);
		System.out.println(index+" : "+userID);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoomId() {
		return title;
	}

	public void setRoomId(String roomId) {
		this.title = roomId;
	}

	public String getRoomPw() {
		return roomPw;
	}

	public void setRoomPw(String roomPw) {
		this.roomPw = roomPw;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
//턴메소드
	
//제시어 뱉어내는 메소드
	

}
