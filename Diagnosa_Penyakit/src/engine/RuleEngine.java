package engine;

import java.util.*;
import model.Penyakit;

public class RuleEngine {

    // class hasil untuk menyimpan evaluasi tiap penyakit
    public static class Result {
        public Penyakit penyakit; // objek penyakit yg dicek
        public boolean cocok;     // true jika semua gejala terpenuhi
        public List<String> kurang = new ArrayList<>(); // daftar gejala yg belum terpenuhi
    }

    // fungsi utama untuk diagnosa berbasis aturan
    public static List<Result> diagnose(List<Penyakit> penyakitList, Set<String> inputGejala) {

        List<Result> results = new ArrayList<>(); // menampung hasil semua penyakit

        // iterasi tiap penyakit
        for (Penyakit p : penyakitList) {

            Result r = new Result();
            r.penyakit = p; // set penyakit yg sedang dicek

            // cek setiap gejala yg dimiliki penyakit
            for (String g : p.getBobotGejala().keySet()) {

                // jika gejala tidak ada di input user -> dianggap kurang
                if (!inputGejala.contains(g)) {
                    r.kurang.add(g);
                }
            }

            // jika tidak ada gejala yg kurang -> cocok
            r.cocok = r.kurang.isEmpty();

            results.add(r); // simpan hasil evaluasi penyakit
        }

        return results; // kembalikan semua hasil
    }
}