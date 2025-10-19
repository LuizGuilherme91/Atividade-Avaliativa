import java.sql.Connection;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://localhost:5432/atividade_avaliativa";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";


    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
