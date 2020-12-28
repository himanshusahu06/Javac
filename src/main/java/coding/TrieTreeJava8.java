class TrieNode {
    boolean word = false;
    Map<Character, TrieNode> children = new HashMap<>();
}

class TrieTree {
    TrieNode root = new TrieNode();
    public void insert(String s) {
        TrieNode current = root;
        for (int i = s.length()-1; i >= 0; i--) {
            current = current.children.computeIfAbsent(s.charAt(i), x -> new TrieNode());
        }
        current.word = true;
    }

    public boolean search(String text) {
        TrieNode current = root;
        for (char ch: text.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return false;
            if (current.word) return true;
        }
        return false;
    }
}
