package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI("Towers of Hanoi");

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    for (int i = nDisks; i > 0; i--) {
    	pegs[0].push(i);
    }

  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };

    while (!pegs[0].empty() || !pegs[1].empty()) {/* EXERCISE:  player has not moved all the disks to 'c'. */ 
      displayPegs();
      int imove = ui.getCommand(moves);
      if (imove == -1)
        return;
      String move = moves[imove];
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
    while(!peg.empty()) {
    	helper.push(peg.pop());
    }
    
    while(!helper.empty()) {
    	s += "" + helper.peek();
    	peg.push(helper.pop());
    }

    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages.

	  if(pegs[from].empty()) {
		  ui.sendMessage("Empty");
		  return;
	  }

	  if(pegs[to].empty() || pegs[to].peek() > pegs[from].peek()) {
		  pegs[to].push(pegs[from].pop());
	  }
	  
	  else if(pegs[from].peek() > pegs[to].peek()) {
		  ui.sendMessage("Disk could not be placed at location " + pegs[from].peek() + " on top of disk " + pegs[to].peek());
	  }

  }

  // EXERCISE:  create Goal class.
  StackInt<Goal> finish = new ListStack<Goal>();
  class Goal {
    // Data.
	  int num;
	  int fromPeg;
	  int toPeg;
	  
	  	Goal (int num, int fromPeg, int toPeg){
	  		this.num = num;
	  		this.fromPeg = fromPeg;
	  		this.toPeg = toPeg;
	  	}
	 
    // Constructor.






    public String toString () {
      String[] pegNames = { "a", "b", "c" };
      String s = "Exchange " + finish.peek().num + "disk(s) from " + (char)('a' + finish.peek().fromPeg) + "to" + (char)('a' + finish.peek().toPeg) + "\n";










      return s;
    }
  }
  


  // EXERCISE:  display contents of a stack of goals



  
  void solve () {
    // EXERCISE
	  finish.push(new Goal(nDisks, 0, 2));
	  	while(!finish.empty()) {
	  		int number = finish.peek().num;
	  		int source = finish.peek().fromPeg;
	  		int destination = finish.peek().toPeg;
	  		int transfer;
	  		
	  		if((source + destination) == 1) {
	  			transfer = 2;
	  			
	  		}
	  		
	  		else if ((source + destination ) == 3) {
	  			transfer = 0;
	  		}
	  		
	  		else {
	  			transfer = 1;
	  		}
	  		
	  		displayPegs();
	  		finish.pop();
	  		
	  		if (number == 1) {
	  			move(source, destination);
	  			displayPegs();
	  		}
	  		
	  		else {
	  			finish.push(new Goal(number - 1, transfer, destination));
	  			finish.push(new Goal(1, source, destination));
	  			finish.push(new Goal(number - 1, source, transfer));
	  		}
	  	}
	  	
	  	ui.sendMessage("Computer wins!");


  }        
}
