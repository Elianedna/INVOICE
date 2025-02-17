/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.UserLogado;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author watel
 */
public class Venda extends javax.swing.JFrame
{

    /**
     * Creates new form Venda
     */
    public Venda()
    {
        initComponents();
        factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]
            {
            },
            new String[]
            {
                "ID", "Nome", "Preço", "Quantidade", "Total"
            }
        ));

        productos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]
            {
            },
            new String[]
            {
                "ID", "Nome", "Categoria", "Preço", "Quantidade em Estoque"
            }
        ));

        pnome.setEditable(false);
        pPreco.setEditable(false);
        vendedor.setEditable(false);
        Troco.setEditable(false);

        exibirDadosTabela();
        vendedor.setText(UserLogado.getNome());

        productos1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                int selectedRow = productos1.getSelectedRow();
                if (selectedRow != -1)
                {
                    DefaultTableModel model = (DefaultTableModel) productos1.getModel();
                    String nomeProduto = model.getValueAt(selectedRow, 1).toString();
                    String preco = model.getValueAt(selectedRow, 3).toString();
                    pnome.setText(nomeProduto);
                    pPreco.setText(preco);
                }
            }
        });
    }

    public boolean verificarSenhaAdministrador(String senhaDigitada)
    {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";
        String sql = "SELECT senha FROM administradores WHERE senha = ?";
        try (Connection conn = DriverManager.getConnection(connectionURL, dbUser, dbPassword); PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, senhaDigitada);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Erro ao verificar senha do administrador: " + e.getMessage());
        }
        return false;
    }

    private void inserirFaturaNoBanco()
    {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";

        DefaultTableModel modelFatura = (DefaultTableModel) factura.getModel();

        double totalGlobal = 0.0;
        for (int i = 0; i < modelFatura.getRowCount(); i++)
        {
            totalGlobal += (double) modelFatura.getValueAt(i, 4);
        }

        try (Connection con = DriverManager.getConnection(connectionURL, dbUser, dbPassword))
        {
            con.setAutoCommit(false);

            String sql = "INSERT INTO factura (nome_vendedor, data_emissao, total) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                String nomeVendedor = vendedor.getText();
                LocalDate dataEmissao = LocalDate.now();

                stmt.setString(1, nomeVendedor);
                stmt.setDate(2, Date.valueOf(dataEmissao));
                stmt.setDouble(3, totalGlobal);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next())
                {
                    long facturaId = rs.getLong(1);
                    inserirItensFatura(con, facturaId);
                }

                con.commit();
                JOptionPane.showMessageDialog(null, "Fatura inserida com sucesso.");
            }
            catch (SQLException e)
            {
                con.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao inserir fatura no banco de dados.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
        }
    }

    private void imprimirRelatorioVendas()
    {
        DefaultTableModel modelVendas = (DefaultTableModel) factura.getModel();

        String nomeVendedor = vendedor.getText();
        try
        {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new Printable()
            {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
                {
                    if (pageIndex > 0)
                    {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    g2d.setFont(new Font("Rockwell Condensed", Font.PLAIN, 14));

                    int y = 20;

                    // Cabeçalho
                    g2d.setColor(Color.GRAY);
                    g2d.drawString(" LOJA DA QUÉTURA, LDA", 10, y);
                    y += 20;
                    
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("CENTRALIDADE ZANGO 8000, V 144", 10, y);
                    y += 20;
                    g2d.drawString("LUANDA, ANGOLA", 10, y);
                    y += 20;
                    g2d.drawString("NIF:12345678900", 10, y);
                    y += 20;
                    g2d.drawString("FACTURA ProForma", 10, y);
                    y += 20;
                    g2d.drawString("Data: " + java.time.LocalDate.now(), 10, y);
                    y += 20;
                    g2d.drawString("Nome Cliente: " + nomeVendedor, 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString("=======================================================================================================", 10, y);
                    y += 30;

                    // Cabeçalho da Tabela
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("ID", 10, y);
                    g2d.drawString("Nome Vendedor", 60, y);
                    g2d.drawString("Preco", 200, y);
                    g2d.drawString("Quantidade", 340, y);
                    g2d.drawString("Total", 460, y);
                    g2d.drawLine(10, y + 2, 500, y + 2);
                    y += 20;

                    // Linhas da Tabela
                    g2d.setColor(Color.BLACK);
                    for (int i = 0; i < modelVendas.getRowCount(); i++)
                    {
                        if (i % 2 == 0)
                        {
                            g2d.setColor(new Color(230, 230, 250)); // Cor de fundo para linhas pares
                            g2d.fillRect(10, y - 12, 500, 20);
                        }

                        g2d.setColor(Color.BLACK);
                        for (int col = 0; col < modelVendas.getColumnCount(); col++)
                        {
                            Object cellValue = modelVendas.getValueAt(i, col);
                            String value = (cellValue != null) ? cellValue.toString() : "";
                            switch (col)
                            {
                                case 0:
                                    g2d.drawString(value, 10, y); // ID
                                    break;
                                case 1:
                                    g2d.drawString(value, 60, y); // Nome Vendedor
                                    break;
                                case 2:
                                    g2d.drawString(value, 200, y); // Nome Cliente
                                    break;
                                case 3:
                                    g2d.drawString(value, 340, y); // Data Emissão
                                    break;
                                case 4:
                                    g2d.drawString(value, 460, y); // Total
                                    break;
                            }
                        }
                        y += 20;
                    }

                    g2d.drawString("=======================================================================================================", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" 7% de IVA incluído na factura para cada producto. ", 10, y);
                    y += 20;
                    g2d.drawString("=======================================================================================================", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString(" 7% de IVA incluído na factura para cada producto. ", 10, y);
                    y += 20;
                    return PAGE_EXISTS;
                }
            });

            boolean doPrint = job.printDialog();
            if (doPrint)
            {
                job.print();
            }
        }
        catch (PrinterException ex)
        {
            JOptionPane.showMessageDialog(null, "Erro ao imprimir o relatório de vendas: " + ex.getMessage(), "Erro de Impressão", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*private double calcularValorAPagar()
    {
        // Supondo que ValorAPagar seja o valor sem IVA
        double valorSemIVA = Double.parseDouble(ValorAPagar.getText());
        double iva = valorSemIVA * 0.07; // Calcula 7% de IVA
        double valorComIVA = valorSemIVA + iva;
        ValorAPagar.setText(String.valueOf(valorComIVA));
        System.out.println(valorComIVA);
        return valorComIVA;
    }

    private double calcularTroco()
    {
        double valorAPagar = Double.parseDouble(ValorAPagar.getText());
        double valorPago = Double.parseDouble(ValorPago.getText());
        double troco = valorPago - valorAPagar;
        this.Troco.setText(String.valueOf(troco));
        return troco;
    }*/
    private void imprimirFactura()
    {
        DefaultTableModel modelVendas = (DefaultTableModel) factura.getModel();

        String nomeVendedor = vendedor.getText();
        // double valorAPagarComIVA = calcularValorAPagar();
        // double trocoCalculado = calcularTroco();

        // Verifica se o campo valorPago não está vazio
        String valorPagoText = ValorPago.getText();
        if (valorPagoText.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "O campo Valor Pago está vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double valorPago = Double.parseDouble(valorPagoText);

        String telefoneCliente = telefone.getText(); // Supondo que você tenha um JTextField chamado telefone
        String nomeCliente = getNomeClienteByTelefone(telefoneCliente);

        if (nomeCliente.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado para o telefone informado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Adiciona a linha na tabela 'factura'
        // adicionarFactura(nomeVendedor, nomeCliente, LocalDate.now(), valorAPagarComIVA);
        // Troco.setText(String.format("%.2f", trocoCalculado));
        try
        {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new Printable()
            {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
                {
                    if (pageIndex > 0)
                    {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    g2d.setFont(new Font("Rockwell Condensed", Font.PLAIN, 14));

                    int y = 20;

                    // Cabeçalho
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("FACTURA CLIENTE", 10, y);
                    y += 20;
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("Data: " + java.time.LocalDate.now(), 10, y);
                    y += 20;
                    g2d.drawString("Nome Vendedor: " + nomeVendedor, 10, y);
                    y += 20;
                    g2d.drawString("Nome Cliente: " + nomeCliente, 10, y);
                    y += 20;
                    g2d.drawString("Nome da Empresa: LOJA DA QUÉTURA", 10, y);
                    y += 20;
                    g2d.drawString("Localização: CENTRALIDADE ZANGO 8000, V 144", 10, y);
                    y += 20;
                    g2d.drawString("LUANDA, ANGOLA", 10, y);
                    y += 20;
                    g2d.drawString("NIF:12345678900", 10, y);
                    y += 20;
                    g2d.drawString(" ", 10, y);
                    y += 20;
                    g2d.drawString("=======================================================================================================", 10, y);
                    y += 30;

                    // Cabeçalho da Tabela
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("ID", 10, y);
                    g2d.drawString("Nome Vendedor", 60, y);
                    g2d.drawString("Cliente", 200, y);
                    g2d.drawString("Data Emissão", 340, y);
                    g2d.drawString("Total", 460, y);
                    g2d.drawLine(10, y + 2, 500, y + 2);
                    y += 20;g2d.drawString("=======================================================================================================", 10, y);
                    y += 30;
                    

                    // Linhas da Tabela
                    g2d.setColor(Color.BLACK);
                    for (int i = 0; i < modelVendas.getRowCount(); i++)
                    {
                        if (i % 2 == 0)
                        {
                            g2d.setColor(new Color(230, 230, 250)); // Cor de fundo para linhas pares
                            g2d.fillRect(10, y - 12, 500, 20);
                        }

                        g2d.setColor(Color.BLACK);
                        for (int col = 0; col < modelVendas.getColumnCount(); col++)
                        {
                            Object cellValue = modelVendas.getValueAt(i, col);
                            String value = (cellValue != null) ? cellValue.toString() : "";
                            switch (col)
                            {
                                case 0:
                                    g2d.drawString(value, 10, y); // ID
                                    break;
                                case 1:
                                    g2d.drawString(value, 60, y); // Nome Vendedor
                                    break;
                                case 2:
                                    g2d.drawString(nomeCliente, 200, y); // Nome Cliente
                                    break;
                                case 3:
                                    g2d.drawString(value, 340, y); // Data Emissão
                                    break;
                                case 4:
                                    g2d.drawString(value, 460, y); // Total
                                    break;
                            }
                        }
                        y += 20;
                    }
                    return PAGE_EXISTS;
                    
                }
            });

            if (job.printDialog())
            {
                job.print();
            }
            
            
        }
        catch (PrinterException e)
        {
            e.printStackTrace();
        }
    }

    public void adicionarFactura(String nomeVendedor, String nomeCliente, LocalDate dataEmissao, double total)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";
        System.out.println(total);

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/faturacao", dbUser, dbPassword);
            String sql = "INSERT INTO factura (nome_vendedor, cliente, data_emissao, total) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeVendedor);
            stmt.setString(2, nomeCliente);
            stmt.setDate(3, Date.valueOf(dataEmissao));
            stmt.setDouble(4, total);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

    }

    public String getNomeClienteByTelefone(String telefone)
    {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";
        String nomeCliente = "";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/faturacao", dbUser, dbPassword);
            String sql = "SELECT nome FROM clientes WHERE telefone = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, telefone);
            rs = stmt.executeQuery();

            if (rs.next())
            {
                nomeCliente = rs.getString("nome");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return nomeCliente;
    }

    // Método para calcular o troco
    private void calcularTroco()
    {
        String precoText = pPreco.getText();
        String quantidadeText = quantidade.getText();
        String valorPagoTxt = ValorPago.getText();

        // Realize a validação e conversão dos valores conforme você já implementou
        double preco;
        int quantidade;

        try
        {
            quantidade = Integer.parseInt(quantidadeText);
            if (quantidade <= 0)
            {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida (maior que zero).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            preco = Double.parseDouble(precoText);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o preço.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = productos1.getSelectedRow();
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um produto da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelProdutos = (DefaultTableModel) productos1.getModel();
        int idProduto = (int) modelProdutos.getValueAt(selectedRow, 0);
        int quantidadeEmEstoque = (int) modelProdutos.getValueAt(selectedRow, 4);

        if (quantidade > quantidadeEmEstoque)
        {
            JOptionPane.showMessageDialog(this, "Quantidade insuficiente em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = preco * quantidade;

        // Adicionar o produto à tabela factura apenas se ainda não estiver lá
        boolean produtoJaAdicionado = false;
        DefaultTableModel modelFactura = (DefaultTableModel) factura.getModel();
        for (int i = 0; i < modelFactura.getRowCount(); i++)
        {
            int id = (int) modelFactura.getValueAt(i, 0);
            if (id == idProduto)
            {
                produtoJaAdicionado = true;
                break;
            }
        }

        /*if (!produtoJaAdicionado) {
        adicionarProdutoAFactura(idProduto, pnome.getText(), preco, quantidade, total);
    }*/
        double totalGlobal = 0.0;
        double troco = 0.0;
        double valorPagoD = 0.0;

        try
        {
            valorPagoD = Double.parseDouble(valorPagoTxt);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o Valor Pago.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular o total global
        for (int i = 0; i < modelFactura.getRowCount(); i++)
        {
            totalGlobal += (double) modelFactura.getValueAt(i, 4);
        }

        // Calcular o troco
        troco = valorPagoD - totalGlobal;
        this.Troco.setText(String.valueOf(troco));
    }

    private void inserirItensFatura(Connection con, long facturaId) throws SQLException
    {
        DefaultTableModel modelFatura = (DefaultTableModel) factura.getModel();

        String sql = "INSERT INTO itens_fatura (factura_id, produto_id, quantidade, preco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            for (int i = 0; i < modelFatura.getRowCount(); i++)
            {
                int produtoId = (int) modelFatura.getValueAt(i, 0);
                int quantidade = (int) modelFatura.getValueAt(i, 3);
                double preco = (double) modelFatura.getValueAt(i, 2);

                stmt.setLong(1, facturaId);
                stmt.setInt(2, produtoId);
                stmt.setInt(3, quantidade);
                stmt.setDouble(4, preco);
                stmt.addBatch();

                atualizarEstoque(con, produtoId, quantidade);
            }
            stmt.executeBatch();
        }
    }

    private void atualizarEstoque(Connection con, int produtoId, int quantidadeVendida) throws SQLException
    {
        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        }
    }

    private void exibirDadosTabela()
    {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";

        String sql = "SELECT id_produto, nome_produto, categoria, preco, quantidade FROM produtos";

        try (Connection con = DriverManager.getConnection(connectionURL, dbUser, dbPassword); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            DefaultTableModel model = (DefaultTableModel) productos1.getModel();
            model.setRowCount(0);

            while (rs.next())
            {
                int idProduto = rs.getInt("id_produto");
                String nomeProduto = rs.getString("nome_produto");
                String categoria = rs.getString("categoria");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                model.addRow(new Object[]
                {
                    idProduto, nomeProduto, categoria, preco, quantidade
                });
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productos1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        factura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        adicionarAFactura = new javax.swing.JButton();
        vendedor = new javax.swing.JTextField();
        pnome = new javax.swing.JTextField();
        pPreco = new javax.swing.JTextField();
        quantidade = new javax.swing.JTextField();
        anular = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        facturaProForma = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        imprimir = new javax.swing.JButton();
        Troco = new javax.swing.JTextField();
        Trocol = new javax.swing.JLabel();
        telefone = new javax.swing.JTextField();
        pagamentos = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ValorPago = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        CadastrarCliente = new javax.swing.JButton();
        Pagamento = new javax.swing.JButton();

        jLabel9.setText("jLabel9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 51, 51));

        jPanel1.setBackground(new java.awt.Color(163, 174, 208));
        jPanel1.setForeground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productos1.setFont(new java.awt.Font("Rockwell Condensed", 1, 14)); // NOI18N
        productos1.setForeground(new java.awt.Color(0, 51, 51));
        productos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "ID", "Nome", "Categoria", "Preço"
            }
        ));
        productos1.setSelectionForeground(new java.awt.Color(0, 51, 51));
        jScrollPane1.setViewportView(productos1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 440, 162));

        factura.setFont(new java.awt.Font("Rockwell Condensed", 1, 14)); // NOI18N
        factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String []
            {
                "ID", "Nome", "Preço", "Quantidade", "Total"
            }
        ));
        jScrollPane2.setViewportView(factura);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 220, 440, 370));

        jLabel1.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("TOTAL:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 590, 70, -1));

        jLabel2.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("00.0 KZS");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 590, -1, -1));

        adicionarAFactura.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        adicionarAFactura.setForeground(new java.awt.Color(0, 51, 51));
        adicionarAFactura.setText("Adicionar à Factura");
        adicionarAFactura.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                adicionarAFacturaMouseClicked(evt);
            }
        });
        adicionarAFactura.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                adicionarAFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(adicionarAFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, -1, 30));

        vendedor.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                vendedorActionPerformed(evt);
            }
        });
        jPanel1.add(vendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 179, 37));
        jPanel1.add(pnome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 179, 37));
        jPanel1.add(pPreco, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 179, 37));
        jPanel1.add(quantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 179, 37));

        anular.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        anular.setForeground(new java.awt.Color(0, 51, 51));
        anular.setText(" Anular Factura");
        anular.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                anularMouseClicked(evt);
            }
        });
        anular.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                anularActionPerformed(evt);
            }
        });
        jPanel1.add(anular, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 400, 180, -1));

        jLabel3.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("FACTURA CLIENTE");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, -1, -1));

        jLabel4.setBackground(new java.awt.Color(163, 174, 208));
        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("VENDER PRODUCTOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, -1, -1));

        jLabel5.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Nome Producto:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Nome Vendedor:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        jLabel7.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Preço:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Quantidade:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        facturaProForma.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        facturaProForma.setForeground(new java.awt.Color(0, 51, 51));
        facturaProForma.setText("FACTURA PRO-FORMA");
        facturaProForma.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                facturaProFormaMouseClicked(evt);
            }
        });
        jPanel1.add(facturaProForma, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 570, 200, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 0, 153));
        jLabel10.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 51));
        jLabel10.setText("SAIR");
        jLabel10.setToolTipText("");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 70));

        imprimir.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        imprimir.setText("Imprimir Factura");
        imprimir.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                imprimirMouseClicked(evt);
            }
        });
        imprimir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                imprimirActionPerformed(evt);
            }
        });
        jPanel1.add(imprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 530, 190, 40));

        Troco.setFont(new java.awt.Font("Rockwell Condensed", 0, 12)); // NOI18N
        jPanel1.add(Troco, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 400, 180, 30));

        Trocol.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        Trocol.setText("Valor Pago");
        jPanel1.add(Trocol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 230, -1, -1));

        telefone.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(telefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 330, 180, 30));

        pagamentos.setFont(new java.awt.Font("Rockwell Condensed", 1, 12)); // NOI18N
        pagamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão" }));
        pagamentos.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                pagamentosActionPerformed(evt);
            }
        });
        jPanel1.add(pagamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 240, 180, 30));

        jLabel11.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel11.setText("Forma de Pagamento");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 220, -1, -1));

        jLabel12.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel12.setText("Telefone Cliente");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 310, -1, -1));

        ValorPago.setFont(new java.awt.Font("Rockwell Condensed", 0, 12)); // NOI18N
        ValorPago.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ValorPagoActionPerformed(evt);
            }
        });
        jPanel1.add(ValorPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 260, 180, 30));

        jLabel13.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel13.setText("Troco");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1410, 380, -1, -1));

        jLabel15.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 51));
        jLabel15.setText("DADOS CLIENTES");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 140, -1, -1));

        CadastrarCliente.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        CadastrarCliente.setText("Cadastrar Cliente");
        CadastrarCliente.setToolTipText("");
        CadastrarCliente.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                CadastrarClienteMouseClicked(evt);
            }
        });
        jPanel1.add(CadastrarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 470, 180, 30));

        Pagamento.setBackground(new java.awt.Color(0, 102, 102));
        Pagamento.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        Pagamento.setForeground(new java.awt.Color(51, 0, 51));
        Pagamento.setText("PAGAR");
        Pagamento.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                PagamentoMouseClicked(evt);
            }
        });
        Pagamento.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                PagamentoActionPerformed(evt);
            }
        });
        jPanel1.add(Pagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 530, 90, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1508, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anularActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_anularActionPerformed
    {//GEN-HEADEREND:event_anularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_anularActionPerformed

    private void adicionarAFacturaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_adicionarAFacturaActionPerformed
    {//GEN-HEADEREND:event_adicionarAFacturaActionPerformed
        // TODO add your handling code 
        // TODO add your handling code here
        String nomeProduto = pnome.getText();
        String precoText = pPreco.getText();
        String quantidadeText = quantidade.getText();
        String valorPagoTxt = ValorPago.getText();

        if (nomeProduto.isEmpty() || precoText.isEmpty() || quantidadeText.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantidade;
        try
        {
            quantidade = Integer.parseInt(quantidadeText);
            if (quantidade <= 0)
            {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida (maior que zero).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double preco;
        try
        {
            preco = Double.parseDouble(precoText);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o preço.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = productos1.getSelectedRow();
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um produto da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel modelProdutos = (DefaultTableModel) productos1.getModel();
        int idProduto = (int) modelProdutos.getValueAt(selectedRow, 0);
        int quantidadeEmEstoque = (int) modelProdutos.getValueAt(selectedRow, 4);

        if (quantidade > quantidadeEmEstoque)
        {
            JOptionPane.showMessageDialog(this, "Quantidade insuficiente em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = preco * quantidade;

        DefaultTableModel modelFactura = (DefaultTableModel) factura.getModel();
        modelFactura.addRow(new Object[]
        {
            idProduto, nomeProduto, preco, quantidade, total
        });

        double totalGlobal = 0.0;
//        double troco = 0.0;
//        double valorPagoD = 0.0;
        for (int i = 0; i < modelFactura.getRowCount(); i++)
        {
            totalGlobal += (double) modelFactura.getValueAt(i, 4);
        }
        jLabel2.setText(totalGlobal + " KZS");


    }//GEN-LAST:event_adicionarAFacturaActionPerformed


    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel10MouseClicked
    {//GEN-HEADEREND:event_jLabel10MouseClicked
        // TODO add your handling code here:
        new LoginAdm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_imprimirActionPerformed
    {//GEN-HEADEREND:event_imprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirActionPerformed

    private void CadastrarClienteMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CadastrarClienteMouseClicked
    {//GEN-HEADEREND:event_CadastrarClienteMouseClicked
        // TODO add your handling code here:
        new Clientes().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CadastrarClienteMouseClicked

    private void facturaProFormaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_facturaProFormaMouseClicked
    {//GEN-HEADEREND:event_facturaProFormaMouseClicked
        // TODO add your handling code here:FacturaPro
        DefaultTableModel modelVendas = (DefaultTableModel) factura.getModel();
        if (modelVendas.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Não há dados para imprimir.", "Tabela Vazia", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método sem imprimir
        }
        imprimirRelatorioVendas();
    }//GEN-LAST:event_facturaProFormaMouseClicked

    private void imprimirMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_imprimirMouseClicked
    {//GEN-HEADEREND:event_imprimirMouseClicked
        // TODO add your handling code here:
        DefaultTableModel modelVendas = (DefaultTableModel) factura.getModel();
        if (modelVendas.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Não há dados para imprimir.", "Tabela Vazia", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método sem imprimir
        }
        if (this.Troco.getText().isEmpty() || this.ValorPago.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método sem imprimir
        }
        imprimirFactura();
    }//GEN-LAST:event_imprimirMouseClicked

    private void pagamentosActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pagamentosActionPerformed
    {//GEN-HEADEREND:event_pagamentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagamentosActionPerformed

    private void anularMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_anularMouseClicked
    {//GEN-HEADEREND:event_anularMouseClicked
        // TODO add your handling code here:

        String inputPassword = JOptionPane.showInputDialog(this, "Digite a palavra-passe do administrador:");

        if (verificarSenhaAdministrador(inputPassword))
        {
            int selectedRow = factura.getSelectedRow();
            if (selectedRow != -1)
            {
                DefaultTableModel model = (DefaultTableModel) factura.getModel();
                model.removeRow(selectedRow);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Nenhum item selecionado para anular.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Palavra-passe incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_anularMouseClicked

    private void vendedorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_vendedorActionPerformed
    {//GEN-HEADEREND:event_vendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorActionPerformed

    private void adicionarAFacturaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_adicionarAFacturaMouseClicked
    {//GEN-HEADEREND:event_adicionarAFacturaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_adicionarAFacturaMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel2MouseClicked
    {//GEN-HEADEREND:event_jLabel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

    private void PagamentoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_PagamentoActionPerformed
    {//GEN-HEADEREND:event_PagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PagamentoActionPerformed

    private void PagamentoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_PagamentoMouseClicked
    {//GEN-HEADEREND:event_PagamentoMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_PagamentoMouseClicked

    private void ValorPagoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ValorPagoActionPerformed
    {//GEN-HEADEREND:event_ValorPagoActionPerformed
        // TODO add your handling code here:
        calcularTroco();
    }//GEN-LAST:event_ValorPagoActionPerformed

//   private void atualizarEstoque(int idProduto, int novaQuantidade) {
//    String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
//    String dbUser = "root";
//    String dbPassword = "123456";
//
//    String sql = "UPDATE produtos SET quantidade = ? WHERE id_produto = ?";
//
//    try (Connection con = DriverManager.getConnection(connectionURL, dbUser, dbPassword); PreparedStatement stmt = con.prepareStatement(sql)) {
//        stmt.setInt(1, novaQuantidade);
//        stmt.setInt(2, idProduto);
//        stmt.executeUpdate();
//
//        // Atualiza a tabela productos1 na interface
//        exibirDadosTabela();
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erro ao atualizar a quantidade em estoque.");
//    }
//}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Venda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Venda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Venda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Venda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Venda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CadastrarCliente;
    private javax.swing.JButton Pagamento;
    private javax.swing.JTextField Troco;
    private javax.swing.JLabel Trocol;
    private javax.swing.JTextField ValorPago;
    private javax.swing.JButton adicionarAFactura;
    private javax.swing.JButton anular;
    private javax.swing.JTable factura;
    private javax.swing.JButton facturaProForma;
    private javax.swing.JButton imprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pPreco;
    private javax.swing.JComboBox<String> pagamentos;
    private javax.swing.JTextField pnome;
    private javax.swing.JTable productos1;
    private javax.swing.JTextField quantidade;
    private javax.swing.JTextField telefone;
    private javax.swing.JTextField vendedor;
    // End of variables declaration//GEN-END:variables
}
