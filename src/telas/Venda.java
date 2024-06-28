/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

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
                "ID", "Nome", "Categoria", "Preço"
            }
        ));

        pnome.setEditable(false);
        pPreco.setEditable(false);
        vendedor.setEditable(false);

        //método para exibir os dados na tabela
        exibirDadosTabela();

        //Metodo para exibir nas textbox
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
            con.setAutoCommit(false); // Iniciar transação

            String sql = "INSERT INTO factura (nome_vendedor, data_emissao, total) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql))
            {
                String nomeVendedor = vendedor.getText();
                LocalDate dataEmissao = LocalDate.now();

                stmt.setString(1, nomeVendedor);
                stmt.setDate(2, Date.valueOf(dataEmissao));
                stmt.setDouble(3, totalGlobal);
                stmt.executeUpdate();

                con.commit(); // Commit transação
                JOptionPane.showMessageDialog(null, "Fatura inserida com sucesso.");
            }
            catch (SQLException e)
            {
                con.rollback(); // Rollback transação em caso de erro
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

    private void exibirDadosTabela()
    {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";

        String sql = "SELECT id_produto, nome_produto, categoria, preco FROM produtos";

        try (Connection con = DriverManager.getConnection(connectionURL, dbUser, dbPassword); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {
            DefaultTableModel model = (DefaultTableModel) productos1.getModel();
            model.setRowCount(0); // Limpar a tabela antes de adicionar os resultados

            while (rs.next())
            {
                int idProduto = rs.getInt("id_produto");
                String nomeProduto = rs.getString("nome_produto");
                String categoria = rs.getString("categoria");
                double preco = rs.getDouble("preco");
                model.addRow(new Object[]
                {
                    idProduto, nomeProduto, categoria, preco
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productos1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        factura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        adicionarAFactura = new javax.swing.JButton();
        vendedor = new javax.swing.JTextField();
        pnome = new javax.swing.JTextField();
        pPreco = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 440, 162));

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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 142, 440, 294));

        jLabel1.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("TOTAL:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 450, 70, -1));

        jLabel2.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("00.0 KZS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 450, -1, -1));

        jButton1.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 51));
        jButton1.setText("Pagar");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 497, -1, -1));

        adicionarAFactura.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        adicionarAFactura.setForeground(new java.awt.Color(0, 51, 51));
        adicionarAFactura.setText("Adicionar à Factura");
        adicionarAFactura.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                adicionarAFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(adicionarAFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 500, -1, -1));
        jPanel1.add(vendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 142, 179, 37));
        jPanel1.add(pnome, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 142, 179, 37));
        jPanel1.add(pPreco, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 179, 37));
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 230, 179, 37));

        jButton3.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 51));
        jButton3.setText("Anular");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(773, 497, -1, -1));

        jLabel3.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("FACTURA CLIENTE");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, -1, -1));

        jLabel4.setBackground(new java.awt.Color(163, 174, 208));
        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("VENDER PRODUCTOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, -1, -1));

        jLabel5.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Nome Producto:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Nome Vendedor:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        jLabel7.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Preço:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Quantidade:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void adicionarAFacturaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_adicionarAFacturaActionPerformed
    {//GEN-HEADEREND:event_adicionarAFacturaActionPerformed
        // TODO add your handling code here
    // Obter os valores dos campos
    String nomeProduto = pnome.getText();
    String precoText = pPreco.getText();
    String quantidadeText = jTextField4.getText();
    
    // Verificar se algum campo está vazio
    if (nomeProduto.isEmpty() || precoText.isEmpty() || quantidadeText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Verificar se a quantidade é válida
    int quantidade;
    try {
        quantidade = Integer.parseInt(quantidadeText);
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida (maior que zero).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Verificar se o preço é válido
    double preco;
    try {
        preco = Double.parseDouble(precoText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, insira um número válido para o preço.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obter o ID do produto a partir da tabela de produtos
    int selectedRow = productos1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecione um produto da tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    DefaultTableModel modelProdutos = (DefaultTableModel) productos1.getModel();
    int idProduto = (int) modelProdutos.getValueAt(selectedRow, 0);

    // Calcular o total
    double total = preco * quantidade;

    // Adicionar os valores na tabela "factura"
    DefaultTableModel modelFactura = (DefaultTableModel) factura.getModel();
    modelFactura.addRow(new Object[]{idProduto, nomeProduto, preco, quantidade, total});

    // Atualizar o total global
    double totalGlobal = 0.0;
    for (int i = 0; i < modelFactura.getRowCount(); i++) {
        totalGlobal += (double) modelFactura.getValueAt(i, 4);
    }
    jLabel2.setText(totalGlobal + " KZS");

    }//GEN-LAST:event_adicionarAFacturaActionPerformed

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
    private javax.swing.JButton adicionarAFactura;
    private javax.swing.JTable factura;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField pPreco;
    private javax.swing.JTextField pnome;
    private javax.swing.JTable productos1;
    private javax.swing.JTextField vendedor;
    // End of variables declaration//GEN-END:variables
}
