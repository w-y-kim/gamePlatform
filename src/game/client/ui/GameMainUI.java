package game.client.ui;

import java.applet.Applet;


import java.applet.AudioClip;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.Color;
import java.awt.Component;

public class GameMainUI {

	private JFrame frame;
	private Image backgroundImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameMainUI window = new GameMainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameMainUI() {
		initialize();

		// BGM
		// AudioClip clip;
		// try {
		// File file = new File("Flap+toward+the+hope.mp3");
		// clip = Applet.newAudioClip(file.toURL());
		// clip.play();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	public void Sound(String file, boolean Loop) {
		// 사운드재생용메소드
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

	
	public void Movie(String url, boolean Auto){
		Media media = new Media(getClass().getResource(url).toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media); 
		mediaPlayer.setAutoPlay(Auto);

	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//소리
		Sound("Shooting Range_1.wav", true);

		//영상

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 631, 613);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// try {
		// backgroundImage = ImageIO.read(new File("background.jpg"));
		// } catch (IOException e)
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JPanel panel_1 = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, frame.getContentPane());
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.add(new JLabel(new ImageIcon("main.jpg")));
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(panel_1);

		JPanel panel_2 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 0, SpringLayout.NORTH, panel_2);
		springLayout.putConstraint(SpringLayout.EAST, panel_1, -6, SpringLayout.WEST, panel_2);
		panel_2.setBackground(new Color(204, 255, 255));
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 490, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_2, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);

		JButton btnNewButton = new JButton("START");
		btnNewButton.setBackground(new Color(255, 255, 255));
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnNewButton, 143, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, btnNewButton, 10, SpringLayout.WEST, panel_2);
		panel_2.add(btnNewButton);

		JButton btnScore = new JButton("SCORE");
		btnScore.setBackground(new Color(255, 255, 255));
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnScore, 209, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, btnNewButton, -25, SpringLayout.NORTH, btnScore);
		sl_panel_2.putConstraint(SpringLayout.WEST, btnScore, 10, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnScore, -10, SpringLayout.EAST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, btnScore);
		panel_2.add(btnScore);

		JButton btnCredit = new JButton("CREDIT");
		btnCredit.setBackground(new Color(255, 255, 255));
		sl_panel_2.putConstraint(SpringLayout.SOUTH, btnScore, -148, SpringLayout.NORTH, btnCredit);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, btnCredit, -54, SpringLayout.SOUTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnCredit, 398, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, btnCredit, 0, SpringLayout.WEST, btnNewButton);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnCredit, -10, SpringLayout.EAST, panel_2);
		panel_2.add(btnCredit);

		JButton btnNewButton_1 = new JButton("BGM ON/OFF");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		sl_panel_2.putConstraint(SpringLayout.WEST, btnNewButton_1, 0, SpringLayout.WEST, btnNewButton);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, btnNewButton_1, -52, SpringLayout.NORTH, btnNewButton);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnNewButton_1, -10, SpringLayout.EAST, panel_2);
		panel_2.add(btnNewButton_1);
	}
}
