
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class MemoryGameWendy {
	
	public static int findThNumbers(int[] startingNum, int target) {
		Map<Integer, Queue> valLastOccurrences = new HashMap<Integer, Queue>();
		
		int prevNum = 0;
		for (int i=0; i<startingNum.length; i++) {	
			var q = valLastOccurrences.getOrDefault(startingNum[i], new ArrayBlockingQueue<Integer>(2));
			q.add(i);
			valLastOccurrences.put(startingNum[i], q);
			prevNum = startingNum[i];
		}		

		for (int i=startingNum.length; i<target; i++) {
			//find the last time the prevNum appeared
			var q = valLastOccurrences.getOrDefault(prevNum, new ArrayBlockingQueue<Integer>(2));			
			if (q.size() == 1) { //only appeared once
				//this round = 0
				prevNum = 0;						
			} else { //appeared twice before
				int prevPrev = (int) q.remove();
				int prev = (int) q.peek();
				int diff = prev - prevPrev;
				//this round = diff
				prevNum = diff;
			}
			q = valLastOccurrences.getOrDefault(prevNum, new ArrayBlockingQueue<Integer>(2));
			q.add(i);	
			valLastOccurrences.put(prevNum, q);
			
			
		}
		return prevNum;
	}	
	
	public static void main(String[] args) {
				
		int target = 2020;
		assert findThNumbers(new int[] {0,3,6}, target) == 436;
		assert findThNumbers(new int[] {1,3,2}, target) == 1;
		assert findThNumbers(new int[] {2,1,3}, target) == 10;
		assert findThNumbers(new int[] {1,2,3}, target) == 27;
		assert findThNumbers(new int[] {2,3,1}, target) == 78;
		assert findThNumbers(new int[] {3,2,1}, target) == 438;
		assert findThNumbers(new int[] {3,1,2}, target) == 1836;		
		
		System.out.format("Answer is %s", findThNumbers(new int[] {11,18,0,20,1,7,16}, target));
		
		target = 30000000;
		assert findThNumbers(new int[] {0,3,6}, target) == 175594;
		assert findThNumbers(new int[] {1,3,2}, target) == 2578;
		assert findThNumbers(new int[] {2,1,3}, target) == 3544142;
		assert findThNumbers(new int[] {1,2,3}, target) == 261214;
		assert findThNumbers(new int[] {2,3,1}, target) == 6895259;
		assert findThNumbers(new int[] {3,2,1}, target) == 18;
		assert findThNumbers(new int[] {3,1,2}, target) == 362;		
		
		System.out.format("Answer is %s", findThNumbers(new int[] {11,18,0,20,1,7,16}, target));		
		
		
	}

}
