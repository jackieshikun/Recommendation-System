package DB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Vector.BookVector;
import Vector.UserVector;


public final class ImportData {
	private HashMap<Integer,UserVector> userMap;
	private HashMap<String,BookVector> bookMap;
	private static final String NUM_PATTERN = "^\\d*$";
	private static final String STRING_PATTERN = "^[^']*$";
	public ImportData(){
		userMap = new HashMap<Integer, UserVector>();
		bookMap = new HashMap<String, BookVector>();
		//insertUser2DB();
		insertBook2DB();
		//constructUserVector();
		//constructBookVector();
		
	}
	public HashMap<Integer, UserVector> getUserMap(){
		return userMap;
	}
	public HashMap<String, BookVector> getBookMap(){
		return bookMap;
	}
	private void constructUserVector(){
		File file = new File("BX-Book-Ratings.csv");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			reader.readLine();
			int userID;
			String bookID;
			Byte score;
			String line;
			while((line = reader.readLine()) != null){
				String[] columns = line.split(";");
				if(columns.length >= 2){
					/*
					Pattern is_num  = Pattern.compile(NUM_PATTERN);
					Pattern is_string = Pattern.compile(STRING_PATTERN);
					Matcher num_match1 = is_num.matcher(columns[0]);
					Matcher num_match2 = is_num.matcher(columns[2]);
					Matcher string_match = is_string.matcher(columns[1]);
					*/
					userID = Integer.parseInt(columns[0].substring(1, columns[0].length()-1));
					bookID = columns[1].substring(1,columns[1].length()-1);
					score = Byte.decode(columns[2].substring(1, columns[2].length()-1));
					if(userMap.containsKey(userID)){
						userMap.get(userID).rateBook(bookID,score);
					}else{
						UserVector newUser = new UserVector(userID);
						newUser.rateBook(bookID, score);
						userMap.put(userID, newUser);
					}
					//System.out.println(columns[0] + "," + columns[1] + "," + columns[2]);
					
				
				}
			}
			//System.out.println("read file, row = " +i);
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	private void constructBookVector(){
		File file = new File("BX-Books.csv");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try {
			reader.readLine();
			String line = null;
			String ISBN;
			String title;
			String author;
			while((line = reader.readLine()) != null){
				String [] columns = line.split(";");
				if(columns.length >= 2){
					ISBN = columns[0].substring(1,columns[0].length()-1);
					if(columns[1].length() > 2)
						title = columns[1].substring(1,columns[1].length()-1);
					else
						title = null;
					if(columns[2].length() > 2){
						author = columns[2].substring(1,columns[2].length()-1);
					}else
						author = null;
					BookVector book = new BookVector(ISBN,title,author);
					bookMap.put(ISBN, book);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void insertUser2DB(){
		File file = new File("users.csv");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try {
			Connection connect = DBHelper.getConnection();
			reader.readLine();
			Pattern is_num  = Pattern.compile(NUM_PATTERN);
			String line = null;
			String username = "whatever";
			String password = "12ij4eoijfw90r";
			int id;
			int age;
			int genre;
			
			while((line = reader.readLine()) != null){
				String [] columns = line.split(";");
				if(columns.length >= 4){
					Matcher num_match1 = is_num.matcher(columns[0]);
					Matcher num_match2 = is_num.matcher(columns[2]);
					Matcher num_match3 = is_num.matcher(columns[3]);
					if(num_match1.find() && num_match2.find() && num_match3.find()){
						id = Integer.parseInt(columns[0]);
						age = Integer.parseInt(columns[2]);
						genre = Integer.parseInt(columns[3]);
						Statement stmt = connect.createStatement();
						//System.out.println("insert into user(userID,username,password,age,genre) values("+id + ",'"+username+"','" + password + "'," + age+"," +genre + ") ;");
					    stmt.executeUpdate("insert into user(userID,username,password,age,genre) values("+id + ",'"+username+"','" + password + "'," + age+"," +genre + ") ;");
					}
					
				}
			}
			
			reader.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void insertGenre2DB(){
		
	}
	private void insertBook2DB(){
		File file = new File("books.csv");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try {
			Connection connect = DBHelper.getConnection();
			reader.readLine();
			Pattern is_num  = Pattern.compile(NUM_PATTERN);
			Pattern is_string = Pattern.compile(STRING_PATTERN);
			String line = null;
			String ISBN,bookTitle,author,publisher;
			int year,genre;
			int i =0;
			while((line = reader.readLine()) != null){
				String [] columns = line.split(";");
				System.out.println("i:"+ i);
				i++;
				if(columns.length >= 6){
					Matcher num_match1 = is_num.matcher(columns[3]);
					Matcher num_match2 = is_num.matcher(columns[5]);
					/*
					Matcher string_match1 = is_string.matcher(columns[0]);
					Matcher string_match2 = is_string.matcher(columns[1].replace('\'', ' '));
					Matcher string_match3 = is_string.matcher(columns[2].replace('\'', ' '));
					Matcher string_match4 = is_string.matcher(columns[4]);
					*/
					if(num_match1.find() && num_match2.find()){
						ISBN = columns[0].substring(1,columns[0].length()-1);
						bookTitle = columns[1].substring(1,columns[1].length()-1).replace('\'', ' ');
						author = columns[2].substring(1,columns[2].length()-1).replace('\'', ' ');
						year = Integer.parseInt(columns[3]);
						publisher = columns[4].substring(1, columns[4].length()-1).replace('\'', ' ');
						genre = Integer.parseInt(columns[5]);
						Statement stmt = connect.createStatement();
						//System.out.println("insert into book(ISBN,bookTitle,author,year,publisher,genre) values('"+ISBN + "','"+bookTitle+"','" + author + "'," + year+",'"+publisher + "'," +genre + ") ;");
					    stmt.executeUpdate("insert into book(ISBN,bookTitle,author,year,publisher,genre) values('"+ISBN + "','"+bookTitle+"','" + author + "'," + year+",'"+publisher + "'," +genre + ") ;");
					}
					
				}
			}
			
			reader.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void insertRating2DB(){
		
	}
}
