package searchengine;

import java.util.ArrayList;

/**
 * The Heap Sort algorithm
 * @author Mark Mathew
 *
 */

public class HeapSort {
	
	// size of the Heap
	private int heapsize;
	
	/**
	 * Gets the size of the Heap
	 * @return the size of the Heap
	 */
	public int getHeapSize()
	{
		return heapsize;
	}
	
	/**
	 * Returns the left child of the target node
	 * @param i the target node
	 * @return the left child of the target node
	 */
	public int getLeftChild(int i)
	{
		return 2*i;
	}
	
	/**
	 * Returns the right child of the target node
	 * @param i the target node
	 * @return the right child of the target node
	 */
	public int getRightChild(int i)
	{
		return 2*i+1;
	}
	
	/**
	 * Returns the parent child of the target node
	 * @param i the target node
	 * @return the parent of the target node
	 */
	public int getParent(int i)
	{
		return i/2;
	}
	
	/**
	 * Maintains the max-heap property
	 * @param A an array A
	 * @param i an index in array A
	 */
	public void MaxHeapify(int A[], int i)
	{
		int l = getLeftChild(i); // left child 
 		int r = getRightChild(i); // right child
		int largest = i;
		
		// Check left child
		// if left child > largest
		if(l <= heapsize && A[l] > A[i])
		{
			largest = l; // largest = left child 
		}
		else
		{
			largest = i;
		}
	
		//Check right child
		// if right child > largest
		if(r <= heapsize && A[r] > A[largest])
		{
			largest = r; // largest = right child 
		}
		
		// largest is not the root
		if(largest != i)
		{
			// swap A[i] with A[largest]
			int temp = A[i];
			A[i] = A[largest];
			A[largest] = temp;

			
			// recursively call MaxHeapify
			MaxHeapify(A, largest);
		}
	}
	
	/**
	 * Produces a max-heap from an unordered input array
	 * @param A an array A
	 */
	public void BuildMaxHeap(int A[])
	{
		this.heapsize = A.length -1;
	
		//Call Max-Heapify on each element in a bottom-up manner
		for(int i = (A.length/2)-1; i >= 0; i--)
		{
			MaxHeapify(A, i);
		}
	}
	
	/**
	 * Sorts an array in place
	 * @param A array A
	 */
	public void Heapsort(int A[])
	{
		BuildMaxHeap(A);
		for (int i = heapsize; i >= 0; i--)
		{
			// swap A[0] with A[i]
			int temp = A[0];
			A[0] = A[i];
			A[i] = temp;
			this.heapsize--;	
			
			MaxHeapify(A, 0);
		}
	}
	
	
	/**
	 * Returns the element of array A with the largest key
	 * @param A array A
	 * @return the element of array A with the largest key.
	 */
	public int HeapMaximum(int A[])
	{
		return A[0];
	}
	
	/**
	 * Increases the value of element i's key to the new
	 * value k, where k >= i's current key value
	 * @param A array A
	 * @param i the index of the target element in array A
	 * @param key a new key
	 */
	public void HeapIncreaseKey(int A[], int i, int key)
	{
		if(key < A[i])
		{
			throw new IllegalArgumentException("ERROR: New key is smaller than current key.");
		}
		
		A[i] = key;
		while(i > 0 && A[getParent(i)] < A[i])
		{
			// swap A[i] with A[getParent(i)]
			int temp = A[i];
			A[i] = A[getParent(i)];
			A[getParent(i)] = temp;
			
			i = getParent(i);
		}
	}
	
	/**
	 * Inserts the element key into array A
	 * @param A array A
	 * @param key the inserted key 
	 */
	public void MaxHeapInsert(int A[], int key)
	{
		this.heapsize++;
		A[this.heapsize] = Integer.MIN_VALUE;
		HeapIncreaseKey(A, heapsize, key);
	}
	
	/**
	 * Removes and returns the element of array A with the largest key.
	 * @param A array A
	 * @return the element of array A with the largest key
	 */
	public int HeapExtractMax(int A[])
	{
		if(this.heapsize < 0)
		{
			throw new IllegalArgumentException("ERROR: Heap underflow.");
		}
		
		int max = A[0];
		A[0] = A[this.heapsize];
		this.heapsize--;
		MaxHeapify(A, 0);
		return max;
	}
}
