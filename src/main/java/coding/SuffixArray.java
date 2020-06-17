import java.util.Arrays;

/**
 * Suffix array for a given string
 */
public class SuffixArray {
    private final String str;
    private String[] suffixArray;
    private final boolean caseSensitive;

    public SuffixArray(String str, boolean caseSensitive) {
        this.str = str;
        this.caseSensitive = caseSensitive;
        eagerComputeSuffixArray();
    }

    private void eagerComputeSuffixArray() {
        int size = str.length();
        suffixArray = new String[size];
        for (int i = 0; i < size; i++) {
            suffixArray[i] = caseSensitive ? str.substring(i, size) : str.substring(i, size).toLowerCase();
        }
        Arrays.sort(suffixArray);
    }

    public String[] getSuffixArray() {
        return suffixArray;
    }
}

class LongestRepeatedSubstringRunner {
    public static void main(String[] args) {
        int longestSuffixSize = 0;
        String longestSuffix = null;
        String[] suffixArray = new SuffixArray("AACAAGTTTACAAGC", false).getSuffixArray();
        for (int i = 0; i < suffixArray.length - 1; i++) {
            String commonSuffix = getCommonSuffix(suffixArray[i], suffixArray[i + 1]);
            if (commonSuffix.length() > longestSuffixSize) {
                longestSuffixSize = commonSuffix.length();
                longestSuffix = commonSuffix;
            }
        }
        System.out.println("Longest repeated string is : " + longestSuffix);
    }

    private static String getCommonSuffix(String a, String b) {
        int size = Math.min(a.length(), b.length());
        for (int i = 0; i < size; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, size);
    }
}
