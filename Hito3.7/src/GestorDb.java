import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GestorDb {
    static final String SERVER_IP = "localhost";
    static final String DB_NAME = "hito3_6";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://" + SERVER_IP + ":3306/" + DB_NAME;
    static final String USER = "root";
    static final String PASS = "zubiri";
    private Connection conn;
    private Statement st;
    private ResultSet resu;

    public GestorDb() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conectado");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] select(String tabla, String columna, String condicion) {
        String consulta = "SELECT " + columna + " FROM " + tabla + " WHERE " + condicion;
        if (condicion==null){
             consulta = "SELECT " + columna + " FROM " + tabla;
        }

        ArrayList<String> valores = new ArrayList<>();
        try {
            st = conn.createStatement();
            resu = st.executeQuery(consulta);
            while (resu.next()) {
                valores.add(resu.getString(columna));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[] valoresStr = valores.toArray(new String[0]);

        return valoresStr;
    }

    public void update(String condicion) {
        String consulta = "UPDATE pictures SET visits = visits + 1 WHERE " + condicion;
        if (condicion == null) {
            consulta = "UPDATE pictures SET visits = visits + 1 WHERE " + condicion;
        }

        try {
            st = conn.createStatement();
            st.executeUpdate(consulta);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, String> createVisitsMap() {
        HashMap<String, String> visitsMap = new HashMap<>();
            String photographerId[] = select("photographers","PhotographerId", null);
            for (int i = 0; i < photographerId.length; i++) {
                String visitas = select("pictures", "SUM(visits)", "PhotographerId = ' " + photographerId[i] + "'")[0];
                visitsMap.put(photographerId[i], visitas);
            }
        return visitsMap;
    }

    public void updatePhotographers(int minVisits) {
        HashMap<String, String> visitsMap = createVisitsMap();
        for (Map.Entry<String, String> map : visitsMap.entrySet()) {
            if (Integer.parseInt(map.getValue()) >= minVisits) {
                try {
                    st = conn.createStatement();
                    st.executeUpdate("UPDATE photographers SET awarded = true WHERE PhotographerId = " + map.getKey());
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    st = conn.createStatement();
                    st.executeUpdate("UPDATE photographers SET awarded = false WHERE PhotographerId = " + map.getKey());
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void deleteUnseenImg() {
        String [] nonAwardedPhotographers = select("photographers","PhotographerId", "awarded = false");
        System.out.println("Non awarded: " + Arrays.toString(nonAwardedPhotographers));
        String [] unseenImg = select("pictures", "pictureId","visits = 0 and photographerid IN (select photographerid from photographers where awarded = 0)");
        System.out.println("UnseenImg: " + Arrays.toString(unseenImg));

        if (unseenImg.length == 0) {
            JOptionPane.showMessageDialog(null,"No hay imagenes sin visitas", "Sin imagenes", JOptionPane.ERROR_MESSAGE);
        }

        JLabel img = new JLabel();

        for (int i = 0; i < unseenImg.length; i++) {

            String [] files = select("pictures", "file", "pictureId = " + unseenImg[i]);
            String file = Arrays.toString(files);
            String fileSB = file.substring(1,file.length()-1);
            ImageIcon icono = new ImageIcon(fileSB);
            Image image = icono.getImage();
            Image scaledImg = image.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
            icono = new ImageIcon(scaledImg);
            img.setIcon(icono);

            int confirm = JOptionPane.showConfirmDialog(null,img,"Eliminar imagen",JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                try {
                    st = conn.createStatement();
                    st.executeUpdate("DELETE FROM pictures WHERE PictureId = " + unseenImg[i]);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static void main(String[] args) {
        GestorDb gestorDb = new GestorDb();

    }
}