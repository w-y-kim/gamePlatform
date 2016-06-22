package game.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class GameRoom implements Serializable{

	private User user; //방장
	private String title; 
	private String roomPw; 
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<String> words;
	
	public GameRoom(User loginUser, String roomId, String roomPw) {
		this.user = loginUser; 
		this.title = roomId; 
		this.roomPw = roomPw; 
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

	

}
