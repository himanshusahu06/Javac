/**
 * Binary search tree interface that describe list of operation can be performed in a BST
 *
 * @param <Key> object for which binary search tree is built for
 */
interface BinarySearchTree<Key extends Comparable<Key>> {
    void insert(Key key);
    Key search(Key key);
    void delete(Key key);
}

/**
 * Left leaning red black tree implementation of balanced BST
 * Left leaning red black (LLRB) is RB-tree where a node connected to it's parent with red link will be left child.
 * all root to leaf path must contain exact number of black nodes.
 *
 * @param <Key> object for which binary search tree is built for
 */
class RedBlackBinarySearchTree<Key extends Comparable<Key>> implements BinarySearchTree<Key> {

    private Node treeRoot;
    private final boolean RED = true;
    private final boolean BLACK = false;

    /**
     * class that represent tree node
     */
    private class Node {
        Key val;
        boolean parentLinkColor;
        Node left;
        Node right;

        public Node(Key val, boolean parentLinkColor) {
            this.val = val;
            this.parentLinkColor = parentLinkColor;
        }
    }

    @Override
    public void insert(Key key) {
        treeRoot = insert(treeRoot, key);
    }

    @Override
    public Key search(Key key) {
        return search(treeRoot, key);
    }

    @Override
    public void delete(Key key) {
        throw new UnsupportedOperationException("deletion is not allowed");
    }

    private Key search(Node root, Key key) {
        if (root != null) {
            int compare = key.compareTo(root.val);
            if (compare == 0) {
                return root.val;
            } else if (compare < 0) {
                return search(root.left, key);
            } else {
                return search(root.right, key);
            }
        }
        return null;
    }

    private Node insert(Node root, Key key) {
        if (root == null) {
            return new Node(key, RED);
        }
        int compare = key.compareTo(root.val);
        if (compare < 0) {
            root.left = insert(root.left, key);
        } else if (compare > 0) {
            root.right = insert(root.right, key);
        }

        ////////////////////////////////////////////////////////////////
        // this piece of code makes a tree red black and self balanced//
        ////////////////////////////////////////////////////////////////

        // RULE 1: left link is black and right link is red, rotate left
        if (!isRed(root.left) && isRed(root.right)) {
            root = rotateLeft(root); // lean left
        }
        // RULE 2: if both left and left grandchild is red, rotate right
        if (isRed(root.left) && (root.left != null && isRed(root.left.left))) {
            root = rotateRight(root); // balance 4 node
        }
        // RULE 3: if both child are red, flip the node's all link color
        if (isRed(root.left) && isRed(root.right)) {
            root = flipColor(root); // split 4 node
        }
        return root;
    }

    /**
     * rotate a node left by preserving root's link color to it's parent
     *
     * @param root node to rotate left
     * @return new root of the subtree after rotation
     */
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        newRoot.parentLinkColor = root.parentLinkColor; // preserve root link color
        root.parentLinkColor = RED;
        return newRoot;
    }

    /**
     * rotate a node right by preserving root's link color to it's parent
     *
     * @param root node to rotate right
     * @return new root of the subtree after rotation
     */
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        newRoot.parentLinkColor = root.parentLinkColor; // preserve root link color
        root.parentLinkColor = RED;
        return newRoot;
    }

    /**
     * if both the child are connected via red link,
     * make them black and flip node's parent link color
     *
     * @param node node to flip the color
     */
    private Node flipColor(Node node) {
        if (isRed(node.left) && isRed(node.right) && !isRed(node)) {
            node.left.parentLinkColor = BLACK;
            node.right.parentLinkColor = BLACK;
            node.parentLinkColor = RED;
        }
        return node;
    }

    /**
     * Checks whether a node's parent is red or not.
     *
     * @param node node to check for red color
     * @return true if node's parent is red, false otherwise
     */
    private boolean isRed(Node node) {
        if (node == null) {
            return true;
        }
        return node.parentLinkColor = RED;
    }
}
