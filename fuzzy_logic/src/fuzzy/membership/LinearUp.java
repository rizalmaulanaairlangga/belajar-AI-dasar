package src.fuzzy.membership;

// kelas untuk implementasi fungsi keanggotaan linear naik
public class LinearUp implements MembershipFunction {

    // variabel untuk menyimpan titik awal dan titik akhir domain
    private final double start;
    private final double end;

    // konstruktor untuk menentukan nilai start dan end
    public LinearUp(double start, double end) {

        this.start = start;
        this.end = end;
    }

    // implementasi perhitungan nilai keanggotaan linear naik
    @Override
    public double calculate(double x) {

        // jika x kurang dari atau sama dengan start, nilai keanggotaan adalah 0
        if (x <= start) {
            return 0;
        }

        // jika x lebih dari atau sama dengan end, nilai keanggotaan adalah 1
        if (x >= end) {
            return 1;
        }

        // rumus interpolasi linear untuk nilai di antara start dan end
        return (x - start) / (end - start);
    }
}