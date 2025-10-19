import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("AtividadeAvaliativa Prog1");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new java.awt.BorderLayout());

        JLabel titulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        add(titulo, java.awt.BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        JButton btnPrioridade = new JButton("Prioridades");
        JButton btnResponsavel = new JButton("Responsáveis");
        JButton btnListaTarefas = new JButton("Lista de Tarefas");

        painelBotoes.add(btnPrioridade);
        painelBotoes.add(btnListaTarefas);
        painelBotoes.add(btnResponsavel);

        add(painelBotoes, java.awt.BorderLayout.CENTER);

        btnPrioridade.addActionListener(e -> {
            TelaPrioridade telaPrioridade = new TelaPrioridade();
            telaPrioridade.setVisible(true);
        });

        btnResponsavel.addActionListener(e -> {
            TelaResponsavel telaResponsavel = new TelaResponsavel();
            telaResponsavel.setVisible(true);
        });

        btnListaTarefas.addActionListener(e -> {
            TelaListaTarefas telaListaTarefas = new TelaListaTarefas();
            telaListaTarefas.setVisible(true);
        });

    }

    


}
