<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class ventana extends JFrame implements ActionListener {

    JComboBox comboBox;
    JLabel label;
    JTextField textField;
    JCheckBox checkBox;

    public ventana() {
        JFrame frame = new JFrame("Hito3.4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 475);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout());

        JPanel panelN = new JPanel();
        panelN.setLayout(new BorderLayout());
        JPanel panelC = new JPanel();
        panelC.setLayout(new BorderLayout());
        JPanel panelCS = new JPanel();
        panelCS.setLayout(new GridLayout());
        JPanel panelS = new JPanel();

        comboBox = new JComboBox<String>(new String[] {
                "Jurgi chino😑.jpg", "Payasin.jpg", "messi.jpg"
        });
        comboBox.addActionListener(new ComboListener());
        comboBox.setPreferredSize(new Dimension(277, 50));
        panelN.add(comboBox, BorderLayout.WEST);

        label = new JLabel(new ImageIcon("Hito3.4\\src\\images\\Jurgi chino\uD83D\uDE11.jpg"));
        panelC.add(label, BorderLayout.WEST);

        checkBox = new JCheckBox("Guardar comentario");
        checkBox.setSelected(true);
        panelCS.add(checkBox, BorderLayout.SOUTH);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(150,30));
        panelCS.add(textField, BorderLayout.SOUTH);

        JButton button = new JButton("GUARDAR");
        panelS.add(button);
        button.addActionListener(this);

        panelC.add(panelCS, BorderLayout.SOUTH);
        contentPane.add(panelN, BorderLayout.NORTH);
        contentPane.add(panelC, BorderLayout.CENTER);
        contentPane.add(panelS,BorderLayout.SOUTH);

        frame.add(contentPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowOpened(e);
                JOptionPane.showMessageDialog(null, "¡Adiós!");

                // close the window
                dispose();
            }
        });

        frame.setVisible(true);
    }

    public void load_combo(){
        String img = comboBox.getSelectedItem().toString();
        String filename ="Hito3.4\\src\\images" + File.separator + img;
        label.setIcon(new ImageIcon(filename));
    }

    public void actionPerformed(ActionEvent e) {
        String nombre = comboBox.getSelectedItem().toString();
        String comentario = this.textField.getText();
        boolean selected = checkBox.isSelected();
        BufferedWriter textField = null;
        String filename ="Hito3.4\\src" + File.separator + nombre;
        try {
            textField = new BufferedWriter(new FileWriter(filename + ".txt", true));
            if (selected) {
                textField.write("Nombre:" + nombre + " Comentario:" + comentario + "\n");
            } else {
                textField.write(nombre + "\n");
            }
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            try {
                if (textField != null) {
                    textField.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    class ComboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            load_combo();
        }
    }

}
=======
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class ventana extends JFrame implements ActionListener {

    JComboBox comboBox;
    JLabel label;
    JTextField textField;
    JCheckBox checkBox;

    public ventana() {
        JFrame frame = new JFrame("Hito3.4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 475);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPane.setLayout(new BorderLayout());

        JPanel panelN = new JPanel();
        panelN.setLayout(new BorderLayout());
        JPanel panelC = new JPanel();
        panelC.setLayout(new BorderLayout());
        JPanel panelCS = new JPanel();
        panelCS.setLayout(new GridLayout());
        JPanel panelS = new JPanel();

        comboBox = new JComboBox<String>(new String[] {
                "Jurgi chino😑.jpg", "Payasin.jpg", "messi.jpg"
        });
        comboBox.addActionListener(new ComboListener());
        comboBox.setPreferredSize(new Dimension(277, 50));
        panelN.add(comboBox, BorderLayout.WEST);

        label = new JLabel(new ImageIcon("Hito3.4\\src\\images\\Jurgi chino\uD83D\uDE11.jpg"));
        panelC.add(label, BorderLayout.WEST);

        checkBox = new JCheckBox("Guardar comentario");
        checkBox.setSelected(true);
        panelCS.add(checkBox, BorderLayout.SOUTH);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(150,30));
        panelCS.add(textField, BorderLayout.SOUTH);

        JButton button = new JButton("GUARDAR");
        panelS.add(button);
        button.addActionListener(this);

        panelC.add(panelCS, BorderLayout.SOUTH);
        contentPane.add(panelN, BorderLayout.NORTH);
        contentPane.add(panelC, BorderLayout.CENTER);
        contentPane.add(panelS,BorderLayout.SOUTH);

        frame.add(contentPane);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowOpened(e);
                JOptionPane.showMessageDialog(null, "¡Adiós!");

                // close the window
                dispose();
            }
        });

        frame.setVisible(true);
    }

    public void load_combo(){
        String img = comboBox.getSelectedItem().toString();
        String filename ="Hito3.4\\src\\images" + File.separator + img;
        label.setIcon(new ImageIcon(filename));
    }

    public void actionPerformed(ActionEvent e) {
        String nombre = comboBox.getSelectedItem().toString();
        String comentario = this.textField.getText();
        boolean selected = checkBox.isSelected();
        BufferedWriter textField = null;
        String filename ="Hito3.4\\src" + File.separator + nombre;
        try {
            textField = new BufferedWriter(new FileWriter(filename + ".txt", true));
            if (selected) {
                textField.write("Nombre:" + nombre + " Comentario:" + comentario + "\n");
            } else {
                textField.write(nombre + "\n");
            }
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        finally {
            try {
                if (textField != null) {
                    textField.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    class ComboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            load_combo();
        }
    }

}
>>>>>>> origin/master
