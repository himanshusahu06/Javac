import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Shortest Cycle in direct graph
 */
public class ShortestCycleInDirectedGraph {
    private final Graph graph;

    public ShortestCycleInDirectedGraph(Graph graph) {
        this.graph = graph;
    }

    public Iterator<Integer> getShortestCycle() {
        int minLength = graph.size() + 1;
        Stack<Integer> cycle = new Stack<>();
        Graph revGraph = graph.reverse();

        for (int v = 0; v < graph.size(); v++) {
            // do BFS in reverse graph
            BreadthFirstSearch bfs = new BreadthFirstSearch(revGraph, v);
            for (int w = 0; w < graph.size(); w++) {
                if (!graph.getGraph()[v][w]) {
                    continue;
                }
                // there is a direct path from v->w because w is adj of v
                if (bfs.hasPathTo(w) && (bfs.getPathLength(w) + 1) < minLength) {
                    /*
                      there is a path from v->w in reverse graph that means
                      there is a path from w->v in the original graph.
                      since there is edge from v->w and a path from v->w, there  is a cycle.
                     */
                    minLength = bfs.getPathLength(w) + 1;
                    cycle.clear();
                    cycle.addAll(bfs.getPathTo(w));
                }
            }
        }
        return cycle.iterator();
    }
}

/**
 * BFS class
 */
class BreadthFirstSearch {
    Graph graph;
    int root;
    int[] parent;

    public BreadthFirstSearch(Graph graph, int root) {
        this.graph = graph;
        this.root = root;
        doBfs();
    }

    public boolean hasPathTo(int w) {
        return parent[w] != w;
    }

    public int getPathLength(int w) {
        // path will be total number of edges from root to node
        return getPathTo(w).size();
    }

    public Stack<Integer> getPathTo(int w) {
        Stack<Integer> pathTo = new Stack<>();
        pathTo.push(w);
        while (w != parent[w]) {
            w = parent[w];
            pathTo.push(w);
        }
        return pathTo;
    }

    private void doBfs() {
        parent = new int[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            parent[i] = i;
        }

        boolean[] visited = new boolean[graph.size()];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int node = queue.remove();
            visited[node] = true;
            for (int adj = 0; adj < graph.size(); adj++) {
                if (graph.getGraph()[node][adj] && !visited[adj]) {
                    queue.add(adj);
                    parent[adj] = node;
                }
            }
        }
    }
}

class ShortestCycleInDirectedGraphRunner {
    public static void main(String[] args) {
        Graph graph = new Graph(13);

        graph.addEdge(4, 2);
//        graph.addEdge(2, 3);
        graph.addEdge(3, 2);
        graph.addEdge(6, 0);
        graph.addEdge(0, 1);
        graph.addEdge(2, 0);
        graph.addEdge(11, 12);
//        graph.addEdge(12, 9);
        graph.addEdge(9, 10);
        graph.addEdge(9, 11);
        graph.addEdge(7, 9);
        graph.addEdge(10, 12);
        graph.addEdge(11, 4);
//        graph.addEdge(4, 3);
        graph.addEdge(3, 5);
//        graph.addEdge(6, 8);
        graph.addEdge(8, 6);
        graph.addEdge(5, 4);
        graph.addEdge(0, 5);
        graph.addEdge(6, 4);
        graph.addEdge(6, 9);
        graph.addEdge(7, 7);

        ShortestCycleInDirectedGraph shortestCycleInDirectedGraph = new ShortestCycleInDirectedGraph(graph);
        Iterator<Integer> it = shortestCycleInDirectedGraph.getShortestCycle();
        int cycleSize = 0;
        while (it.hasNext()) {
            cycleSize++;
            System.out.println(it.next());
        }
        if (cycleSize == 0) {
            System.out.println("Graph is acyclic.");
        }
    }
}
