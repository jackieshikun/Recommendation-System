package Vector;
import java.util.Comparator;


public class KNNBookCompare implements Comparator<KNNBookVector> {
	@Override
	public int compare(KNNBookVector book1, KNNBookVector book2){
		if(book1.getRating() < book2.getRating())
			return -1;
		else if(book1.getRating() == book2.getRating())
			return 0;
		else
			return 1;
	}
}
