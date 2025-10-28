import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrioridade extends JFrame {

    // O gerenciador de "cartões" (telas)
    private CardLayout cardLayout;
    
    // O painel principal que conterá todos os "cartões"
    private JPanel painelPrincipal;

    // Constantes para os nomes dos "cartões"
    private final String TELA_INICIAL = "TELA_INICIAL";
    private final String FORM_SALVAR = "FORM_SALVAR";
    private final String FORM_PESQUISAR = "FORM_PESQUISAR";
    private final String FORM_ALTERAR = "FORM_ALTERAR";
    private final String FORM_DELETAR = "FORM_DELETAR";

    public TelaPrioridade() {
        setTitle("Tela - Prioridades");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. Status Bar ---
        add(criarStatusBar(), BorderLayout.SOUTH);

        // --- 2. Painel Central ---
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Adiciona os "cartões" (painéis) ao gerenciador
        painelPrincipal.add(criarTelaInicial(), TELA_INICIAL);
        painelPrincipal.add(criarFormularioSalvar(), FORM_SALVAR);
        painelPrincipal.add(criarFormularioPesquisar(), FORM_PESQUISAR);
        painelPrincipal.add(criarFormularioAlterar(), FORM_ALTERAR);
        painelPrincipal.add(criarFormularioDeletar(), FORM_DELETAR);

        // Adiciona o painel principal ao centro da janela
        add(painelPrincipal, BorderLayout.CENTER);

        // Mostra o "cartão" inicial
        cardLayout.show(painelPrincipal, TELA_INICIAL);
    }

    /**
     * Cria a barra de status inferior.
     */
    private JLabel criarStatusBar() {
        JLabel statusBar = new JLabel(" Sistema pronto para uso");
        statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        return statusBar;
    }

    /**
     * Cria o "Cartão 1": A tela inicial de boas-vindas e botões de ação.
     */
    private JPanel criarTelaInicial() {
        JPanel painel = new JPanel(new BorderLayout(20, 20));
        painel.setBackground(new Color(240, 248, 255));
        painel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- Títulos Centrais ---
        JPanel painelTitulos = new JPanel();
        painelTitulos.setOpaque(false);
        painelTitulos.setLayout(new BoxLayout(painelTitulos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Sistema de Prioridades");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 0, 102));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instrucao = new JLabel("Use as opções abaixo para gerenciar:");
        instrucao.setFont(new Font("Arial", Font.PLAIN, 14));
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelTitulos.add(titulo);
        painelTitulos.add(Box.createRigidArea(new Dimension(0, 20)));
        painelTitulos.add(instrucao);

        painel.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        painel.add(painelTitulos, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setOpaque(false);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnPesquisar = new JButton("Pesquisar");

        // Ações para trocar os "cartões"
        btnSalvar.addActionListener(e -> cardLayout.show(painelPrincipal, FORM_SALVAR));
        btnPesquisar.addActionListener(e -> cardLayout.show(painelPrincipal, FORM_PESQUISAR));
        btnAlterar.addActionListener(e -> cardLayout.show(painelPrincipal, FORM_ALTERAR));
        btnDeletar.addActionListener(e -> cardLayout.show(painelPrincipal, FORM_DELETAR));

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnPesquisar);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    /**
     * Cria o "Cartão 2": O formulário para Salvar uma nova prioridade.
     */
    private JPanel criarFormularioSalvar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Cadastrar Nova Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Campos do formulário ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        final JTextField txtDescricao = new JTextField(20);
        form.add(txtDescricao, gbc);

        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);

        // --- Botões de ação ---
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar Cadastro");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar (Salvar)
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = txtDescricao.getText();
                if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Prioridade prioridade = new Prioridade(descricao);
                    boolean sucesso = prioridade.salvar(); // <-- Lógica Corrigida

                    if (sucesso) {
                        JOptionPane.showMessageDialog(painel, "Prioridade '" + descricao + "' salva com sucesso! ID gerado: " + prioridade.getId());
                        txtDescricao.setText(""); 
                        cardLayout.show(painelPrincipal, TELA_INICIAL);
                    } else {
                        JOptionPane.showMessageDialog(painel, "Erro ao salvar prioridade.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return painel;
    }

    /**
     * Cria o "Cartão 3": O formulário para Pesquisar prioridades (por ID).
     */
    private JPanel criarFormularioPesquisar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Pesquisar Prioridades", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Painel de Busca (por ID) ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por ID:")); // <-- Corrigido
        final JTextField txtBusca = new JTextField(10);
        painelBusca.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(btnBuscar);
        painel.add(painelBusca, BorderLayout.CENTER);

        // Ação de Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = txtBusca.getText();
                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe o ID.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Integer id = Integer.parseInt(idTexto);
                    Prioridade pEncontrada = Prioridade.pesquisarPorId(id); // <-- Lógica Corrigida

                    if (pEncontrada != null) {
                        JOptionPane.showMessageDialog(painel, "Prioridade encontrada:\nID: " + pEncontrada.getId() + "\nDescrição: " + pEncontrada.getDescricao());
                    } else {
                        JOptionPane.showMessageDialog(painel, "Prioridade com ID " + id + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    txtBusca.setText("");
                    cardLayout.show(painelPrincipal, TELA_INICIAL);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "ID inválido. Por favor, digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Botão de Voltar ---
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar ao Menu");
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        return painel;
    }

    /**
     * Cria o "Cartão 4": O formulário para Alterar uma prioridade (por ID).
     */
    private JPanel criarFormularioAlterar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Alterar Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Formulário (ID + Nova Descrição) ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Linha 0: ID ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        form.add(new JLabel("ID da Prioridade:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        final JTextField txtId = new JTextField(10);
        form.add(txtId, gbc);

        // --- Linha 1: Nova Descrição ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        form.add(new JLabel("Nova Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        final JTextField txtNovaDescricao = new JTextField(20);
        form.add(txtNovaDescricao, gbc);

        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);

        // --- Botões de ação ---
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar Alteração");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar (Alterar)
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = txtId.getText();
                String novaDescricao = txtNovaDescricao.getText();
                
                if (idTexto.isEmpty() || novaDescricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe o ID e a nova descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Integer id = Integer.parseInt(idTexto);
                    
                    // 1. Pesquisar primeiro
                    Prioridade pParaAlterar = Prioridade.pesquisarPorId(id); // <-- Lógica Corrigida
                    
                    if (pParaAlterar == null) {
                        JOptionPane.showMessageDialog(painel, "Prioridade com ID " + id + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 2. Modificar o objeto
                        pParaAlterar.setDescricao(novaDescricao);
                        
                        // 3. Salvar (Alterar) no banco
                        boolean sucesso = pParaAlterar.alterar(); // <-- Lógica Corrigida
                        
                        if (sucesso) {
                            JOptionPane.showMessageDialog(painel, "Prioridade alterada com sucesso!");
                            txtId.setText("");
                            txtNovaDescricao.setText("");
                            cardLayout.show(painelPrincipal, TELA_INICIAL);
                        } else {
                             JOptionPane.showMessageDialog(painel, "Erro ao alterar a prioridade.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "ID inválido. Por favor, digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return painel;
    }

    /**
     * Cria o "Cartão 5": O formulário para Deletar uma prioridade (por ID).
     */
    private JPanel criarFormularioDeletar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Deletar Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Formulário (por ID) ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("ID da Prioridade:"), gbc); // <-- Corrigido

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        final JTextField txtId = new JTextField(10); // <-- Corrigido
        form.add(txtId, gbc);

        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);

        // --- Botões de ação ---
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Deletar");
        JButton btnVoltar = new JButton("Voltar ao Menu");
        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar (Deletar)
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idTexto = txtId.getText();
                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe o ID.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    Integer id = Integer.parseInt(idTexto);
                    
                    // 1. Pesquisar para confirmar que existe
                    Prioridade pParaDeletar = Prioridade.pesquisarPorId(id); // <-- Lógica Corrigida
                    
                    if (pParaDeletar == null) {
                         JOptionPane.showMessageDialog(painel, "Prioridade com ID " + id + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 2. Deletar
                        boolean sucesso = pParaDeletar.deletar(); // <-- Lógica Corrigida
                        
                        if (sucesso) {
                            JOptionPane.showMessageDialog(painel, "Prioridade '" + pParaDeletar.getDescricao() + "' (ID: " + id + ") deletada com sucesso!");
                            txtId.setText("");
                            cardLayout.show(painelPrincipal, TELA_INICIAL);
                        } else {
                            JOptionPane.showMessageDialog(painel, "Erro ao deletar a prioridade.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "ID inválido. Por favor, digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return painel;
    }
}