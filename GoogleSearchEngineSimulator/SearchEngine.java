package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Builds a micro version  Google Search Engine Simulator
 * @author Mark Mathew 
 *
 */
public class SearchEngine {

	private WebCrawler wCrawler; 

	private PageRank pRank; 
	
	 // A list of websites with PageRank scores that user assigns
	private ArrayList<WebLink> userAssignedWebList; 
	
	 // A list of websites with PageRank scores are assigned by the random number generator
	private ArrayList<WebLink> randomAssignedWebList;
	
	// A list of the first 30 URL links after WebCrawler search()
	private ArrayList<String> webList30;
	
	// An array of PageRank scores of the first 30 URL links 
	private int[] pRankArr;
	
	private HeapSort sorter = new HeapSort();
	
	// A list of the sorted URL links
	private ArrayList<WebLink> sortedWebList;
	
	// An array represents a Heap
	private int[] heap = new int[30];
	
	// A list of websites stored in the Heap
	private ArrayList<WebLink> webHeap;
	
	/**
	 * No-arg constructor that initialize its instance variables.
	 */
	public SearchEngine()
	{
		userAssignedWebList = new ArrayList<>();
		randomAssignedWebList = new ArrayList<>();
		sortedWebList = new ArrayList<>();
		webHeap = new ArrayList<>();
	}
	
	/**
	 * Assigns PageRank scores to the website
	 * @param url The URL of the website
	 * @param frequency The PageRank frequency score
	 * @param age The PageRank age score
	 * @param numOfLinks The PageRank numOfLinks score
	 * @param money The PageRank money score
	 * @return a new WebLink with the website's URL and the PageRank scores
	 */
	public WebLink rankedWeb(String url, int frequency, int age, int numOfLinks, int money)
	{
		PageRank pr = new PageRank(frequency, age, numOfLinks, money);
		return new WebLink(url, pr);		
	}
	
	/**
	 * Reverses the order of the Array
	 * @param arr the array needs to be inverted
	 */
	public void reverseArr(int[] arr)
	{
		for(int i = 0; i < arr.length / 2; i++) 
		{
			// Swap from the first element with the last element and go on
			int temp = arr[i];
			arr[i] = arr[arr.length - 1 - i];
			arr[arr.length - 1 - i] = temp;
		}
	}
	
	/**
	 * Max Heap Priority Queue
	 * Stores the first sorted 20 out of 30 web url links into Heap
	 */
	public void createPriorityQueue()
	{
		for(int i = 0; i < 20; i++)
		{
			sorter.MaxHeapInsert(heap, sortedWebList.get(i).getTotal());
			webHeap.add(sortedWebList.get(i));
		}
	}
	
	/**
	 * Updates the webHeap ArrayList
	 * @return
	 */
	public ArrayList<WebLink> updateWebHeap()
	{
		// Creates a new ArrayList<WebLink> that is a copy of the webHeap 
		ArrayList<WebLink> tempList = new ArrayList<>(webHeap);
		
		// Creates a new array and
		// Copies all the elements (EXCEPT 0) in the Heap Array to the new array 
		int tempArrSize = 0;
		// counts how many non-0 elements in the Heap
		for(int i : heap)
		{
			if(i != 0)
			{
				tempArrSize++;
			}
		}

		int[] tempArr = new int[tempArrSize]; 
		for (int i = 0; i < tempArrSize; i++)
		{
			tempArr[i] = heap[i]; // copy all the non-0 elements in the Heap to tempArr
		}
		
		sorter.Heapsort(tempArr);
		// reverse the array
		reverseArr(tempArr);
		sorter.BuildMaxHeap(heap);  // reset the heap size
		
		// Create another ArrayList that add all the elements in tempList but in the tempArr's order
		ArrayList<WebLink> updatedHeap = new ArrayList<>();
		
		for(int i = 0 ; i < tempArr.length; i++)
		{
			for(int j = 0 ; j < tempList.size(); j++)
			{
				if(tempList.get(j).getTotal() == tempArr[i])
				{
					// check for duplicate
					if(!updatedHeap.contains(tempList.get(j)))
					{
						updatedHeap.add(tempList.get(j));
					}
				}
			}
		}

		return updatedHeap;
	}	
	
