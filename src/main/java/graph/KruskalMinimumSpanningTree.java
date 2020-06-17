import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * MST for undirected, weighted graph  algorithm:-
 * 1. Sort the edges by their weight (put it in minPQ)
 * 2.iterate in the sorted order and include edges in mst
 * set if new edge is not creating cycle in MST
 * 3. stop when total number of edges in MST becomes N-1
 */
public class KruskalMinimumSpanningTree {
    private final WeightedGraph graph;

    public KruskalMinimumSpanningTree(WeightedGraph graph) {
        this.graph = graph;
    }

    public List<WeightedGraph.Edge> edges() {
        // result collection
        List<WeightedGraph.Edge> mstEdges = new ArrayList<>();

        // insert all edges to minPQ
        PriorityQueue<WeightedGraph.Edge> pq = new PriorityQueue<>();
        for (int i = 0; i < graph.size(); i++) {
            Iterator<WeightedGraph.Edge> it = graph.edges(i);
            while (it.hasNext()) {
                pq.add(it.next());
            }
        }

        UnionFind uf = new UnionFind(graph.size());
        int edgeCount = 0;
        while (!pq.isEmpty() && edgeCount < graph.size() - 1) {
            WeightedGraph.Edge top = pq.remove();
            if (!uf.connected(top.v, top.w)) {
                // if edge doesn't cause a cycle in the MST, include it
                mstEdges.add(top);
                uf.union(top.v, top.w); // union it to MST
                edgeCount++;
            }
        }

        if (edgeCount != graph.size() - 1) {
            // graph is a MST forest
            return new ArrayList<>();
        }
        return mstEdges;
    }

    public int mstWeight() {
        int weight = 0;
        for (WeightedGraph.Edge edge : edges()) {
            weight = weight + edge.weight;
        }
        return weight;
    }
}

/**
 * Union find Algorithm
 */
class UnionFind {
    private final int[] id;
    private final int[] weight;

    public UnionFind(int size) {
        id = new int[size];
        weight = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }

    public boolean connected(int v, int w) {
        return find(v) == find(w);
    }

    private int find(int v) {
        while (v != id[v]) {
            id[v] = id[id[v]];
            v = id[v];
        }
        return v;
    }

    public void union(int v, int w) {
        int rootV = find(v);
        int rootW = find(w);
        if (rootV == rootW) {
            return;
        }
        if (weight[v] < weight[w]) {
            id[rootV] = rootW;
            weight[rootW] += weight[rootV];
        } else {
            id[rootW] = rootV;
            weight[rootV] += weight[rootW];
        }
    }
}

/**
 * WeightedGraph data structure
 */
class WeightedGraph {
    private final int numNodes;
    private final List<List<Edge>> graph;

    public WeightedGraph(int numNodes) {
        this.numNodes = numNodes;
        graph = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public void addEdge(int v, int w, int weight) {
        graph.get(v).add(new Edge(v, w, weight));
    }

    public Iterator<Edge> edges(int v) {
        return graph.get(v).iterator();
    }

    public int size() {
        return numNodes;
    }

    static class Edge implements Comparable<Edge> {
        public int v;
        public int w;
        public int weight;

        public Edge(int v, int w, int weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "v=" + v +
                    ", w=" + w +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }
}

class KruskalMinimumSpanningTreeRunner {
    public static void main(String[] args) {
        WeightedGraph weightedGraph = new WeightedGraph(9);
        weightedGraph.addEdge(7, 6, 1);
        weightedGraph.addEdge(8, 2, 2);
        weightedGraph.addEdge(6, 5, 2);
        weightedGraph.addEdge(0, 1, 4);
        weightedGraph.addEdge(2, 5, 4);
        weightedGraph.addEdge(8, 6, 6);
        weightedGraph.addEdge(2, 3, 7);
        weightedGraph.addEdge(7, 8, 7);
        weightedGraph.addEdge(0, 7, 8);
        weightedGraph.addEdge(1, 2, 8);
        weightedGraph.addEdge(3, 4, 9);
        weightedGraph.addEdge(5, 4, 10);
        weightedGraph.addEdge(1, 7, 11);
        weightedGraph.addEdge(3, 5, 14);
        KruskalMinimumSpanningTree kruskalMinimumSpanningTree = new KruskalMinimumSpanningTree(weightedGraph);
        int weight = kruskalMinimumSpanningTree.mstWeight();
        if (weight == 0) {
            System.out.println("MST could not be created from a give graph. Graph is MST forest instead.");
        } else {
            System.out.printf("MST weight: %d\n", weight);
            System.out.println(kruskalMinimumSpanningTree.edges().toString());
        }
    }
}
