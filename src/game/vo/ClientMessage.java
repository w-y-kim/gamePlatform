package game.vo;


public class ClientMessage {
	
	private String id;
	private String sid;
	private String ct;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public ClientMessage(String id, String sid, String ct) {
		super();
		this.id = id;
		this.sid = sid;
		this.ct = ct;
	}
	@Override
	public String toString() {
		return "ClientMessage [id=" + id + ", sid=" + sid + ", ct=" + ct + "]";
	}
	
	
}
