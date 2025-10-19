import java.sql.Connection;
import java.sql.Date;

import javax.xml.crypto.Data;

public class ListaTarefas extends ClasseGenerica{
    private Integer id;
    private Date data_tarefa;
    private String descricao_tarefa;
    private String observacao;
    private Responsavel responsavel;
    private Prioridade prioridade;

    public ListaTarefas() {}
    public ListaTarefas( Date data_tarefa, String descricao_tarefa, String observacao, Responsavel responsavel, Prioridade prioridade) {
        this.data_tarefa = data_tarefa;
        this.descricao_tarefa = descricao_tarefa;
        this.observacao = observacao;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData_tarefa() {
        return data_tarefa;
    }
    public void setData_tarefa(Date data_tarefa) {
        this.data_tarefa = data_tarefa;
    }

    public String getDescricao_tarefa () {
        return descricao_tarefa;
    }
    public void setDescricao_tarefa (String descricao_tarefa) {
        this.descricao_tarefa = descricao_tarefa;
    }

    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public boolean salvarListaTarefas(ListaTarefas listaTarefas) {
        String sql = "INSERT INTO lista_tarefas (data_tarefa, descricao_tarefa, observacao, responsavel_id, prioridade_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.getConnection();          
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, listaTarefas.getData_tarefa());
            stmt.setString(2, listaTarefas.getDescricao_tarefa());
            stmt.setString(3, listaTarefas.getObservacao());
            stmt.setInt(4, listaTarefas.getResponsavel().getId());
            stmt.setInt(5, listaTarefas.getPrioridade().getId());
            stmt.executeUpdate();
            System.out.println("Lista de tarefas salva com sucesso!");
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao salvar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarListaTarefas(String antigaDescricao, String novaDescricao, Date dataTarefa, String observacao, Responsavel responsavel, Prioridade prioridade) {
        String sql = "UPDATE lista_tarefas SET data_tarefa = ?, descricao_tarefa = ?, observacao = ?, responsavel_id = ?, prioridade_id = ? WHERE descricao_tarefa = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, dataTarefa);
            stmt.setString(2, novaDescricao);
            stmt.setString(3, observacao);
            stmt.setInt(4, responsavel.getId());
            stmt.setInt(5, prioridade.getId());
            stmt.setString(6, antigaDescricao);
            stmt.executeUpdate();
            System.out.println("Lista de tarefas alterada com sucesso!");
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao alterar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarListaTarefas(String descricaoTarefa) {
        String sql = "DELETE FROM lista_tarefas WHERE descricao_tarefa = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricaoTarefa);
            stmt.executeUpdate();
            System.out.println("Lista de tarefas deletada com sucesso!");
            return true;
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao deletar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    public boolean pesquisarListaTarefas(String descricaoTarefa) {
        String sql = "SELECT * FROM lista_tarefas WHERE descricao_tarefa = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricaoTarefa);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                setData_tarefa(rs.getDate("data_tarefa"));
                setDescricao_tarefa(rs.getString("descricao_tarefa"));
                setObservacao(rs.getString("observacao"));
                Responsavel responsavel = new Responsavel();
                responsavel.setId(rs.getInt("responsavel_id"));
                setResponsavel(responsavel);
                Prioridade prioridade = new Prioridade();
                prioridade.setId(rs.getInt("prioridade_id"));
                setPrioridade(prioridade);
                System.out.println("Lista de tarefas encontrada: " + getDescricao_tarefa());
                return true;
            } else {
                System.out.println("Lista de tarefas não encontrada.");
                return false;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao pesquisar lista de tarefas: " + e.getMessage());
            return false;
        }
    }

    public Responsavel buscarResponsavel(String nome) {
        String sql = "SELECT * FROM responsavel WHERE nome = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Responsavel responsavel1 = new Responsavel();
                responsavel1.setId(rs.getInt("id"));
                responsavel1.setNome(rs.getString("nome"));
                System.out.println("Responsável encontrado: " + responsavel1.getNome());
                return responsavel1;
            } else {
                System.out.println("Responsável não encontrado.");
                return null;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao buscar responsável: " + e.getMessage());
            return null;
        }
    }

    public Prioridade buscarPrioridade(String descricao) {
        String sql = "SELECT * FROM prioridade WHERE descricao = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            var rs = stmt.executeQuery();
            if (rs.next()) {

                Prioridade prioridade1 = new Prioridade();
                prioridade1.setId(rs.getInt("id"));
                prioridade1.setDescricao(rs.getString("descricao"));
                System.out.println("Prioridade encontrada: " + prioridade1.getDescricao());
                return prioridade1;
            } else {
                System.out.println("Prioridade não encontrada.");
                return null;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao buscar prioridade: " + e.getMessage());
            return null;
        }
    }

}
