package filter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import Vector.BookVector;
import Vector.KNNBookCompare;
import Vector.KNNBookVector;
import Vector.KNNCompare;
import Vector.KNNUserVector;
import Vector.UserVector;
public class Collaborative_filter {
	private static final String NUM_PATTERN = "^\\d*$";
	private static final String STRING_PATTERN = "^[0-9a-zA-Z]*$";
	private HashMap<Integer,UserVector> userMap;
	private HashMap<String,BookVector> bookMap;
	public Collaborative_filter(HashMap<Integer,UserVector> umap, HashMap<String,BookVector> bmap){
		userMap = umap;
		bookMap = bmap;
		
	}
	public BookVector[] recommendBook(int userID, int amount){
		PriorityQueue<KNNUserVector> users = findSimiUser(userID,amount);
		
		/*
		for(KNNUserVector user: users){
			System.out.println("Similarity: " + user.getSimilarity() + "   UserID: " + user.getCompareUser().getUserID());
			verify(userID, user.getCompareUser().getUserID());
		}
		*/
		
		/*
		BookVector [] books = knnSearch(users,amount);
		for(BookVector book : books)
			if(book != null)
				System.out.println("title: " + book.getTitle() + " author: " + book.getAuthor());
			//System.out.println("userID:" + user.getCompareUser().getUserID() + " simi: " + user.getSimilarity());
			 * 
		*/
		return knnSearch(users,amount);
		//PrintBook();
		//PrintAvgScore();
		//System.out.println("user size:" + userMap.size() + "BookVector:" + bookMap.size());
	}
	private void verify(int user1, int user2){
		System.out.println("*******Start Verifying*********");
		UserVector uv1 = userMap.get(user1);
		UserVector uv2 = userMap.get(user2);
		HashSet<String> allBook = new HashSet<String>();
		allBook.addAll(uv1.getRatingRecord().keySet());
		allBook.retainAll(uv2.getRatingRecord().keySet());
		
		for(String ISBN: allBook){
			if(uv1.getRatingRecord().containsKey(ISBN) && uv2.getRatingRecord().containsKey(ISBN)){
				System.out.println("ISBN:" + ISBN + "User1 rating: " + uv1.getRatingRecord().get(ISBN) + "    User2 rating: " + uv2.getRatingRecord().get(ISBN));
			}
		}
		System.out.println("********End Verifying************");
	}
	private void PrintAvgScore(){
		for(int id: userMap.keySet()){
			System.out.println("userID: " + id + ",avgScore: " + userMap.get(id).getAvgScore());
		}
	}
	private void PrintBook(){
		for(String ISBN: bookMap.keySet()){
			BookVector book = bookMap.get(ISBN);
			System.out.println("ISBN:" + ISBN +" bookTitle:" + book.getTitle() + "  author:" + book.getAuthor());
		}
		System.out.println("BookNum = " + bookMap.size());
	}
	
	private double CosineSimilarity(UserVector curUser, UserVector target){
		HashMap<String, Byte> curRatingRecord = curUser.getRatingRecord();
		HashMap<String, Byte> targetRatingRecord = target.getRatingRecord();
		//normalize the vectors
		double avg1 = curUser.getAvgScore();
		double avg2 = target.getAvgScore();
		
		HashSet<String> allBook = new HashSet<String>();
		allBook.addAll(curRatingRecord.keySet());
		allBook.retainAll(targetRatingRecord.keySet());
		
		double simi = 0.0, m1 = 0.0, m2 = 0.0;
		double num1,num2;
		for(String ISBN: allBook){
			num1 = curRatingRecord.get(ISBN);
			num2 = targetRatingRecord.get(ISBN);
			simi += (num1 - avg1) * (num2 - avg2);
		}
		for(int score:curRatingRecord.values() )
			m1 += (score - avg1) * (score - avg1);
		for(int score: targetRatingRecord.values())
			m2 += (score-avg2) * (score-avg2);
		if(m1 == 0.0 || m2 == 0.0)
			return 0.0;
		return simi / ((Math.sqrt(m1)) * (Math.sqrt(m2)));
	}
	private PriorityQueue<KNNUserVector> findSimiUser(int userID, int amount){
		PriorityQueue<KNNUserVector> simiQueue = new PriorityQueue<KNNUserVector>(amount, 
				new KNNCompare());
		UserVector curUser = userMap.get(userID);
		for(int id: userMap.keySet()){
			if(id != userID){
				double simi = CosineSimilarity(curUser,userMap.get(id));
				simiQueue.add(new KNNUserVector(userMap.get(id),simi));
				if(simiQueue.size() > amount)
					simiQueue.poll();
			}
		}
		return simiQueue;
	}
	
	private BookVector [] knnSearch(PriorityQueue<KNNUserVector> pq,int amount){
		KNNUserVector [] users = new KNNUserVector [pq.size()];
		BookVector [] books = new BookVector[amount];
		HashSet<String> allBook = new HashSet<String>();
		PriorityQueue<KNNBookVector> bookPQ = new PriorityQueue<KNNBookVector>(amount, 
				new KNNBookCompare());
		int i = 0;
		for(KNNUserVector user: pq){
			users[i++] = user;
			allBook.addAll(user.getCompareUser().getRatingRecord().keySet());
		}
		for(String ISBN: allBook){
			double avg = 0.0;
			for(int j = 0; j < users.length;j++){
				Byte score = users[j].getCompareUser().getRatingRecord().get(ISBN);
				if(score == null)
					score = 0;
				avg += (score - users[j].getCompareUser().getAvgScore()) * users[j].getSimilarity();
			}
			avg/=users.length;
			bookPQ.add(new KNNBookVector(avg,bookMap.get(ISBN)));
			if(bookPQ.size() > amount)
				bookPQ.poll();
			
		}
		
		
		for(int j = 0; j < amount && bookPQ.isEmpty() == false;j++){
			System.out.println("simi: " + bookPQ.peek().getRating() + "  title:" + bookPQ.peek().getBookVector().getTitle());
			books[j] = bookPQ.poll().getBookVector();
		}
		
		return books;
	}
	
	
}
