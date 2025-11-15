package UTSSMT5;

import UTSSMT5.view.MainFrame;
import javax.swing.UIManager;


public class app {
    
    public static void main(String[] args) {
        // Mengatur tampilan (Look and Feel) agar sesuai dengan OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Menjalankan MainFrame (GUI)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}