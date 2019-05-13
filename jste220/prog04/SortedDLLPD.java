package prog04;

public class SortedDLLPD extends DLLBasedPD {
	
	protected DLLEntry add (DLLEntry location, String name, String number) {
	    DLLEntry entry = new DLLEntry(name, number);
	    
	    if (head == null) {
	    	head = entry;
	    	tail = entry;
	    }
	    else if (location == head) {
	    	entry.setNext(head);
	    	head.setPrevious(entry);
	    	head = entry;
	    }
	    else if (location == null) {
	    	entry.setPrevious(tail);
	    	tail.setNext(entry);
	    	tail = entry;
	    }
	    else {
	    	DLLEntry previous = location.getPrevious();
	    	previous.setNext(entry);
	    	entry.setPrevious(previous);
	    	entry.setNext(location);
	    	location.setPrevious(entry);
	    	
	    }
	    	
	    return entry;
	  
	
	  
	  
}

	  /** Find an entry in the directory.
	      @param name The name to be found
	      @return The entry with the same name or null if it is not there.
	  */
	
	protected DLLEntry find (String name) {
	    // EXERCISE
	    // For each entry in the directory.
	    // What is the first?  What is the next?  How do you know you got them all?
		  for (DLLEntry entry = head; entry != null;entry = entry.getNext()) {
			  
		// If this is the entry you want
			  if (entry.getName().equals(name)) {
				  	return entry;
			  }
			  else if (entry.getName().compareTo(name) > 0) {
				  return entry;
			  }
	     
		  }
	        // return it.

	    return null; // Name not found.
	  }
	 
	  
	  /** Check if a name is found at a location.
	      @param location The location to check
	      @param name The name to look for at that location
	      @return false, if location is null or it does not have that
	      name; true, otherwise.
	  */
	
	protected boolean found (DLLEntry location, String name) {
	    if (location == null) {
	      return false;
	    }
	    else if (!location.getName().equals(name)) {
	    	return false;
	    }
	    return true;
	  }

	  /** Remove an entry from the directory.
	      @param name The name of the person to be removed
	      @return The current number. If not in directory, null is
	      returned
	  */

}
