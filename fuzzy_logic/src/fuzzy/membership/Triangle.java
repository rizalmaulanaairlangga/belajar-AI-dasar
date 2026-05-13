package src.fuzzy.membership;

// kelas untuk implementasi fungsi keanggotaan segitiga
public class Triangle implements MembershipFunction {

    // variabel untuk menyimpan titik kiri, tengah, dan kanan segitiga
    private final double left;
    private final double center;
    private final double right;

    // konstruktor untuk menentukan nilai left, center, dan right
    public Triangle(
            double left,
            double center,
            double right
    ) {

        this.left = left;
        this.center = center;
        this.right = right;
    }

    // implementasi perhitungan nilai keanggotaan segitiga
    @Override
    public double calculate(double x) {

        // jika x di luar rentang left dan right, nilai keanggotaan adalah 0
        if (x <= left || x >= right) {
            return 0;
        }

        // jika x tepat di titik center, nilai keanggotaan adalah 1
        if (x == center) {
            return 1;
        }

        // jika x berada di antara left dan center (sisi naik segitiga)
        if (x < center) {
            return (x - left) / (center - left);
        }

        // jika x berada di antara center dan right (sisi turun segitiga)
        return (right - x) / (right - center);
    }
}