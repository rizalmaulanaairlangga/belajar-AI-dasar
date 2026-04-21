package model;

// representasi satu gejala
public class Gejala {
    private String kode; // kode unik gejala (contoh: G1)
    private String nama; // nama gejala (contoh: demam, batuk)
    private String deskripsi; // deskripsi detail (untuk pertanyaan)

    // constructor untuk inisialisasi gejala
    public Gejala(String kode, String nama, String deskripsi) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    // mengambil kode gejala
    public String getKode() {
        return kode;
    }

    // mengambil nama gejala
    public String getNama() {
        return nama;
    }

    // mengambil deskripsi gejala
    public String getDeskripsi() {
        return deskripsi;
    }
}