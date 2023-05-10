import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class PictureViewer extends JFrame {
    private JComboBox<String> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<String> pictureList;
    private JLabel pictureLabel;

    private Connection conn;

    GestorDb gestorDb;

    public PictureViewer() {
        JFrame frame = new JFrame();
        frame.setTitle("Photography");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        frame.setLayout(new GridLayout(3, 2, 5, 5));

        // Crear el combo box de fotógrafos
        JPanel panel1 = new JPanel();
        gestorDb = new GestorDb();
        photographerComboBox = new JComboBox<>();

        String[] nombres = gestorDb.select("photographers", "nombre", null);

        for (String nombre : nombres) {
            photographerComboBox.addItem(nombre);
        }

        photographerComboBox.setPreferredSize(new Dimension(200, 25));
        panel1.add(new JLabel("Photographer: "));
        panel1.add(photographerComboBox);
        frame.add(panel1);

        // Crear el componente de selección de fecha
        JPanel panel2 = new JPanel();
        datePicker = new JXDatePicker();
        panel2.add(new JLabel("Photos after: "));
        panel2.add(datePicker);
        frame.add(panel2);

        // Crear la lista de imágenes
        JPanel panel3 = new JPanel();
        pictureList = new JList<>();
        pictureList.setPreferredSize(new Dimension(300, 200));
        panel3.add(new JScrollPane(pictureList));
        frame.add(panel3);

        // Crear la etiqueta de imagen
        JPanel panel4 = new JPanel();
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureLabel.setPreferredSize(new Dimension(300, 200));
        panel4.add(pictureLabel);
        frame.add(panel4);

        //Añadir botones
        JButton premioButton = new JButton("PREMIAR");
        premioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int minVisits = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número mínimo de visitas para ser premiado"));
                gestorDb.updatePhotographers(minVisits);
            }
        });

        JButton eliminarButton = new JButton("ELIMINAR");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorDb.deleteUnseenImg();
            }
        });

        frame.add(premioButton);
        frame.add(eliminarButton);


        photographerComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date fecha = datePicker.getDate();

                if (fecha == null) {
                    String name = photographerComboBox.getSelectedItem().toString();
                    String [] titulos = gestorDb.select("pictures", "Title","PhotographerId IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "')");

                    pictureList.setListData(titulos);

                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                    String fechaFormateada = formato.format(fecha);

                    String name = photographerComboBox.getSelectedItem().toString();
                    String [] titulos = gestorDb.select("pictures", "Title","PhotographerId IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND fecha > '" + fechaFormateada + "')");

                    pictureList.setListData(titulos);

                }

            }
        });

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fecha = datePicker.getDate();

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                String fechaFormateada = formato.format(fecha);
                String name = photographerComboBox.getSelectedItem().toString();
                String [] titulos = gestorDb.select("pictures", "Title","PhotographerId IN (SELECT PhotographerId FROM photographers WHERE nombre = '" + name + "' AND fecha > '" + fechaFormateada + "')");


                pictureList.setListData(titulos);
            }
        });


        // Configurar el comportamiento de la lista de imágenes
        pictureList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                String titulo = pictureList.getSelectedValue();
                String src = Arrays.toString(gestorDb.select("pictures", "File","title = '" + titulo + "'"));
                String file = src.substring(1,src.length()-1);

                ImageIcon icono = new ImageIcon(file);
                int labelWidth = pictureLabel.getWidth();
                int labelHeight = pictureLabel.getHeight();
                Image img = icono.getImage();
                Image scaledImg = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                icono = new ImageIcon(scaledImg);
                pictureLabel.setIcon(icono);

                gestorDb.update("title = '" + titulo + "'");
            }
        }
                );

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PictureViewer pictureViewer = new PictureViewer();
    }
}

