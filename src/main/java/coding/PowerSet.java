import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    private final String str;
    private final List<String> powerSet;
    public PowerSet(String str) {
        this.str = str;
        this.powerSet = new ArrayList<>();
        compute();
    }

    private void compute() {
        int size = (int) Math.pow(2, str.length());
        for (int i = 1; i < size; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < str.length(); j++) {
                if ((i & (1 << j)) > 0) {
                    builder.append(str.charAt(j));
                }
            }
            powerSet.add(builder.toString());
        }
    }

    public void print() {
        System.out.println(powerSet);
    }
}

class PowerSetRunner {
    public static void main(String[] args) {
        PowerSet powerSet = new PowerSet("abcd");
        PowerSetRecursion powerSetRecursion = new PowerSetRecursion("abcd");
        powerSet.print();
        powerSetRecursion.print();
    }
}

class PowerSetRecursion {
    private final String str;
    private final List<String> powerSet;
    public PowerSetRecursion(String str) {
        this.str = str;
        this.powerSet = new ArrayList<>();
        dfs(0, "");
    }

    private void dfs(int index, String curr) {
        if (!curr.isEmpty()) {
            powerSet.add(curr);
        }
        for (int i = index; i < str.length(); i++) {
            dfs(i + 1, curr + str.charAt(i));
        }
    }

    public void print() {
        System.out.println(powerSet);
    }
}
