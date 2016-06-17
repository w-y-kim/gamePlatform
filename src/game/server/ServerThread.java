package game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import game.vo.RoomInfo;
import game.dao.Database;
import game.errors.DuplicateIDException;
import game.errors.RecordNotFoundException;
import game.vo.ClientMessage;
import game.vo.Data;
import game.vo.Friend;
import game.vo.User;
import game.vo.RoomInfo;



public class ServerThread implements Runnable {

   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   static ArrayList<User> connectedUserList = new ArrayList<>(); //서버에 접속된 클라이언트, 각 클라이언트의 ObjectOutputStream이 저정되어 있음
   static HashMap<String, RoomInfo> gameRoomList = new HashMap<>();
   private Data data;
   private User loginUser;
   private RoomInfo ri;
   private String roomTitle;
   private Socket socket;
   private boolean exit; // exit이 true 면 나가기
   private Database db = new Database(); 
   private ArrayList<Friend> friendList;
   
   public ServerThread(Socket socket) {
      super();
      this.socket = socket;
      exit = false;
      try {
         ois = new ObjectInputStream(socket.getInputStream());
         oos = new ObjectOutputStream(socket.getOutputStream());
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

/**
 * Data Command 별 분기처리 
 * broadCasting 메소드 만들기
 */
   @Override
   public void run() {
      System.out.println("서버스레드 접속");
      while(!exit){
      try {
         data = (Data)ois.readObject();
         switch(data.getCommand()){
         case Data.CONNECTION: //전체 접속자 정보 보냄
            data.setUserList(connectedUserList);
            data.setAllUserIds(db.getAllUserId());
            oos.writeObject(data); //자신한테만 보내는 것이라서 broadcasting 안씀
            break;
         case Data.SIGNUP:
            /* 유저 정보 받아서 DB에 저장 
             * 가입 허가 넘겨주기
             */
            boolean addrs =false;
            loginUser = data.getUser();
            addrs = db.addUser(loginUser); //database에서 등록됐으면 true, 안됐으면 false 넘김
            data.setUserAddrs(addrs);
            oos.writeObject(data);
            
            break;
         case Data.LOGIN:
            User user = data.getUser();
            user = db.loginCheck(user); // pw와 일치하면 완전한 유저 정보가 담김, pw일치 안하면 user null
            if(user!= null){
               friendList = db.getFriendList(user.getId());
               data.setFriendList(friendList);
               data.setUser(user);
               data.setUserList(connectedUserList);
            }else{}
            
            oos.writeObject(data);
            break;
         case Data.ADDFRIEND:
        	Friend fr = data.getFriend();
        	boolean faddrs =db.addFriend(fr); //friend add result
        	 break;
         case Data.SEND_NOTE:
        	 /*- 받은 message db에 저장
			  *- 특정 아이디의 oos에만 보내는 메소드 만들기 
			  * 1. 받는 user가 로그아웃 중일 때는 db에만 저장
			  * 2. 로그인 중일 때는 ClientMessage의 id의 oos로 보내기
        	  */
        	 ClientMessage cm = data.getClientMessage();
        	 db.saveMessage(cm);
        	 String rcid = cm.getId(); //받는 유저 아이디
        	boolean ss = false;
        	User rcUser= null; 
        	for (int i = 0; i < connectedUserList.size(); i++) {
				if(connectedUserList.get(i).equals(rcid)){
					ss = true;
					rcUser = connectedUserList.get(i);
				}
			}
        	 if(ss){ //그인 중일 때는 ClientMessage의 id의 oos로 보내기
        		 sendMemo(rcUser);
        	 } 
        	 //ss가 false면 안보냄.
        	 
        	 break;
         case Data.CHAT_MESSAGE:
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
        
         
         case Data.EXIT://로그 아웃 currentUserList에서 빼고 갱신된 유저리스트 브로드캐스팅 해주기.
        	 String id = data.getUser().getId();
        	 for (int i = 0; i < connectedUserList.size(); i++) {
        		 if(connectedUserList.get(i).getId().equals(id)){
        			 connectedUserList.remove(i);
        		 }
			}
        	 data.setUserList(connectedUserList);
        	 broadCasting(data);
            break;
         default:
            break;
            
      }//switch
         
      } catch (ClassNotFoundException | IOException e) {
         e.printStackTrace();
         exit = true;
      }//catch
 catch (DuplicateIDException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (RecordNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      }//while
   }//run
   public void sendMemo(User rcUser) throws IOException{ //받는 사람 user id
	   // 로그인한 유저에게 쪽지 보냄
	   rcUser.getOos().writeObject(data);
   }
   
   public void sendDataRoommate(Data data){
      
   }
   
   public void broadCasting(Object obj) throws IOException{
      for(User userr : connectedUserList){
         System.out.println("broadcasting");
         userr.getOos().writeObject(obj);
         userr.getOos().reset();
      }
   }

}