package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaloriesWendy {

	public static void main(String[] args) {
		try {
			List<String> strings = Files.readAllLines(Paths.get("src/main/day1/input.txt"));
	
			List<List<String>> initial = new ArrayList<>();
			initial.add(new ArrayList<>());
			List<List<String>> result = strings.stream().reduce(initial, (subtotal, element) -> {
			    if (element.trim().isEmpty()) {
			        subtotal.add(new ArrayList<>());
			    } else {
			        subtotal.get(subtotal.size() - 1).add(element);
			    }
			    return subtotal;
	
			}, (list1, list2) -> new ArrayList<>());		

			ArrayList<Integer> sums = new ArrayList<Integer>();
			for (List<String> elf:result) {
				System.out.println(elf.toString());
				int sum = elf.stream().mapToInt(Integer::valueOf).sum();
				sums.add(sum);
			}
			sums.sort(null);
			
			int numElves = sums.size();
			System.out.println(sums.toString());
			System.out.println(sums.get(numElves-1) + " " + 
					sums.get(numElves-2) + " " + 
					sums.get(numElves-3));
			System.out.println(sums.get(numElves-1) +  
					sums.get(numElves-2) + 
					sums.get(numElves-3));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
