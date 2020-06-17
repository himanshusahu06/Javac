import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Below algorithm runs faster than Dijkstra Algorithm when graph is direct acyclic (DAG)
 * 1. topologically sort the graph
 * 2. in the topological sort order, find the source
 * 3. once source is found, adjust the distance of adjacent nodes
 * 4. repeat step 3 for each node which is either vertex or reachable from vertex
 */
public class SingleSourceShortestPathForDirectedAcyclicGraph {
    private final DirectedWeightedGraph graph;
    private final int source;
    private final int[] parent;

    public SingleSourceShortestPathForDirectedAcyclicGraph(DirectedWeightedGraph graph, int source) {
        this.graph = graph;
        this.source = source;
        this.parent = new int[graph.size()];
        Arrays.fill(this.parent, -1);
        doSingleSourceShortestPathForDAG();
    }

    private void doSingleSourceShortestPathForDAG() {
        List<Integer> topologicalOrdering = new TopologicalSort(graph).sort();
        int index = 0;
        // find the source in the topological sort order
        while (index < graph.size() && topologicalOrdering.get(index) != source) {
            index++;
        }
        int[] distFromSource = new int[graph.size()];
        Arrays.fill(distFromSource, Integer.MAX_VALUE);
        parent[source] = -1;
        distFromSource[source] = 0;

        while (index < topologicalOrdering.size()) {
            int vertex = topologicalOrdering.get(index);
            if (vertex == source || parent[vertex] != -1) {
                // for each node which is either vertex or reachable from vertex
                relaxEdge(vertex, distFromSource);
            }
            index++;
        }
    }

    private void relaxEdge(int v, int[] distFromSource) {
        for (int w = 0; w < graph.size(); w++) {
            int edgeWeight = graph.edges(v)[w];
            if (edgeWeight > 0 && distFromSource[v] + edgeWeight < distFromSource[w]) {
                distFromSource[w] = distFromSource[v] + edgeWeight;
                parent[w] = v;
            }
        }
    }

    public List<SingleSourceShortestPathForDirectedAcyclicGraph.DirectedWeightedGraph.DirectedEdge> getShortestPath(int destination) {
        Stack<SingleSourceShortestPathForDirectedAcyclicGraph.DirectedWeightedGraph.DirectedEdge> stack = new Stack<>();
        while (parent[destination] != -1) {
            int v = parent[destination];
            int w = destination;
            int edge = graph.edges(v)[w];
            stack.push(new SingleSourceShortestPathForDirectedAcyclicGraph.DirectedWeightedGraph.DirectedEdge(v, w, edge));
            destination = parent[destination];
        }
        Collections.reverse(stack);
        return stack;
    }

    public int getShortestPathWeight(int destination) {
        return getShortestPath(destination).stream().mapToInt(e -> e.weight).sum();
    }


    static class TopologicalSort {
        private final DirectedWeightedGraph graph;

        public TopologicalSort(DirectedWeightedGraph graph) {
            this.graph = graph;
        }

        public List<Integer> sort() {
            boolean[] visited = new boolean[graph.size()];
            Stack<Integer> stack = new Stack<>();
            for (int v = 0; v < graph.size(); v++) {
                if (!visited[v]) {
                    sort(v, visited, stack);
                }
            }
            Collections.reverse(stack);
            return stack;
        }

        private void sort(int v, boolean[] visited, Stack<Integer> stack) {
            visited[v] = true;
            for (int w = 0; w < graph.size(); w++) {
                int edge = graph.edges(v)[w];
                if (edge > 0 && !visited[w]) {
                    sort(w, visited, stack);
                }
            }
            stack.push(v);
        }
    }

    static class DirectedWeightedGraph {
        private final int numNodes;
        private final int[][] graph;

        public DirectedWeightedGraph(int numNodes) {
            this.numNodes = numNodes;
            this.graph = new int[numNodes][numNodes];
        }

        public void addEdge(int v, int w, int weight) {
            graph[v][w] = weight;
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


class SingleSourceShortestPathForDirectedAcyclicGraphRunner {
    public static void main(String[] args) {
        SingleSourceShortestPathForDirectedAcyclicGraph.DirectedWeightedGraph weightedGraph
                = new SingleSourceShortestPathForDirectedAcyclicGraph.DirectedWeightedGraph(8);
        weightedGraph.addEdge(0, 1, 5);
        weightedGraph.addEdge(0, 4, 9);
        weightedGraph.addEdge(0, 7, 8);
        weightedGraph.addEdge(1, 7, 4);
        weightedGraph.addEdge(1, 3, 15);
        weightedGraph.addEdge(1, 2, 12);
        weightedGraph.addEdge(2, 3, 3);
        weightedGraph.addEdge(2, 6, 11);
        weightedGraph.addEdge(3, 6, 9);
        weightedGraph.addEdge(4, 7, 5);
        weightedGraph.addEdge(4, 5, 4);
        weightedGraph.addEdge(4, 6, 20);
        weightedGraph.addEdge(5, 2, 1);
        weightedGraph.addEdge(5, 6, 13);
        weightedGraph.addEdge(7, 2, 7);
        weightedGraph.addEdge(7, 5, 6);
        SingleSourceShortestPathForDirectedAcyclicGraph algorithm = new SingleSourceShortestPathForDirectedAcyclicGraph(weightedGraph, 0);
        for (int v = 0; v < weightedGraph.size(); v++) {
            System.out.printf("[%d] : weight: %d, path: %s\n"
                    , v, algorithm.getShortestPathWeight(v), algorithm.getShortestPath(v).toString());
        }
    }
}
