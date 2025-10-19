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

    // (Você pode adicionar FORM_ALTERAR, FORM_DELETAR, etc.)

    public TelaPrioridade() {
        setTitle("Tela - Prioridades");
        setSize(600, 400); // Tamanho um pouco maior
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Layout principal da janela

        // --- 1. Menu Bar (Como na imagem) ---
        setJMenuBar(criarMenuBar());

        // --- 2. Status Bar (Como na imagem) ---
        add(criarStatusBar(), BorderLayout.SOUTH);

        // --- 3. Painel Central (Onde a mágica acontece) ---
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Adiciona os "cartões" (painéis) ao gerenciador
        painelPrincipal.add(criarTelaInicial(), TELA_INICIAL);
        painelPrincipal.add(criarFormularioSalvar(), FORM_SALVAR);
        painelPrincipal.add(criarFormularioPesquisar(), FORM_PESQUISAR);
        painelPrincipal.add(criarFormularioAlterar(), FORM_ALTERAR);
        painelPrincipal.add(criarFormularioDeletar(), FORM_DELETAR);
        // (Adicione os outros formulários aqui)

        // Adiciona o painel principal (com CardLayout) ao centro da janela
        add(painelPrincipal, BorderLayout.CENTER);

        // Mostra o "cartão" inicial
        cardLayout.show(painelPrincipal, TELA_INICIAL);
    }

    /**
     * Cria a barra de menu superior
     */
    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuAjuda = new JMenu("Ajuda");

        // Item de menu para voltar à tela inicial de Prioridades
        JMenuItem itemPrioridade = new JMenuItem("Prioridades");
        itemPrioridade.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));
        
        menuCadastros.add(itemPrioridade);
        menuBar.add(menuCadastros);
        menuBar.add(menuAjuda);
        return menuBar;
    }

    /**
     * Cria a barra de status inferior
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
        painel.setBackground(new Color(240, 248, 255)); // Fundo azul claro (AliceBlue)
        painel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- Títulos Centrais ---
        JPanel painelTitulos = new JPanel();
        painelTitulos.setOpaque(false); // Transparente para usar o fundo do painel principal
        painelTitulos.setLayout(new BoxLayout(painelTitulos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Sistema de Prioridades");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 0, 102)); // Azul escuro
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instrucao = new JLabel("Use as opções abaixo para gerenciar:");
        instrucao.setFont(new Font("Arial", Font.PLAIN, 14));
        instrucao.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelTitulos.add(titulo);
        painelTitulos.add(Box.createRigidArea(new Dimension(0, 20))); // Espaçamento
        painelTitulos.add(instrucao);

        // Adiciona os títulos (com espaçamento) ao centro
        painel.add(Box.createVerticalStrut(20), BorderLayout.NORTH); // Margem superior
        painel.add(painelTitulos, BorderLayout.CENTER);

        // --- Painel de Botões (Inferior) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setOpaque(false);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnPesquisar = new JButton("Pesquisar");

        // ESTA É A LÓGICA PRINCIPAL: Trocar os "cartões"
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

        // Título
        JLabel tituloForm = new JLabel("Cadastrar Nova Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // Campos do formulário
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Faz o campo de texto expandir
        JTextField txtDescricao = new JTextField(20);
        form.add(txtDescricao, gbc);

        // Adiciona o formulário ao centro (com um "wrapper")
        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);


        // Botões de ação do formulário
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar Cadastro");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar: Sempre volta para a TELA_INICIAL
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar: Pega os dados e salva
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = txtDescricao.getText();
                if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Prioridade prioridade = new Prioridade(descricao);
                    prioridade.salvarPrioridade(prioridade);
                    
                    JOptionPane.showMessageDialog(painel, "Prioridade '" + descricao + "' salva com sucesso!");
                    txtDescricao.setText(""); // Limpa o campo
                    
                    // Opcional: Volta ao menu automaticamente após salvar
                    cardLayout.show(painelPrincipal, TELA_INICIAL);
                }
            }
        });

        return painel;
    }

    /**
     * Cria o "Cartão 3": O formulário para Pesquisar prioridades.
     * (Implementação de exemplo)
     */
    private JPanel criarFormularioPesquisar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloForm = new JLabel("Pesquisar Prioridades", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // Painel de Busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por Descrição:"));
        JTextField txtBusca = new JTextField(25);
        painelBusca.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(btnBuscar);
        
        painel.add(painelBusca, BorderLayout.CENTER);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = txtBusca.getText();
                if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Prioridade prioridade = new Prioridade(descricao);
                    boolean sucesso = prioridade.pesquisarPrioridade(descricao);

                    if (sucesso) {
                        JOptionPane.showMessageDialog(painel, "Prioridade '" + descricao + "' encontrada com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(painel, "Prioridade '" + descricao + "' não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    txtBusca.setText(""); // Limpa o campo
                    
                    // Opcional: Volta ao menu automaticamente após salvar
                    cardLayout.show(painelPrincipal, TELA_INICIAL);
                }
            }
        });



        // Botão de Voltar
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar ao Menu");
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        return painel;
    }

    private JPanel criarFormularioAlterar() {
     JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloForm = new JLabel("Alterar Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // Campos do formulário
        // Campos do formulário
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento
        gbc.anchor = GridBagConstraints.WEST;

        // --- Linha 0: Antiga Descrição ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE; // Resetar fill para o label
        gbc.weightx = 0.0; // Resetar weight para o label
        form.add(new JLabel("Antiga Descrição:"), gbc); // <-- Texto do label corrigido

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Faz o campo de texto expandir
        // Você precisa declarar os campos ANTES do listener para poder usá-los lá
        // (Provavelmente você já fez isso, movendo-os para o topo da classe)
        JTextField txtAntigaDescricao = new JTextField(20); 
        form.add(txtAntigaDescricao, gbc);

        // --- Linha 1: Nova Descrição ---
        gbc.gridx = 0;
        gbc.gridy = 1; // <-- CORREÇÃO: Mudar para a linha 1
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        form.add(new JLabel("Nova Descrição:"), gbc); // <-- Label que faltava

        gbc.gridx = 1;
        gbc.gridy = 1; // <-- CORREÇÃO: Mudar para a linha 1
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; 
        JTextField txtNovaDescricao = new JTextField(20);
        form.add(txtNovaDescricao, gbc);

        // Adiciona o formulário ao centro (com um "wrapper")
        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);


        // Botões de ação do formulário
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar Alteração");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar: Sempre volta para a TELA_INICIAL
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar: Pega os dados e salva
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novaDescricao = txtNovaDescricao.getText();
                String antigaDescricao = txtAntigaDescricao.getText();
                if (novaDescricao.isEmpty() || antigaDescricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Prioridade prioridade = new Prioridade(novaDescricao);
                    prioridade.alterarPrioridade(antigaDescricao, novaDescricao);
                    
                    JOptionPane.showMessageDialog(painel, "Prioridade '" + novaDescricao + "' salva com sucesso!");
                    txtAntigaDescricao.setText(""); // Limpa o campo
                    txtNovaDescricao.setText("");

                    // Opcional: Volta ao menu automaticamente após salvar
                    cardLayout.show(painelPrincipal, TELA_INICIAL);
                }
            }
        });

        return painel;
    }

    private JPanel criarFormularioDeletar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloForm = new JLabel("Deletar Prioridade", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // Campos do formulário
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Faz o campo de texto expandir
        JTextField txtDescricao = new JTextField(20);
        form.add(txtDescricao, gbc);

        JPanel painelCentralWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCentralWrapper.add(form);
        painel.add(painelCentralWrapper, BorderLayout.CENTER);


        // Botões de ação do formulário
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Deletar");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        painelBotoesForm.add(btnConfirmar);
        painelBotoesForm.add(btnVoltar);
        painel.add(painelBotoesForm, BorderLayout.SOUTH);

        // Ação de Voltar: Sempre volta para a TELA_INICIAL
        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, TELA_INICIAL));

        // Ação de Confirmar: Pega os dados e salva
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = txtDescricao.getText();
                if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, informe a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Prioridade prioridade = new Prioridade(descricao);
                    prioridade.deletarPrioridade(descricao);
                    
                    JOptionPane.showMessageDialog(painel, "Prioridade '" + descricao + "' deletada com sucesso!");
                    txtDescricao.setText(""); // Limpa o campo
                    
                    // Opcional: Volta ao menu automaticamente após salvar
                    cardLayout.show(painelPrincipal, TELA_INICIAL);
                }
            }
        });

        return painel;
    }
}