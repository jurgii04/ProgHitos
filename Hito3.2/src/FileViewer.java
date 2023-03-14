import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class FileViewer extends JFrame implements ActionListener, ItemListener {
    private JComboBox<String> fileComboBox;
    private JTextArea fileTextArea;

    public FileViewer() {
        // Inicializar la ventana
        JFrame frame = new JFrame("Visor de archvios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        // Crear el JComboBox con los nombres de archivo
        fileComboBox = new JComboBox<String>(new String[] {
                "ELIGE UN LENGUAJE DE PROGRAMACIÓN", "python.txt", "c.txt", "java.txt"
        });
        fileComboBox.addItemListener(this);
        fileComboBox.setPreferredSize(new Dimension(300, 25));
        panel1.add(fileComboBox);

        // Crear el botón "Borrar"
        JButton clearButton = new JButton("Borrar");
        clearButton.addActionListener(this);
        panel1.add(clearButton);

        // Crear el JTextArea donde se mostrará el contenido del archivo
        fileTextArea = new JTextArea();
        fileTextArea.setPreferredSize(new Dimension(400, 400));
        fileTextArea.setEditable(false);
        panel2.add(fileTextArea);
        JScrollPane scrollPane = new JScrollPane(fileTextArea);
        frame.add(scrollPane);

        // Crear el botón "Cerrar"
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel3.add(closeButton, BorderLayout.SOUTH);

        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(panel3, BorderLayout.SOUTH);

        // Mostrar la ventana
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileTextArea.setText("");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Cargar el archivo seleccionado en el JTextArea
        String fileName = (String)fileComboBox.getSelectedItem();
        if (!fileName.equals("ELIGE UN LENGUAJE DE PROGRAMACIÓN")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("Hito3.2\\src\\" + fileName));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                fileTextArea.setText(sb.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }


}
