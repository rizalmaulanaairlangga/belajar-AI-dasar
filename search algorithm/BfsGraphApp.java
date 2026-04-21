import java.util.*;

public class BfsGraphApp {

    // menyimpan state bfs: node sekarang dan jalur dari root ke node tersebut
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

        // representasi graph menggunakan adjacency list
        Map<String, List<String>> graph = new LinkedHashMap<>();

        // inisialisasi hubungan antar node
        graph.put("S", Arrays.asList("A", "B"));
        graph.put("A", Arrays.asList("S", "B", "D"));
        graph.put("B", Arrays.asList("S", "A", "C"));
        graph.put("C", Arrays.asList("B", "D", "E"));
        graph.put("D", Arrays.asList("A", "C", "F"));
        graph.put("E", Arrays.asList("C", "Z"));
        graph.put("F", Arrays.asList("D"));
        graph.put("Z", Arrays.asList("E"));

        // jalankan bfs dari S ke Z
        bfsQueueStyle(graph, "S", "Z");
    }

    public static void bfsQueueStyle(Map<String, List<String>> graph, String start, String goal) {

        // queue berisi state (node + path)
        Queue<State> queue = new LinkedList<>();

        // inisialisasi dengan node awal
        queue.add(new State(start, new ArrayList<>(List.of(start))));

        int level = 1;

        System.out.println("=== BFS EARLY STOP ===\n");

        // proses selama queue tidak kosong
        while (!queue.isEmpty()) {

            // jumlah node pada level saat ini
            int size = queue.size();

            // tampilkan isi queue (hanya node)
            System.out.println("Level " + level + ": " + queueNodesOnly(queue));

            // iterasi per level
            for (int i = 0; i < size; i++) {

                // ambil state dari queue
                State current = queue.poll();

                // ambil tetangga node sekarang
                List<String> neighbors = new ArrayList<>(graph.getOrDefault(current.node, new ArrayList<>()));

                // urutkan agar konsisten kiri ke kanan
                Collections.sort(neighbors);

                // ekspansi setiap tetangga
                for (String next : neighbors) {

                    // hindari mengunjungi node yang sudah ada di path yang sama
                    if (!current.path.contains(next)) {

                        // buat path baru
                        List<String> newPath = new ArrayList<>(current.path);
                        newPath.add(next);

                        // jika goal ditemukan saat ekspansi, langsung berhenti
                        if (next.equals(goal)) {
                            System.out.println(
                                "Expand " + current.node +
                                " via " + current.path +
                                " -> " + next + " (GOAL)"
                            );

                            System.out.println("\n=== HASIL ===");
                            System.out.println("Path ditemukan: " + newPath);

                            // hentikan seluruh proses
                            return;
                        }

                        // masukkan state baru ke queue
                        queue.add(new State(next, newPath));
                    }
                }

                // tampilkan hasil ekspansi node saat ini
                System.out.println(
                    "Expand " + current.node +
                    " via " + current.path +
                    " | Queue: " + queueNodesOnly(queue)
                );
            }

            System.out.println("----------------------------");
            level++;
        }

        // jika goal tidak ditemukan
        System.out.println("Goal tidak ditemukan.");
    }

    // helper untuk mengambil isi queue hanya berupa node (tanpa path)
    private static String queueNodesOnly(Queue<State> queue) {
        List<String> nodes = new ArrayList<>();
        for (State s : queue) {
            nodes.add(s.node);
        }
        return nodes.toString();
    }
}