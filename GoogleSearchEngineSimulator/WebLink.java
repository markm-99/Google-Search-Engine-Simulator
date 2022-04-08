package searchengine;

/**
 * Builds a WebLink item
 * @author Mark Mathew
 *
 */
public class WebLink implements Comparable {
	private String url;
	private PageRank pRank;
	
	/**
	 * Constructs a WebLink item with url and rank
	 * @param url the URL of the website
	 * @param pRank the PageRank score of the website
	 */
	public WebLink(String url, PageRank pRank) 
	{
		this.url = url;
		this.pRank = pRank;
	}
	
	/**
	 * Gets the url of the website
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}
	
	/**
	 * Gets the rank of the website
	 * @return the rank
	 */
	public PageRank getRank()
	{
		return pRank;
	}
	
	/**
	 * Gets a PageRank's Frequency score of the website
	 * @return PageRank's Frequency score
	 */
	public int getFrequency()
	{
		return pRank.frequency;
	}
	
	/**
	 * Gets a PageRank's Age score of the website
	 * @return PageRank's Age score
	 */
	public int getAge()
	{
		return pRank.age;
	}
	
	/**
	 * Gets a PageRank's Number of other web pages that link to the page score of the website
	 * @return PageRank's Number of other web pages that link to the page score score
	 */
	public int getOtherWebLinks()
	{
		return pRank.otherWebLinks;
	}
	
	/**
	 * Gets a PageRank's Money score of the website
	 * @return PageRank's Money score
	 */
	public int getMoney()
	{
		return pRank.money;
	}
	
	/**
	 * Gets a PageRank's Total score of the website
	 * @return PageRank's Total score
	 */
	public int getTotal()
	{
		return pRank.getTotalScore();
	}
	
	/**
	 * @Override
	 * Returns a string representation of the WebLink item
	 * @return a string representation of the WebLink item
	 */
	public String toString()
	{
		 return getUrl() + " , PageRank score: " + getTotal();		
	}
	
	/**
	 * @Override
	 * Checks if 2 WebLink items have the same total PageRank score and URL
	 * @return true if 2 items have the same total PageRank score and URL, false otherwise
	 */
	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
		{
			return false;
		}
		if (this.getClass() != otherObject.getClass()) 
		{
			return false;
		}
		
		WebLink other = (WebLink)otherObject;
		return (this.getTotal() == other.getTotal() && this.url.equals(other.url));
	}
	
	/**
	 * @Override
	 * Compares two WebLink objects by the PageRank total score first
	 * If PageRank total scores are equal, compare the URL
	 * @return the difference of the 2 items
	 */
	public int compareTo(Object otherObject)
	{
		WebLink other = (WebLink) otherObject;
		if(this.getTotal() == other.getTotal())
    	{
    	          return this.url.compareTo(other.url);//compare String
    	}
    	else
    	{
    	         return Double.compare(this.getTotal(), other.getTotal());//compare num
    	}
	}
	
	/**
	 * @Override
	 * Generates the hash code for a WebLink items
	 * @return the hash code 
	 */
	public int hashCode() 
	{
		return url.length() + pRank.getTotalScore();
	}
}
