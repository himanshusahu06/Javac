    public class UF {
        Map<String, String> id = new HashMap<String, String>();

        public void union(String u, String v) {
            id.put(find(u), find(v));
        }

        private String find(String v) {
            while(!v.equals(id.computeIfAbsent(v, key -> key))) {
                v = id.get(v);
            }
            return v;
        }

        public boolean connected(String u, String v) {
            return find(u).equals(find(v));
        }
    }
