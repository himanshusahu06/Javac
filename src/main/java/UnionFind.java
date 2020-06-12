public class UnionFind {
    int[] id;
    int[] treeDepth;

    UnionFind(int n) {
        id = new int[n];
        treeDepth = new int[n];
        for (int i = 0 ; i < n ; i++) {
            id[i] = i;
            treeDepth[i] = 1;
        }
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (treeDepth[rootP] < treeDepth[rootQ]) {
            // OPTIMIZATION 1: make smaller tree's root to the root of larger tree
            id[rootP] = rootQ;
            treeDepth[rootQ] += treeDepth[rootP];
        } else {
            id[rootQ] = rootP;
            treeDepth[rootP] += treeDepth[rootQ];
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int p) {
        while (p != id[p]) {
            // OPTIMIZATION 2: (path compression) set node's parent to it's grand parent
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
}
