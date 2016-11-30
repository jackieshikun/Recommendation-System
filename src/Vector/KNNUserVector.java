package Vector;


public class KNNUserVector {
	private UserVector compareUser;
	private double similarity;
	
	public KNNUserVector(UserVector uv, double simi){
		compareUser = uv;
		similarity = simi;
	}
	
	public UserVector getCompareUser(){
		return compareUser;
	}
	public double getSimilarity(){
		return similarity;
	}
}
