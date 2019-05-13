package prog07;

import prog02.GUI;
import prog02.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class WordStep {
	static ArrayList<String> e = new ArrayList<String>();
	static UserInterface ui = new GUI("WordStep");
	
	
	public static void main (String[] args) {
		
		WordStep game = new WordStep();
		
		String words = ui.getInfo("Enter a dictionary file name");
		game.loadWords(words);
		
		
		String start;
		String target;
		
		start = ui.getInfo("Enter a starting word");
		
		if (find(e, start) == -1) {
			ui.sendMessage("The initial word " + start + " is not a valid word");
			return;
		}
		
		target = ui.getInfo("Enter a target word");
		
		if (find(e, target) == -1) {
			ui.sendMessage("The intial word " + target + " is not a valid word");
			return;
		}
		
		System.out.println(start);
		System.out.println(target);
		
		String[] commands = { "Human plays.", "Computer plays." };
		
		int command = 0;
			command = ui.getCommand(commands);
			
			switch (command) {
			case 0:
				game.play(start, target);
				return;
			case 1:
				game.solve(start, target);
				return;
			}
		}
	

	
	void play (String start, String target) { 
		while(!start.contentEquals(target)) {
			ui.sendMessage("The current word is: " + start);
			ui.sendMessage("The target word is: " + target);
			
			String next = ui.getInfo("Enter the next word");
			
			
			if (find(e, next) == -1) {
				ui.sendMessage("Word is not on list");
				continue;
			}
			else if (offBy1(start, next)) {
				start = next;
			}
			else {
				ui.sendMessage(next + " differs by more than one letter from " + start);
			}
		}
		ui.sendMessage("You win!");
		return;
			
	}
	
	
	void solve (String start, String target) {
		int[] parents = new int [e.size()];
		Arrays.fill(parents,  -1);
		
		//ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
		Queue<Integer> wordQueue = new PriorityQueue<Integer>(new IndexComparator(parents, target));
		
		int startIndex = e.indexOf(start);
		
		wordQueue.add(startIndex);
		
		int num = 0;
		int wordPoll;
		
		while(!wordQueue.isEmpty()) {
			wordPoll = wordQueue.poll();
			num++;
			
			String update = e.get(wordPoll);
			
			for(int i = 0; i < e.size(); i++) {
				if(offBy1(update, e.get(i)) && !start.contentEquals(e.get(i)) && parents[i] == -1 || numSteps(parents, wordPoll) + 1 < numSteps(parents, i)) {
					parents[i] = wordPoll;
					wordQueue.offer(i);
					
					if(e.get(i).contentEquals(target)) {
						String one = (String) e.get(i) + "\n" + target;
						
						while(i != startIndex) {
							i = parents[i];
							one = (String) e.get(i) + "\n" + one;
						}
						ui.sendMessage(one);
						ui.sendMessage("Congrats, you win! The count was: " + num);
						ui.sendMessage("It took " + numSteps(parents, find(e, target)) + " steps");
						return;
					}
			}
		}
			
		}
		
	}
	
	static int find (ArrayList<String> e, String target) {
		for (int i = 0; i < e.size(); i++) {
			if(target.equals(e.get(i))) {
				return i;
			}		
		}
		return -1;
	}
	
	//static List<String> j = new ArrayList<String>();
	
	static int numSteps (int[] parents, int index) {
		int x = index;
		int count = 0;
		
		while(parents[x] != -1) {
			x = parents[x];
			count++;
		}
		return count;
	}
	
	static int numDifferent(String oldS, String newS) {
		if(oldS.length() != newS.length()) {
			return -1;
		}
		int count = 0;
		for (int i = 0; i < oldS.length(); i++) {
			if(oldS.charAt(i) == newS.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	
	public class IndexComparator implements Comparator<Integer> {
		int[] parents;
		String target;
		
		public IndexComparator(int[] parents, String target) {
			this.parents = parents;
			this.target = target;
			
		}
		int sumNums (int index) {
			int sum = numDifferent(e.get(index), target) + numSteps(parents, index);
			return sum;
			
		}
		
			public int compare(Integer indexOne, Integer indexTwo) {
				return sumNums(indexOne) - sumNums(indexTwo);
			}
	
		
		
	}
	
	public void loadWords(String words) {
		try {
			Scanner n = new Scanner(new File("words"));
			
			while(n.hasNextLine()) {
				String word = n.nextLine();
				e.add(word);
			}
			n.close();
		}
		catch(FileNotFoundException f) {
			System.out.println(words + " file not found");
			System.out.println(f);
			
			return;
		}
	}
	

	
	static boolean offBy1(String one, String two) {
		int count = 0;
		
		if (one.length() != two.length()) {
			return false;
		}
		else {
			for (int i = 0; i < one.length(); i++) {
				if (one.charAt(i) != two.charAt(i)) {
					count++;
				}
			}
		}
		if (count > 1) {
			return false;
		}
		else {
			return true;
		}
			
	}
		
		
	}
