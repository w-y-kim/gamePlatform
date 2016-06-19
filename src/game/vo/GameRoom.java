package game.vo;

public class GameRoom {

	User user; 
	String roomId; 
	String roomPw; 
	
	public GameRoom(User loginUser, String roomId, String roomPw) {
		this.user = loginUser; 
		this.roomId = roomId; 
		this.roomPw = roomPw; 
	}

}
