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
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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
public class FXMLDocumentController {

	@FXML
	private Button btnSignIn;
	@FXML
	private ToolBar toolbar;
	@FXML
	private Button btnCancel;
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

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	
	public FXMLDocumentController(){
	}
	// @Override
	// public void initialize(URL url, ResourceBundle rb) {
	// // TODO
	//// btnSpread.setVisible(false);
	// //
	// // // 소켓통신
	// // try {
	// // socket = new Socket("localhost", 8888);
	// // oos = new ObjectOutputStream(socket.getOutputStream());
	// // ois = new ObjectInputStream(socket.getInputStream());
	// // //스레드실행 , 로그인 이후에 넣도록 하자
	// // ClientThread ct = new ClientThread(oos,ois);
	// // Thread t = new Thread(ct);
	// // t.start();
	// //
	// // } catch (IOException e) {
	// // e.printStackTrace();
	// // }
	//
	// }

	@FXML
	private void CloseAction(ActionEvent e) throws IOException {

		Stage stage;
		Parent root;

		if (e.getSource() == btnSignIn) {
//			this.PaneLoading(true);
			loadingPane.setVisible(true);
			
			stage = new Stage();
			root = FXMLLoader.load(getClass().getResource("./Login.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("로그인창");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.TRANSPARENT);// 스테이지 모양
			stage.initOwner(btnSignIn.getScene().getWindow());
			stage.showAndWait();
		} else {
			stage = (Stage) btnCancel.getScene().getWindow();
			stage.close();
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

}
