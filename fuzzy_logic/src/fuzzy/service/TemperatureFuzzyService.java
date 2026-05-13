package src.fuzzy.service;

import src.fuzzy.membership.LinearDown;
import src.fuzzy.membership.LinearUp;
import src.fuzzy.membership.Triangle;
import src.fuzzy.model.FuzzyResult;

import java.util.List;

// kelas layanan untuk memproses variabel fuzzy temperatur
public class TemperatureFuzzyService {

    // inisialisasi variabel fuzzy temperatur dengan beberapa himpunan fuzzy
    private final FuzzyVariable variable =
            new FuzzyVariable()

                    // himpunan dingin menggunakan kurva linear turun
                    .add(
                            "DINGIN",
                            new LinearDown(15, 20)
                    )

                    // himpunan sejuk menggunakan kurva segitiga
                    .add(
                            "SEJUK",
                            new Triangle(15, 20, 25)
                    )

                    // himpunan normal menggunakan kurva segitiga
                    .add(
                            "NORMAL",
                            new Triangle(20, 25, 30)
                    )

                    // himpunan hangat menggunakan kurva segitiga
                    .add(
                            "HANGAT",
                            new Triangle(25, 30, 35)
                    )

                    // himpunan panas menggunakan kurva linear naik
                    .add(
                            "PANAS",
                            new LinearUp(30, 35)
                    );

    // metode untuk melakukan fuzzifikasi pada nilai temperatur
    public List<FuzzyResult> fuzzify(
            double temperature 
    ) {

        return variable.fuzzify(temperature);
    }
} 