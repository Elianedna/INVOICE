/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author watel
 */
public class Clientes extends javax.swing.JFrame
{
      public Connection con;
    String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
    String dbUser = "root";
    String dbPassword = "123456";

    /**
     * Creates new form Clientes
     */
    public Clientes()
    {
        initComponents();
        try
        {
            con = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
            System.out.println("Conexão bem-sucedida!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        mostrarCliente();
        adicionarListenerTabela();

        vendedores.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                if (!event.getValueIsAdjusting() && vendedores.getSelectedRow() != -1)
                {
                    carregarClienteSelecionado();
                }
            }
        });
    }

    
    // Método para atualizar produto no banco de dados
    public void atualizarCliente(int id, String nome, String telefone)
    {
        String sql = "UPDATE clientes SET nome = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
         pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");
            JOptionPane.showMessageDialog(this, "Cliente Atualizado com Sucesso!!!");

            // Atualizar a tabela após a atualização do produto
            mostrarCliente();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void carregarClienteSelecionado()
    {
        int selectedRow = vendedores.getSelectedRow();
        if (selectedRow != -1)
        {
            int id = (int) vendedores.getValueAt(selectedRow, 0);
            String nome = (String) vendedores.getValueAt(selectedRow, 1);
            String telefone = (String) vendedores.getValueAt(selectedRow, 2);

            nomeTxt.setText(nome);
            emailTxt.setText(telefone);

        }
    }
    public void inserirCliente(String nome,String telefone)
    {
        String sql = "INSERT INTO clientes (nome, telefone,data_emissao ) VALUES (?, ?,?)";
        LocalDate dataAdmissao = LocalDate.now();
        if (verificarTelefoneExistente("clientes", telefone))
        {
            JOptionPane.showMessageDialog(this, "Telefone já está em uso por um Cliente.",
                "Erro de Inserção", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);
            pstmt.setDate(3, java.sql.Date.valueOf(dataAdmissao));
            pstmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");
            JOptionPane.showMessageDialog(this, "Cliente Adicionado com Sucesso!!!");

            // Atualizar a tabela após a inserção do produto
            mostrarCliente();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean verificarTelefoneExistente(String tabela, String telefone)
    {
        String sql = "SELECT COUNT(*) FROM " + tabela + " WHERE telefone = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, telefone);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    // Método para atualizar a tabela com todos os adms
    public void mostrarCliente()
    {
        String sql = "SELECT id, nome, telefone, data_emissao FROM clientes";
        try (java.sql.Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql))
        {

            DefaultTableModel model = (DefaultTableModel) vendedores.getModel();
            model.setRowCount(0); // Limpar a tabela antes de adicionar os resultados

            while (rs.next())
            {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("telefone");
                java.util.Date dataDeCriacao = rs.getDate("data_emissao");
                model.addRow(new Object[]
                {
                    id, nome, email, dataDeCriacao
                });
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void adicionarListenerTabela()
    {
        vendedores.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent event)
            {
                if (!event.getValueIsAdjusting() && vendedores.getSelectedRow() != -1)
                {
                    int selectedRow = vendedores.getSelectedRow();
                    Object dataDeCriacao = vendedores.getValueAt(selectedRow, 3); // Assumindo que a data está na quarta coluna
                    if (dataDeCriacao != null)
                    {
                        java.util.Date data = (java.util.Date) dataDeCriacao;
                        System.out.println("Data de admissão: " + data);
                        // Você pode adicionar mais lógica aqui se necessário
                    }
                }
            }
        });
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
        nomeTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        guardar = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        apagar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        vendedores = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(163, 174, 208));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("CLIENTES");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        nomeTxt.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        nomeTxt.setForeground(new java.awt.Color(255, 0, 153));
        jPanel2.add(nomeTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 178, 31));

        emailTxt.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        emailTxt.setForeground(new java.awt.Color(255, 0, 153));
        jPanel2.add(emailTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 178, 31));

        jLabel7.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Telefone");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 178, 40));

        jLabel9.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText(" Lista de Clientes");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        guardar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        guardar.setForeground(new java.awt.Color(0, 102, 102));
        guardar.setText("GUARDAR");
        guardar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                guardarActionPerformed(evt);
            }
        });
        jPanel2.add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, 100, 30));

        editar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        editar.setForeground(new java.awt.Color(0, 51, 51));
        editar.setText("EDITAR");
        editar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editarActionPerformed(evt);
            }
        });
        jPanel2.add(editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 100, 30));

        apagar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        apagar.setForeground(new java.awt.Color(153, 0, 0));
        apagar.setText("APAGAR");
        apagar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                apagarActionPerformed(evt);
            }
        });
        jPanel2.add(apagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, 100, 30));

        vendedores.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        vendedores.setForeground(new java.awt.Color(0, 102, 102));
        vendedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "ID", "Nome", "Telefone", "Data_Admissão"
            }
        ));
        vendedores.setRowHeight(29);
        jScrollPane2.setViewportView(vendedores);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 510, 450));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Nome do Cliente");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 178, 20));

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

        jLabel10.setBackground(new java.awt.Color(255, 0, 153));
        jLabel10.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 51));
        jLabel10.setText("VER VENDAS");
        jLabel10.setToolTipText("");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 51));
        jLabel12.setText("ADMINISTRADORES");

        jLabel11.setBackground(new java.awt.Color(255, 0, 153));
        jLabel11.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 51));
        jLabel11.setText("PRODUCTOS");
        jLabel11.setToolTipText("");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_guardarActionPerformed
    {//GEN-HEADEREND:event_guardarActionPerformed
        // TODO add your handling code here:
        String nome = nomeTxt.getText();
        String telefone = emailTxt.getText();
        
        

        // Verificar se os campos não estão vazios
        if (nome.isEmpty() || telefone.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
            return;
        }

      
        // Inserir o administrador
        inserirCliente(nome, telefone);
    }//GEN-LAST:event_guardarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_editarActionPerformed
    {//GEN-HEADEREND:event_editarActionPerformed
        // TODO add your handling code here:
        int selectedRow = vendedores.getSelectedRow();
        if (selectedRow != -1)
        {
            int id = (int) vendedores.getValueAt(selectedRow, 0);
            String nome = nomeTxt.getText();
            String telefone = emailTxt.getText();
             atualizarCliente(id, nome, telefone);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!");
        }
    }//GEN-LAST:event_editarActionPerformed

    private void apagarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_apagarActionPerformed
    {//GEN-HEADEREND:event_apagarActionPerformed
        // TODO add your handling code here:
        int selectedRow = vendedores.getSelectedRow();
        if (selectedRow != -1)
        {
            int id = (int) vendedores.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja apagar este Cliente?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION)
            {
                String sql = "DELETE FROM clientes WHERE id = ?";

                try (PreparedStatement pstmt = con.prepareStatement(sql))
                {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    System.out.println("Cliente apagado com sucesso!");
                    JOptionPane.showMessageDialog(this, "Vendedor Apagado com Sucesso!!!");

                    mostrarCliente();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Selecione um administrador para apagar!");
        }
    }//GEN-LAST:event_apagarActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel1MouseClicked
    {//GEN-HEADEREND:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new LoginAdm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel10MouseClicked
    {//GEN-HEADEREND:event_jLabel10MouseClicked
        // TODO add your handling code here:
        new Venda().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel11MouseClicked
    {//GEN-HEADEREND:event_jLabel11MouseClicked
        // TODO add your handling code here:
        new Productos().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton apagar;
    private javax.swing.JButton editar;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nomeTxt;
    private javax.swing.JTable vendedores;
    // End of variables declaration//GEN-END:variables
}
