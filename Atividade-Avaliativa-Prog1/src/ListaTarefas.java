import java.sql.Date;

public class ListaTarefas extends ClasseGenerica{
    private Integer id;
    private Date data_tarefa;
    private String descricao_tarefa;
    private String observacao;
    private Responsavel responsavel;
    private Prioridade prioridade;

    public ListaTarefas() {}
    public ListaTarefas(Integer id, Date data_tarefa, String descricao_tarefa, String observacao, Responsavel responsavel, Prioridade prioridade) {
        this.id = id;
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

    public void salvarListaTarefas(ListaTarefas listaTarefas) {}

    public void alterarListaTarefas(ListaTarefas listaTarefas) {}

    public void deletarListaTarefas(ListaTarefas listaTarefas) {}

    public void pesquisarListaTarefas(ListaTarefas listaTarefas) {}

    public void buscarResponsavel(Responsavel responsavel) {}

    public void buscarPrioridade(Prioridade prioridade) {}

}
