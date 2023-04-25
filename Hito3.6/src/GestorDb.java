import java.sql.*;
import java.util.ArrayList;

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

    public static void main(String[] args) {
        GestorDb gestorDb = new GestorDb();

    }
}