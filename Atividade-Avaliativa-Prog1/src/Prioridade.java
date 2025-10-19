import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Prioridade extends ClasseGenerica {
    private Integer id;
    private String descricao;


    public Prioridade() {}

    public Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean salvarPrioridade(Prioridade prioridade) {
        String sql = "INSERT INTO prioridade (descricao) VALUES (?)";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prioridade.getDescricao());
            stmt.executeUpdate();
            System.out.println("Prioridade salva com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar prioridade: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarPrioridade(String antigaDescricao, String novaDescricao) {
        String sql = "UPDATE prioridade SET descricao = ? WHERE descricao = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaDescricao);
            stmt.setString(2, antigaDescricao);
            stmt.executeUpdate();
            System.out.println("Prioridade alterada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar prioridade: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPrioridade(String descricao) {
        String sql = "DELETE FROM prioridade WHERE descricao = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.executeUpdate();
            System.out.println("Prioridade deletada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar prioridade: " + e.getMessage());
            return false;
        }
    }

    public boolean pesquisarPrioridade(String descricao) {
        String sql = "SELECT * FROM prioridade WHERE descricao = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                setDescricao(rs.getString("descricao"));
                System.out.println("Prioridade encontrada: " + getDescricao());
                return true;
            } else {
                System.out.println("Prioridade não encontrada.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar prioridade: " + e.getMessage());
            return false;
        }
    }
}
