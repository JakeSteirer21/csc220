package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  List<E> theData;

  /** Initialize theData to an empty List. */
  public ListStack() {
    theData = new ArrayList<E>();
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    theData.add(obj);
    return obj;
  }

  /**** EXERCISE ****/
  
  public E pop(){
	  if (empty()) 
		  throw new EmptyStackException();
	  
	  E data = theData.get(theData.size() - 1);
	  theData.remove(theData.size() - 1); //-1
	  return data;
  }
  
  public E peek() {
	  if (empty())
		  throw new EmptyStackException();
	  
	  return theData.get(theData.size() - 1);
  }

  public boolean empty() {
	  if (theData.size() == 0)
		  return true;
	  return false;
  }
}
