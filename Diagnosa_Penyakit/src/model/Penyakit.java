package model;

import java.util.Map;

// representasi penyakit beserta gejala dan bobotnya
public class Penyakit {

    private String kode; // kode unik penyakit (contoh: P1)
    private String nama; // nama penyakit
    private String jenis; // jenis penyakit (infeksi / non-infeksi)

    // relasi gejala dan bobotnya
    // key   = kode gejala (G1, G2, ...)
    // value = bobot pentingnya gejala
    private Map<String, Integer> bobotGejala;

    // constructor untuk inisialisasi penyakit
    public Penyakit(String kode, String nama, String jenis, Map<String, Integer> bobotGejala) {
        this.kode = kode;
        this.nama = nama;
        this.jenis = jenis;
        this.bobotGejala = bobotGejala;
    }

    // mengambil nama penyakit
    public String getNama() {
        return nama;
    }

    // mengambil jenis penyakit
    public String getJenis() {
        return jenis;
    }

    // mengambil relasi gejala + bobot
    public Map<String, Integer> getBobotGejala() {
        return bobotGejala;
    }
}