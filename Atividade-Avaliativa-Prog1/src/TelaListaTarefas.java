import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class TelaListaTarefas extends JFrame {

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

    public TelaListaTarefas() {
        setTitle("Tela - Lista de Tarefas");
        // Aumentei a altura para caber os formulários maiores
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

        JLabel titulo = new JLabel("Sistema de Tarefas");
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
     * Cria o "Cartão 2": O formulário para Salvar uma nova tarefa.
     */
    private JPanel criarFormularioSalvar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Cadastrar Nova Tarefa", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Campos do formulário ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int linha = 0;

        // --- Linha 0: Data ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Data (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtData = new JTextField(20);
        form.add(txtData, gbc);
        linha++;

        // --- Linha 1: Descrição ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtDescricao = new JTextField(20);
        form.add(txtDescricao, gbc);
        linha++;

        // --- Linha 2: Observação ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Observação:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtObservacao = new JTextField(20);
        form.add(txtObservacao, gbc);
        linha++;

        // --- Linha 3: ID Responsável ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("ID do Responsável:"), gbc); // <-- Corrigido
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtResponsavelId = new JTextField(20); // <-- Corrigido
        form.add(txtResponsavelId, gbc);
        linha++;

        // --- Linha 4: ID Prioridade ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("ID da Prioridade:"), gbc); // <-- Corrigido
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtPrioridadeId = new JTextField(20); // <-- Corrigido
        form.add(txtPrioridadeId, gbc);
        
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
                String dataStr = txtData.getText();
                String descricao = txtDescricao.getText();
                String observacao = txtObservacao.getText();
                String responsavelIdStr = txtResponsavelId.getText();
                String prioridadeIdStr = txtPrioridadeId.getText();

                if (dataStr.isEmpty() || descricao.isEmpty() || responsavelIdStr.isEmpty() || prioridadeIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Por favor, preencha Data, Descrição, ID Responsável e ID Prioridade.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 1. Converter dados
                    Date data = Date.valueOf(dataStr); // Pode lançar IllegalArgumentException
                    Integer respId = Integer.parseInt(responsavelIdStr); // Pode lançar NumberFormatException
                    Integer prioId = Integer.parseInt(prioridadeIdStr); // Pode lançar NumberFormatException

                    // 2. Buscar objetos relacionados
                    Responsavel resp = Responsavel.pesquisarPorId(respId);
                    Prioridade prio = Prioridade.pesquisarPorId(prioId);

                    if (resp == null) {
                        JOptionPane.showMessageDialog(painel, "Responsável com ID " + respId + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (prio == null) {
                        JOptionPane.showMessageDialog(painel, "Prioridade com ID " + prioId + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 3. Criar e salvar a tarefa
                    ListaTarefas novaTarefa = new ListaTarefas(data, descricao, observacao, resp, prio);
                    boolean sucesso = novaTarefa.salvar(); // <-- Lógica Corrigida

                    if (sucesso) {
                        JOptionPane.showMessageDialog(painel, "Tarefa '" + descricao + "' salva com sucesso! ID: " + novaTarefa.getId());
                        txtData.setText("");
                        txtDescricao.setText("");
                        txtObservacao.setText("");
                        txtResponsavelId.setText("");
                        txtPrioridadeId.setText("");
                        cardLayout.show(painelPrincipal, TELA_INICIAL);
                    } else {
                        JOptionPane.showMessageDialog(painel, "Erro ao salvar a tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "ID inválido. IDs de Responsável e Prioridade devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(painel, "Formato de data inválido. Use AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return painel;
    }

    /**
     * Cria o "Cartão 3": O formulário para Pesquisar tarefas (por ID).
     */
    private JPanel criarFormularioPesquisar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Pesquisar Tarefa", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Painel de Busca (por ID) ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por ID da Tarefa:")); // <-- Corrigido
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
                    JOptionPane.showMessageDialog(painel, "Por favor, informe o ID da tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Integer id = Integer.parseInt(idTexto);
                    ListaTarefas tEncontrada = ListaTarefas.pesquisarPorId(id); // <-- Lógica Corrigida

                    if (tEncontrada != null) {
                        // Monta uma mensagem com os dados encontrados
                        String mensagem = String.format(
                            "Tarefa Encontrada:\n" +
                            "ID: %d\n" +
                            "Data: %s\n" +
                            "Descrição: %s\n" +
                            "Observação: %s\n" +
                            "Responsável: %s (ID: %d)\n" +
                            "Prioridade: %s (ID: %d)",
                            tEncontrada.getId(),
                            tEncontrada.getData_tarefa().toString(),
                            tEncontrada.getDescricao_tarefa(),
                            tEncontrada.getObservacao(),
                            tEncontrada.getResponsavel().getNome(),
                            tEncontrada.getResponsavel().getId(),
                            tEncontrada.getPrioridade().getDescricao(),
                            tEncontrada.getPrioridade().getId()
                        );
                        JOptionPane.showMessageDialog(painel, mensagem);
                    } else {
                        JOptionPane.showMessageDialog(painel, "Tarefa com ID " + id + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
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
     * Cria o "Cartão 4": O formulário para Alterar uma tarefa (por ID).
     */
    private JPanel criarFormularioAlterar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Alterar Tarefa", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Formulário (ID + Novos Dados) ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int linha = 0;

        // --- Linha 0: ID da Tarefa ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("ID da Tarefa a Alterar:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtIdTarefa = new JTextField(10);
        form.add(txtIdTarefa, gbc);
        linha++;

        // --- Linha 1: Nova Data ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Nova Data (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtNovaData = new JTextField(20);
        form.add(txtNovaData, gbc);
        linha++;

        // --- Linha 2: Nova Descrição ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Nova Descrição:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtNovaDescricao = new JTextField(20);
        form.add(txtNovaDescricao, gbc);
        linha++;

        // --- Linha 3: Nova Observação ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Nova Observação:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtNovaObservacao = new JTextField(20);
        form.add(txtNovaObservacao, gbc);
        linha++;

        // --- Linha 4: Novo ID Responsável ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Novo ID Responsável:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtNovoResponsavelId = new JTextField(20);
        form.add(txtNovoResponsavelId, gbc);
        linha++;

        // --- Linha 5: Novo ID Prioridade ---
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        form.add(new JLabel("Novo ID Prioridade:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        final JTextField txtNovoPrioridadeId = new JTextField(20);
        form.add(txtNovoPrioridadeId, gbc);

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
                // Coleta todos os dados
                String idTarefaStr = txtIdTarefa.getText();
                String dataStr = txtNovaData.getText();
                String descricao = txtNovaDescricao.getText();
                String observacao = txtNovaObservacao.getText();
                String responsavelIdStr = txtNovoResponsavelId.getText();
                String prioridadeIdStr = txtNovoPrioridadeId.getText();

                // Validação de preenchimento
                if (idTarefaStr.isEmpty() || dataStr.isEmpty() || descricao.isEmpty() || responsavelIdStr.isEmpty() || prioridadeIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(painel, "Preencha o ID da Tarefa e todos os novos campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 1. Converter e validar IDs e Data
                    Integer idTarefa = Integer.parseInt(idTarefaStr);
                    Date data = Date.valueOf(dataStr);
                    Integer respId = Integer.parseInt(responsavelIdStr);
                    Integer prioId = Integer.parseInt(prioridadeIdStr);

                    // 2. Buscar a tarefa que será alterada
                    ListaTarefas tParaAlterar = ListaTarefas.pesquisarPorId(idTarefa);
                    if (tParaAlterar == null) {
                        JOptionPane.showMessageDialog(painel, "Tarefa com ID " + idTarefa + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 3. Buscar os novos objetos relacionados
                    Responsavel novoResp = Responsavel.pesquisarPorId(respId);
                    Prioridade novaPrio = Prioridade.pesquisarPorId(prioId);

                    if (novoResp == null) {
                        JOptionPane.showMessageDialog(painel, "Novo Responsável com ID " + respId + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (novaPrio == null) {
                        JOptionPane.showMessageDialog(painel, "Nova Prioridade com ID " + prioId + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 4. Atualizar o objeto tarefa
                    tParaAlterar.setData_tarefa(data);
                    tParaAlterar.setDescricao_tarefa(descricao);
                    tParaAlterar.setObservacao(observacao);
                    tParaAlterar.setResponsavel(novoResp);
                    tParaAlterar.setPrioridade(novaPrio);

                    // 5. Salvar (Alterar) no banco
                    boolean sucesso = tParaAlterar.alterar(); // <-- Lógica Corrigida

                    if (sucesso) {
                        JOptionPane.showMessageDialog(painel, "Tarefa ID " + idTarefa + " alterada com sucesso!");
                        // Limpa todos os campos
                        txtIdTarefa.setText("");
                        txtNovaData.setText("");
                        txtNovaDescricao.setText("");
                        txtNovaObservacao.setText("");
                        txtNovoResponsavelId.setText("");
                        txtNovoPrioridadeId.setText("");
                        cardLayout.show(painelPrincipal, TELA_INICIAL);
                    } else {
                        JOptionPane.showMessageDialog(painel, "Erro ao alterar a tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "ID inválido. IDs devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(painel, "Formato de data inválido. Use AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return painel;
    }

    /**
     * Cria o "Cartão 5": O formulário para Deletar uma tarefa (por ID).
     */
    private JPanel criarFormularioDeletar() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel tituloForm = new JLabel("Deletar Tarefa", SwingConstants.CENTER);
        tituloForm.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(tituloForm, BorderLayout.NORTH);

        // --- Formulário (por ID) ---
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("ID da Tarefa:"), gbc); // <-- Corrigido

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
                    JOptionPane.showMessageDialog(painel, "Por favor, informe o ID da tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    Integer id = Integer.parseInt(idTexto);
                    
                    // 1. Pesquisar para confirmar que existe
                    ListaTarefas tParaDeletar = ListaTarefas.pesquisarPorId(id); // <-- Lógica Corrigida
                    
                    if (tParaDeletar == null) {
                         JOptionPane.showMessageDialog(painel, "Tarefa com ID " + id + " não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 2. Deletar
                        boolean sucesso = tParaDeletar.deletar(); // <-- Lógica Corrigida
                        
                        if (sucesso) {
                            JOptionPane.showMessageDialog(painel, "Tarefa '" + tParaDeletar.getDescricao_tarefa() + "' (ID: " + id + ") deletada com sucesso!");
                            txtId.setText("");
                            cardLayout.show(painelPrincipal, TELA_INICIAL);
                        } else {
                            JOptionPane.showMessageDialog(painel, "Erro ao deletar a tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
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