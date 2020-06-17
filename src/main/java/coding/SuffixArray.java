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

class LongestRepeatingSubstring {
    private final String str;

    public LongestRepeatingSubstring(String str) {
        this.str = str;
    }

    public String get() {
        String longestSuffix = "";
        String[] suffixArray = new SuffixArray(str, false).getSuffixArray();
        for (int i = 0; i < suffixArray.length - 1; i++) {
            int len = longestCommonPrefix(suffixArray[i], suffixArray[i + 1]);
            if (len > longestSuffix.length()) {
                longestSuffix = suffixArray[i].substring(0, len);
            }
        }
        return longestSuffix;
    }

    private int longestCommonPrefix(String a, String b) {
        int size = Math.min(a.length(), b.length());
        for (int i = 0; i < size; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return i;
            }
        }
        return size;
    }
}

class LongestRepeatedSubstringRunner {
    public static void main(String[] args) {
        LongestRepeatingSubstring longestRepeatingSubstring = new LongestRepeatingSubstring("AACAAGTTTACAAGC");
        System.out.println("Longest repeated string is : " + longestRepeatingSubstring.get());
    }
}
