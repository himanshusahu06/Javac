package trietree;

import java.util.Map;

/**
 * Hashmap implementation of trie tree
 * @author hsahu
 *
 */
public class HashMapTrieTree implements TrieTree {

	private TreeNode root = new TreeNode('#');

	@Override
	public void insert(String word) {

		TreeNode childTreeNode = null;
		Map<Character, TreeNode> childrens = root.getChildrens();

		for (char ch : word.toCharArray()) {
			if (!childrens.containsKey(ch)) {
				childrens.put(ch, new TreeNode(ch));
			}
			childTreeNode = childrens.get(ch);
			childrens = childTreeNode.getChildrens();
		}
		childTreeNode.setIsLeaf(Boolean.TRUE);
	}

	@Override
	public boolean search(String word) {
		TreeNode searchNode = searchNode(word);
		return searchNode(word) != null && searchNode.getIsLeaf();
	}

	@Override
	public boolean startsWith(String word) {
		return searchNode(word) != null;
	}

	private TreeNode searchNode(String word) {
		TreeNode childTreeNode = null;
		Map<Character, TreeNode> childrens = root.getChildrens();

		for (char ch : word.toCharArray()) {
			if (!childrens.containsKey(ch)) {
				return null;
			}
			childTreeNode = childrens.get(ch);
			childrens = childTreeNode.getChildrens();
		}
		return childTreeNode;
	}

}
