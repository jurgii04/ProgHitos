import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
import java.sql.*;

public class PictureViewer extends JFrame {
    private JComboBox<Photographer> photographerComboBox;
    private JXDatePicker datePicker;
    private JList<Picture> pictureList;
    private JLabel pictureLabel;

    private Connection conn;

    public PictureViewer() {
        super("Picture Viewer");

        // Establecer el layout del marco
        setLayout(new GridLayout(2, 2, 5, 5));

        // Crear el combo box de fotógrafos
        photographerComboBox = new JComboBox<>();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/my_database?user=root&password=root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Photographers");
            while (rs.next()) {
                int photographerId = rs.getInt("PhotographerId");
                String name = rs.getString("Name");
                boolean awarded = rs.getBoolean("Awarded");
                Photographer photographer = new Photographer(photographerId, name, awarded);
                photographerComboBox.addItem(photographer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        add(photographerComboBox);

        // Crear el componente de selección de fecha
        datePicker = new JXDatePicker();
        add(datePicker);

        // Crear la lista de imágenes
        pictureList = new JList<>();
        add(new JScrollPane(pictureList));

        // Crear la etiqueta de imagen
        pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        add(pictureLabel);

        // Configurar el comportamiento del combo box de fotógrafos
        photographerComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Photographer selectedPhotographer = (Photographer) photographerComboBox.getSelectedItem();
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Pictures WHERE PhotographerId = " + selectedPhotographer.getId());
                    DefaultListModel<Picture> listModel = new DefaultListModel<>();
                    while (rs.next()) {
                        int pictureId = rs.getInt("PictureId");
                        String title = rs.getString("Title");
                        Date date = rs.getDate("Date");
                        String file = rs.getString("File");
                        int visits = rs.getInt("Visits");
                        Picture picture = new Picture(pictureId, title, date, file, visits, selectedPhotographer);
                        listModel.addElement(picture);
                    }
                    pictureList.setModel(listModel);
                    pictureLabel.setIcon(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Configurar el comportamiento de la lista de imágenes
        pictureList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Picture selectedPicture = (Picture) pictureList.getSelectedValue();
                if (selectedPicture != null) {
                    ImageIcon imageIcon = new ImageIcon(selectedPicture.getFile());
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(pictureLabel.getWidth(), pictureLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                    pictureLabel.setIcon(scaledImageIcon);
                } else {
                    pictureLabel.setIcon(null);
                }
            }
        });

        // Configurar el marco
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PictureViewer();
            }
        });
    }
}

