import java.util.Scanner;

import DB.DBHelper;
import DB.User;


public final class UserOperation {
	public static User login(){
		String username,password;
		Scanner scanner = new Scanner(System.in);
		System.out.print("username:");
		username = scanner.nextLine();
		System.out.print("password");
		password = scanner.nextLine();
		if(DBHelper.exists(username)){
			User user = DBHelper.check(username, password);
			if(user != null){
				System.out.println("login success");
				return user;
			}else{
				System.out.println("login password incorrect");
			}
		}else
			System.out.println("username isn't exist");
		
		return null;
	}
	public static User signup(){
		String username,password;
		Scanner scanner = new Scanner(System.in);
		System.out.print("username:");
		username = scanner.nextLine();
		System.out.print("password");
		password = scanner.nextLine();
		if(DBHelper.exists(username)){
			System.out.println("Username exists");
		}else{
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			DBHelper.save(user);
			System.out.println("signUp success");
			return user;
		}
		return null;
		
	}
}
