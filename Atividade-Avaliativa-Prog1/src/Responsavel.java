import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // <-- Importação necessária

public class Responsavel extends ClasseGenerica {
    private Integer id;
    private String nome;

    // Construtor vazio
    public Responsavel() {}

    // Construtor para criar um novo objeto (ID ainda não existe)
    public Responsavel(String nome) {
        this.nome = nome;
    }

    // Construtor para objetos vindos do banco (já têm ID)
    private Responsavel(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
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

    // --- Métodos de Instância (operam no 'this') ---

    /**
     * Salva o objeto ATUAL (this) no banco de dados.
     * Se for um novo responsável (ID nulo), ele insere e ATUALIZA o ID do objeto.
     */
    public boolean salvar() {
        // Se já tem ID, deve ser uma alteração
        if (this.id != null) {
            return this.alterar();
        }
        
        String sql = "INSERT INTO responsavel (nome) VALUES (?)";
        
        // Usamos RETURN_GENERATED_KEYS para pegar o ID que o banco criou
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, this.getNome());
            stmt.executeUpdate();
            
            // Buscar o ID gerado
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1)); // Define o ID no objeto
            }
            
            System.out.println("Responsável salvo com sucesso! ID: " + this.getId());
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar responsável: " + e.getMessage());
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
        
        String sql = "UPDATE responsavel SET nome = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.getNome());
            stmt.setInt(2, this.getId()); // Usa o ID do objeto
            stmt.executeUpdate();
            
            System.out.println("Responsável alterado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar responsável: " + e.getMessage());
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
        
        String sql = "DELETE FROM responsavel WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, this.getId()); // Usa o ID do objeto
            stmt.executeUpdate();
            
            System.out.println("Responsável deletado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar responsável: " + e.getMessage());
            return false;
        }
    }

    // --- Métodos Estáticos (para buscar ou operações em lote) ---

    /**
     * Busca um responsável no banco pelo ID e retorna um NOVO objeto.
     * Retorna 'null' se não encontrar.
     */
    public static Responsavel pesquisarPorId(Integer id) {
        String sql = "SELECT * FROM responsavel WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Cria um novo objeto com os dados do banco
                return new Responsavel(rs.getInt("id"), rs.getString("nome"));
            } else {
                System.out.println("Responsável não encontrado.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar responsável: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método útil para exibir o objeto
     */
    @Override
    public String toString() {
        return "Responsavel [ID: " + id + ", Nome: " + nome + "]";
    }
}