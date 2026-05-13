package src.app;

import src.fuzzy.model.FuzzyResult;
import src.fuzzy.service.AgeFuzzyService;
import src.fuzzy.service.TemperatureFuzzyService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// kelas utama untuk menjalankan program logika fuzzy
public class Main {

    public static void main(String[] args) {

        // mengatur locale ke US agar pemisah desimal menggunakan titik
        Locale.setDefault(Locale.US);

        // inisialisasi scanner untuk menerima input dari user
        Scanner scanner = new Scanner(System.in);

        // inisialisasi layanan fuzzy untuk umur dan temperatur
        AgeFuzzyService ageService =
                new AgeFuzzyService();

        TemperatureFuzzyService temperatureService =
                new TemperatureFuzzyService();

        // perulangan utama program
        while (true) {

            System.out.println("\n=== LOGIKA FUZZY ===");
            System.out.println("1. Input Umur");
            System.out.println("2. Input Temperatur");
            System.out.println("0. Keluar");

            System.out.print("Pilih menu: ");
            String choice = scanner.nextLine();

            // blok try-catch untuk menangani kesalahan input non-angka
            try {
                switch (choice) {

                    case "1":

                        System.out.print("Masukkan umur: ");
                        // mengambil input umur dan mengubahnya ke tipe double
                        double age = Double.parseDouble(
                                scanner.nextLine()
                        );

                        // melakukan fuzzifikasi pada input umur
                        List<FuzzyResult> ageResults =
                                ageService.fuzzify(age);

                        // menampilkan hasil fuzzifikasi umur
                        printResult("UMUR", age, ageResults);

                        break;

                    case "2":

                        System.out.print("Masukkan temperatur: ");
                        // mengambil input temperatur dan mengubahnya ke tipe double
                        double temperature = Double.parseDouble(
                                scanner.nextLine()
                        );

                        // melakukan fuzzifikasi pada input temperatur
                        List<FuzzyResult> temperatureResults =
                                temperatureService.fuzzify(temperature);

                        // menampilkan hasil fuzzifikasi temperatur
                        printResult(
                                "TEMPERATUR",
                                temperature,
                                temperatureResults
                        );

                        break;

                    case "0":

                        // keluar dari program
                        System.out.println("Program selesai.");
                        return;

                    default:

                        // pesan jika menu yang dipilih tidak tersedia
                        System.out.println("Menu tidak valid.");
                }
            } catch (NumberFormatException e) {
                // menangani error jika input yang dimasukkan bukan angka
                System.out.println("Error: masukkan harus berupa angka.");
            }
        }
    }

    // metode untuk mencetak hasil fuzzifikasi ke layar
    private static void printResult(
            String variableName,
            double input,
            List<FuzzyResult> results
    ) {

        System.out.println(
                "\nHasil fuzzy " +
                        variableName +
                        " = " +
                        input
        );

        System.out.println("--------------------------------");

        // mencari hasil dengan nilai keanggotaan tertinggi (dominan)
        FuzzyResult highest = results.get(0);

        for (FuzzyResult result : results) {

            // mencetak label, nilai, dan persentase tiap himpunan fuzzy
            System.out.printf(
                    "%-12s = %.4f (%.2f%%)%n",
                    result.getLabel(),
                    result.getValue(),
                    result.getPercentage()
            );

            // membandingkan untuk mencari nilai yang paling dominan
            if (result.getValue() > highest.getValue()) {
                highest = result;
            }
        }

        System.out.println("--------------------------------");

        // mencetak himpunan yang paling dominan
        System.out.printf(
                "Dominan : %s (%.2f%%)%n",
                highest.getLabel(),
                highest.getPercentage()
        );
    }
}