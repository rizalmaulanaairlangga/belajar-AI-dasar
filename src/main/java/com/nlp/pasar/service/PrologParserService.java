// package layanan pemrosesan
package com.nlp.pasar.service;

import com.nlp.pasar.model.ParseResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// layanan untuk menghubungkan java dengan mesin prolog
public class PrologParserService {

    // pola untuk validasi atom prolog agar aman
    private static final Pattern SAFE_ATOM = Pattern.compile("[a-z][a-z0-9_]*");
    private final Path prologFile;
    private final String swiplCommand;

    public PrologParserService(Path prologFile) {
        this(prologFile, "swipl");
    }

    public PrologParserService(Path prologFile, String swiplCommand) {
        this.prologFile = prologFile;
        this.swiplCommand = swiplCommand;
    }

    // fungsi utama untuk memproses input teks menjadi hasil parse
    public ParseResult parse(String input) {
        // mengubah input menjadi token kata
        List<String> tokens = tokenize(input);
        
        // validasi jika hasil tokenisasi kosong
        if (tokens.isEmpty()) {
            return new ParseResult(
                    false,
                    input,
                    tokens,
                    "-",
                    "-",
                    "",
                    "Input kosong.",
                    -1
            );
        }

        // menggabungkan token menjadi list string yang dipisahkan koma untuk prolog
        String tokenList = tokens.stream()
                .map(this::toPrologTerm)
                .collect(Collectors.joining(","));

        // menyusun query goal prolog
        String goal = String.format("parse_sentence([%s],Tree),writeln(Tree)", tokenList);

        // menyusun daftar argumen perintah untuk menjalankan swipl
        List<String> command = Arrays.asList(
                swiplCommand,
                "-q", // mode diam tanpa pesan selamat datang
                "-s", // memuat file prolog
                prologFile.toAbsolutePath().toString(),
                "-g", // menjalankan goal yang ditentukan
                goal,
                "-t", // berhenti setelah selesai
                "halt"
        );

        // konfigurasi proses eksternal
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(false);

        try {
            // menjalankan proses eksternal
            Process process = pb.start();

            // menunggu proses selesai dengan batas waktu maksimal 10 detik
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                // paksa berhenti jika melebihi batas waktu
                process.destroyForcibly();
                return new ParseResult(
                        false,
                        input,
                        tokens,
                        goal,
                        "-",
                        "",
                        "Prolog timeout.",
                        -1
                );
            }

            // membaca output standar dan error dari proses
            String stdout = read(process);
            String stderr = readError(process);
            int exitCode = process.exitValue();

            // menentukan keberhasilan berdasarkan kode keluar dan isi output
            boolean success = exitCode == 0 && !stdout.isBlank();

            // mengembalikan hasil pemrosesan lengkap
            return new ParseResult(
                    success,
                    input,
                    tokens,
                    goal,
                    stdout.trim(),
                    stdout,
                    stderr,
                    exitCode
            );
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ParseResult(
                    false,
                    input,
                    tokens,
                    goal,
                    "-",
                    "",
                    e.getMessage(),
                    -1
            );
        }
    }

    // memecah kalimat menjadi token kata kecil
    private List<String> tokenize(String input) {
        // membersihkan input: lowercase dan hapus tanda baca agar sinkron dengan Prolog
        String cleaned = input
                .toLowerCase(Locale.ROOT)
                .replaceAll("[\"'.?!,;:()\\-]", " ") // hapus tanda baca termasuk tanda kutip
                .replaceAll("\\s+", " ")             // satukan spasi ganda
                .trim();

        if (cleaned.isEmpty()) {
            return List.of();
        }

        String[] parts = cleaned.split(" ");
        List<String> tokens = new ArrayList<>();
        for (String part : parts) {
            if (!part.isBlank()) {
                tokens.add(part.trim());
            }
        }
        return tokens;
    }

    // konversi token ke format yang valid bagi prolog
    private String toPrologTerm(String token) {
        // jika berupa angka, biarkan apa adanya
        if (token.matches("\\d+")) {
            return token;
        }
        // jika aman sebagai atom prolog, biarkan apa adanya
        if (SAFE_ATOM.matcher(token).matches()) {
            return token;
        }
        // tambahkan kutipan tunggal dan escape jika mengandung karakter spesial
        return "'" + token.replace("'", "''") + "'";
    }

    private String read(Process process) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    private String readError(Process process) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}