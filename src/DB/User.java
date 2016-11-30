package DB;

public class User {
	private int userID;
	private String username;
	private String password;
	public int getID(){
		return userID;
	}
	public void setID(int id){
		userID = id;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String name){
		username = name;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String pw){
		password = pw;
	}
}
