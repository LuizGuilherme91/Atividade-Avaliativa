public class Responsavel extends ClasseGenerica{
    private Integer id;
    private String nome;

    public Responsavel() {}
    public Responsavel(Integer id, String nome) {
        this.id = id;
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

    public void salvarResponsavel(Responsavel responsavel) {}

    public void alterarResponsavel(Responsavel responsavel) {}

    public void deletarResponsavel(Responsavel responsavel) {}

    public void pesquisarResponsavel(Responsavel responsavel) {}
}
