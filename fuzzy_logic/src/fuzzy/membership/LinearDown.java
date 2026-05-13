package src.fuzzy.membership;

// kelas untuk implementasi fungsi keanggotaan linear turun
public class LinearDown implements MembershipFunction {

    // variabel untuk menyimpan titik awal dan titik akhir domain
    private final double start;
    private final double end;

    // konstruktor untuk menentukan nilai start dan end
    public LinearDown(double start, double end) {

        this.start = start;
        this.end = end;
    }

    // implementasi perhitungan nilai keanggotaan linear turun
    @Override
    public double calculate(double x) {

        // jika x kurang dari atau sama dengan start, nilai keanggotaan adalah 1
        if (x <= start) {
            return 1;
        }

        // jika x lebih dari atau sama dengan end, nilai keanggotaan adalah 0
        if (x >= end) {
            return 0;
        }

        // rumus interpolasi linear untuk nilai di antara start dan end
        return (end - x) / (end - start);
    }
}