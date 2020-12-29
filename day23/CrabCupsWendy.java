public class CrabCupsWendy {

	private int simCount;
	private int currentCup;
    private CircularBufferLL<Integer> cb; 
    private int max;
	
	public CrabCupsWendy(String input, int simCount) {
		this.simCount = simCount;
		
		max = 9;
		
        // Creating the Circular Buffer 
        cb = new CircularBufferLL<>(max); 
  
        // Adding elements to the circular Buffer
		input.chars().forEach(c -> cb.add(Integer.parseInt("" + (char)c)));        
  

	}
	
	public CrabCupsWendy(String input, int totalCups, int simCount) {
		this.simCount = simCount;
		
		max = totalCups;
		
        // Creating the Circular Buffer 
        cb = new CircularBufferLL<>(totalCups); 
  
        // Adding elements to the circular Buffer
		input.chars().forEach(c -> cb.add(Integer.parseInt("" + (char)c)));
		for (int i=10; i<=totalCups; i++) {
			cb.add(i);
		}
  

	}
	
	public String sim1() {
		
		for (int move=0; move<simCount; move++) {
			System.out.format("====== Move %s ======\n", move);
			
			currentCup = cb.peek();
//			System.out.format("Current cup: %s, Current config: %s\n", currentCup, cb.toString());		
			cb.next(); //move the pointer to one after current
			
		    //The crab picks up the three cups that are immediately clockwise of the current cup. 
			//They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
			int c1 = cb.get();
			int c2 = cb.get();
			int c3 = cb.get();
			System.out.format("Removed: %s, %s, %s\n", c1, c2, c3);
//			System.out.println(cb.toString());			
			
			//The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
			//If this would select one of the cups that was just picked up, the crab will keep subtracting one until 
			//it finds a cup that wasn't just picked up. If at any point in this process the value goes below the 
			//lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.			
			//min=1, max=9
			int destination = currentCup-1;
			if (destination == 0) {
				destination = max;
			}
//			while(!cb.contains(destination)) {
			while(destination == currentCup || destination == c1 || destination == c2 || destination == c3) {			
				destination = destination - 1;
				if (destination == 0) {
					destination = max;
				}								
			}
			System.out.println("Destination is: " + destination);
			
			//The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. 
			//They keep the same order as when they were picked up.
			cb.moveTail(destination);
			cb.add(c1);
			cb.add(c2);
			cb.add(c3);						
//			System.out.println(cb.toString());			
			
			System.out.println("Head: " + cb.head.data);
		    //The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
			currentCup = cb.findNextTo(currentCup);
			System.out.println("Next cup: " + currentCup);
			cb.moveHead(currentCup);
		}

		cb.moveHead(1);
		cb.next();
		String config = cb.toString();
//		System.out.println(config);
		return config.substring(0, config.length()-1);
	}
	
	public long sim2() {
		
		long time = System.currentTimeMillis();
		for (int move=0; move<simCount; move++) {
			if (move % 1000 == 0) {
				System.out.format("====== Move %s ======\n", move);
				long newTime = System.currentTimeMillis();				
				System.out.println("Took: " + (newTime - time));
				time = newTime;
			}
			
			currentCup = cb.peek();
//			System.out.format("Current cup: %s, Current config: %s\n", currentCup, cb.toString());		
			cb.next(); //move the pointer to one after current
			
		    //The crab picks up the three cups that are immediately clockwise of the current cup. 
			//They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.
			int c1 = cb.get();
			int c2 = cb.get();
			int c3 = cb.get();
//			System.out.format("Removed: %s, %s, %s\n", c1, c2, c3);
//			System.out.println(cb.toString());			
			
			//The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. 
			//If this would select one of the cups that was just picked up, the crab will keep subtracting one until 
			//it finds a cup that wasn't just picked up. If at any point in this process the value goes below the 
			//lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.			
			//min=1, max=9
			int destination = currentCup-1;
			if (destination == 0) {
				destination = max;
			}
			
			while(destination == currentCup || destination == c1 || destination == c2 || destination == c3) {
				destination = destination - 1;
				if (destination == 0) {
					destination = max;
				}								
			}
//			System.out.println("Destination is: " + destination);
			
			//The crab places the cups it just picked up so that they are immediately clockwise of the destination cup. 
			//They keep the same order as when they were picked up.
			cb.moveTail(destination);
			cb.add(c1);
			cb.add(c2);
			cb.add(c3);						
//			System.out.println(cb.toString());			
			
//			System.out.println("Head: " + cb.head.data);
		    //The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
			currentCup = cb.findNextTo(currentCup);
//			System.out.println("Next cup: " + currentCup);
			cb.moveHead(currentCup);
		}

		cb.moveHead(1);
		cb.next();
		int next1 = cb.peek();
		cb.next();
		int next2 = cb.peek();
		return (long)next1 * (long)next2;
	}
	
	//https://www.geeksforgeeks.org/java-program-to-implement-circular-buffer/	  
	// A Generic Node class is used to create a Linked List 
	class Node<E> { 
	    // Data Stored in each Node of the Linked List 
	    E data; 
	    // Pointer to the next node in the Linked List 
	    Node<E> next; 
	  
	    // Node class constructor used to initializes 
	    // the data in each Node 
	    Node(E data) { this.data = data; } 
	} 
	  
	class CircularBufferLL<E> { 
	  
	    // Head node 
	    Node<E> head; 
	  
	    // Tail Node 
	    Node<E> tail; 
	    int size = 0; 
	    int capacity = 0; 
	  
	    // Constructor 
	    CircularBufferLL(int capacity) 
	    { 
	        this.capacity = capacity; 
	    } 
	  
	    // Addition of Elements 
	    public void add(E element) 
	    { 
	  
	        // Size of buffer increases as elements 
	        // are added to the Linked List 
	        size++; 
	  
	        // Checking if the buffer is full 
	        if (size > capacity) { 
	        	System.err.println("Buffer Overflow");
	        	System.exit(-1);
	        } 
	  
	        // Checking if the buffer is empty 
	        if (head == null) { 
	            head = new Node<>(element); 
	            tail = head; 
	            return; 
	        } 
	  
	        // Node element to be linked 
	        Node<E> temp = new Node<>(element); 
	  
	        // Referencing the last element to the head node 
	        temp.next = head; 
	  
	        // Updating the tail reference to the 
	        // latest node added 
	        tail.next = temp; 
	  
	        // Updating the tail to the latest node added 
	        tail = temp; 
	    } 
	  
	    // Retrieving the head element 
	    public E get() 
	    { 
	  
	        // Cheking if the buffer is empty 
	        if (size == 0) { 
	        	System.err.println("Empty Buffer");
	        	System.exit(-1);	        	
	        } 
	        // Getting the element 
	        E element = head.data; 
	  
	        // Updating the head pointer 
	        head = head.next; 
	  
	        // Updating the tail reference to 
	        // the new head pointer 
	        tail.next = head; 
	  
	        // Decrementing the size 
	        size--; 
	        if (size == 0) { 
	            // Removing any references present 
	            // when the buffer becomes empty 
	            head = tail = null; 
	        } 
	        return element; 
	    } 
	  
	    // Retrieving the head element without deleting 
	    public E peek()
	    { 
	  
	        // Checking if the buffer is empty 
	        if (size == 0) { 
	        	System.err.println("Empty Buffer");
	        	System.exit(-1);	
	        } 
	        // Getting the element 
	        E element = head.data; 
	        return element; 
	    } 
	  
	    // Checking if the buffer is empty 
	    public boolean isEmpty() { return size == 0; } 
	  
	    // Retrieving the size of the buffer 
	    public int size() { return size; } 
	    
	    //move the pointers ahead once without removing
	    public void next() {
	    	head = head.next;
	    	tail = tail.next;
	    }
	    
	    public void moveHead(E element) {
	    	Node m = tail;
	    	Node n = head;
	    	for (int i=0; i<size; i++) {
	    		if (element.equals(n.data)) {
	    			break;
	    		} else {
	    			m = m.next;	    			
	    			n = n.next;
	    		}	    			
	    	}	 
	    	tail = m;
	    	head = n;
	    }	    
	    
	    public void moveTail(E element) {
	    	Node n = head;
	    	for (int i=0; i<size; i++) {
	    		if (element.equals(n.data)) {
	    			break;
	    		} else {
	    			n = n.next;
	    		}	    			
	    	}	 
	    	tail = n;
	    	head = tail.next;
	    }
	    
	    public String toString() {
	    	String output = "";
	    	Node n = head;
	    	for (int i=0; i<capacity; i++) {
	    		output = output + n.data;
	    		n = n.next;
	    	}
	    	return output;
	    }
	    
	    public boolean contains(E element) {
	    	Node n = head;
	    	for (int i=0; i<size; i++) {
	    		if (element.equals(n.data)) {
	    			return true;
	    		} else {
	    			n = n.next;
	    		}
	    			
	    	}
	    	return false;
	    }
	    
	    public int findNextTo(E element) {
	    	Node n = head;
	    	for (int i=0; i<size; i++) {
	    		if (element.equals(n.data)) {
	    			return (int)n.next.data;
	    		} else {
	    			n = n.next;
	    		}
	    			
	    	}
	    	return -1;	    	
	    }
	} 	
	
	public static void main(String[] args) {
		CrabCupsWendy ccw = new CrabCupsWendy("389125467", 10);
		assert ccw.sim1().equals("92658374");
		ccw = new CrabCupsWendy("389125467", 100);
		assert ccw.sim1().equals("67384529");
		ccw = new CrabCupsWendy("643719258", 100);
		System.out.println("Part 1 answer is " + ccw.sim1());
		
		//part2
		ccw = new CrabCupsWendy("389125467",1000000,10000000);
		assert ccw.sim2() == Long.valueOf("149245887792");

	}

}
