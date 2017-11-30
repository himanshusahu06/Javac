package trietree;

public interface TrieTree {

	public void insert(String word);

	public boolean search(String word);

	public boolean startsWith(String word);
}
