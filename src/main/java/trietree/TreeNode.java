package trietree;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {

	private Character label;
	private Boolean isLeaf;
	private Map<Character, TreeNode> childrens = new HashMap<>();

	TreeNode(Character label) {
		this.label = label;
		this.isLeaf = false;
	}

	public Character getLabel() {
		return label;
	}

	public void setLabel(Character label) {
		this.label = label;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Map<Character, TreeNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(Map<Character, TreeNode> childrens) {
		this.childrens = childrens;
	}

}
