import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class FoodWendy {

	public FoodWendy() throws IOException {
		List<String> input = Files.readAllLines(Paths.get("./day21/input"));
		Map<String, Set<String>> ingredientsToAllergens = new HashMap<>();
		Map<String, Set<String>> allergenToCommonIngredients = new HashMap<>();
		Map<String, Integer> ingredientsCount = new HashMap<>();
		for(String line: input) {
			Pattern pattern = Pattern.compile("(.*) \\(contains (.*)\\)");
			Matcher matcher = pattern.matcher(line);
			matcher.matches();		
			String[] ingredients = matcher.group(1).split(" ");
			String[] allergens = matcher.group(2).replace(" ", "").split(",");
			System.out.format("Ingredients: %s, Allergens: %s\n", Arrays.toString(ingredients), Arrays.toString(allergens));

			for (String ingredient: ingredients) {
				Set<String> allergenSet = ingredientsToAllergens.getOrDefault(ingredient, new HashSet<String>());
				int ingredientCount = ingredientsCount.getOrDefault(ingredient, 0) + 1;
				for (String allergen: allergens) {
					allergenSet.add(allergen);
				}
				ingredientsToAllergens.put(ingredient, allergenSet);
				ingredientsCount.put(ingredient, ingredientCount);
			}	

			for (String allergen: allergens) {
				Set<String> prevIngredientSet = allergenToCommonIngredients.getOrDefault(allergen, new HashSet<String>());

				Set<String> currentIngredientSet = new HashSet<String>();
				for (String ingredient: ingredients) {
					currentIngredientSet.add(ingredient);
				}

				if (prevIngredientSet.isEmpty()) {
					allergenToCommonIngredients.put(allergen, currentIngredientSet);
				} else {
					Set<String> intersection = prevIngredientSet.stream().filter(currentIngredientSet::contains).collect(Collectors.toSet());
					allergenToCommonIngredients.put(allergen, intersection);
				}

			}	

		}
		System.out.println("ingredient count");
		ingredientsCount.forEach((i, a) -> System.out.format("Ingredient: %s, Count: %s\n", i, a));		 
		System.out.println("map ingredient to possible allergen");
		ingredientsToAllergens.forEach((i, a) -> System.out.format("Ingredient: %s, Allergens: %s\n", i, a));
		System.out.println("intersected map from allergen to possible ingredient");
		allergenToCommonIngredients.forEach((i, a) -> System.out.format("Allergen: %s, Ingredients: %s\n", i, a));		 

		int numAllergen = allergenToCommonIngredients.size();
		LinkedHashMap<String, Set<String>> allergenToIngredient = sortByValueSize(allergenToCommonIngredients);
		for (int n=0; n<numAllergen; n++) {
			System.out.println("sort...");
			LinkedHashMap<String, Set<String>> newMap = sortByValueSize(allergenToIngredient);
			newMap.forEach((i, a) -> System.out.format("Allergen: %s, Ingredients: %s\n", i, a));	
			Iterator<String> iter = newMap.keySet().iterator();
			String key = null;
			for (int position=0; position-1<n; position++) {
				key = iter.next();
			}

			String value = newMap.get(key).iterator().next();
			System.out.println("removing " + value);
			newMap.replaceAll((a, i) -> {
				if (i.size() != 1) i.remove(value);
				return i;
			});
			allergenToIngredient = newMap;
		}



		allergenToIngredient.forEach((i, a) -> System.out.format("Allergen: %s, Ingredient: %s\n", i, a));		

		Set<String> ingredientsWithoutAllergen = ingredientsToAllergens.keySet();
		System.out.println("All ingredients: " + ingredientsWithoutAllergen);
		allergenToCommonIngredients.values().stream().forEach(s -> ingredientsWithoutAllergen.removeAll(s));
		System.out.println("Ingredients without allergen: " + ingredientsWithoutAllergen);
		int totalOccurrences = ingredientsCount.entrySet()
				.stream()
				.mapToInt(i-> {
					if (ingredientsWithoutAllergen.contains(i.getKey())) {
						return i.getValue();
					} else {
						return 0;
					}
				})
				.sum();
		System.out.println("Total occurrences of ingredients not containing allergen: " + totalOccurrences);


		LinkedHashMap<String, Set<String>> allergenToIngredientSorted = sortByKeyAlphabet(allergenToIngredient);	
		allergenToIngredientSorted.forEach((i, a) -> System.out.format("Allergen: %s, Ingredient: %s\n", i, a));		
		System.out.println(Arrays.toString(allergenToIngredientSorted.values().toArray()).replace("[", "").replace("]", "").replace(" ", ""));

	}


	public  LinkedHashMap<String, Set<String>> sortByKeyAlphabet(final Map<String, Set<String>> allergenToIngredients) {

		return allergenToIngredients.entrySet()
				.stream()
				.sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.collect(Collectors.toMap(e->e.getKey(), 
						e->{
							Set<String> copy = new HashSet<String>();
							copy.addAll(e.getValue()); 
							return copy;
						},
						(e1, e2) -> e1, LinkedHashMap::new));


	}

	public  LinkedHashMap<String, Set<String>> sortByValueSize(final Map<String, Set<String>> allergenToIngredients) {

		return allergenToIngredients.entrySet()
				.stream()
				.sorted((e1, e2) -> Integer.compare(e1.getValue().size(), e2.getValue().size()))
				.collect(Collectors.toMap(e->e.getKey(), 
						e->{
							Set<String> copy = new HashSet<String>();
							copy.addAll(e.getValue()); 
							return copy;
						},
						(e1, e2) -> e1, LinkedHashMap::new));


	}


	public static void main(String[] args) throws IOException {
		new FoodWendy();

	}

}
