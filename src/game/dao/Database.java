package game.dao;

import java.beans.Statement;
import java.nio.channels.ClosedByInterruptException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import game.vo.User;
import game.vo.ClientMessage;
import game.vo.Friend;
import game.errors.DuplicateIDException;
import game.errors.RecordNotFoundException;

public class Database {
   
   /**
    * gameUser Table에서 id 존재 유무 확인 
    *  
    * @param id
    * @return
    * @throws SQLException
    */
   public boolean userExist(String id)throws SQLException{
      boolean exist = false;
      Connection con = ConnectionManager.getConnection();
      
      String sql = "Select * from gameUser where id = ? " ;
      try{
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();
      if(rs.next()){
         exist = true;
         String id1 = rs.getString(1);
         System.out.println(id1+"님의 정보가 등록되어 있습니다.");
      }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      return exist;
   }
   /**
    * 1. login시 사용, 
    * @param id
    * @return
    * @throws SQLException
    */
   public User loginCheck(User us)throws SQLException{
      boolean exist = false;
      User user = null;
      Connection con = ConnectionManager.getConnection();
      
      String sql = "Select * from gameUser where id = ? " ;
      try{
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, us.getId());
      ResultSet rs = pstmt.executeQuery();
      if(rs.next()){
         exist = true;
         String id1 = rs.getString(1);
         String pw = rs.getString(2);
         String em = rs.getString(3);
         int pfimg = rs.getInt(4);
         
         if(pw.equals(us.getPw())){
            user = new User(id1, pw, em, pfimg);
            System.out.println(id1+"님의 정보를 담아서 보냄.");}
         else {
            System.out.println("id와 패스워드 일치하지 않음.");
            user = null;
         }
      }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      return user;
   }
   
   
   /**
    * id 검사후 있으면 유저 정보 가져오기 
    * @param id
    * @return
    * @throws SQLException
    */
   public User getUser(String id)throws SQLException{
      if(!userExist(id)){ System.out.println("다른 아이디를 입력해 주세요."); return null;}
      User cl = null ;
      Connection con = ConnectionManager.getConnection();
      String sql ="SELECT * FROM gameUser where id =?";
      try{
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();
      if(rs.next()){
         String idd = rs.getString(1);
         String pw = rs.getString(2);
         String em = rs.getString(3);
         int img = rs.getInt(4);
         cl = new User(idd, pw, em, img);
      }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      
      return cl;
      
   }
   
   
   public ArrayList<String> getAllUserId(){
      ArrayList<String> userIds = new ArrayList<>();
      Connection con = ConnectionManager.getConnection();
      String sql ="SELECT id FROM gameUser";
      try{
      PreparedStatement pstmt = con.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
         String idd = rs.getString(1);
         userIds.add(idd);
      }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      return userIds;
      
   }
   /**User 객체 받아와서 DB에 등록 
    * 결과 consol로 띄우기 
    * 
    * @param us
    * @throws SQLException 
    */
   public boolean addUser(User us) throws DuplicateIDException, SQLException{
   
   if(userExist(us.getId())){throw new DuplicateIDException();}
   boolean addrs =false;
   Connection con = ConnectionManager.getConnection();
   String sql  = "INSERT INTO gameUser VALUES(?,?,?,?)";
   try{
      PreparedStatement pt = con.prepareStatement(sql);
      pt.setString(1, us.getId());
      pt.setString(2, us.getPw());
      pt.setString(3, us.getEm());
      pt.setInt(4, us.getPfimg());
      int row = pt.executeUpdate();
      if(row>0){
         addrs = true;
      System.out.println(row + "명 고객정보 입력됨.");
      }
   }catch(SQLException e){e.printStackTrace();}
   finally{
      ConnectionManager.close(con);
   }
   return addrs;
   
   }
   
   /**
    * 모든 유저의 정보를 가져와서 while문을 돌리고 list에 담아서 리턴
    * @return ArrayList<Client> cusList
    */
   public ArrayList<User> getAllUser(){
      ArrayList<User> cusList = new ArrayList<>();
      Connection con = ConnectionManager.getConnection();
      String sql = "SELECT * FROM gameUser ";
      User cl = null;
      try{
         PreparedStatement pt = con.prepareStatement(sql);
         ResultSet rs = pt.executeQuery();
         
         while(rs.next()){
            String id = rs.getString(1);
            String pw = rs.getString(2);
            String em = rs.getString(3);
            int img = rs.getInt(4);
            cl = new User(id, pw, em, img);
            cusList.add(cl);
         }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{}
      return cusList;
   }
   /**
    * user가 받은 message 저장 
    * 
    * @param cm
    * @throws SQLException 
    */
   public boolean saveMessage(ClientMessage cm ) throws RecordNotFoundException, SQLException{
      if(!userExist(cm.getId())||!userExist(cm.getSid())){throw new RecordNotFoundException();}
      boolean mok = false; //message insert ok
      Connection con = ConnectionManager.getConnection();
      String sql = "INSERT INTO messages VALUES(? ,? ,?)";
      try{
      PreparedStatement pt = con.prepareStatement(sql);
      pt.setString(1, cm.getId());
      pt.setString(2, cm.getSid());
      pt.setString(3, cm.getCt());
      int row = pt.executeUpdate();
      if(row >0){
         mok = true;
         System.out.println(row);
      }
      
      
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      return mok;
   }
   
   public ArrayList getAllMessages(String id){
   ArrayList<ClientMessage> cml = new ArrayList<>();
   Connection con = ConnectionManager.getConnection();
   String sql = "SELECT * FROM MESSAGES WHERE id = ?";
   ClientMessage cm = null;
   try{
      PreparedStatement pt = con.prepareStatement(sql);
      pt.setString(1, id);
      
      ResultSet rs = pt.executeQuery();
      while(rs.next()){
         String uid = rs.getString(1);
         String sid = rs.getString(2);
         String cnt = rs.getString(3);
         cm = new ClientMessage(uid, sid, cnt);
         cml.add(cm);
         System.out.println(cm.toString());
         System.out.println(cml.toString());
      }
   }catch(SQLException e){
      e.printStackTrace();
   }finally{
      ConnectionManager.close(con);
   }
      return cml;
   }
   
   /**
    * Friend Table에 유저 id와 친구 id를 같이 등록한다
    * @param fr
    */
   public void addFriend(Friend fr){
      Connection con = ConnectionManager.getConnection();
      String sql = "INSERT INTO friends VALUES(?,?)";
      try{
         PreparedStatement pt = con.prepareStatement(sql);
         pt.setString(1, fr.getId());
         pt.setString(2, fr.getFid());
         int row = pt.executeUpdate();
         if(row>0){
            System.out.println(row );
         }
      }catch(SQLException e){e.printStackTrace();}finally{
         ConnectionManager.close(con);
      }
   }
   
   /**
    * 내 id를 입력하면 내가 등록한 모든 친구의 아이디를 리스트에 저장해 리턴한다.
    * @param id
    * @return
    */
   public ArrayList<Friend> getFriendList (String id){
      ArrayList<Friend> flist = new ArrayList<>();
      Connection con = ConnectionManager.getConnection();
      String sql = "SELECT * FROM friends where id = ?";
      Friend fr = null;
      try{
         PreparedStatement pt = con.prepareStatement(sql);
         pt.setString(1, id);
         ResultSet rs = pt.executeQuery();
         while(rs.next()){
            String uid = rs.getString(1);
            String fid = rs.getString(2);
            fr = new Friend(uid, fid);
            flist.add(fr);
            
         }
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
         ConnectionManager.close(con);
      }
      return flist;
      
   }
}