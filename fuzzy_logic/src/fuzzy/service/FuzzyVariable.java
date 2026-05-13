package src.fuzzy.service;

import src.fuzzy.membership.MembershipFunction;
import src.fuzzy.model.FuzzyResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// kelas untuk mengelola variabel fuzzy dan proses fuzzifikasi
public class FuzzyVariable {

    // map untuk menyimpan himpunan fuzzy (label dan fungsinya)
    private final Map<String, MembershipFunction>
            memberships = new LinkedHashMap<>();

    // metode untuk menambahkan himpunan fuzzy baru ke dalam variabel
    public FuzzyVariable add(
            String label,
            MembershipFunction function
    ) {

        memberships.put(label, function);

        return this;
    }

    // metode untuk melakukan proses fuzzifikasi dari nilai input crisp
    public List<FuzzyResult> fuzzify(double input) {

        List<FuzzyResult> results =
                new ArrayList<>();

        // melakukan iterasi untuk setiap himpunan fuzzy yang ada
        for (Map.Entry<String, MembershipFunction> entry
                : memberships.entrySet()) {

            // menghitung nilai derajat keanggotaan
            double value =
                    entry.getValue().calculate(input);

            // menyimpan hasil perhitungan ke dalam list
            results.add(
                    new FuzzyResult(
                            entry.getKey(),
                            value
                    )
            );
        }

        return results;
    }
}