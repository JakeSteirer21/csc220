package prog12;

import java.util.*;
import java.util.List;

public class Binge implements SearchEngine{
	
	HardDisk<PageFile> pageFiles = new HardDisk<PageFile>();
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	
	PageTrie urlToIndex = new PageTrie();
	
	WordTable word2Index = new WordTable();
	
	public long indexPage(String url) {
		Long index = pageFiles.newFile();
		
		PageFile pageFile = new PageFile(index, url);
		
		pageFiles.put(index, pageFile);
		urlToIndex.put(url, index);
		
		System.out.println("Indexing page" + pageFile);
		
		return index;
	}
	
	

	public void gather(Browser browser, List<String> startingURLs) {
		Queue<Long> pageQueue = new ArrayDeque<Long>();
		
		System.out.println("Gather " + startingURLs);
		
		for (String url: startingURLs) {
			if (!urlToIndex.containsKey(url)) {
				Long storeIndex = indexPage(url);
				
				pageQueue.offer(storeIndex);
				
			}
		}
		
		while (!pageQueue.isEmpty()) {
			Set<Long> pageIndicies = new HashSet<Long>();
			System.out.println("Queue is " + pageQueue);
			
			Long Pageindex = pageQueue.poll();
			
			PageFile pageFile = pageFiles.get(Pageindex);
			System.out.println("Dequeued " + pageFile);
			
			if(browser.loadPage(pageFile.url)) {
				List<String> urls = browser.getURLs();
				
				System.out.println("URLS are " + urls);
				
				for (String url2: urls) {
					if (!urlToIndex.containsKey(url2)) {
						Long storeIndex2 = indexPage(url2);
						pageQueue.offer(storeIndex2);
						pageIndicies.add(storeIndex2);
					}
					else {
						pageIndicies.add(urlToIndex.get(url2));
					}
				}
				
				for (Long index: pageIndicies) {
					pageFiles.get(index).incRefCount();
					System.out.println("Inc ref " + pageFiles.get(index));
				}
				List<String> words = browser.getWords();
				System.out.println("Words " + words);
				
				for (String word: browser.getWords()) {
					indexWord(word, pageFile.index);
				}
			}
		}
		
		System.out.println("pageDisk");
		System.out.println(pageFiles);
		System.out.println("urlToIndex");
		System.out.println(urlToIndex);
		System.out.println("wordDisk");
		System.out.println(wordDisk);
		System.out.println("wordToIndex");
		System.out.println(word2Index);
		

	}
	
	public long indexWord(String word, Long Pageindex) {
		if (word2Index.containsKey(word)) {
			if(!wordDisk.get(word2Index.get(word)).contains(Pageindex)) {
				wordDisk.get(word2Index.get(word)).add(Pageindex);
				System.out.println("Add Page " + word2Index.get(word) + "(" + word + ")" );
			}
		}
		else {
			long index = wordDisk.newFile();
			System.out.println("Indexing Word " + index + "(" + word + ")[]");
			
			List<Long> wordFile = new LinkedList<Long>();
			
			wordFile.add(Pageindex);
			
			wordDisk.put(index, wordFile);
			word2Index.put(word, index);
			
			//System.out.println("Add Page " + index + "(" + word + ")" + wordDisk.get(word2Index));
			
		}
		return Pageindex;
		
	}



	public String[] search (List<String> keyWords, int numResults) {
		
		
		return new String[0];
	}
}
