import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // <-- Importação necessária

public class Prioridade extends ClasseGenerica {
    private Integer id;
    private String descricao;

    // Construtor vazio
    public Prioridade() {}

    // Construtor para criar um novo objeto (ID ainda não existe)
    public Prioridade(String descricao) {
        this.descricao = descricao;
    }

    // Construtor para objetos vindos do banco (já têm ID)
    private Prioridade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters e Setters
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

    // --- Métodos de Instância (operam no 'this') ---

    /**
     * Salva o objeto ATUAL (this) no banco de dados.
     * Se for uma nova prioridade (ID nulo), ele insere e ATUALIZA o ID do objeto.
     */
    public boolean salvar() {
        // Se já tem ID, deve ser uma alteração
        if (this.id != null) {
            return this.alterar();
        }
        
        String sql = "INSERT INTO prioridade (descricao) VALUES (?)";
        
        // Usamos RETURN_GENERATED_KEYS para pegar o ID que o banco criou
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, this.getDescricao());
            stmt.executeUpdate();
            
            // Buscar o ID gerado
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1)); // Define o ID no objeto
            }
            
            System.out.println("Prioridade salva com sucesso! ID: " + this.getId());
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar prioridade: " + e.getMessage());
            return false;
        }
    }

    /**
     * Altera o registro no banco de dados com base no ID deste objeto.
     */
    public boolean alterar() {
        if (this.id == null) {
            System.out.println("Erro ao alterar: ID nulo. Use o método salvar() primeiro.");
            return false;
        }
        
        String sql = "UPDATE prioridade SET descricao = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.getDescricao());
            stmt.setInt(2, this.getId()); // Usa o ID do objeto
            stmt.executeUpdate();
            
            System.out.println("Prioridade alterada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar prioridade: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deleta o registro no banco de dados com base no ID deste objeto.
     */
    public boolean deletar() {
        if (this.id == null) {
            System.out.println("Erro ao deletar: ID nulo.");
            return false;
        }
        
        String sql = "DELETE FROM prioridade WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, this.getId()); // Usa o ID do objeto
            stmt.executeUpdate();
            
            System.out.println("Prioridade deletada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar prioridade: " + e.getMessage());
            return false;
        }
    }

    // --- Métodos Estáticos (para buscar ou operações em lote) ---

    /**
     * Busca uma prioridade no banco pelo ID e retorna um NOVO objeto.
     * Retorna 'null' se não encontrar.
     */
    public static Prioridade pesquisarPorId(Integer id) {
        String sql = "SELECT * FROM prioridade WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Cria um novo objeto com os dados do banco
                return new Prioridade(rs.getInt("id"), rs.getString("descricao"));
            } else {
                System.out.println("Prioridade não encontrada.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar prioridade: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca uma prioridade no banco pela DESCRIÇÃO e retorna um NOVO objeto.
     * Retorna 'null' se não encontrar.
     */
    public static Prioridade pesquisarPorDescricao(String descricao) {
        String sql = "SELECT * FROM prioridade WHERE descricao = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, descricao);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Cria um novo objeto com os dados do banco
                return new Prioridade(rs.getInt("id"), rs.getString("descricao"));
            } else {
                System.out.println("Prioridade não encontrada.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar prioridade: " + e.getMessage());
            return null;
        }
    }

    /**
     * Altera a descrição de uma prioridade identificada por outra descrição.
     * (Mantido da sua lógica original)
     */
    public static boolean alterarPorDescricao(String antigaDescricao, String novaDescricao) {
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

    /**
     * Deleta uma prioridade identificada pela descrição.
     * (Mantido da sua lógica original)
     */
    public static boolean deletarPorDescricao(String descricao) {
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

    /**
     * Método útil para exibir o objeto
     */
    @Override
    public String toString() {
        return "Prioridade [ID: " + id + ", Descrição: " + descricao + "]";
    }
}