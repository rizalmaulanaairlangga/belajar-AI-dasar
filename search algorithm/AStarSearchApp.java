import java.util.*;

public class AStarSearchApp {

    // menyimpan node, jalur, dan cost dari start (g)
    static class State {
        String node;
        List<String> path;
        int g; // cost dari start ke node ini

        // konstruktor state
        State(String node, List<String> path, int g) {
            this.node = node;
            this.path = path;
            this.g = g;
        }
    }

    public static void main(String[] args) {

        // graph berbobot (adjacency map)
        Map<String, Map<String, Integer>> graph = new LinkedHashMap<>();

        // inisialisasi node
        graph.put("S", new LinkedHashMap<>());
        graph.put("A", new LinkedHashMap<>());
        graph.put("B", new LinkedHashMap<>());
        graph.put("C", new LinkedHashMap<>());
        graph.put("D", new LinkedHashMap<>());
        graph.put("E", new LinkedHashMap<>());
        graph.put("F", new LinkedHashMap<>());
        graph.put("Z", new LinkedHashMap<>());

        // tambah edge dua arah beserta bobot
        addEdge(graph, "S", "B", 3);
        addEdge(graph, "S", "A", 4);
        addEdge(graph, "B", "A", 5);
        addEdge(graph, "B", "C", 3);
        addEdge(graph, "A", "D", 5);
        addEdge(graph, "C", "D", 5);
        addEdge(graph, "C", "E", 2);
        addEdge(graph, "D", "F", 3);
        addEdge(graph, "E", "Z", 2);

        // heuristic: estimasi jarak ke goal
        Map<String, Double> h = new HashMap<>();
        h.put("S", 10.0);
        h.put("A", 8.0);
        h.put("B", 6.0);
        h.put("C", 3.7);
        h.put("D", 4.5);
        h.put("E", 2.0);
        h.put("F", 2.0);
        h.put("Z", 0.0);

        // jalankan a*
        aStar(graph, h, "S", "Z");
    }

    // menambahkan edge dua arah
    private static void addEdge(Map<String, Map<String, Integer>> graph,
                                String from, String to, int weight) {
        graph.get(from).put(to, weight);
        graph.get(to).put(from, weight);
    }

    public static void aStar(Map<String, Map<String, Integer>> graph,
                             Map<String, Double> h,
                             String start,
                             String goal) {

        // queue menyimpan semua cabang pencarian
        Queue<State> queue = new LinkedList<>();

        // mulai dari node awal dengan cost 0
        queue.add(new State(start, new ArrayList<>(List.of(start)), 0));

        System.out.println("=== a* search ===\n");

        // loop selama masih ada node
        while (!queue.isEmpty()) {

            // ambil node terdepan
            State currentState = queue.poll();
            String current = currentState.node;
            List<String> path = currentState.path;
            int g = currentState.g;

            // tampilkan kondisi saat ini
            System.out.println("ambil: " + current + " via " + path + " | g(n) = " + g);
            System.out.println("queue: " + queueNodes(queue));

            // cek apakah sudah goal
            if (current.equals(goal)) {
                System.out.println("\n=== hasil ===");
                System.out.println("path ditemukan: " + path);
                return;
            }

            // ambil tetangga
            Map<String, Integer> neighbors = graph.get(current);

            // list kandidat hasil ekspansi
            List<State> candidates = new ArrayList<>();

            for (String next : neighbors.keySet()) {

                // hindari node yang sudah ada di path
                if (!path.contains(next)) {

                    // hitung cost baru (g)
                    int cost = neighbors.get(next);
                    int newG = g + cost;

                    // buat path baru
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(next);

                    candidates.add(new State(next, newPath, newG));
                }
            }

            // jika tidak ada kandidat, jalur buntu
            if (candidates.isEmpty()) {
                System.out.println("buntu, lanjut cabang lain...");
                System.out.println("-----------------------------------");
                continue;
            }

            // urutkan berdasarkan f(n) = g(n) + h(n)
            candidates.sort((a, b) -> {

                double fa = a.g + h.get(a.node);
                double fb = b.g + h.get(b.node);

                if (fa != fb) return Double.compare(fa, fb);
                return a.node.compareTo(b.node);
            });

            // tampilkan kandidat beserta g, h, f
            System.out.println("candidates:");
            for (State s : candidates) {
                double f = s.g + h.get(s.node);

                System.out.println(
                    "  " + s.node +
                    " via " + s.path +
                    " | g(n)=" + s.g +
                    ", h(n)=" + h.get(s.node) +
                    ", f(n)=" + f
                );
            }

            // early stop jika goal ditemukan saat ekspansi
            for (State s : candidates) {
                if (s.node.equals(goal)) {
                    System.out.println("dipilih: " + s.node + " (GOAL)");

                    System.out.println("\n=== hasil ===");
                    System.out.println("path ditemukan: " + s.path);
                    return;
                }
            }

            // masukkan kandidat ke depan queue (best dulu)
            LinkedList<State> q = (LinkedList<State>) queue;

            // dibalik agar urutan tetap benar saat addFirst
            for (int i = candidates.size() - 1; i >= 0; i--) {
                q.addFirst(candidates.get(i));
            }

            // tampilkan queue setelah update
            System.out.println("queue update: " + queueNodes(queue));
            System.out.println("-----------------------------------");
        }

        // jika goal tidak ditemukan
        System.out.println("goal tidak ditemukan.");
    }

    // helper untuk menampilkan isi queue (node saja)
    private static String queueNodes(Queue<State> queue) {
        List<String> list = new ArrayList<>();
        for (State s : queue) {
            list.add(s.node);
        }
        return list.toString();
    }
}