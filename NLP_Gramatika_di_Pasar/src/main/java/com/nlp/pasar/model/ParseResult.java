// package model data aplikasi
package com.nlp.pasar.model;

import java.util.List;

// model data untuk menampung hasil pemrosesan nlp
public class ParseResult {
    // variabel penampung status dan detail parse
    private final boolean success; // status keberhasilan parse
    private final String input; // teks input asli
    private final List<String> tokens; // daftar kata hasil tokenisasi
    private final String prologQuery; // query yang dikirim ke prolog
    private final String parseTree; // struktur pohon hasil parse
    private final String stdout; // output standar dari swipl
    private final String stderr; // output error dari swipl
    private final int exitCode; // kode keluar proses swipl

    // konstruktor untuk inisialisasi ParseResult
    public ParseResult(
            boolean success,
            String input,
            List<String> tokens,
            String prologQuery,
            String parseTree,
            String stdout,
            String stderr,
            int exitCode
    ) {
        this.success = success;
        this.input = input;
        this.tokens = tokens;
        this.prologQuery = prologQuery;
        this.parseTree = parseTree;
        this.stdout = stdout;
        this.stderr = stderr;
        this.exitCode = exitCode;
    }

    public boolean isSuccess() {
        return success;
    }

    // memformat output untuk tampilan antarmuka
    public String formatForUi() {
        // inisialisasi stringbuilder untuk menyusun teks laporan
        StringBuilder sb = new StringBuilder();
        
        // menambahkan informasi dasar input dan token
        sb.append("Input      : ").append(input).append("\n");
        sb.append("Token      : ").append(tokens).append("\n");
        
        // menambahkan informasi query dan hasil pohon parse
        sb.append("Prolog     : ").append(prologQuery).append("\n");
        sb.append("Hasil      : ").append(success ? parseTree : "GAGAL PARSE").append("\n");
        
        // menambahkan kode keluar proses
        sb.append("Exit Code  : ").append(exitCode).append("\n");

        // menambahkan output standar jika ada isi
        if (stdout != null && !stdout.isBlank()) {
            sb.append("Stdout     : ").append(stdout.trim()).append("\n");
        }
        
        // menambahkan output error jika ada isi
        if (stderr != null && !stderr.isBlank()) {
            sb.append("Stderr     : ").append(stderr.trim()).append("\n");
        }
        return sb.toString();
    }
}