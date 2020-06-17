import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Dijkstra algorithm is applicable for both directed and undirected graph
 */
public class DijkstraSingleSourceShortestPathAlgorithm {
    private final WeightedGraph graph;
    private final int[] parent;
    private final int source;

    public DijkstraSingleSourceShortestPathAlgorithm(WeightedGraph graph, int source) {
        this.graph = graph;
        this.source = source;
        this.parent = new int[graph.size()];
        doDijkstra();
    }

    private void doDijkstra() {
        int[] distFromSource = new int[graph.size()];
        boolean[] visited = new boolean[graph.size()];
        Arrays.fill(distFromSource, Integer.MAX_VALUE);

        distFromSource[source] = 0;
        parent[source] = -1;

        for (int i = 0; i < graph.size(); i++) {
            int v = getVertexWithShortestPathFromSource(visited, distFromSource);
            visited[v] = true;

            for (int w = 0; w < graph.size(); w++) {
                int connectingEdgeWeight = graph.edges(v)[w];
                if (connectingEdgeWeight <= 0 || visited[w]) {
                    continue;
                }
                if (connectingEdgeWeight + distFromSource[v] < distFromSource[w]) {
                    distFromSource[w] = connectingEdgeWeight + distFromSource[v];
                    parent[w] = v;
                }
            }
        }
    }

    private int getVertexWithShortestPathFromSource(boolean[] visited, int[] distFromSource) {
        int vertex = -1;
        int minWeight = Integer.MAX_VALUE;
        for (int v = 0; v < graph.size(); v++) {
            if (!visited[v] && distFromSource[v] < minWeight) {
                minWeight = distFromSource[v];
                vertex = v;
            }
        }
        return vertex;
    }

    public List<WeightedGraph.DirectedEdge> getShortestPath(int destination) {
        Stack<WeightedGraph.DirectedEdge> stack = new Stack<>();
        while (parent[destination] != -1) {
            int v = parent[destination];
            int w = destination;
            int edge = graph.edges(v)[w];
            stack.push(new WeightedGraph.DirectedEdge(v, w, edge));
            destination = parent[destination];
        }
        Collections.reverse(stack);
        return stack;
    }

    public int getShortestPathWeight(int destination) {
        return getShortestPath(destination).stream().mapToInt(e -> e.weight).sum();
    }

    static class WeightedGraph {
        private final int numNodes;
        private final int[][] graph;
        private final boolean isDirected;

        public WeightedGraph(int numNodes, boolean isDirected) {
            this.numNodes = numNodes;
            this.isDirected = isDirected;
            this.graph = new int[numNodes][numNodes];
        }

        public void addEdge(int v, int w, int weight) {
            graph[v][w] = weight;
            if (!isDirected) {
                graph[w][v] = weight;
            }
        }

        public int[] edges(int v) {
            return graph[v];
        }

        public int size() {
            return numNodes;
        }

        static class DirectedEdge {
            private final int v;
            private final int w;
            private final int weight;

            public DirectedEdge(int v, int w, int weight) {
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
                        "}";
            }
        }
    }
}

class DijkstraSingleSourceShortestPathAlgorithmRunner {
    public static void main(String[] args) {
        DijkstraSingleSourceShortestPathAlgorithm.WeightedGraph weightedGraph
                = new DijkstraSingleSourceShortestPathAlgorithm.WeightedGraph(9, false);
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
        DijkstraSingleSourceShortestPathAlgorithm algorithm = new DijkstraSingleSourceShortestPathAlgorithm(weightedGraph, 0);
        for (int v = 0; v < weightedGraph.size(); v++) {
            System.out.printf("[%d] : weight: %d, path: %s\n"
                    , v, algorithm.getShortestPathWeight(v), algorithm.getShortestPath(v).toString());
        }
    }
}
