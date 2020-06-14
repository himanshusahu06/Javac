import java.util.ArrayList;
import java.util.List;

/*
Perform a topological sort of the DAG, then check if successive vertices
in the sort are connected in the graph. If so, the topological sort gives
a Hamiltonian path. On the other hand, if there is a Hamiltonian path,
then the path gives a topological sort of the DAG.
*/
public class HamiltonianPathInDirectedGraph {
    private final Graph g;

    public HamiltonianPathInDirectedGraph(Graph graph) {
        this.g = graph;
    }

    public boolean pathExists() {
        List<Integer> path = getHamiltonianPath();
        return (path != null && !path.isEmpty());
    }

    public List<Integer> getHamiltonianPath() {
        List<Integer> topologicalOrder = new TopologicalSort(g).sort();
        for (int i = 0; i < topologicalOrder.size() - 1; i++) {
            int current = topologicalOrder.get(i);
            int next = topologicalOrder.get(i + 1);
            if (!g.getGraph()[current][next]) {
                return null;
            }
        }
        return topologicalOrder;
    }

    static class TopologicalSort {
        private Graph g;

        public TopologicalSort(Graph graph) {
            this.g = graph;
        }

        public List<Integer> sort() {
            boolean[] visited = new boolean[g.size()];
            Stack<Integer> topologicalSort = new Stack<>();
            for (int v = 0; v < g.size(); v++) {
                if (!visited[v]) {
                    sort(visited, topologicalSort, v);
                }
            }
            List<Integer> order = new ArrayList<>();
            while (!topologicalSort.isEmpty()) {
                order.add(topologicalSort.pop());
            }
            return order;
        }

        private void sort(boolean[] visited, Stack<Integer> stack, int v) {
            visited[v] = true;
            for (int w = 0; w < g.size(); w++) {
                if (!g.getGraph()[v][w]) {
                    continue;
                }
                if (!visited[w]) {
                    sort(visited, stack, w);
                }
            }
            stack.push(v);
        }
    }
}

class HamiltonianPathDirectedGraphRunner {
    public static void main(String[] args) {
//        Graph g = new Graph(4);
//        g.addEdge(3, 2);
//        g.addEdge(3, 2);
//        g.addEdge(2, 1);
//        g.addEdge(3, 0);
        Graph g = new Graph(3);
        g.addEdge(2, 2);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 2);
        HamiltonianPathInDirectedGraph hamiltonianPath = new HamiltonianPathInDirectedGraph(g);
        if (hamiltonianPath.pathExists()) {
            System.out.println("Hamiltonian path: " + hamiltonianPath.getHamiltonianPath().toString());
        } else {
            System.out.println("Hamiltonian path doesn't exist.");
        }
    }
}
