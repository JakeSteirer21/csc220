package prog06;

import prog02.GUI;
import prog02.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WordStep {
	
	static UserInterface ui = new GUI("WordStep");
	
	
	public static void main (String[] args) {
		
		WordStep game = new WordStep();
		
		//Step 7
		//game.loadWords(ui.getInfo("Enter the name of a word file"));
		
		String start;
		String target;
		
		start = ui.getInfo("Enter a starting word");
		target = ui.getInfo("Enter a target word");
		
		String[] commands = { "Human plays.", "Computer plays." };
		
		int command = 0;
		while (true) {
			command = ui.getCommand(commands);
			
			switch (command) {
			case 0:
				game.play(start, target);
			case 1:
				game.solve(start, target);
			}
		}
	}

	
	void play (String start, String target) { 
		while(true) {
			ui.sendMessage("The current word is: " + start);
			ui.sendMessage("The target word is: " + target);
			
			String next = ui.getInfo("Enter the next word");
			start = next;
			
			if (find(next) == -1) {
				ui.sendMessage("Please enter a valid word");
			}
			else if (offBy1(start, target) == true) {
				ui.sendMessage("You are off by one letter!");
			}
			if(next.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}
			else {
				ui.sendMessage("Try again");
			}
		}
	}
	
	
	void solve (String start, String target) {
		int[] parents = new int [j.size()];
		Arrays.fill(parents,  -1);
		
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
		
		int par = find(start);
		queue.offer(par);
		String current;
		
		while(queue.size() != 0) {
			current = j.get(queue.poll());
			
			for(int i = 0; i < j.size(); i++) {
				if(!(start.contentEquals(j.get(i))) && parents[i] == -1 && offBy1(j.get(i), current)) {
					queue.offer(i);
					parents[i] = find(current);
					
					if(j.get(i).contentEquals(target)) {
						ui.sendMessage("Got to " + target + " from " + current);
						String solution = current;
						int currentFound = find(current);
					
					
					
					while(parents[currentFound] != -1) {
						int place = parents[currentFound];
						solution = j.get(place) + "\n" + solution;
						currentFound = place;
					}
					ui.sendMessage(solution);
				}
			}
		}
			
	}
		
			
		
	}
	static int find (String target) {
		for (int i = 0; i < j.size(); i++) {
			if(j.get(i).contentEquals(target)) {
				return i;
			}		
		}
		return -1;
	}
	
	static List<String> j = new ArrayList<String>();
	
	public void loadWords(String words) {
		try {
			Scanner n = new Scanner(new File("words.txt"));
			
			while(n.hasNextLine()) {
				String word = n.nextLine();
				j.add(word);
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
