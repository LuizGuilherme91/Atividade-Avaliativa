import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;

public class TelaPrincipal extends JFrame {

    // --- Constantes de Estilo ---
    private static final Color COR_FUNDO = new Color(45, 45, 45); // Cinza escuro (como na imagem)
    private static final Color COR_BOTAO = new Color(220, 220, 220);  // Cinza BEM claro (como na imagem)
    private static final Color COR_TEXTO_BOTAO = new Color(30, 30, 30); // Texto escuro (COMO PEDIDO)
    private static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 16);
    private static final Color COR_TEXTO_TITULO = Color.WHITE; // Branco para o título

    public TelaPrincipal() {
        setTitle("AtividadeAvaliativa Prog1 - Menu");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- 1. Painel Principal ---
        Container painelPrincipal = getContentPane();
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setLayout(new BorderLayout(15, 15));

        // --- 2. Título ---
        JLabel titulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(COR_TEXTO_TITULO);
        titulo.setBorder(new EmptyBorder(30, 20, 20, 20));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // --- 3. Painel de Botões (Centralizado) ---
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(COR_FUNDO); // O painel central também precisa do background escuro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JButton btnPrioridade = new JButton("Gerenciar Prioridades");
        JButton btnResponsavel = new JButton("Gerenciar Responsáveis");
        JButton btnListaTarefas = new JButton("Gerenciar Lista de Tarefas");

        styleButton(btnPrioridade);
        styleButton(btnResponsavel);
        styleButton(btnListaTarefas);

        painelCentral.add(btnPrioridade, gbc);
        
        gbc.gridy++;
        painelCentral.add(btnResponsavel, gbc);
        
        gbc.gridy++;
        painelCentral.add(btnListaTarefas, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        painelCentral.add(Box.createVerticalGlue(), gbc);

        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        // --- 4. Ações dos Botões ---
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

    private void styleButton(JButton button) {
        button.setFont(FONTE_BOTAO);
        button.setBackground(COR_BOTAO);
        button.setForeground(COR_TEXTO_BOTAO); // Define o texto para escuro
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(280, 55));

        // Borda cinza médio para combinar com o botão claro no fundo escuro
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1), 
            new EmptyBorder(12, 25, 12, 25)
        ));
    }

    /**
     * Método Main: Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {
        // Ativa o anti-aliasing para fontes mais suaves
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            // Usa o visual do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o Look and Feel do sistema.");
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TelaPrincipal frame = new TelaPrincipal();
            frame.setVisible(true);
        });
    }
}