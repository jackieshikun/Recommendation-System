package Vector;
import java.util.HashMap;
import java.util.PriorityQueue;

public class UserVector {
	private double avgScore = 0;
	private int userID = 0;
	private long totalScore = 0;
	private HashMap<String,Byte> ratedMovie;
	public UserVector(int UserID){
		userID = UserID;
		ratedMovie = new HashMap<String,Byte>();
		
	}
	public HashMap<String, Byte> getRatingRecord(){
		return ratedMovie;
	}
	public void rateBook(String bookID,Byte score){
		if(ratedMovie.containsKey(bookID))
			totalScore = totalScore - ratedMovie.get(bookID)-score;
		else{
			totalScore += score;
			
		}
		ratedMovie.put(bookID, score);
		computeAvgScore();
	}
	private void computeAvgScore(){
		avgScore = (double)totalScore / ratedMovie.size();
	}
	public double getAvgScore(){
		return avgScore;
	}
	public int getUserID(){
		return userID;
	}
	
}
