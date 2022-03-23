import java.util.*;

public class Main {
    private static final double SIMILARITY_INDEX = 0.5;

    public static void main(String[] args) {

        List<String> listNumberN = new ArrayList<>();
        List<String> listNumberM = new ArrayList<>();
        List<String> similarityOutList = new ArrayList<>();

        System.out.println("Введите число вводимых строк для 'n'");
        Scanner scannerNumberN = new Scanner(System.in);
        int numberIntN = scannerNumberN.nextInt();
        System.out.println("Вводите наименовая для 'n' строк");
        for (int i = 0; i < numberIntN; i++) {
            Scanner scannerLineN = new Scanner(System.in);
            listNumberN.add(scannerLineN.nextLine());
        }
        System.out.println("Введите число вводимых строк для 'm'");
        Scanner scannerNumberM = new Scanner(System.in);
        int numberIntM = scannerNumberM.nextInt();
        for (int i = 0; i < numberIntM; i++) {
            Scanner scannerLineM = new Scanner(System.in);
            listNumberM.add(scannerLineM.nextLine());
        }
        for (String stringLineN : listNumberN) {
            for (String stringLineM : listNumberM) {
                double similarityIndex = similarity(stringLineN, stringLineM);
                if (similarityIndex >= SIMILARITY_INDEX) {
                    similarityOutList.add(stringLineN + ":" + stringLineM);
                }
            }
        }
        addLineWithoutMatch(similarityOutList, listNumberN);
        for (String outString : similarityOutList) {
            System.out.println(outString);
        }
    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    public static void addLineWithoutMatch(List<String> similarityOutList, List<String> listNumberN) {
        List<String> checkList = new ArrayList<>();
        for (String stringSimilarityOutList : similarityOutList) {
            String[] arraySimilarityOutList = stringSimilarityOutList.split(":");
            checkList.add(arraySimilarityOutList[0]);
        }

        for (String stringLineN : listNumberN) {
            if (!checkList.contains(stringLineN)) {
                similarityOutList.add(stringLineN + ": ?");
            }
        }
    }
}
