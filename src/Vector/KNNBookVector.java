package Vector;


public class KNNBookVector {
	private double rating;
	private BookVector bv;
	public KNNBookVector(double r, BookVector p){
		rating = r;
		bv = p;
	}
	public double getRating(){
		return rating;
	}
	public BookVector getBookVector(){
		return bv;
	}
}
