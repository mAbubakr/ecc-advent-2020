import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BitMaskWendy {

	public static void run1() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day14/input"));
		
		Map<Integer, Boolean> mask = null;
		Map<Integer, Long> mem = new HashMap<>();
		
		for (String line: input) {
			String[] parts = line.split("=");
			String op = parts[0].trim();
			String val = parts[1].trim();
					
			if (op.equals("mask")) {
				System.out.println("This is a mask");
				mask = new HashMap<>();
				System.out.println(val);
				for (int i=0; i<val.length(); i++) {
					char c = val.charAt(val.length()-1-i);
					if (c != 'X') mask.put(i, c == '1');
					System.out.println("At " + i + " mask is " + mask.get(i));
				}
			} else {
				int index = Integer.parseInt(op.replace("mem[", "").replace("]", ""));
				System.out.println("This is a mem at index" + index); 
				System.out.println(val);
				BitSet val_bs = convert(Integer.parseInt(val));
				mask.forEach((i, b) -> val_bs.set(i, b));
				long convertedValue = convert(val_bs);
				System.out.println(convertedValue);				
				mem.put(index, convertedValue);
			}
			
		}

		long total = mem.values().stream().reduce(0l, Long::sum);
		System.out.println("Answer is " + total);				
	}
	
	public static void run2() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day14/test3"));
		
		Map<Integer, Boolean> mask = null;
		Set<Integer> maskFloating = null;
		Map<Long, Long> mem = new TreeMap<>();
		
		for (String line: input) {
			String[] parts = line.split("=");
			String op = parts[0].trim();
			String val = parts[1].trim();
			if (op.equals("mask")) {
				System.out.println("This is a mask");
				mask = new HashMap<>();
				maskFloating = new HashSet<>();
				System.out.println(val);
				for (int i=0; i<val.length(); i++) {
					char c = val.charAt(val.length()-1-i);
					if (c == 'X') {
						maskFloating.add(i);					
					} else {
						mask.put(i, c == '1');						
					}					
				}
			} else {
				int index = Integer.parseInt(op.replace("mem[", "").replace("]", ""));
				BitSet index_bs = convert(index);	

				System.out.println("This is a mem at index" + index_bs); 
				System.out.println("Current mask is" + mask);
				mask.forEach((i, b) -> {
					if (b && !index_bs.get(i)) { //if 1 and not already 1						
						index_bs.flip(i);
					}
				}); 
				maskFloating.forEach(i -> index_bs.clear(i));				
				System.out.println("BitSet: " + index_bs);				
				long convertedIndex = convert(index_bs);
				System.out.println(convertedIndex);
				Set<Long> addresses = new HashSet<>();
				addresses.add(convertedIndex);
				maskFloating.forEach(i-> {
					System.out.println("float: " + i);
					int diff = (int)Math.pow(2, i);
					System.out.println(diff);
					Set<Long> newAddresses = new HashSet<>();
					addresses.forEach(a -> newAddresses.add(a + diff));
					addresses.addAll(newAddresses);
				});
				
				addresses.forEach(a -> mem.put(a, Long.valueOf(val)));
				System.out.println(addresses);
			}
			
		}
		
	
		long total = mem.values().stream().reduce(0l, Long::sum);
		System.out.println("Answer is " + total);				
	}
	
	public static void main(String[] args) throws IOException {
//		run1();
		run2();
	}

	//https://stackoverflow.com/questions/2473597/bitset-to-and-from-integer-long	
	  public static BitSet convert(long value) {
		    BitSet bits = new BitSet();
		    int index = 0;
		    while (value != 0L) {
		      if (value % 2L != 0) {
		        bits.set(index);
		      }
		      ++index;
		      value = value >>> 1;
		    }
		    return bits;
		  }

		  public static long convert(BitSet bits) {
		    long value = 0L;
		    for (int i = 0; i < bits.length(); ++i) {
		      value += bits.get(i) ? (1L << i) : 0L;
		    }
		    return value;
	  }
	
}
