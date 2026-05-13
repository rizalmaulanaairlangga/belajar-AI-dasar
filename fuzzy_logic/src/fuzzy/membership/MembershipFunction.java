package src.fuzzy.membership;

// interface untuk fungsi keanggotaan fuzzy
public interface MembershipFunction {

    // metode untuk menghitung nilai keanggotaan berdasarkan input x
    double calculate(double x);
}