import java.sql.Date;

public class ListaTarefas extends ClasseGenerica{
    private Integer id;
    private Date data_tarefa;
    private String descricao_tarefa;
    private String observacao;

    public ListaTarefas() {}
    public ListaTarefas(Integer id, Date data_tarefa, String descricao_tarefa, String observacao) {
        this.id = id;
        this.data_tarefa = data_tarefa;
        this.descricao_tarefa = descricao_tarefa;
        this.observacao = observacao;
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


}
