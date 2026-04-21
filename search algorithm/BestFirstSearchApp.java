import java.util.*;

public class BestFirstSearchApp {

    // menyimpan node saat ini dan jalur dari start ke node tersebut
    static class State {
        String node;
        List<String> path;

        // konstruktor state
        State(String node, List<String> path) {
            this.node = node;
            this.path = path;
        }
    }

    public static void main(String[] args) {

        // graph tanpa bobot (relasi antar node)
        Map<String, List<String>> graph = new LinkedHashMap<>();

        // adjacency list (tetangga tiap node)
        graph.put("S", Arrays.asList("B", "A"));
        graph.put("A", Arrays.asList("S", "D"));
        graph.put("B", Arrays.asList("S", "A", "C"));
        graph.put("C", Arrays.asList("B", "D", "E"));
        graph.put("D", Arrays.asList("A", "C", "F"));
        graph.put("E", Arrays.asList("C", "Z"));
        graph.put("F", Arrays.asList("D"));
        graph.put("Z", Arrays.asList("E"));

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

        // jalankan best first search dari S ke Z
        bestFirstSearch(graph, h, "S", "Z");
    }

    public static void bestFirstSearch(Map<String, List<String>> graph,
                                       Map<String, Double> h,
                                       String start,
                                       String goal) {

        // queue untuk menyimpan cabang (frontier)
        Queue<State> queue = new LinkedList<>();

        // mulai dari node awal
        queue.add(new State(start, new ArrayList<>(List.of(start))));

        System.out.println("=== best first search (greedy) ===\n");

        // loop selama masih ada node di queue
        while (!queue.isEmpty()) {

            // ambil node terdepan
            State currentState = queue.poll();
            String current = currentState.node;
            List<String> path = currentState.path;

            // tampilkan node yang sedang diproses
            System.out.println("ambil: " + current + " via " + path);
            System.out.println("queue: " + queueNodes(queue));

            // jika sudah mencapai goal
            if (current.equals(goal)) {
                System.out.println("\n=== hasil ===");
                System.out.println("path ditemukan: " + path);
                return;
            }

            // daftar kandidat dari node sekarang
            List<State> candidates = new ArrayList<>();

            // ambil semua tetangga
            for (String next : graph.getOrDefault(current, new ArrayList<>())) {

                // hindari loop dalam jalur yang sama
                if (!path.contains(next)) {

                    // buat jalur baru
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(next);

                    candidates.add(new State(next, newPath));
                }
            }

            // jika tidak ada kandidat, jalur buntu
            if (candidates.isEmpty()) {
                System.out.println("buntu, lanjut cabang lain...");
                System.out.println("-----------------------------------");
                continue;
            }

            // urutkan kandidat berdasarkan heuristic terkecil
            candidates.sort((a, b) -> {
                double ha = h.get(a.node);
                double hb = h.get(b.node);

                if (ha != hb) return Double.compare(ha, hb);
                return a.node.compareTo(b.node);
            });

            // tampilkan kandidat beserta heuristic
            System.out.println("candidates:");
            for (State s : candidates) {
                System.out.println(
                    "  " + s.node +
                    " via " + s.path +
                    " | h(n) = " + h.get(s.node)
                );
            }

            // cek apakah goal ditemukan saat ekspansi
            for (State s : candidates) {
                if (s.node.equals(goal)) {

                    System.out.println("dipilih: " + s.node + " (GOAL)");

                    System.out.println("\n=== hasil ===");
                    System.out.println("path ditemukan: " + s.path);
                    return;
                }
            }

            // masukkan semua kandidat ke depan queue
            // kandidat terbaik akan diproses lebih dulu
            LinkedList<State> q = (LinkedList<State>) queue;

            // dibalik agar urutan tetap sesuai setelah addFirst
            for (int i = candidates.size() - 1; i >= 0; i--) {
                q.addFirst(candidates.get(i));
            }

            // tampilkan kondisi queue setelah update
            System.out.println("queue update: " + queueNodes(queue));
            System.out.println("-----------------------------------");
        }

        // jika goal tidak ditemukan
        System.out.println("goal tidak ditemukan.");
    }

    // helper untuk menampilkan isi queue (hanya node)
    private static String queueNodes(Queue<State> queue) {
        List<String> list = new ArrayList<>();
        for (State s : queue) {
            list.add(s.node);
        }
        return list.toString();
    }
}