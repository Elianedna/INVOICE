/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.AdmLogado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author watel
 */
public class LoginAdm extends javax.swing.JFrame
{

    /**
     * Creates new form LoginAdm
     */
    public LoginAdm()
    {
        initComponents();
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

        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nomeUser = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        entrar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        telaAdm = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(163, 174, 208));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("Palavra-passe");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 168, -1));

        nomeUser.setForeground(new java.awt.Color(255, 0, 204));
        jPanel6.add(nomeUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 271, 36));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Nome do Administrador");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 190, -1));

        password.setForeground(new java.awt.Color(0, 51, 51));
        password.setToolTipText("");
        password.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                passwordActionPerformed(evt);
            }
        });
        jPanel6.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 271, 37));

        entrar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        entrar.setForeground(new java.awt.Color(0, 51, 51));
        entrar.setText("ENTRAR");
        entrar.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                entrarMouseClicked(evt);
            }
        });
        entrar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                entrarActionPerformed(evt);
            }
        });
        jPanel6.add(entrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(204, 255, 255));
        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 0, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("LOGIN");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel8)
                .addContainerGap(266, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 600, 50));

        telaAdm.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        telaAdm.setForeground(new java.awt.Color(0, 51, 51));
        telaAdm.setText("Continuar como Vendedor");
        telaAdm.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                telaAdmMouseClicked(evt);
            }
        });
        jPanel6.add(telaAdm, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 240, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passwordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_passwordActionPerformed
    {//GEN-HEADEREND:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void entrarMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_entrarMouseClicked
    {//GEN-HEADEREND:event_entrarMouseClicked
        // TODO add your handling code here:
        String nomeAdministrador = nomeUser.getText();
        
        if (this.nomeUser.getText().isEmpty() || new String(password.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta Informacoes");
        } else {
            String url = "jdbc:mysql://localhost:3306/faturacao";
            String user = "root";
            String pwd = "123456";

            String sql = "SELECT * FROM administradores WHERE nome = ? AND senha = ?";

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pwd);
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, this.nomeUser.getText());
                stmt.setString(2, new String(this.password.getPassword()));

                rs = stmt.executeQuery();
                if (rs.next()) {
                    AdmLogado.setNomeAdministrador(nomeAdministrador); 
                    new Productos().setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Palavra-Passe ou Utilizador Invalido!!");
                    this.nomeUser.setText("");
                    this.password.setText("");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Driver JDBC não encontrado!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_entrarMouseClicked

    private void entrarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_entrarActionPerformed
    {//GEN-HEADEREND:event_entrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entrarActionPerformed

    private void telaAdmMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_telaAdmMouseClicked
    {//GEN-HEADEREND:event_telaAdmMouseClicked
        // TODO add your handling code here:
        new LoginFuncionario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_telaAdmMouseClicked

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
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(LoginAdm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new LoginAdm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton entrar;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField nomeUser;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel telaAdm;
    // End of variables declaration//GEN-END:variables
}
