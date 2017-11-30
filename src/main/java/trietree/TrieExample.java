package trietree;

public class TrieExample {

	public static void main(String[] args) {

		TrieTree trieTree = new HashMapTrieTree();

		trieTree.insert("dog");
		trieTree.insert("cat");
		trieTree.insert("doggy");

		System.out.println(trieTree.search("doggy"));
		System.out.println(trieTree.search("cat"));
	}
}
