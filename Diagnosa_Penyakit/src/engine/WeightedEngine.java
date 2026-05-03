package engine;

import java.util.*;
import model.Penyakit;

// hasil detail untuk weighted
public class WeightedEngine {

    // class hasil untuk menyimpan perhitungan bobot
    public static class Result {
        public Penyakit penyakit; // penyakit yg dihitung
        public int skor;          // total bobot yg cocok
        public int totalBobot;    // total seluruh bobot gejala penyakit
        public double persen;     // persentase kecocokan
        public Map<String, Integer> kontribusi = new LinkedHashMap<>(); // gejala yg berkontribusi
    }

    // fungsi diagnosa berbasis bobot
    public static List<Result> diagnose(List<Penyakit> penyakitList, Set<String> inputGejala) {

        List<Result> results = new ArrayList<>(); // menampung hasil semua penyakit

        // iterasi tiap penyakit
        for (Penyakit p : penyakitList) {

            Result r = new Result();
            r.penyakit = p;

            int total = 0; // total bobot semua gejala penyakit
            int skor = 0;  // total bobot yg cocok dengan input

            // iterasi setiap gejala + bobotnya
            for (Map.Entry<String, Integer> entry : p.getBobotGejala().entrySet()) {

                total += entry.getValue(); // akumulasi total bobot

                // jika gejala dipilih user -> tambah skor
                if (inputGejala.contains(entry.getKey())) {
                    skor += entry.getValue();

                    // simpan gejala yg berkontribusi
                    r.kontribusi.put(entry.getKey(), entry.getValue());
                }
            }

            r.totalBobot = total; // set total bobot
            r.skor = skor;        // set skor hasil

            // hitung persentase jika total tidak nol
            if (total > 0) {
                r.persen = (skor * 100.0) / total;
            }

            results.add(r); // simpan hasil penyakit
        }

        // urutkan dari persentase tertinggi ke terendah
        results.sort((a, b) -> Double.compare(b.persen, a.persen));

        return results; // kembalikan hasil ranking
    }
}