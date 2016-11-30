package Vector;

public class BookVector {
	private String ISBN;
	private String bookTitle;
	private String bookAuthor;
	private int publishYear;
	private String publisher;
	public BookVector(String BookID,String title,String author){
		ISBN = BookID;
		bookTitle = title;
		bookAuthor = author;
	}
	public String getTitle(){
		return bookTitle;
	}
	public String getAuthor(){
		return bookAuthor;
	}
}
