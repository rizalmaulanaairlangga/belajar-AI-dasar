import java.util.*;

public class HillClimbingApp {

    // menyimpan node aktif dan jalur dari start ke node tersebut
    static class State {
        String node;
        List<String> path;

        // konstruktor untuk inisialisasi state
        State(String node, List<String> path) {
            this.node = node;
            this.path = path;
        }
    }

    public static void main(String[] args) {

        // graph berbobot menggunakan adjacency map
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

        // menambahkan edge dua arah beserta bobot
        addEdge(graph, "S", "B", 3);
        addEdge(graph, "S", "A", 4);
        addEdge(graph, "B", "A", 5);
        addEdge(graph, "B", "C", 3);
        addEdge(graph, "A", "D", 5);
        addEdge(graph, "C", "D", 5);
        addEdge(graph, "C", "E", 2);
        addEdge(graph, "D", "F", 3);
        addEdge(graph, "E", "Z", 2);

        // menjalankan hill climbing dari S ke Z
        hillClimbingWithQueue(graph, "S", "Z");
    }

    // helper untuk menambahkan edge dua arah
    private static void addEdge(Map<String, Map<String, Integer>> graph, String from, String to, int weight) {
        graph.get(from).put(to, weight);
        graph.get(to).put(from, weight);
    }

    public static void hillClimbingWithQueue(Map<String, Map<String, Integer>> graph, String start, String goal) {

        // queue menyimpan semua kemungkinan cabang (frontier)
        Queue<State> queue = new LinkedList<>();

        // mulai dari node awal
        queue.add(new State(start, new ArrayList<>(List.of(start))));

        System.out.println("=== hill climbing + queue cabang ===\n");

        // proses selama queue masih ada
        while (!queue.isEmpty()) {

            // ambil state terdepan
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

            // ambil tetangga node sekarang
            Map<String, Integer> neighbors = graph.get(current);

            // daftar kandidat hasil ekspansi
            List<State> candidates = new ArrayList<>();

            for (String next : neighbors.keySet()) {
                // hindari node yang sudah ada di jalur yang sama
                if (!path.contains(next)) {
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

            // urutkan kandidat berdasarkan bobot terkecil lalu alfabet
            candidates.sort((a, b) -> {
                // tampilkan kandidat beserta jalur dan bobot
                System.out.println("candidates:");
                for (State s : candidates) {
                    System.out.println(
                        "  " + s.node +
                        " via " + s.path +
                        " | weight = " + neighbors.get(s.node)
                    );
                }

                int wa = neighbors.get(a.node);
                int wb = neighbors.get(b.node);

                if (wa != wb) return Integer.compare(wa, wb);
                return a.node.compareTo(b.node);
            });

            // cek apakah goal sudah muncul saat ekspansi
            for (State s : candidates) {
                if (s.node.equals(goal)) {
                    for (State c : candidates) {
                        if (c.node.equals(goal)) {
                            System.out.println("dipilih: " + c.node + " (GOAL)");

                            System.out.println("\n=== hasil ===");
                            System.out.println("path ditemukan: " + c.path);
                            return;
                        }
                    }

                    System.out.println("\n=== hasil ===");
                    System.out.println("path ditemukan: " + s.path);
                    return;
                }
            }

            // masukkan kandidat ke queue
            // semua kandidat dimasukkan ke depan agar best diproses lebih dulu
            LinkedList<State> q = (LinkedList<State>) queue;

            // dibalik agar urutan tetap benar saat addFirst
            for (int i = candidates.size() - 1; i >= 0; i--) {
                q.addFirst(candidates.get(i));
            }

            // tampilkan isi queue setelah update
            System.out.println("queue update: " + queueNodes(queue));
            System.out.println("-----------------------------------");
        }

        // jika goal tidak ditemukan
        System.out.println("goal tidak ditemukan.");
    }

    // helper untuk menampilkan isi queue hanya berupa node
    private static String queueNodes(Queue<State> queue) {
        List<String> list = new ArrayList<>();
        for (State s : queue) {
            list.add(s.node);
        }
        return list.toString();
    }
}