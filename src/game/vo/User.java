package game.vo;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {

	private String id;
	private String pw;
	private String em;//email
	private int pfimg; //프로필 이미지 
	
	
	public final static int IMAGE1 = 10;
	public final static int IMAGE2 = 20;
	public final static int IMAGE3 = 30;
	public final static int IMAGE4 = 40;
	public final static int IMAGE5 = 50;
	
	private transient ObjectOutputStream oos;
	
	public User(String id, String pw, String em, int pfimg) {
		super();
		this.id = id;
		this.pw = pw;
		this.em = em;
		this.pfimg = pfimg;
	}
	
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	public int getPfimg() {
		return pfimg;
	}
	public void setPfimg(int pfimg) {
		this.pfimg = pfimg;
	}
	@Override
	public String toString() {
		return "Client [id=" + id + ", pw=" + pw + ", em=" + em + ", pfimg=" + pfimg + "]";
	}
	
}
