package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
      to talk to the user.
      @param pd The PhoneDirectory object to use
      to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
				break;
			case 0:
				name = ui.getInfo("Enter the name");
				if (name == null) {
					break;
				}
				if (name.isEmpty() || name.contentEquals("")) {
					ui.sendMessage("Enter a valid name");
					break;
				}
				number = ui.getInfo("Enter the number");
				
				if (number == null) {
					ui.sendMessage("Enter a valid number");
					break;
				}
				oldNumber = pd.addOrChangeEntry(name, number);
				if(oldNumber == null) {
					ui.sendMessage(name + " has been added to the directory");
				}
				else {
					ui.sendMessage(number + " has been changed to " + oldNumber);
				}
				
				
				break;
			case 1:
				name = ui.getInfo("Enter the name");
				number = pd.lookupEntry(name);
				if (name == null) {
					break;
				}
				if (name.isEmpty()) {
					ui.sendMessage("Please enter a name");
				} else if(number == null){
					ui.sendMessage("Name not in the directory");
				}
				else {
					ui.sendMessage(name + " has number " + number);
				}
				
				
				break;
			case 2:
				name = ui.getInfo("Enter the name");
				if (name == null) {
					break;
				}
				if (name.isEmpty()) {
					ui.sendMessage("Please enter a name");
				} 
				else {
					pd.removeEntry(name);
					ui.sendMessage(name + " has been removed");
				}
				
				break;
			case 3:
				pd.save();
				break;
			case 4:
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI("Phone Directory");
		//UserInterface ui = new TestUI();
		processCommands(fn, ui, pd);
	}
}
