package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Call fib(n) ncalls times (needs a loop!).
    for (int i = 0; i < ncalls; i++) {
      fibn = fib.fib(n);
    }
    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return (end - start) / 1000.0 / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n, 1);

    // If the time is (equivalent to) more than a second, return it.
    if (t > 1e6) {
    	return t;
    }
    		


    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numCalls = (int)(1e6 / t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numCalls);
  }

  private static UserInterface ui = new GUI("Fibonacci experiments");

  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
    String userInput = "";
    double constantTime = 0;
    int n = 0;
    UserInterface ui = new GUI("Fibonacci experiment");
    
    
 try { 	
    while (true) {
    	userInput = ui.getInfo("Please enter an integer");
    	
    	
    	if (userInput.equals("") || userInput.isEmpty()) {
    		ui.sendMessage("Please enter a valid number");
    		break;
    	}
    		
    	n = Integer.parseInt(userInput);
    	
    	while (n < 0) {
    		ui.sendMessage("Do not enter a negative number. Try again");
    		userInput = ui.getInfo("Please enter an integer");
    		n = Integer.parseInt(userInput);
    	}
    	
    	double estimateTime = 0;
    	if (constantTime != 0) {
    		estimateTime = constantTime * fib.O(n);
    		ui.sendMessage("Estimate time: " + estimateTime);
    	}
    	
    	double runningTime = accurateTime(fib, n);
    	constantTime = runningTime / fib.O(n);
    	double percentError =(Math.abs(estimateTime - runningTime) / (runningTime) * 100);
    	
    	ui.sendMessage("The value of n: " + n + "\nThe value of fib(n): " + fibn + 
    			"\nThe running time is: " + runningTime + 
    			"\nThe percent error is: " + percentError);
    }
    
 }
catch (NullPointerException e) {}
catch (NumberFormatException e) {
	ui.sendMessage("You have entered a string. Try again");
  }
    
    
    
    	
    // EXERCISES 8 and 9
  }

  public static void doExperiments () {
    String [] commands = {"ExponentialFib", "LinearFib", "LogFib", "ConstantFib", "MysteryFib", "EXIT"};
    
    while (true) {
    	int x = ui.getCommand(commands);
    	
    	switch (x) {
    	case -1: 
    		ui.sendMessage("You have exited the program");
    		break;
    	case 0:
    		Fib eFib = new ExponentialFib();
    		doExperiments(eFib);
    		break;
    	case 2:
    		Fib LinFib = new LinearFib();
    		doExperiments(LinFib);
    		break;
    	case 3: 
    		Fib lFib = new LogFib();
    		doExperiments(lFib);
    		break;
    	case 4:
    		Fib cFib = new ConstantFib();
    		doExperiments(cFib);
    		break;
    	case 5: 
    		Fib mFib = new MysteryFib();
    		doExperiments(mFib);
    		break;
    	case 6:
    		return;
    	}
    }
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new LinearFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    // insert source code here
    // Calculating ncalls = ?
    int nCalls = (int)(1e6 / time1);
    // Call averageTimes with ncalls
    time1 = averageTime(efib, n1, nCalls);
    // Print the results
    System.out.println("The time for the ncalls run is: " + time1);
    // call accurateTime()
    time1 = accurateTime(efib, n1);
    // print the results
    System.out.println("The accurate time for ncalls run is: " + time1);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    // insert source code here
    // Calculating ncalls = ?
    int nCalls2 = (int)(1e6 / time2);
    // Call averageTimes with ncalls
    time2 = averageTime(efib, n2, nCalls2);
    // Print the results
    System.out.println("The time for the ncalls2 run is: " + time2);
    // call accurateTime()
    time2 = accurateTime(efib, n2);
    // print the results
    System.out.println("The accurate time for ncalls2 run is: " + time2); 
  }

  /**
   * @param args the command line arguments
   */
  
  public static void main (String[] args) {
    //labExperiments();
    doExperiments(new ExponentialFib());
    //doExperiments();
  }
}
