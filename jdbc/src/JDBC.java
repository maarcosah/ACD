import java.sql.*;

public class JDBC {

    // Método que devuelve el valor de una columna en un registro específico
    public static String selectCampo(int numRegistro, String nomColumna) {
        String resultado = null;

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/agenda", "root", "");
             Statement s = conexion.createStatement(
                 ResultSet.TYPE_SCROLL_INSENSITIVE,  // permite moverse libremente por el ResultSet
                 ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = s.executeQuery("SELECT * FROM fichero");

            // Nos movemos al registro indicado (los índices empiezan en 1)
            if (rs.absolute(numRegistro)) {
                resultado = rs.getString(nomColumna);
            } else {
                System.out.println("El registro " + numRegistro + " no existe.");
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultado;
    }

    public static void main(String[] args) {
        String valor = selectCampo(2, "NOMBRE");
        if (valor != null) {
            System.out.println("Valor obtenido: " + valor);
        }
    }
}
