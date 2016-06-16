package game.errors;

public class DuplicateIDException extends Exception{

	public DuplicateIDException() {
		super("동일한 ID의 레코드가 이미 존재합니다.");
	}

	public DuplicateIDException(String message) {
		super(message);
	}
	
	
}
