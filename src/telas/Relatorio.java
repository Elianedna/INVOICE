/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;
import classes.AdmLogado;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author watel
 */
public class Relatorio extends javax.swing.JFrame
{

    
    /**
     * Creates new form visualizarVendas
     */
    public Relatorio()
    {
        initComponents();
        carregarDadosFactura();
    }

   private void carregarDadosFactura() {
        String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
        String dbUser = "root";
        String dbPassword = "123456";

        String nomeClienteFiltro = nomeCliente.getText().trim(); // Obtém o nome do cliente do campo

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM factura");

        // Se o campo nomeCliente não estiver vazio, adiciona filtro na consulta SQL
        if (!nomeClienteFiltro.isEmpty()) {
            sqlBuilder.append(" WHERE cliente LIKE '%").append(nomeClienteFiltro).append("%'");
        }

        String sql = sqlBuilder.toString();

        try (Connection con = DriverManager.getConnection(connectionURL, dbUser, dbPassword)) {
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

                DefaultTableModel model = new DefaultTableModel(new Object[]{
                    "ID", "Nome Vendedor", "Nome Cliente", "Data Emissão", "Total"
                }, 0);

                while (rs.next()) {
                    int idFactura = rs.getInt("id_factura");
                    String nomeVendedor = rs.getString("nome_vendedor");
                    String nomeCliente = rs.getString("cliente");
                    Date dataEmissao = rs.getDate("data_emissao");
                    double total = rs.getDouble("total");
                    model.addRow(new Object[]{
                        idFactura, nomeVendedor, nomeCliente, dataEmissao, total
                    });
                }

                // Define o modelo da tabela productos1
                productos1.setModel(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.");
        }
    }

    
    
    

   
   private void imprimirRelatorioVendas() {
        DefaultTableModel modelVendas = (DefaultTableModel) productos1.getModel();
        
        String nomeAdministrador = AdmLogado.getNomeAdministrador(); // Obtém o nome do administrador logado

        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    g2d.setFont(new Font("Rockwell Condensed", Font.PLAIN, 14));

                    int y = 20;

                    // Cabeçalho
                    g2d.setColor(Color.GRAY);
                    g2d.drawString("RELATÓRIO DE VENDAS", 10, y);
                    y += 20;
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("Data: " + java.time.LocalDate.now(), 10, y);
                    y += 20;
                    g2d.drawString("Nome Administrador: " + nomeAdministrador, 10, y);
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
                    g2d.drawString("Nome Cliente", 200, y);
                    g2d.drawString("Data Emissão", 340, y);
                    g2d.drawString("Total", 460, y);
                    g2d.drawLine(10, y + 2, 500, y + 2);
                    y += 20;

                    // Linhas da Tabela
                    g2d.setColor(Color.BLACK);
                    for (int i = 0; i < modelVendas.getRowCount(); i++) {
                        if (i % 2 == 0) {
                            g2d.setColor(new Color(230, 230, 250)); // Cor de fundo para linhas pares
                            g2d.fillRect(10, y - 12, 500, 20);
                        }

                        g2d.setColor(Color.BLACK);
                        for (int col = 0; col < modelVendas.getColumnCount(); col++) {
                            Object cellValue = modelVendas.getValueAt(i, col);
                            String value = (cellValue != null) ? cellValue.toString() : "";
                            switch (col) {
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

                    return PAGE_EXISTS;
                }
            });

            boolean doPrint = job.printDialog();
            if (doPrint) {
                job.print();
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao imprimir o relatório de vendas: " + ex.getMessage(), "Erro de Impressão", JOptionPane.ERROR_MESSAGE);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        productos1 = new javax.swing.JTable();
        relatorio = new javax.swing.JButton();
        nomeCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(163, 174, 208));

        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("LISTA DE VENDAS");

        productos1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        productos1.setForeground(new java.awt.Color(0, 102, 102));
        productos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String []
            {
                "ID", "Vendedor", "Cliente", "Data_Emissao", "Total_Pago"
            }
        ));
        productos1.setRowHeight(29);
        jScrollPane2.setViewportView(productos1);

        relatorio.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        relatorio.setForeground(new java.awt.Color(0, 51, 51));
        relatorio.setText("BAIXAR RELATÓRIO");
        relatorio.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                relatorioMouseClicked(evt);
            }
        });

        nomeCliente.setFont(new java.awt.Font("Rockwell Condensed", 0, 14)); // NOI18N
        nomeCliente.setText("Nome Cliente");
        nomeCliente.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nomeClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(relatorio)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(relatorio)
                    .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        jLabel1.setBackground(new java.awt.Color(255, 0, 153));
        jLabel1.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("SAIR");
        jLabel1.setToolTipText("");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 0, 153));
        jLabel2.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("PRODUCTOS");
        jLabel2.setToolTipText("");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel3.setText("VENDEDORES");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setText("ADMINISTRADORES");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jLabel3)
                .addGap(63, 63, 63)
                .addComponent(jLabel6)
                .addGap(119, 119, 119)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel1MouseClicked
    {//GEN-HEADEREND:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new LoginAdm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void relatorioMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_relatorioMouseClicked
    {//GEN-HEADEREND:event_relatorioMouseClicked
       imprimirRelatorioVendas();
    }//GEN-LAST:event_relatorioMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel2MouseClicked
    {//GEN-HEADEREND:event_jLabel2MouseClicked
        // TODO add your handling code here:
        new Productos().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void nomeClienteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nomeClienteActionPerformed
    {//GEN-HEADEREND:event_nomeClienteActionPerformed
        // TODO add your handling code here:
        carregarDadosFactura();
    }//GEN-LAST:event_nomeClienteActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel3MouseClicked
    {//GEN-HEADEREND:event_jLabel3MouseClicked
        // TODO add your handling code here:
        new Funcionarios().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel6MouseClicked
    {//GEN-HEADEREND:event_jLabel6MouseClicked
        // TODO add your handling code here:
        new Adminstradores().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

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
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Relatorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Relatorio().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nomeCliente;
    private javax.swing.JTable productos1;
    private javax.swing.JButton relatorio;
    // End of variables declaration//GEN-END:variables
}
