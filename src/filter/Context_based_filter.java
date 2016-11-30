package filter;

import java.util.HashMap;
import java.util.PriorityQueue;

import Vector.BookVector;
import Vector.KNNBookCompare;
import Vector.KNNBookVector;
import Vector.UserVector;

public class Context_based_filter {
	private HashMap<Integer,UserVector> userMap;
	private HashMap<String,BookVector> bookMap;
	public Context_based_filter(HashMap<Integer, UserVector> users, HashMap<String, BookVector> books){
		userMap = users;
		bookMap = books;
	}
	public BookVector [] recommendBook(int userID,int amount){
		HashMap<String,Double> userProfile = processQuery(userID);
		return knnSearch(userProfile,amount);
	}
	private HashMap<String,Double> processQuery(int userID){
		HashMap<String, Byte> ratingRecord = userMap.get(userID).getRatingRecord();
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		HashMap<String, Double> userProfile = new HashMap<String, Double>();
		double avgScore = userMap.get(userID).getAvgScore();
		for(String key: ratingRecord.keySet()){
			if(bookMap.get(key) == null) continue;
			String authorName = bookMap.get(key).getAuthor();
			double curRate = ratingRecord.get(key) - avgScore;
			if(count.containsKey(authorName) == false){
				count.put(authorName, 0);
				userProfile.put(authorName, curRate);
			}
			int num = count.get(authorName);
			userProfile.put(authorName, (userProfile.get(authorName) * num + curRate)/(num + 1) );
			count.put(authorName, num+1);
		}
		return userProfile;
		
	}
	private double calSimilarity(HashMap<String, Double> userProfile, BookVector book){
		String author = book.getAuthor();
		Double weight = userProfile.get(author);
		if(weight == null)
			return 0;
		return weight;
	}
	private BookVector[] knnSearch(HashMap<String, Double> userProfile, int amount){
		PriorityQueue<KNNBookVector> pq = new PriorityQueue<KNNBookVector>(amount,new KNNBookCompare());
		BookVector [] books = new BookVector[amount];
		for(BookVector book: bookMap.values()){
			pq.add(new KNNBookVector(calSimilarity(userProfile,book),book));
			if(pq.size() > amount)
				pq.poll();
		}
		for(int i = 0; i < books.length;i++){
			KNNBookVector rBook = pq.poll();
			books[i] = rBook.getBookVector();
			System.out.println("Rating: " + rBook.getRating() + "  title:" + books[i].getTitle() + "author: " + books[i].getAuthor());
		}
		
		return books;
	}
	
	
	
}
