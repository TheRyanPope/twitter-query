package twitter;

public class Tweet {

	String username;
	String tweet;
	String dob; // date and time tweet was posted
	
	public Tweet(String username, String tweet, String dob){
		this.username = username;
		this.tweet = tweet;
		this.dob = dob;
	}
	
	public void clear(){
		username = "";
		tweet = "";
		dob = "";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}