import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9Main {

    private static final String INPUT_FILE = "input-file.txt";

    public static void main(String[] args) throws Exception {
        Day9Main day9Main = new Day9Main();
        day9Main.run();
    }

    public Day9Main() {}

    public void run() throws Exception {
        String input = getInputFileAsString();
        List<String> inputAsList = Arrays.asList(input.split("\n").clone());
        proccessInputs(inputAsList);
    }

    private String getInputFileAsString() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(INPUT_FILE));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private void proccessInputs(List<String> inputs) {
        List<String> preamble = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            preamble.add(inputs.get(i));
        }

        int beginIndex = 0;

        int foundIndex = -1;

        for (int i = 25; i < inputs.size(); i++) {
            long c = Long.parseLong(inputs.get(i));
            if(checkIfInputValid(c, preamble)) {
                beginIndex++;
                preamble = inputs.subList(beginIndex, beginIndex + 25);
            }
            else {
                foundIndex = i;
                break;
            }
        }

        long invalidNumber = Long.parseLong(inputs.get(foundIndex));
        long sum = 0;

        int size = 2;
        while (size <= inputs.size()){
            beginIndex = 0;
            List<String> slideList = inputs.subList(beginIndex, beginIndex + size);
            for (int i = 0; i < inputs.size(); i++) {
                long max = -1;
                long min = 999999999;
                min = max = Long.parseLong(slideList.get(0));
                for (String val : slideList) {
                    sum += Long.parseLong(val);
                    if(Long.parseLong(val) < min) {
                        min = Long.parseLong(val);
                    }
                    else if (Long.parseLong(val) > max) {
                        max = Long.parseLong(val);
                    }
                }
                if(sum == invalidNumber) {
                    System.out.println(min + max);
                    return;
                }
                else {
                    sum = 0;
                    beginIndex++;
                    if((beginIndex + size) < inputs.size() -1) {
                        slideList = inputs.subList(beginIndex, beginIndex + size);
                    }
                }
            }
            sum = 0;
            size++;
        }

    }

    private boolean checkIfInputValid(long c, List<String> preamble) {
        if(sumRemainingValues(c, preamble, 0)) {
            return true;
        }
        return false;
    }

    private boolean sumRemainingValues(long c, List<String> preamble, int beginIndex) {
        while(beginIndex != preamble.size() - 1) {
            for (int i = beginIndex + 1; i < preamble.size(); i++) {
                long valAtBegin = Long.parseLong(preamble.get(beginIndex));
                long valAtI = Long.parseLong(preamble.get(i));
                long sum = valAtBegin + valAtI;
                if(sum == c) {
                    return true;
                }
            }
            beginIndex++;
            return sumRemainingValues(c, preamble, beginIndex);
        }
        return false;
    }

}
