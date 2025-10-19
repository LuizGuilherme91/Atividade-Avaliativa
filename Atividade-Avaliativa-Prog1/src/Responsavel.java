import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Responsavel extends ClasseGenerica{
    private Integer id;
    private String nome;

    public Responsavel() {}
    public Responsavel(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean salvarResponsavel(Responsavel responsavel) {
        String sql = "INSERT INTO responsavel (nome) VALUES (?)";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, responsavel.getNome());
            stmt.executeUpdate();
            System.out.println("Responsável salvo com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar responsável: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarResponsavel(String antigoNome, String novoNome) {
        String sql = "UPDATE responsavel SET nome = ? WHERE nome = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, antigoNome);
            stmt.executeUpdate();
            System.out.println("Responsável alterado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar responsável: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarResponsavel(String nome) {
        String sql = "DELETE FROM responsavel WHERE nome = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Responsável deletado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar responsável: " + e.getMessage());
            return false;
        }
    }

    public boolean pesquisarResponsavel(String nome) {
        String sql = "SELECT * FROM responsavel WHERE nome = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                setNome(rs.getString("nome"));
                System.out.println("Responsável encontrado: " + getNome());
                return true;
            } else {
                System.out.println("Responsável não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar responsável: " + e.getMessage());
            return false;
        }
    }
}
