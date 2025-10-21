import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListaTarefas extends ClasseGenerica {
    private Integer id;
    private Date data_tarefa;
    private String descricao_tarefa;
    private String observacao;
    private Responsavel responsavel;
    private Prioridade prioridade;

    // Construtor vazio
    public ListaTarefas() {}

    // Construtor para criar uma nova tarefa (sem ID)
    public ListaTarefas(Date data_tarefa, String descricao_tarefa, String observacao, Responsavel responsavel, Prioridade prioridade) {
        this.data_tarefa = data_tarefa;
        this.descricao_tarefa = descricao_tarefa;
        this.observacao = observacao;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
    }

    // Construtor privado para ser usado pelos métodos de pesquisa
    private ListaTarefas(Integer id, Date data_tarefa, String descricao_tarefa, String observacao, Responsavel responsavel, Prioridade prioridade) {
        this.id = id;
        this.data_tarefa = data_tarefa;
        this.descricao_tarefa = descricao_tarefa;
        this.observacao = observacao;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
    }

    // --- Getters e Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Date getData_tarefa() { return data_tarefa; }
    public void setData_tarefa(Date data_tarefa) { this.data_tarefa = data_tarefa; }
    public String getDescricao_tarefa() { return descricao_tarefa; }
    public void setDescricao_tarefa(String descricao_tarefa) { this.descricao_tarefa = descricao_tarefa; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public Responsavel getResponsavel() { return responsavel; }
    public void setResponsavel(Responsavel responsavel) { this.responsavel = responsavel; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    // --- Métodos de Instância (operam no 'this') ---

    /**
     * Salva ou atualiza a tarefa atual (this) no banco de dados.
     * Se o ID for nulo, insere e atualiza o ID do objeto.
     * Se o ID não for nulo, apenas atualiza.
     */
    public boolean salvar() {
        if (this.id != null) {
            return this.alterar();
        }
        
        String sql = "INSERT INTO lista_tarefas (data_tarefa, descricao_tarefa, observacao, responsavel_id, prioridade_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDate(1, this.data_tarefa);
            stmt.setString(2, this.descricao_tarefa);
            stmt.setString(3, this.observacao);
            stmt.setInt(4, this.responsavel.getId());
            stmt.setInt(5, this.prioridade.getId());
            stmt.executeUpdate();

            // Buscar o ID gerado
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1)); // Define o ID no objeto
            }
            
            System.out.println("Lista de tarefas salva com sucesso! ID: " + this.getId());
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar lista de tarefas: " + e.getMessage());
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
        
        String sql = "UPDATE lista_tarefas SET data_tarefa = ?, descricao_tarefa = ?, observacao = ?, responsavel_id = ?, prioridade_id = ? WHERE id = ?";
        
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, this.data_tarefa);
            stmt.setString(2, this.descricao_tarefa);
            stmt.setString(3, this.observacao);
            stmt.setInt(4, this.responsavel.getId());
            stmt.setInt(5, this.prioridade.getId());
            stmt.setInt(6, this.id); // Cláusula WHERE
            
            stmt.executeUpdate();
            System.out.println("Lista de tarefas alterada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar lista de tarefas: " + e.getMessage());
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
        
        String sql = "DELETE FROM lista_tarefas WHERE id = ?";
        
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, this.id);
            stmt.executeUpdate();
            
            System.out.println("Lista de tarefas deletada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    // --- Métodos Estáticos (para buscar ou operações em lote) ---

    /**
     * Busca uma tarefa no banco pelo ID e retorna um NOVO objeto completo.
     * Retorna 'null' se não encontrar.
     */
    public static ListaTarefas pesquisarPorId(Integer id) {
        String sql = "SELECT * FROM lista_tarefas WHERE id = ?";
        
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Pega os dados básicos
                Date data = rs.getDate("data_tarefa");
                String desc = rs.getString("descricao_tarefa");
                String obs = rs.getString("observacao");
                
                // Pega os IDs das chaves estrangeiras
                int respId = rs.getInt("responsavel_id");
                int prioId = rs.getInt("prioridade_id");
                
                // "Hidrata" os objetos: Busca os objetos completos
                Responsavel resp = Responsavel.pesquisarPorId(respId);
                Prioridade prio = Prioridade.pesquisarPorId(prioId);
                
                // Retorna um novo objeto ListaTarefas preenchido
                return new ListaTarefas(id, data, desc, obs, resp, prio);
            } else {
                System.out.println("Lista de tarefas não encontrada.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar lista de tarefas: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deleta uma tarefa identificada pela descrição.
     * (Mantido da sua lógica original, mas agora é estático)
     */
    public static boolean deletarPorDescricao(String descricaoTarefa) {
        String sql = "DELETE FROM lista_tarefas WHERE descricao_tarefa = ?";
        
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, descricaoTarefa);
            stmt.executeUpdate();
            
            System.out.println("Lista de tarefas deletada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    // --- Métodos Estáticos Auxiliares ---
    // (Corrigidos para usar os métodos estáticos das outras classes)

    /**
     * Busca um objeto Responsavel pelo NOME.
     * Agora é estático e usa o método de pesquisa de Responsavel.
     */
    public static Responsavel buscarResponsavel(String nome) {
        // Reutiliza o método que já criamos em Responsavel
        return Responsavel.pesquisarPorNome(nome);
    }

    /**
     * Busca um objeto Prioridade pela DESCRIÇÃO.
     * Agora é estático e usa o método de pesquisa de Prioridade.
     */
    public static Prioridade buscarPrioridade(String descricao) {
        // Reutiliza o método que já criamos em Prioridade
        return Prioridade.pesquisarPorDescricao(descricao);
    }
    
    @Override
    public String toString() {
        return "ListaTarefas [id=" + id + ", data_tarefa=" + data_tarefa + ", descricao_tarefa=" + descricao_tarefa
                + ", responsavel=" + (responsavel != null ? responsavel.getNome() : "null") 
                + ", prioridade=" + (prioridade != null ? prioridade.getDescricao() : "null") + "]";
    }
}