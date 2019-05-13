package prog12;

import java.util.*;

public class Binge2 implements SearchEngine{
	
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	HardDisk<PageFile> pageFiles = new HardDisk<PageFile>();
	
	WordTable word2Index = new WordTable();
	PageTrie urlToIndex = new PageTrie();
	
	public void gather(Browser browser, List<String> startingURLs) {
		urlToIndex.read(pageFiles);
		word2Index.read(wordDisk);
		
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		// Iterator into list of page ids for each key word.
	    Iterator<Long>[] wordFileIterators =
	      (Iterator<Long>[]) new Iterator[keyWords.size()];
	    
	    // Current page index in each list, just ``behind'' the iterator.
	    long[] currentPageIndices = new long[keyWords.size()];
	    
	    // LEAST popular page is at top of heap so if heap has numResults
	    // elements and the next match is better than the least popular page
	    // in the queue, the least popular page can be thrown away.
	    PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new PageComparator());
	    
	    
	    //Write a loop to initialize the entries of wordFileIterators. 
	    for (int i = 0; i < keyWords.size(); i++) {
	    	String word = keyWords.get(i);
	    	
	    	if (word2Index.containsKey(word)) {
	    		return new String[0];
	    	}
	    	
	    	long wordIndex = word2Index.get(word);
	    	
	    	wordFileIterators[i] = wordDisk.get(wordIndex).iterator();
	    			
	    }
	    
	    while (getNextPageIndices(currentPageIndices, wordFileIterators)) {
	    	if (allEqual(currentPageIndices)) {
	    		long pageIndex = currentPageIndices[0];
	    		int priority = pageFiles.get(pageIndex).getRefCount();
	    		
	    		if (bestPageIndices.size() < numResults || 
	    				pageFiles.get(pageIndex).getRefCount() > pageFiles.get(bestPageIndices.peek()).getRefCount()) {
	    			if (bestPageIndices.size() == numResults) {
	    				bestPageIndices.poll();
	    			}
	    		}
	    		
	    	}
	    	String[] display = new String[bestPageIndices.size()];
	    	for (int i = bestPageIndices.size() - 1; i >= 0; i--) 
	    		display[i] = pageFiles.get(bestPageIndices.poll());
	    	return display;
	    	
	    }

	   

		
	}
	
	public class PageComparator implements Comparator<Long> {

		@Override
		public int compare(Long o1, Long o2) {
			// TODO Auto-generated method stub
			return pageFiles.get(o1).getRefCount() - pageFiles.get(o2).getRefCount();
		}
		
	}
	
	 /** If all the currentPageIndices are the same (because are just
    starting or just found a match), get the next page index for
    each word: call next() for each word file iterator and put the
    result into current page indices.

    If they are not all the same, only get the next index if the
    current index is smaller than the largest.

    Return false if hasNext() is false for any iterator.

    @param currentPageIndices array of current page indices
    @param wordFileIterators array of iterators with next page indices
    @return true if all minimum page indices updates, false otherwise
*/
	private boolean getNextPageIndices (long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {
			long goal = currentPageIndices[0];
			
			for (int i = 0; i < currentPageIndices.length; i++) 
				if (currentPageIndices[i] < goal) 
					goal = currentPageIndices[i];
			
			if (allEqual(currentPageIndices)) {
				goal++;
			}
					
			for (int i = 0; i < currentPageIndices.length; i++) {
				if (currentPageIndices[i] < goal)
					if (wordFileIterators[i].hasNext()) {
						return false;
					}
					else {
						currentPageIndices[i] = wordFileIterators[i].next();
					}
					
			}
			
			return true;
		}
	
			
	
	 private boolean allEqual (long[] array) {
		 for (int i = 0; i < array.length - 1; i++) {
			 if(array[i] != array[i + 1]) {
				 return false;
			 }
		 }
		 return true;
	 }
	
}
