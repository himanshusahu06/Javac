class UnionFind {
    Map<String, String> id = new HashMap<>();

    public boolean connected(String u, String v) {
        return find(u).equals(find(v));
    }

    public void union(String u, String v) {
        id.put(find(u), find(v));
    }

    private String find(String s) {
        while(!s.equals(id.getOrDefault(s, s))) {
            s = id.get(s);
        }
        return s;
    }
}
