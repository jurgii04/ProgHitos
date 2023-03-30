import javax.swing.*;

public class emergente extends JFrame {
    public emergente() {
        String password = JOptionPane.showInputDialog(null, "Introduce la contraseña:", "Etrada", JOptionPane.QUESTION_MESSAGE);

        if (password == null || !password.equals("damocles")) {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else {
            ventana ventana = new ventana();
        }
    }
}
