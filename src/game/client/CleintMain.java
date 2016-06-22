package game.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import game.client.ui.FXMLController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class CleintMain extends Application {

	public CleintMain() {
//		Sound("Shooting Range_1.wav", true);
		
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("./ui/FXMLDoc.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		// stage.initStyle(StageStyle.UTILITY);//스테이지 모양
	 	stage.show();
	
		stage.setOnHiding(new EventHandler<WindowEvent>() {

			//클라이언트 종료
			public void handle(WindowEvent event) {
           
                        System.out.println("Application Closed by click to Close Button(X)");
                        System.exit(0);
            }
        });
		
		
	}
	
	

	public void Movie(String url, boolean Auto) {
		Media media = new Media(getClass().getResource(url).toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(Auto);

	}

	public void Sound(String file, boolean Loop) {
		// 사운드재생용메소드~
		// 메인 클래스에 추가로 메소드를 하나 더 만들었습니다.
		// 사운드파일을받아들여해당사운드를재생시킨다.
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);
			// Loop 값이true면 사운드재생을무한반복시킵니다.
			// false면 한번만재생시킵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
		
		

	}
}
