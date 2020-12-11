import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day10Main {

    private static final String INPUT_FILE = "input-file.txt";

    public static void main(String[] args) throws Exception {
        Day10Main day10Main = new Day10Main();
        day10Main.run();
    }

    public Day10Main() {}

    public void run() throws Exception {
        String input = getInputFileAsString();
        List<String> inputAsList = Arrays.asList(input.split("\n").clone());
        processInputs(inputAsList);
    }

    private String getInputFileAsString() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(INPUT_FILE));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private void processInputs(List<String> inputs) {
        int maximumJolts = 0;

        List<Integer> jolts = new ArrayList<>();
        for (String input : inputs) {
            int jolt = Integer.parseInt(input);
            if (jolt > maximumJolts) {
                maximumJolts = jolt;
            }
            jolts.add(jolt);
        }

        Collections.sort(jolts);

        List<Integer> joltsToo = new ArrayList<>(jolts);

        int threeJoltDiffCount = 0;
        int oneJoltDiffCount = 0;

        List<Integer> usedJolts = new ArrayList<>();
        usedJolts.add(0);

        while (jolts.size() > 0) {
            for (int i = 0; i < jolts.size(); i++) {
                int jolt = jolts.get(i);
                int prevJolt = usedJolts.get(usedJolts.size() - 1);
                if ((jolt - prevJolt == 3) || (jolt - prevJolt == 2) || (jolt - prevJolt == 1)) {
                    usedJolts.add(jolts.remove(i));
                    if (jolt - prevJolt == 3) {
                        threeJoltDiffCount++;
                    }
                    else if (jolt - prevJolt == 1) {
                        oneJoltDiffCount++;
                    }
                    break;
                }
            }
        }
        threeJoltDiffCount++;

        joltsToo.add(0, 0);

        long[] sumList = new long[109];
        sumList[sumList.length - 1] = 1;

        for (int i = joltsToo.size() - 1; i >= 0; i--) {
            for (int j = i + 1; j < joltsToo.size(); j++) {
                if(joltsToo.get(j) - joltsToo.get(i) <= 3) {
                    sumList[i] += sumList[j];
                }
            }
        }

        System.out.println(sumList[0]);

    }

}
