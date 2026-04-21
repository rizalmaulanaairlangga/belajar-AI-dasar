package gui;

import engine.RuleEngine;
import engine.WeightedEngine;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import knowledge.KnowledgeBase;
import model.Gejala;
import model.Penyakit;

public class MainFrame extends JFrame {

    // menyimpan semua checkbox gejala
    private List<JCheckBox> checkBoxes = new ArrayList<>();

    // area untuk menampilkan hasil diagnosa
    private JTextArea hasilArea;

    public MainFrame() {
        setTitle("sistem pakar diagnosa penyakit");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // membagi layout kiri (input) dan kanan (hasil)
        JSplitPane splitPane = new JSplitPane();

        splitPane.setLeftComponent(createInputPanel());
        splitPane.setRightComponent(createResultPanel());

        splitPane.setDividerLocation(400);

        add(splitPane);
    }

    // panel kiri untuk input gejala
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // membuat kelompok gejala berdasarkan penyakit
        content.add(createGroup("influenza", "G1", "G2", "G3", "G4"));
        content.add(createGroup("demam berdarah", "G5", "G6", "G7", "G8"));
        content.add(createGroup("diabetes", "G9", "G10", "G11", "G12"));
        content.add(createGroup("hipertensi", "G13", "G14", "G15", "G16"));

        JScrollPane scroll = new JScrollPane(content);
        panel.add(scroll, BorderLayout.CENTER);

        // panel bawah berisi tombol
        JPanel bottom = new JPanel(new GridLayout(1, 2));

        JButton btnDiagnosa = new JButton("diagnosa");
        JButton btnClear = new JButton("clear");

        bottom.add(btnDiagnosa);
        bottom.add(btnClear);

        panel.add(bottom, BorderLayout.SOUTH);

        // aksi tombol diagnosa
        btnDiagnosa.addActionListener(e -> prosesDiagnosa());

        // reset semua checkbox dan hasil
        btnClear.addActionListener(e -> {
            for (JCheckBox cb : checkBoxes) {
                cb.setSelected(false);
            }
            hasilArea.setText("");
        });

        return panel;
    }

    // membuat grup checkbox berdasarkan kode gejala
    private JPanel createGroup(String title, String... kodeGejala) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new TitledBorder(title));

        // ambil semua gejala dari knowledge base
        for (Gejala g : KnowledgeBase.getGejalaList()) {

            // filter sesuai kode yg termasuk group
            for (String kode : kodeGejala) {
                if (g.getKode().equals(kode)) {

                    JCheckBox cb = new JCheckBox("<html><b>" + g.getNama() + "</b><br/>" + g.getDeskripsi() + "</html>");
                    cb.setActionCommand(g.getKode()); // simpan kode gejala
                    checkBoxes.add(cb); // simpan ke list utama
                    panel.add(cb);
                }
            }
        }

        return panel;
    }

    // panel kanan untuk hasil diagnosa
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        hasilArea = new JTextArea();
        hasilArea.setEditable(false); // tidak bisa diedit user
        hasilArea.setFont(new Font("monospaced", Font.PLAIN, 14));

        panel.setBorder(new TitledBorder("hasil diagnosa"));

        panel.add(new JScrollPane(hasilArea), BorderLayout.CENTER);

        return panel;
    }

    // proses utama diagnosa
    private void prosesDiagnosa() {

        Set<String> input = new HashSet<>();

        // ambil semua gejala yg dipilih user
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                input.add(cb.getActionCommand());
            }
        }

        // ambil data penyakit dari knowledge base
        List<Penyakit> penyakitList = KnowledgeBase.getPenyakitList();

        // jalankan dua metode diagnosa
        var ruleResults = RuleEngine.diagnose(penyakitList, input);
        var weightResults = WeightedEngine.diagnose(penyakitList, input);

        // tampilkan hasil ke UI
        tampilkanHasil(ruleResults, weightResults);
    }

    // menampilkan hasil diagnosa ke text area
    private void tampilkanHasil(List<RuleEngine.Result> rule, List<WeightedEngine.Result> weight) {

        StringBuilder sb = new StringBuilder();

        // ===== metode aturan =====
        sb.append("metode aturan:\n");

        boolean adaCocok = false;

        // cek apakah ada penyakit yg memenuhi semua gejala
        for (RuleEngine.Result r : rule) {
            if (r.cocok) {
                adaCocok = true;
                sb.append("- cocok: ").append(r.penyakit.getNama()).append("\n");
            }
        }

        // jika tidak ada yg cocok, tampilkan alasan
        if (!adaCocok) {
            sb.append("- tidak ada yang cocok\n");

            for (RuleEngine.Result r : rule) {
                sb.append("  > ").append(r.penyakit.getNama()).append(" kurang: ")
                .append(r.kurang.size()).append(" gejala\n");
            }
        }

        sb.append("\n");

        // ===== metode bobot =====
        sb.append("metode bobot (ranking):\n");

        // mapping kode gejala ke nama gejala
        Map<String, String> gejalaMap = KnowledgeBase.getGejalaMap();

        // tampilkan ranking penyakit
        for (int i = 0; i < weight.size(); i++) {
            WeightedEngine.Result r = weight.get(i);

            sb.append((i + 1)).append(". ")
            .append(r.penyakit.getNama())
            .append(" = ")
            .append(String.format("%.2f", r.persen))
            .append("%\n");

            sb.append("   kontribusi:\n");

            // tampilkan gejala yg berkontribusi dan berapa bobotnya
            for (String g : r.kontribusi.keySet()) {

                String namaGejala = gejalaMap.getOrDefault(g, g);

                sb.append("   - ")
                .append(namaGejala)
                .append(" (bobot: ")
                .append(r.kontribusi.get(g))
                .append(")\n");
            }

            sb.append("\n");
        }

        // tampilkan ke text area
        hasilArea.setText(sb.toString());
    }
}