	/**
	 * Starts the SearchEngine tasks
	 */
	public void runSearchEngine()
	{
		// get input from user
		Scanner in = new Scanner(System.in);
		
		// when to stop the program
		boolean quit = false;
		
		System.out.println("**************************");
		System.out.println("Search Engine Simulator");
		System.out.println("**************************");

		/// gets a keyword from user
		System.out.println("Please enter a keyword you want to search?");
		String keyword = in.nextLine();
		System.out.println("keyword = "+ keyword);
		
		// initializes wCrawler with the keyword
		wCrawler = new WebCrawler(keyword);
		wCrawler.search(); // start searching

		// A list of the first 30 URL links
		webList30 = new ArrayList<>();
		int count = 1;
		
		// Add the first 30 URL links to webList30
		for(String s : wCrawler.getUrls())
		{
			if(count > 30)  // only print the first 30 links
			{
				break;
			}
			webList30.add(s);
			count++;
		}
		
		// lets user choose which tasks to perform
		// stops when users choose "Quit"
		while(!quit)
		{
			// List of tasks for users to choose
			System.out.println("\n****************************************************");
			System.out.print("What do you want to perform: "  
					+ "\n" + "Enter 'D' --- Display search results"
					+ "\n" + "Enter 'A' --- Assign 4 scores for a specific website"
					+ "\n" + "Enter 'R' --- Assign scores to 30 websites using a random number generator"
					+ "\n" + "Enter 'S' --- Sort the 30 PageRank scores"
					+ "\n" + "Enter 'V' --- View the 4 factor scores of each web url link"
					+ "\n" + "Enter 'P' --- Create a Priority Queue and store the first sorted 20 web url links into Heap"
					+ "\n" + "Enter 'I' --- Insert a new web url link into Heap"
					+ "\n" + "Enter 'F' --- View the first ranked web url link"
					+ "\n" + "Enter 'C' --- Choose one of the web url links stored in the heap Priority Queue and increase its PageRank score"
					+ "\n" + "Enter 'Q' --- Quit"
					+ "\n" + "Enter your option: "
					);
			
			String inputOption = in.nextLine().toUpperCase().substring(0);
			
			switch(inputOption)
			{	
				//Display search results
				case "D":
					System.out.println("\nHere are the first 30 URL links: ");
					for(int i = 0; i < webList30.size(); i++)
					{
						System.out.println(i+1 + ".  " + webList30.get(i));
					}
					break;
				
				// Assign 4 scores for a specific website
				case "A" :
					// Get input from user
					System.out.println("Enter a website URL you want to score:   ");
					String url = in.nextLine();
					
					System.out.print("Enter a score for Frequency (1-100):  ");
					int frequency = in.nextInt();
					// Check if the scores are valid
					while(!(frequency >= 1 && frequency <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter a score for Frequency (1-100):  ");
						frequency = in.nextInt();
					}
					
					System.out.print("Enter a score for Age (1-100):  ");
					int age = in.nextInt();
					// Check if the scores are valid
					while(!(age >= 1 && age <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter a score for Age (1-100):  ");
						age = in.nextInt();
					}
					
					System.out.print("Enter a score for Number of other web pages link to the page (1-100):  ");
					int numOfLinks = in.nextInt();
					// Check if the scores are valid
					while(!(numOfLinks >= 1 && numOfLinks <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter a score for Number of other web pages link to the pagey (1-100):  ");
						numOfLinks = in.nextInt();
					}
					
					System.out.print("Enter a score for Money (1-100):  ");
					int money = in.nextInt();
					// Check if the scores are valid
					while(!(money >= 1 && money <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter a score for Money (1-100):  ");
						money = in.nextInt();
					}	
					
					in.nextLine(); // skip one line
									
					WebLink w = rankedWeb(url,frequency, age, numOfLinks, money);
					System.out.println("\nHere are the scores you assigned for:");
					System.out.println("\n->  " + w);
					System.out.println("Frequecy        = " + w.getFrequency());
					System.out.println("Age             = " + w.getAge());
					System.out.println("Number of Links = " + w.getOtherWebLinks());
					System.out.println("Money           = " + w.getMoney());
					System.out.println("Total           = " + w.getTotal());

					
					// Add a WebLink contains the URL and the PageRank scores too webList
					userAssignedWebList.add(w) ;
	                break;
	             
	            // Assign scores to 30 websites using a random number generator
				case "R" :
					for(String s : webList30)
					{
						WebLink web = new WebLink(s,new PageRank());
						randomAssignedWebList.add(web);
					}
					for(int i = 0; i < randomAssignedWebList.size(); i++)
					{
						System.out.println(i+1 + "  " + randomAssignedWebList.get(i));
					}
					break;
				
				// Sort the 30 PageRank scores
				case "S" :
					// Checks if randomAssignedWebList is empty
					if(randomAssignedWebList.isEmpty())
					{
						System.out.println("You haven't assigned scores to the websites! Enter R to assign scores.");	
					}
					else
					{
						// An array of PageRank scores of the first 30 URL links 
						pRankArr = new int[30];
						for(int i = 0; i < 30; i++)
						{
							pRankArr[i] = randomAssignedWebList.get(i).getTotal();
						}
						sorter.Heapsort(pRankArr);

						// reverse the array
						reverseArr(pRankArr);

						System.out.println("\nHere are the sorted 30 URL links: ");
						
						// Add WebLink to sortedWebList list in an order of pRankArr (descending sorted order)
						for(int i = 0 ; i < pRankArr.length; i++)
						{
							for(int j = 0 ; j < randomAssignedWebList.size(); j++)
							{
								if(randomAssignedWebList.get(j).getTotal() == pRankArr[i])
								{
									// check for duplicate
									if(!sortedWebList.contains(randomAssignedWebList.get(j)))
									{
										sortedWebList.add(randomAssignedWebList.get(j));
									}
								}
							}
						}
						
						// print the sorted list
						for(int swl = 0; swl < sortedWebList.size(); swl++)
						{
							System.out.println(swl+1 + "  "+ sortedWebList.get(swl));
						}
					}
					break;
				
				// View the 4 factor scores of each web url link
				case "V" :
					// Checks if sortedWebList is empty
					if(sortedWebList.isEmpty())
					{
						System.out.println("You haven't sorted the websites! Enter 'S' to Sort.");	
					}
					else
					{
						for(int swl = 0; swl < sortedWebList.size(); swl++)
						{
							System.out.println(swl+1 + "  " + sortedWebList.get(swl) + " | Frequency score: " +sortedWebList.get(swl).getFrequency()
									+ " | Age score: " + sortedWebList.get(swl).getAge()
									+ " | Number of other web pages that link to the page score: " +sortedWebList.get(swl).getOtherWebLinks()
									+ " | Money score: " + sortedWebList.get(swl).getMoney());
						}
					}
					break;
					
				// Creates a Heap priority queue
				// Stores the first sorted 20 out of 30 web url links into Heap
				case "P" :
					// Checks if sortedWebList is empty
					if(sortedWebList.isEmpty())
					{
						System.out.println("You haven't sorted the websites! Enter 'S' to Sort.");	
					}
					else
					{
						createPriorityQueue();
						System.out.println("\nDone! The Priority Queue is created!");
						System.out.println("Here are the 20 websites in the Heap: ");
						for(int i = 0; i < webHeap.size(); i++)
						{
							System.out.println(i+1 + "  "+ webHeap.get(i));
						}
					}
					break;
				
				// Insert a new web url link into Heap
				case "I" :
					if(webHeap.isEmpty())
					{
						System.out.println("You haven't created a Heap priority queue! Enter 'P' to create.");
					}
					else
					{
						// Get input from user
						System.out.println("Enter a website URL you want to insert into the Heap:   ");
						String iURL = in.nextLine();
						System.out.print("Enter a score for Frequency (1-100):  ");
						int iFre = in.nextInt();
						System.out.print("Enter a score for Age (1-100):  ");
						int iAge = in.nextInt();
						System.out.print("Enter a score for Number of other web pages link to the page (1-100):  ");
						int iNumOfLinks = in.nextInt();
						System.out.print("Enter a score for Money (1-100):  ");
						int iMoney = in.nextInt();
						
						in.nextLine(); // skip one line
						
						WebLink iW = rankedWeb(iURL,iFre, iAge, iNumOfLinks, iMoney);

						sorter.MaxHeapInsert(heap, iW.getTotal());

						webHeap.add(iW); // add iW to the end of webHeap

						webHeap = updateWebHeap();
						System.out.println("\nHere is the WebList after inserting ");

						for(int i = 0 ; i < webHeap.size(); i++)
						{
							System.out.println(i+1 + "  " + webHeap.get(i));
						}				
					}
					break;
				
				// View the first ranked web url link
				case "F" :
					if(webHeap.isEmpty())
					{
						System.out.println("You haven't created a Heap priority queue! Enter 'P' to create.");
					}
					else
					{
						int removeIndex = Integer.MIN_VALUE;
						int extractMax = sorter.HeapExtractMax(heap);
						for(int i = 0; i < webHeap.size(); i++)
						{
							if(webHeap.get(i).getTotal() == extractMax)
							{
								System.out.println("\nHere is the First ranked web url link:");
								System.out.println(webHeap.get(i));
								removeIndex = i;
							}
						}
						//remove the first ranked web from the webHeap list
						webHeap.remove(removeIndex);
						
						System.out.println("\nHere is the WebHeap List after removing the First ranked website");
						for(int i = 0; i < webHeap.size(); i++)
						{
							System.out.println(webHeap.get(i));
						}				
					}
					break;
				
				// Choose one of the web url links stored in the heap Priority Queue and increase its PageRank score
				case "C" :
					// Get input from user
					// Get the information of the target website
					System.out.println("Enter a website URL you want to increase its PageRank score:   ");
					String cURL = in.nextLine();

					System.out.print("Enter its CURRENT total PageRank score:  ");
					int currentTotal = in.nextInt();

					//find the target index of the Heap
					int targetIndex = Integer.MIN_VALUE;
					boolean found = false;
					for(int i = 0; i < heap.length; i++)
					{
						if(heap[i] == currentTotal)
						{
							targetIndex = i;
							found = true;
						}
					}	
					
					// If there is no such element in the Heap, break!
					if(found == false)
					{
						System.out.println("There is no website with such URL or PageRank!");
						break;
					}
					
					// Get the NEW scores
					System.out.print("Enter its NEW score for Frequency (1-100):  ");
					int nFre = in.nextInt();
					// Check if the scores are valid
					while(!(nFre >= 1 && nFre <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter its NEW score for Frequency (1-100):  ");
						nFre = in.nextInt();
					}						
					
					System.out.print("Enter its NEW score for Age (1-100):  ");
					int nAge = in.nextInt();
					// Check if the scores are valid
					while(!(nAge >= 1 && nAge <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter its NEW score for Age (1-100):  ");
						nAge = in.nextInt();
					}	
					
					System.out.print("Enter its NEW score for Number of other web pages link to the page (1-100):  ");
					int nNumOfLinks = in.nextInt();
					// Check if the scores are valid
					while(!(nNumOfLinks >= 1 && nNumOfLinks <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter its NEW score for Number of other web pages link to the page (1-100):  ");
						nNumOfLinks = in.nextInt();
					}	
					
					System.out.print("Enter its NEW score for Money (1-100):  ");
					int nMoney = in.nextInt();
					// Check if the scores are valid
					while(!(nMoney >= 1 && nMoney <= 100))
					{
						System.out.println("Invalid input! Please enter an INTEGER from 1 to 100!");
						System.out.print("Enter its NEW score for Money (1-100):  ");
						nMoney = in.nextInt();
					}	
					
					in.nextLine(); // skip 1 line

					int newTotal = nFre + nAge + nNumOfLinks + nMoney;
					
					System.out.println("\nThe new PageRank score of this website is:  " + newTotal);

					
					// increases key
					sorter.HeapIncreaseKey(heap, targetIndex, newTotal);
					sorter.Heapsort(heap);
					// reverse the array
					reverseArr(heap);
					
					//find the target index of webHeap
					int webHeapIndex = Integer.MIN_VALUE;
					for(int i = 0; i < webHeap.size(); i++)
					{
						if(webHeap.get(i).getUrl().equals(cURL) && webHeap.get(i).getTotal() == currentTotal)
						{
							webHeapIndex = i;
						}
					}		
					
					// set the target element to a new element with the same URL and the new PageRank scores
					webHeap.set(webHeapIndex, new WebLink(webHeap.get(webHeapIndex).getUrl(), new PageRank(nFre, nAge, nNumOfLinks, nMoney)));
					
					// create a duplicated list copied of webHeap
					ArrayList<WebLink> dupList = new ArrayList<>(webHeap);
					// create an empty list
					ArrayList<WebLink> tempList = new ArrayList<>();
					
					System.out.println("\nHere are the webHeap list after increasing the PageRank score: ");
					
					for(int i = 0 ; i < heap.length; i++)
					{
						for(int j = 0 ; j < webHeap.size(); j++)
						{
							if(dupList.get(j).getTotal() == heap[i])
							{
								// check for duplicate
								if(!tempList.contains(dupList.get(j)))
								{
									tempList.add(dupList.get(j));
								}
							}
						}
					}
					
					webHeap = tempList;
					
					// print the webHeap
					for(int i = 0; i < webHeap.size(); i++)
					{
						System.out.println(i+1 + "  "+webHeap.get(i));
					}
					
					break;
				
				// Stop the engine
				case "Q" :
					System.out.println("\n****************************************************");
					System.out.println("Thank you! See you again!");
	                quit = true;
	                break;
	            
	            // Invalid input
				default:
	                System.out.println("Invalid input! Please try again");
	                break;
			}
			System.out.println("**************************************************** \n" );
		}
	}

}
