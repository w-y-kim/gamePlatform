package game.errors;

public class RecordNotFoundException extends Exception{

	public RecordNotFoundException() {
		super("존재하지 않는 아이디 입니다.");
	}

	public RecordNotFoundException(String message) {
		super(message);
	}

}
