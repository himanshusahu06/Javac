import java.util.HashMap;
import java.util.Map;

/**
 * Smallest code for trie tree
 */
public class TrieTree {

    private final TrieNode root = new TrieNode();

    /**
     * insert a given string in a trie
     *
     * @param word string to insert
     */
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            // If ch is not present, apply function on it
            current = current.children.computeIfAbsent(ch, character -> new TrieNode());
        }
        current.isWord = true;
    }

    /**
     * find the given string in the trie
     *
     * @param word string to search for
     * @return true if word present in the trie, false otherwise
     */
    public boolean find(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isWord;
    }

    /**
     * check whether given string is prefix of any of the string inserted in trie tree
     *
     * @param word string to check prefix for
     * @return true if it is prefix, false otherwise
     */
    public boolean isPrefix(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    public void delete(String word) {
       throw  new UnsupportedOperationException("Deletion is not allowed currently.");
    }

    private static class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
        private boolean isWord;
    }
}

class TrieTreeRunner {
    public static void main(String[] args) {
        TrieTree trie = new TrieTree();
        trie.insert("programming");
        trie.insert("is");
        trie.insert("a");
        trie.insert("way");
        trie.insert("of");
        trie.insert("life");
        System.out.println(trie.find("progra"));
        System.out.println(trie.isPrefix("progra"));
        System.out.println(trie.find("way"));
        System.out.println(trie.isPrefix("ways"));
    }
}
