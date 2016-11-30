package Vector;
import java.util.Comparator;


public class KNNCompare implements Comparator<KNNUserVector>{
	@Override
	public int compare(KNNUserVector user1, KNNUserVector user2){
		if(user1.getSimilarity() > user2.getSimilarity())
			return 1;
		else if(user1.getSimilarity() == user2.getSimilarity())
			return 0;
		else
			return -1;
	}
}
