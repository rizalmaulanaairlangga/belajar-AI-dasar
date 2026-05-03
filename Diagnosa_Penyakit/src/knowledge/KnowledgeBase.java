package knowledge;

import java.util.*;
import model.*;

// berisi semua data gejala dan penyakit
public class KnowledgeBase {

    // mengambil seluruh daftar gejala
    public static List<Gejala> getGejalaList() {
        List<Gejala> list = new ArrayList<>();

        // ================= influenza =================
        list.add(new Gejala("G1", "demam",
                "suhu 37.5–39°C, meningkat bertahap, menggigil ringan"));
        list.add(new Gejala("G2", "batuk",
                "batuk kering/berdahak ringan, frekuensi >3x/jam"));
        list.add(new Gejala("G3", "pilek",
                "hidung tersumbat, berair, bersin berulang"));
        list.add(new Gejala("G4", "sakit tenggorokan",
                "nyeri saat menelan, kemerahan ringan"));

        // ================= demam berdarah =================
        list.add(new Gejala("G5", "demam tinggi",
                "suhu >39°C mendadak, pola 2–7 hari naik turun"));
        list.add(new Gejala("G6", "nyeri sendi",
                "nyeri pada otot dan tulang"));
        list.add(new Gejala("G7", "ruam kulit",
                "bintik merah, tidak hilang saat ditekan"));
        list.add(new Gejala("G8", "mual",
                "muntah >2x/hari, nafsu makan turun"));

        // ================= diabetes =================
        list.add(new Gejala("G9", "sering haus",
                "minum lebih dari 3 liter per hari"));
        list.add(new Gejala("G10", "sering buang air kecil",
                "lebih dari 8x/hari, terutama malam"));
        list.add(new Gejala("G11", "mudah lelah",
                "energi cepat habis, lemas"));
        list.add(new Gejala("G12", "luka sulit sembuh",
                "penyembuhan lebih dari 2 minggu, infeksi berulang"));

        // ================= hipertensi =================
        list.add(new Gejala("G13", "sakit kepala",
                "terjadi pagi hari, berdenyut di belakang kepala"));
        list.add(new Gejala("G14", "pusing",
                "terasa berputar saat perubahan posisi"));
        list.add(new Gejala("G15", "penglihatan kabur",
                "gangguan visual sementara"));
        list.add(new Gejala("G16", "mimisan",
                "perdarahan dari hidung"));

        return list;
    }

    // mengambil daftar penyakit beserta bobot gejalanya
    public static List<Penyakit> getPenyakitList() {
        List<Penyakit> list = new ArrayList<>();

        // ================= influenza =================
        Map<String, Integer> flu = new HashMap<>();

        // bobot tiap gejala untuk influenza
        flu.put("G1", 1);
        flu.put("G2", 1);
        flu.put("G3", 1);
        flu.put("G4", 1);

        // menambahkan penyakit influenza ke list
        list.add(new Penyakit("P1", "influenza", JenisPenyakit.INFEKSI, flu));

        // ================= demam berdarah =================
        Map<String, Integer> dbd = new HashMap<>();

        dbd.put("G5", 2);
        dbd.put("G6", 2);
        dbd.put("G7", 2);
        dbd.put("G8", 1);

        list.add(new Penyakit("P2", "demam berdarah", JenisPenyakit.INFEKSI, dbd));

        // ================= diabetes =================
        Map<String, Integer> diabetes = new HashMap<>();

        diabetes.put("G9", 2);
        diabetes.put("G10", 2);
        diabetes.put("G11", 1);
        diabetes.put("G12", 2);

        list.add(new Penyakit("P3", "diabetes", JenisPenyakit.NON_INFEKSI, diabetes));

        // ================= hipertensi =================
        Map<String, Integer> hipertensi = new HashMap<>();

        hipertensi.put("G13", 2);
        hipertensi.put("G14", 1);
        hipertensi.put("G15", 2);
        hipertensi.put("G16", 1);

        list.add(new Penyakit("P4", "hipertensi", JenisPenyakit.NON_INFEKSI, hipertensi));

        return list; // mengembalikan semua penyakit
    }

    // mapping kode gejala ke nama gejala
    public static Map<String, String> getGejalaMap() {
        Map<String, String> map = new HashMap<>();

        // isi map dengan pasangan kode -> nama
        for (Gejala g : getGejalaList()) {
            map.put(g.getKode(), g.getNama());
        }

        return map; // digunakan untuk konversi kode ke nama di UI
    }
}