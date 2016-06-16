package game.vo;

public class Friend {

	private String id;
	private String fid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public Friend(String id, String fid) {
		super();
		this.id = id;
		this.fid = fid;
	}
	@Override
	public String toString() {
		return "Friend [id=" + id + ", fid=" + fid + "]";
	}
	
	
}
