// package aplikasi nlp pasar
package com.nlp.pasar;

import com.nlp.pasar.ui.MainFrame;

import javax.swing.SwingUtilities;

// kelas utama untuk menjalankan aplikasi
public class Main {
    // titik masuk utama program
    public static void main(String[] args) {
        // menjalankan antarmuka grafis di thread khusus swing
        SwingUtilities.invokeLater(() -> {
            // inisialisasi frame utama aplikasi
            MainFrame frame = new MainFrame();
            // menampilkan window ke layar
            frame.setVisible(true);
        });
    }
}