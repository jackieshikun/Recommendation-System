import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

import Vector.BookVector;
import DB.DBHelper;
import DB.ImportData;
import DB.User;
import filter.Collaborative_filter;
import filter.Context_based_filter;
public class Recommender {
	public static final int MAX_RECOMMEND_AMOUNT = 10;
	
	public static void main(String[] args){
		/*
		User user = null;
		while(user == null){
			System.out.println("1:login,2:signup,else exit");
			Scanner scanner = new Scanner(System.in);
			int num = scanner.nextInt();
			switch(num){
				case 1:user = UserOperation.login(); break;
				case 2:user = UserOperation.signup(); break;
				default: System.exit(0);
			}
		}
		*/
		//UserOperation.signup();
		ImportData data = new ImportData();
		System.out.println("loading system success");
		int userID = 246798;
		BookVector [] books;
		Collaborative_filter filter = new Collaborative_filter(data.getUserMap(),data.getBookMap());
		books = filter.recommendBook(userID, MAX_RECOMMEND_AMOUNT);
		System.out.println("Collaborative filtering");
		for(BookVector book:books){
			System.out.println("Title: " + book.getTitle() + "   Author:" + book.getAuthor());
		}
		System.out.println("Context_based_filter");
		Context_based_filter filter2 = new Context_based_filter(data.getUserMap(),data.getBookMap());
		books = filter2.recommendBook(userID,MAX_RECOMMEND_AMOUNT);
		for(BookVector book:books){
			System.out.println("Title: " + book.getTitle() + "   Author:" + book.getAuthor());
		}
		//System.out.println("True?:" + DBHelper.exists("123"));
		/*
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading mysql driver");
		}
		catch(Exception e){
			System.out.println("Error loading mysql driver!");
			e.printStackTrace();
		}
		try{
			Connection connect = DriverManager.getConnection(
			          "jdbc:mysql://localhost:3306/test","root","While(1)");
			System.out.println("Success connect Mysql server!");
		    Statement stmt = connect.createStatement();
		    ResultSet rs = stmt.executeQuery("select * from user");
		    while (rs.next()) {
		    	System.out.println(rs.getString("name"));
		    }
		}catch(Exception e){
			System.out.print("get data error!");
		    e.printStackTrace();
		}
		*/
	}
}
