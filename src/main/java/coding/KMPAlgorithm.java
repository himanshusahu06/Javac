import java.util.Arrays;

/**
 * KMP algorithm
 */
public class KMPSubstringMatchingAlgorithm {
    private final String pattern;
    private final int[] lcp;

    public KMPSubstringMatchingAlgorithm(String pattern) {
        this.pattern = pattern;
        this.lcp = new int[pattern.length()];
        doComputeLongestCommonPrefixArray();
    }

    private void doComputeLongestCommonPrefixArray() {
        int m = 0; // length of matching prefix with suffix for pattern[0...i-1]
        int i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(m)) {
                lcp[i] = m + 1;
                m++;
                i++;
            } else {
                if (m == 0) {
                    // matching length is zero, can't go back to the prefix array anymore
                    lcp[i] = 0;
                    i++;
                } else {
                    // since pattern[i] did not match with pattern[m] that means pattern
                    // from [0, m-1] matches with [i-m, i-1] so we go back to next element
                    // of longest prefix which is suffix af [0,i]. Here, lcp[m-1] is the
                    // index next to the element where prefix ends. if m = 0 that means we
                    // have came back to starting of the pattern.
                    m = lcp[m - 1];
                }
            }
        }
    }

    /**
     * Checks whether pattern is substring of text or not
     *
     * @param text pattern to match against string for
     * @return true if text contains pattern, false otherwise
     */
    public boolean matchAgainst(String text) {
        int i = 0; // text index
        int j = 0; // pattern index
        while (i < text.length() && j < pattern.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lcp[j - 1];
                }
            }
        }
        return j == pattern.length();
    }

    public String getPattern() {
        return pattern;
    }

    public int[] getLcpArray() {
        return lcp;
    }
}

class KMPSubstringMatchingAlgorithmRunner {
    public static void main(String[] args) {
        KMPSubstringMatchingAlgorithm algorithm = new KMPSubstringMatchingAlgorithm("abcaby");
        System.out.println(algorithm.matchAgainst("abxabcabcaby"));
        System.out.println(algorithm.matchAgainst("abxabcabcyaby"));
        System.out.println(algorithm.matchAgainst("abcaby"));
        algorithm = new KMPSubstringMatchingAlgorithm("acacabacacabacacac");
        System.out.println("LCP of " + algorithm.getPattern() + " is " + Arrays.toString(algorithm.getLcpArray()));
    }
}
