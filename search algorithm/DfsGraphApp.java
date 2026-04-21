import java.util.*;

public class DfsGraphApp {

    // menyimpan node sekarang dan jalur dari start ke node tersebut
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

        // representasi graph dengan adjacency list
        // urutan disesuaikan agar traversal mengikuti ilustrasi dfs
        Map<String, List<String>> graph = new LinkedHashMap<>();

        graph.put("S", Arrays.asList("A", "B"));
        graph.put("A", Arrays.asList("B", "D", "S"));
        graph.put("B", Arrays.asList("C", "A", "S"));
        graph.put("C", Arrays.asList("E", "D", "B"));
        graph.put("D", Arrays.asList("C", "F", "A"));
        graph.put("E", Arrays.asList("Z", "C"));
        graph.put("F", Arrays.asList("D"));
        graph.put("Z", Arrays.asList("E"));

        // jalankan dfs dari S ke Z
        dfsStackStyle(graph, "S", "Z");
    }

    public static void dfsStackStyle(Map<String, List<String>> graph, String start, String goal) {

        // stack untuk dfs, menyimpan state (node + path)
        Deque<State> stack = new ArrayDeque<>();

        // inisialisasi dengan node awal
        stack.push(new State(start, new ArrayList<>(Arrays.asList(start))));

        System.out.println("=== dfs early stop ===\n");

        // selama stack masih ada, isi
        while (!stack.isEmpty()) {

            // ambil node teratas (lifo)
            State current = stack.pop();

            // tampilkan node yang sedang dikunjungi
            System.out.println("kembali/kunjungi: " + current.node + " via " + current.path);

            // jika node sekarang adalah tujuan, langsung selesai
            if (current.node.equals(goal)) {
                System.out.println("\n=== hasil ===");
                System.out.println("path ditemukan: " + current.path);
                return;
            }

            // ambil semua tetangga node sekarang
            List<String> neighbors = new ArrayList<>(graph.getOrDefault(current.node, new ArrayList<>()));

            // urutkan agar konsisten kiri ke kanan
            Collections.sort(neighbors);

            // menampung anak yang valid untuk diekspansi
            List<String> validChildren = new ArrayList<>();

            for (String next : neighbors) {

                // hindari node yang sudah ada di path yang sama (cycle)
                if (!current.path.contains(next)) {

                    // buat jalur baru
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(next);

                    // jika goal ditemukan saat ekspansi, langsung berhenti
                    if (next.equals(goal)) {
                        System.out.println("ekspansi " + current.node + " -> " + next + " (GOAL)");

                        System.out.println("\n=== hasil ===");
                        System.out.println("path ditemukan: " + newPath);
                        return;
                    }

                    // simpan node yang valid untuk dimasukkan ke stack
                    validChildren.add(next);
                }
            }

            // push ke stack secara terbalik agar urutan dfs tetap benar
            for (int i = validChildren.size() - 1; i >= 0; i--) {
                String next = validChildren.get(i);

                List<String> newPath = new ArrayList<>(current.path);
                newPath.add(next);

                stack.push(new State(next, newPath));
            }

            // tampilkan hasil ekspansi dan isi stack
            System.out.println("ekspansi " + current.node + " -> " + validChildren);
            System.out.println("stack: " + stackNodesOnly(stack));
            System.out.println("-----------------------------------");
        }

        // jika tidak menemukan goal
        System.out.println("goal tidak ditemukan.");
    }

    // mengambil isi stack hanya berupa node (tanpa path)
    private static String stackNodesOnly(Deque<State> stack) {
        List<String> nodes = new ArrayList<>();
        for (State s : stack) {
            nodes.add(s.node);
        }
        return nodes.toString();
    }
}