import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC {

    // Método 1: Devuelve el valor de una columna en un registro específico
    public static String selectCampo(int numRegistro, String nomColumna) {
        String resultado = null;
        String query = "SELECT * FROM fichero";

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/agenda", "root", "");
             PreparedStatement ps = conexion.prepareStatement(query, 
                 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = ps.executeQuery()) {

            if (rs.absolute(numRegistro)) {
                resultado = rs.getString(nomColumna);
            } else {
                System.out.println("El registro " + numRegistro + " no existe.");
            }

        } catch (SQLException e) {
            System.out.println("Error en selectCampo: " + e.getMessage());
        }

        return resultado;
    }

    // Método 2: Devuelve todos los valores de una columna en una lista
    public static List<String> selectColumna(String nomColumna) {
        List<String> valores = new ArrayList<>();

        // OJO: el nombre de columna no se puede pasar como parámetro en un PreparedStatement
        // Por lo tanto, se valida o se construye cuidadosamente antes de ejecutar
        String query = "SELECT " + nomColumna + " FROM fichero";

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/agenda", "root", "");
             PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                valores.add(rs.getString(nomColumna));
            }

        } catch (SQLException e) {
            System.out.println("Error en selectColumna: " + e.getMessage());
        }

        return valores;
    }

    // Método 3: Devuelve una lista con los datos del registro en la posición numRegistro
    public static List<String> selectRowList(int numRegistro) {
        List<String> fila = new ArrayList<>();
        String query = "SELECT * FROM fichero";

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/agenda", "root", "");
             PreparedStatement ps = conexion.prepareStatement(query, 
                 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = ps.executeQuery()) {

            if (rs.absolute(numRegistro)) {
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumnas = metaData.getColumnCount();

                for (int i = 1; i <= numColumnas; i++) {
                    fila.add(rs.getString(i));
                }
            } else {
                System.out.println("El registro " + numRegistro + " no existe.");
            }

        } catch (SQLException e) {
            System.out.println("Error en selectRowList: " + e.getMessage());
        }

        return fila;
    }

    // Método 4: Devuelve un HashMap con los datos del registro en la posición numRegistro
    public static Map<String, String> selectRowMap(int numRegistro) {
        Map<String, String> fila = new HashMap<>();
        String query = "SELECT * FROM fichero";

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/agenda", "root", "");
             PreparedStatement ps = conexion.prepareStatement(query, 
                 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = ps.executeQuery()) {

            if (rs.absolute(numRegistro)) {
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumnas = metaData.getColumnCount();

                for (int i = 1; i <= numColumnas; i++) {
                    String nombreColumna = metaData.getColumnName(i);
                    String valor = rs.getString(i);
                    fila.put(nombreColumna, valor);
                }
            } else {
                System.out.println("El registro " + numRegistro + " no existe.");
            }

        } catch (SQLException e) {
            System.out.println("Error en selectRowMap: " + e.getMessage());
        }

        return fila;
    }

    public static void main(String[] args) {
        // Prueba del método selectCampo
        String valor = selectCampo(2, "NOMBRE");
        if (valor != null) {
            System.out.println("Valor obtenido: " + valor);
        }

        // Prueba del método selectColumna
        List<String> nombres = selectColumna("NOMBRE");
        System.out.println("Lista de nombres:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Prueba del método selectRowList
        List<String> fila = selectRowList(2);
        System.out.println("Datos del registro 2:");
        for (String dato : fila) {
            System.out.println(dato);
        }

        // Prueba del método selectRowMap
        Map<String, String> filaMap = selectRowMap(2);
        System.out.println("Datos del registro 2 (HashMap):");
        for (Map.Entry<String, String> entrada : filaMap.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }
    }
}
