package src.fuzzy.service;

import src.fuzzy.membership.LinearDown;
import src.fuzzy.membership.LinearUp;
import src.fuzzy.membership.Triangle;
import src.fuzzy.model.FuzzyResult;

import java.util.List;

// kelas layanan untuk memproses variabel fuzzy umur
public class AgeFuzzyService {

    // inisialisasi variabel fuzzy umur dengan beberapa himpunan fuzzy
    private final FuzzyVariable variable =
            new FuzzyVariable()

                    // himpunan muda menggunakan kurva linear turun
                    .add(
                            "MUDA",
                            new LinearDown(30, 40)
                    )

                    // himpunan parobaya menggunakan kurva segitiga
                    .add(
                            "PAROBAYA",
                            new Triangle(35, 45, 50)
                    )

                    // himpunan tua menggunakan kurva linear naik
                    .add(
                            "TUA",
                            new LinearUp(40, 50)
                    );

    // metode untuk melakukan fuzzifikasi pada nilai umur
    public List<FuzzyResult> fuzzify(double age) {

        return variable.fuzzify(age);
    }
}