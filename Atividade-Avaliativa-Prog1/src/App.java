import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Connection conn = ConexaoBanco.getConnection();
        if (conn != null) {
            System.out.println("Conexão estabelecida com sucesso!");
        } else {
            System.out.println("Falha ao estabelecer conexão.");
        }
    }
}
