import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Day4Main {

    private static final String INPUT_FILE = "input-file.txt";

    private int validCount = 0;

    private static Set<String> validSet = new HashSet<>();

    static {
        validSet.addAll(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
    }

    private static List<String> validECLs = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    public static void main(String[] args) throws Exception {
        Day4Main day4Main = new Day4Main();
        day4Main.run();
    }

    public Day4Main() { }

    public void run() throws Exception {
        String inputFile = getInputFileAsString();
        List<String> inputsAsList = Arrays.asList(inputFile.split("\n\n").clone());
        processInputs(inputsAsList);
    }

    private String getInputFileAsString() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(INPUT_FILE));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private void processInputs(List<String> inputs) {
        for (String inputString : inputs) {
            processInput(inputString);
        }
        System.out.println(validCount);
    }

    private void processInput(String input) {
        String reducedInput = reduceNewLines(input);
        List<String> fieldsAndValueStrings = Arrays.asList(reducedInput.split(" "));
        HashMap fieldAndValueMap = new HashMap();
        for (String fieldAndValue : fieldsAndValueStrings) {
            List<String> fieldAndValueList = getFieldAndValueList(fieldAndValue);
            fieldAndValueMap.put(fieldAndValueList.get(0), fieldAndValueList.get(1));
        }
        Set<String> keys = fieldAndValueMap.keySet();
        keys.remove("cid");

        if (validSet.equals(keys)) {
            if (checkBYR(fieldAndValueMap) && checkIYR(fieldAndValueMap) && checkEYR(fieldAndValueMap) &&
                checkHGT(fieldAndValueMap) && checkHCL(fieldAndValueMap) && checkECL(fieldAndValueMap) &&
                checkPID(fieldAndValueMap)) {
                validCount++;
            }
        }

    }

    private boolean checkBYR(HashMap<String, String> passportMap) {
        String bYRValue = passportMap.get("byr");
        if (bYRValue.length() == 4) {
            int byrValueInt = Integer.parseInt(bYRValue);
            return byrValueInt >= 1920 && byrValueInt <= 2002;
        }
        return false;
    }

    private boolean checkIYR(HashMap<String, String> passportMap) {
        String iYRValue = passportMap.get("iyr");
        if (iYRValue.length() == 4) {
            int iYRValueInt = Integer.parseInt(iYRValue);
            return iYRValueInt >= 2010 && iYRValueInt <= 2020;
        }
        System.out.println();
        return false;
    }

    private boolean checkEYR(HashMap<String, String> passportMap) {
        String eYRValue = passportMap.get("eyr");
        if (eYRValue.length() == 4) {
            int eYRValueInt = Integer.parseInt(eYRValue);
            return eYRValueInt >= 2020 && eYRValueInt <= 2030;
        }
        return false;
    }

    private boolean checkHGT(HashMap<String, String> passportMap) {
        String hGTValue = passportMap.get("hgt");
        String last2Digits = hGTValue.substring(hGTValue.length() - 2);
        if ("cm".equals(last2Digits) || "in".equals(last2Digits)) {
            String num = hGTValue.substring(0, hGTValue.length() - 2);
            if (StringUtils.isNumeric(num)) {
                int value = Integer.parseInt(num);
                if ("cm".equals(last2Digits)) {
                    return value >= 150 && value <= 193;
                }
                else if ("in".equals(last2Digits)) {
                    return value >= 59 && value <= 76;
                }
            }
        }

        return false;
    }

    private boolean checkHCL(HashMap<String, String> passportMap) {
        String hCLValue = passportMap.get("hcl");
        return hCLValue.matches("^#[0-9a-z][0-9a-z][0-9a-z][0-9a-z][0-9a-z][0-9a-z]$");
    }

    private boolean checkECL(HashMap<String, String> passportMap) {
        String eCLValue = passportMap.get("ecl");
        return validECLs.contains(eCLValue);
    }

    private boolean checkPID(HashMap<String, String> passportMap) {
        String pIDValue = passportMap.get("pid");
        return pIDValue.matches("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$");
    }

    private List<String> getFieldAndValueList(String fieldAndValueString) {
        return Arrays.asList(fieldAndValueString.split(":").clone());
    }

    private String reduceNewLines(String input) {
        String reducedString = "";
        String[] allLines = input.split("\n");
        for (String inputLine : allLines) {
            reducedString = reducedString + inputLine + " ";
        }
        return reducedString;
    }
}
