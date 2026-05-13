package src.fuzzy.model;

// kelas model untuk menyimpan hasil fuzzifikasi
public class FuzzyResult {

    // label untuk nama himpunan fuzzy dan nilai derajat keanggotaannya
    private final String label;
    private final double value;

    // konstruktor untuk inisialisasi label dan nilai
    public FuzzyResult(String label, double value) {

        this.label = label;
        this.value = value;
    }

    // getter untuk mendapatkan label
    public String getLabel() {
        return label;
    }

    // getter untuk mendapatkan nilai derajat keanggotaan
    public double getValue() {
        return value;
    }

    // metode untuk mendapatkan nilai dalam bentuk persentase
    public double getPercentage() {
        return value * 100;
    }
}