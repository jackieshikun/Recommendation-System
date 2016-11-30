package Vector;

import java.util.HashMap;

public class UserProfileVector {
	HashMap<String, Integer> countAuthor;
	int count;
	int userID;
	public UserProfileVector(int id){
		countAuthor = new HashMap<String, Integer>();
		userID = id;
		count = 0;
	}
	public void setProfile(String author){
		if(countAuthor.containsKey(author) == false)
			countAuthor.put(author, 0);
		countAuthor.put(author, countAuthor.get(author)+1);
		count++;
	}
}
