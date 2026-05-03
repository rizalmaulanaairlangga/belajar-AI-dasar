// package antarmuka pengguna
package com.nlp.pasar.ui;

import com.nlp.pasar.model.ParseResult;
import com.nlp.pasar.service.PrologParserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.file.Path;

// kelas untuk antarmuka utama aplikasi menggunakan swing
public class MainFrame extends JFrame {

    // area input dan output teks
    private final JTextArea inputArea = new JTextArea(10, 40);
    private final JTextArea outputArea = new JTextArea(18, 40);
    private final PrologParserService parserService;

    // inisialisasi service dan komponen antarmuka
    public MainFrame() {
        this.parserService = new PrologParserService(Path.of("prolog", "market_parser.pl"));
        initUi();
    }

    // mengatur tata letak dan properti window
    private void initUi() {
        // pengaturan dasar judul, aksi tutup, dan ukuran window
        setTitle("NLP_Gramatika_di_Pasar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // pembuatan panel utama dengan layout border dan margin
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        // pembuatan label judul aplikasi di bagian atas
        JLabel title = new JLabel("NLP Gramatika di Pasar", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        root.add(title, BorderLayout.NORTH);

        // konfigurasi area input teks dengan font monospaced
        inputArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        // membungkus input area dengan scroll pane dan judul border
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Input Percakapan / Kalimat"));

        // konfigurasi area output teks (hanya baca)
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        // membungkus output area dengan scroll pane dan judul border
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Hasil Parsing"));

        // pembuatan tombol kontrol aplikasi
        JButton parseButton = new JButton("Parse");
        JButton clearButton = new JButton("Clear");
        JButton exampleButton = new JButton("Contoh");

        // menambahkan aksi klik untuk setiap tombol
        parseButton.addActionListener(e -> parseInput());
        clearButton.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
        });
        exampleButton.addActionListener(e -> setExample());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(parseButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exampleButton);

        JPanel left = new JPanel(new BorderLayout(8, 8));
        left.add(inputScroll, BorderLayout.CENTER);
        left.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, outputScroll);
        splitPane.setResizeWeight(0.45);
        root.add(splitPane, BorderLayout.CENTER);

        // menambahkan label kaki (footer) di bagian bawah aplikasi
        JLabel footer = new JLabel("Gunakan satu baris untuk satu ujaran. Cocok untuk dialog pasar.", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
    }

    // set contoh input untuk pengujian
    private void setExample() {
        inputArea.setText("""
                permisi
                berapa harga cabai ini
                bisa kurang
                saya mau beli dua kilo cabai
                terima kasih
                """);
    }

    // logika untuk memproses input teks
    private void parseInput() {
        // mengambil teks dari input area dan memvalidasi agar tidak kosong
        String text = inputArea.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan kalimat terlebih dahulu.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // memecah input menjadi baris-baris dan menyiapkan penampung hasil
        String[] lines = text.split("\\R+");
        StringBuilder result = new StringBuilder();

        // melakukan iterasi untuk setiap baris kalimat yang diinputkan
        int index = 1;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            // memanggil layanan parser prolog untuk memproses kalimat
            ParseResult parseResult = parserService.parse(trimmed);

            // menyusun laporan hasil parse ke dalam stringbuilder
            result.append("=== Ujaran ").append(index++).append(" ===\n");
            result.append(parseResult.formatForUi()).append("\n");
        }

        // menampilkan hasil akhir ke area output dan mengembalikan scroll ke atas
        outputArea.setText(result.toString());
        outputArea.setCaretPosition(0);
    }
}