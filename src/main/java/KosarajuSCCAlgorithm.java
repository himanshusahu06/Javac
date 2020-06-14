import java.util.*;

/**
 * Kosaraju Strongly Connected Component Algorithm
 * 1. do DFS in reverse of graph and get the ordering
 * 2. Following the same ordering, run dfs and strongly connect all nodes in the same dfs
 */
class KosarajuSCCAlgorithm {
    private final Graph graph;
    private int[] component;
    private int componentCount;

    public KosarajuSCCAlgorithm(Graph graph) {
        this.graph = graph;
        stronglyConnect();
    }

    public boolean isStronglyConnected(int nodeA, int nodeB) {
        return component[nodeA] == component[nodeB];
    }

    public List<List<Integer>> printStronglyConnectedComponents() {
        List<List<Integer>> scc = new ArrayList<>(componentCount);
        for (int i = 0; i < componentCount; i++) {
            scc.add(new ArrayList<>());
        }
        for (int i = 0; i < component.length; i++) {
           scc.get(component[i]).add(i);
        }
        return scc;
    }

    private void stronglyConnect() {
        this.component = new int[graph.size()];
        this.componentCount = 0;
        Arrays.fill(component, -1);

        boolean[] visited = new boolean[graph.size()];
        Iterator<Integer> iterator = new TopologicalSort(graph.reverse()).sort();
        while (iterator.hasNext()) {
            int node = iterator.next();
            if (!visited[node]) {
                doConnect(visited, node);
                componentCount++;
            }
        }
    }

    private void doConnect(boolean[] visited, int node) {
        visited[node] = true;
        component[node] = componentCount;
        for (int adj = 0; adj < graph.size(); adj++) {
            if (!graph.getGraph()[node][adj]) {
                continue;
            }
            if (!visited[adj]) {
                doConnect(visited, adj);
            }
        }
    }
}

class Graph {
    private final boolean[][] graph;

    public Graph(int size) {
        this.graph = new boolean[size][size];
    }

    public boolean[][] getGraph() {
        return graph;
    }

    public void addEdge(int  v, int w) {
        assert  v <= graph.length-1 && w <= graph.length-1;
        graph[v][w] = true;
    }

    public int size() {
        return graph.length;
    }

    public Graph reverse() {
        Graph reverseGraph = new Graph(graph.length);
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                reverseGraph.graph[i][j] = graph[j][i];
            }
        }
        return reverseGraph;
    }
}

/**
 * Topological sorting class for int graph
 */
class TopologicalSort {
    private final Graph graph;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
    }

    /**
     * return iterator of topological sorted order
     *
     * @return stack iterator
     */
    public Iterator<Integer> sort() {
        boolean[] visited = new boolean[graph.size()];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                dfs(visited, stack, i);
            }
        }
        return stack.iterator();
    }

    private void dfs(boolean[] visited, Stack<Integer> stack, int node) {
        visited[node] = true;
        for (int adj = 0; adj < graph.size(); adj++) {
            if (!graph.getGraph()[node][adj]) {
                continue;
            }
            if (!visited[adj]) {
                dfs(visited, stack, adj);
            }
        }
        stack.push(node);
    }
}

class Application {
    public static void main(String[] args) {
        // input from https://github.com/thinkphp/strongly-connected-components
        Graph graph = new Graph(16);
        graph.addEdge(1, 1);
        graph.addEdge(1, 3);

        graph.addEdge(2, 1);

        graph.addEdge(3, 2);
        graph.addEdge(3, 5);

        graph.addEdge(4, 1);
        graph.addEdge(4, 2);
        graph.addEdge(4, 12);
        graph.addEdge(4, 13);

        graph.addEdge(5, 8);
        graph.addEdge(5, 6);

        graph.addEdge(6, 7);
        graph.addEdge(6, 8);
        graph.addEdge(6, 10);

        graph.addEdge(7, 10);

        graph.addEdge(8, 9);
        graph.addEdge(8, 10);

        graph.addEdge(9, 5);
        graph.addEdge(9, 11);

        graph.addEdge(10, 9);
        graph.addEdge(10, 11);
        graph.addEdge(10, 14);

        graph.addEdge(11, 12);
        graph.addEdge(11, 14);

        graph.addEdge(12, 13);

        graph.addEdge(13, 11);
        graph.addEdge(13, 15);

        graph.addEdge(14, 13);

        graph.addEdge(15, 14);

        KosarajuSCCAlgorithm kosarajuSCCAlgorithm = new KosarajuSCCAlgorithm(graph);
        System.out.println("Following are strongly connected components in given graph ");
        for (List<Integer> scc: kosarajuSCCAlgorithm.printStronglyConnectedComponents()) {
            System.out.println(scc.toString());
        }
    }
}
