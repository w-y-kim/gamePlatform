package game.vo;

import java.io.Serializable;

public class GameRoom implements Serializable{

	User user; 
	String roomId; 
	String roomPw; 
	String port;
	
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

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
