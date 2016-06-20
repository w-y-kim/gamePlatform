package game.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class GameRoom implements Serializable{

	private User user; //방장
	private String roomId; 
	private String roomPw; 
	private ArrayList<User> userList;
	
	public GameRoom(User loginUser, String roomId, String roomPw) {
		this.user = loginUser; 
		this.roomId = roomId; 
		this.roomPw = roomPw; 
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	

}
