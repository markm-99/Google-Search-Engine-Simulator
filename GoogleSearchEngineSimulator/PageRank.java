package searchengine;

/**
 * A PageRank that ranks webpage based on a few factors
 * @author Mark Mathew
 *
 */
public class PageRank {

	// The frequency and location of keywords within the web page
	public int frequency;
	
	// How long the web page has existed
	public int age;

	// The number of other web pages that link to the page 
	public int otherWebLinks;
	
	// How much the webpage owner has paid to Google for advertisement purpose
	public int money;
	
	// The sum of frequency, age, and money
	public int totalScore;

	/**
	 * A constructor that takes 4 scores as parameters
	 * @param frequency
	 * @param age
	 * @param numOfLinks
	 * @param money
	 */
	public PageRank(int frequency, int age, int otherWebLinks, int money)
	{
		this.frequency = frequency;
		this.age = age;
		this.otherWebLinks = otherWebLinks;
		this.money = money;
		getTotalScore();
	}
	
	/**
	 * A no-arg constructor that uses random number generator (1 to 100) for 4 scores
	 */
	public PageRank()
	{
		this.frequency = (int)((Math.random() * 100) + 1);
		this.age = (int)((Math.random() * 100) + 1);
		this.otherWebLinks = (int)((Math.random() * 100) + 1);
		this.money = (int)((Math.random() * 100) + 1);
		getTotalScore();
	}
	
	/**
	 * Returns the total score of all four factors 
	 * @return the total score of all four factors 

	 */
	public int getTotalScore()
	{
		return totalScore = frequency + age + otherWebLinks + money;
	}
}
