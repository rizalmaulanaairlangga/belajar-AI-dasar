import gui.MainFrame;

// entry point program
public class MainApp {

    // method utama yang dijalankan pertama kali saat program start
    public static void main(String[] args) {

        // membuat instance window utama (GUI)
        // setVisible(true) digunakan untuk menampilkan frame ke layar
        new MainFrame().setVisible(true);
    }
}