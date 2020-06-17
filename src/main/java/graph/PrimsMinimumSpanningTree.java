import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Prim's algorithm is applicable to undirected graph and can work with -ve weights
 * 1. create two set - mstSet and nonMstSet
 * 2. pick a random vertex
 * 3. loop until N-1 times
 * 4. pick the vertex from nonMstSet which is connected to minimum weighted cut edge
 * 4. include it in mstSet
 * 5. for each vertex adjacent to the current vertex, check if non Mst edges can be
 * connected to the mst Set in less weighted edge, if yes then update the priority
 * where priority is the edge in the cut edge with minimum weight and update the parent
 * of the vertex, parent indicates current node has cut edge with which mst vertex.
 */
public class PrimsAlgorithm {
    private final WeightedGraph graph;
    List<WeightedGraph.Edge> mstEdge;
    private final int[] priority;

    public PrimsAlgorithm(WeightedGraph graph) {
        this.graph = graph;
        this.mstEdge = new ArrayList<>();
        this.priority = new int[graph.size()];
        Arrays.fill(this.priority, Integer.MAX_VALUE);
        doMst();
    }

    private void doMst() {
        int[] parentNode = new int[graph.size()];
        boolean[] mstSet = new boolean[graph.size()];
        // STEP 1: set the initial vertex priority to zero to pick it up
        priority[0] = 0;
        // since it is starting vertex, it doesn't have any parent parentNode
        parentNode[0] = -1;
        for (int count = 0; count < graph.size() - 1; count++) {
            // STEP 3: find the vertex which is connected to mst set with minimum parentNode weight
            int vertex = getVertexConnectedWithMinimumWeightEdge(mstSet);
            // STEP 4: include it in mst
            mstSet[vertex] = true;
            // STEP 5: check if adjacent vertex can be reached via curr vertex, if yes, update the priority
            for (int w = 0; w < graph.size(); w++) {
                int connectingEdgeWeight = graph.edges(vertex)[w];
                if (connectingEdgeWeight <= 0 || mstSet[w]) {
                    // vertex is not connected or already part of mst set
                    continue;
                }
                // update the priority
                if (connectingEdgeWeight < priority[w]) {
                    priority[w] = connectingEdgeWeight;
                    parentNode[w] = vertex;
                }
            }
        }

        for (int i = 1; i < graph.size(); i++) {
            mstEdge.add(new WeightedGraph.Edge(i, parentNode[i], priority[i]));
        }
    }

    public List<WeightedGraph.Edge> edges() {
        return mstEdge;
    }

    public int mstWeight() {
        return mstEdge.stream().mapToInt(i -> i.weight).sum();
    }

    /**
     * find vertex connected to minimum weight edge which is not in the mst set.
     * priority is defined as minimum edge that connects an nonMst vertex to mst vertex.
     *
     * @param mstSet list of vertex which are already added to mst
     * @return vertex connected to minimum weight edge which is not in the mst set
     */
    private int getVertexConnectedWithMinimumWeightEdge(boolean[] mstSet) {
        int minVertex = -1;
        int minEdge = Integer.MAX_VALUE;
        for (int v = 0; v < priority.length; v++) {
            if (!mstSet[v] && priority[v] < minEdge) {
                minEdge = priority[v];
                minVertex = v;
            }
        }
        return minVertex;
    }

    static class WeightedGraph {
        private final int numNodes;
        private final int[][] graph;

        public WeightedGraph(int numNodes) {
            this.numNodes = numNodes;
            graph = new int[numNodes][numNodes];
        }

        public void addEdge(int v, int w, int weight) {
            graph[v][w] = weight;
            graph[w][v] = weight;
        }

        public int[] edges(int v) {
            return graph[v];
        }

        public int size() {
            return numNodes;
        }

        static class Edge {
            private int v;
            private int w;
            private int weight;

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
                        "}\n";
            }
        }
    }
}

class PrimsAlgorithmRunner {
    public static void main(String[] args) {
        PrimsAlgorithm.WeightedGraph weightedGraph = new PrimsAlgorithm.WeightedGraph(9);
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
        PrimsAlgorithm primsAlgorithm = new PrimsAlgorithm(weightedGraph);
        System.out.println("Weight of MST is: " + primsAlgorithm.mstWeight());
        System.out.println(primsAlgorithm.edges());
    }
}
