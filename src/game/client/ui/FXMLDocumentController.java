/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.client.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import game.vo.Data;
import game.vo.RoomInfo;
import game.vo.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Runnable,Initializable {

	// 접속단계
	@FXML
	private ListView<User> allUserList;
	// 로그인
	@FXML
	private Button loginList;
	@FXML
	private TextField field_id;
	@FXML
	private TextField field_pw;
	@FXML
	private Button btnLogin;
	// 회원가입
	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnOK;
	@FXML
	private Button btnCancel;
	@FXML
	private AnchorPane AnchorSignUp;// 회원가입 패널
	@FXML
	private TextField signID;
	@FXML
	private TextField signPW;
	@FXML
	private TextField confirmPW;
	@FXML
	private TextField signMail;

	@FXML
	private ToolBar toolbar;
	@FXML
	private Text msg;
	@FXML
	private Button btnSplit;
	@FXML
	private SplitPane splitPane;
	@FXML
	private Button btnFold;
	@FXML
	private Button btnSpread;

	@FXML
	private ProgressIndicator ProgressIndicator;
	@FXML
	private AnchorPane loadingPane;

	// 스레드 실행 위함
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	static ArrayList<User> connectedUserList = new ArrayList<>(); // 서버에 접속된
																	// 클라이언트, 각
																	// 클라이언트의
																	// ObjectOutputStream이
																	// 저정되어 있음
	static HashMap<String, RoomInfo> gameRoomList = new HashMap<>();
	private Data data;
	private User user;
	private RoomInfo ri;
	private String roomTitle;
	private boolean exit;
	private FXMLDocumentController fxControl;// FX의 컴포넌트 갱신 위해 스레드 실행 시 받아옴

	public FXMLDocumentController() {

		try {
			// 서버연결
			socket = new Socket("localhost", 7582);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			// 접속후 서버에서 뿌려주도록 스레드 실행 (로그인 안해도)
			// ClientListenerThread ct = new ClientListenerThread(socket, this);
			Thread t = new Thread(this);
			t.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 회원가입버튼을 누르면 가입폼을 연다
	 * 
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void signUpAction(ActionEvent e) throws IOException {
		System.out.println("회원가입 버튼눌림");

		splitPane.setDividerPosition(0, 0);// 전체화면에서는 최대한 펼쳐짐
		AnchorSignUp.setVisible(true);

		// 새창으로 열어서 할 경우
		// Stage stage;
		// Parent root;

		// this.PaneLoading(true);
		// loadingPane.setVisible(true);
		// stage = new Stage();
		// root = FXMLLoader.load(getClass().getResource("./Login.fxml"));
		// stage.setScene(new Scene(root));
		// stage.setTitle("로그인창");
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.TRANSPARENT);// 스테이지 모양
		// stage.initOwner(btnSignUp.getScene().getWindow());
		// stage.showAndWait();

		// 확인
		if (e.getSource() == btnOK) {
			String id = signID.getText();
			String pw = signPW.getText();
			String pw2 = confirmPW.getText();
			String mail = signMail.getText();

			boolean check1 = id.isEmpty();
			boolean check2 = pw.isEmpty();
			boolean check3 = pw2.isEmpty();
			boolean check4 = mail.isEmpty();
			
			if(check1 == true && check2 == true && check3 == true && check4 == true){
				JOptionPane.showMessageDialog(null, "빈칸을 모두 입력해주세요");
			}else {
			
			
				if (pw.equals(pw2) != true) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");// FIXME 리스너에서
					signPW.setText("");
					confirmPW.setText("");
				} else {
					User new_user = new User(id, pw, mail, User.IMAGE1);// 아직 그림 없음
					data.setUser(new_user);
					data.setCommand(Data.SIGNUP);
					this.sendData(data);
					btnCancel.getScene().getWindow();
					AnchorSignUp.setVisible(false);
					
					JOptionPane.showMessageDialog(null, "가입완료", mail, 0);// FIXME
																			// 리스너에서
				}
			}

		}

		// 닫기
		if (e.getSource() == btnCancel) {
			btnCancel.getScene().getWindow();
			AnchorSignUp.setVisible(false);
		}
	}

	@FXML
	private void ButtonAction(ActionEvent e) throws IOException {
		boolean toggle = true;
		if (e.getSource() == btnSplit) {

			if (toggle) {
				msg.setText("버튼 눌렀음");
				toggle = false;
			} else {
				msg.setText("버튼 취소했음");
				toggle = true;
			}
		} else if (e.getSource() == btnFold) {
			splitPane.setDividerPosition(0, 1);
			btnFold.setVisible(false);
			btnSpread.setVisible(true);
		} else if (e.getSource() == btnSpread) {
			splitPane.setDividerPosition(0, 0.5);
			btnFold.setVisible(true);
			btnSpread.setVisible(false);
		}
	}

	@FXML
	private void loginAction(ActionEvent e) throws IOException {
		if (e.getSource() == btnLogin) {
			String id = field_id.getText();
			String pw = field_pw.getText();
			User loginUser = new User(id, pw, "", 0);
			Data data = new Data(Data.LOGIN);
			this.sendData(data);
		}
	}

	private void PaneLoading(boolean toggle) throws IOException {
		if (toggle) {
			// ProgressIndicator.setVisible(toggle);
			loadingPane.setVisible(true);
			loadingPane.setOpacity(1.d);
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			loadingPane.setOpacity(0);
			loadingPane.setVisible(false);
		}
	}

	@Override
	public void run() {

		System.out.println("리스너 스레드 접속");
		// 접속하자마자 접속자 리스트 불러올 command 보냄 (Command만 들어있음)
		// data.setCommand(Data.CONNECTION);
		data = new Data(Data.CONNECTION);// 데이터 객체 최초 생성
		this.sendData(data);
		while (!exit) {
			try {
				data = (Data) ois.readObject();
				switch (data.getCommand()) {
				case Data.CONNECTION: // 전체 접속자 정보 받아와서 ui에 띄워주기
					connectedUserList = data.getUserList();
					// TODO:userList 갱신 메소드 만들기

					if (connectedUserList.isEmpty() == false) {
						renewalConUserList();
					} else {
						// Thread.sleep(2000);
						// JOptionPane.showMessageDialog(null, "아직 너 혼자야! 힘내 ...
						// 살다보면 그럴수도있지뭐");
					}
					break;
				case Data.SIGNUP:
					boolean result = data.isUserAddrs();
					
					System.out.println(result);
					JOptionPane.showMessageDialog(null, result);
					/**
					 * 회원가입 버튼 눌렀을 때 UI 액션리스너에서 서버에 oos로 넘길 내용 TODO: id, pw, em,
					 * pfimg 선택(랜덤), 받아서 User 객체 만들기 하나라도 입력 안하면 오류남! 에러 처리 해줄것!
					 * 서버에 user Data.SIGNIN setCommand 해서 넘겨주기 TODO: 서버에서
					 * boolean(database 등록되었는지 안되었는지 받음) 결과에 따라
					 */

					boolean addrs = data.isUserAddrs();
					if (addrs) { // TODO:등록되었을 경우 - 메세지창:"등록 완료되었습니다."
						JOptionPane.showMessageDialog(null, "가입환영!");
					} else {
						// TODO:등록되지 않았을 경우 - 메세지창: "이미 등록된 아이디입니다. 다른 아이디를 입력해
						// 주세요"
						// 회원가입 textfield 아이디 창만 리셋해주기
					}
					break;
				case Data.LOGIN:
					/**
					 * 로그인 버튼
					 * 
					 */
					break;
				case Data.JOIN:
					break;
				case Data.MAKE_ROOM:
					break;
				case Data.GAME_READY:
					break;
				case Data.GAME_START:
					break;
				case Data.SEND_TURN:
					break;
				case Data.SEND_WINNING_RESULT:
					break;
				case Data.CHAT_MESSAGE:
					break;
				case Data.EXIT:
					break;
				default:
					break;

				}// switch

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				exit = true; // 오류나면 socket , while 문 종료
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} // catch
		} // while
	}

	private void sendData(Data data) {
		try {
			oos.writeObject(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void renewalConUserList() {
		// TODO: connectedUserList 전체 접속자 리스트에 띄우기

		ObservableList<User> ob = FXCollections.observableArrayList(connectedUserList);
		allUserList.setItems(ob);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
