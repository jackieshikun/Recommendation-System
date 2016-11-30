package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBHelper implements DBconfig {
	public static Connection getConnection(){
		MysqlDataSource mds = new MysqlDataSource();
        mds.setDatabaseName(databaseName);
        mds.setUser(username);
        mds.setPassword(password);
        try {
            return mds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	public static boolean exists(String username){
		try{
			Connection connect = getConnection();
			//System.out.println("Success connect Mysql server!");
		    Statement stmt = connect.createStatement();
		    ResultSet rs = stmt.executeQuery("select * from user where username = '"+ username + "';");
		    if(rs.next()) {
		    	//System.out.println(rs.getString("username") + rs.getString("password"));
		    	return true;
		    }
		    return false;
	
		}catch(Exception e){
			System.out.print("get data error!");
		    e.printStackTrace();
		}
		return false;
	}
	public static User check(String username, String password){
		try{
			User user = new User();
			Connection connect = getConnection();
		    Statement stmt = connect.createStatement();
		    ResultSet rs = stmt.executeQuery("select password from user where username = '"+ username + "';");
		    if(rs.next() && password.equals(rs.getString("password")) ){
		    	rs = stmt.executeQuery("select userID from user where username = '" + user.getUsername() + "';" );
		    	if(rs.next()){
		    		user.setID(rs.getInt("userID"));
		    		user.setUsername(username);
			    	user.setPassword(password);
			    	return user;
		    	}
		    	
		    }
	
		}catch(Exception e){
			System.out.print("get data error!");
		    e.printStackTrace();
		}
		return null;
	}
	public static void save(User user){
		try{
			Connection connect = getConnection();
		    Statement stmt = connect.createStatement();
		    stmt.executeUpdate("insert into user(username,password) values('"+user.getUsername()+"','" +user.getPassword() + "') ;");
		    ResultSet rs = stmt.executeQuery("select userID from user where username = '" + user.getUsername() + "';" );
		    if(rs.next())
		    	user.setID(rs.getInt("userID"));
		}catch(Exception e){
			System.out.print("get data error!");
		    e.printStackTrace();
		}
	}
	public static void saveRatingHistory(String username, HashMap<String,Byte> ratingHistory){
		try{
			Connection connect = getConnection();
		    Statement stmt = connect.createStatement();
		    for(String ISBN: ratingHistory.keySet()){
		    	//stmt.executeQuery("select rating from ");
		    }
		    //stmt.executeUpdate("insert into user(username,password) values('"+user.getUsername()+"','" +user.getPassword() + "') ;");
		    //ResultSet rs = stmt.executeQuery("select userID from user where username = '" + user.getUsername() + "';" );
		}catch(Exception e){
			System.out.print("get data error!");
		    e.printStackTrace();
		}
	}
}
