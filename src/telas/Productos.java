package telas;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Productos extends javax.swing.JFrame
{

    public Connection con;
    String connectionURL = "jdbc:mysql://localhost:3306/faturacao";
    String dbUser = "root";
    String dbPassword = "123456";

    public Productos()
    {
        initComponents();
        mostrarProductos();
        try
        {
            con = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
            System.out.println("Conexão bem-sucedida!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        productos1.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                if (!event.getValueIsAdjusting() && productos1.getSelectedRow() != -1)
                {
                    carregarProdutoSelecionado();
                }
            }
        });

    }

    // Método para atualizar produto no banco de dados
    public void atualizarProduto(int id, String nome, double preco, String categoria)
    {
        String sql = "UPDATE produtos SET nome_produto = ?, categoria = ?, preco = ? WHERE id_produto = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, nome);
            pstmt.setString(2, categoria);
            pstmt.setDouble(3, preco);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");
            JOptionPane.showMessageDialog(this, "Produto Atualizado com Sucesso!!!");

            // Atualizar a tabela após a atualização do produto
            mostrarProductos();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Método para inserir produto no banco de dados
    public void inserirProduto(String nome, double preco, String categoria)
    {
        String sql = "INSERT INTO produtos (nome_produto, categoria, preco) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, nome);
            pstmt.setString(2, categoria);
            pstmt.setDouble(3, preco);
            pstmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");
            JOptionPane.showMessageDialog(this, "Produto Adicionado com Sucesso!!!");

            // Atualizar a tabela após a inserção do produto
            mostrarProductos();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Método para atualizar a tabela com todos os produtos
    public void mostrarProductos() {
        String sql = "SELECT id_produto, nome_produto, categoria, preco FROM produtos";
        try (Connection conn = DriverManager.getConnection(connectionURL, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            DefaultTableModel model = (DefaultTableModel) productos1.getModel();
            model.setRowCount(0); // Limpar a tabela antes de adicionar os resultados

            while (rs.next()) {
                int idProduto = rs.getInt("id_produto");
                String nomeProduto = rs.getString("nome_produto");
                String categoria = rs.getString("categoria");
                double preco = rs.getDouble("preco");
                model.addRow(new Object[]{idProduto, nomeProduto, categoria, preco});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarProdutoSelecionado()
    {
        int selectedRow = productos1.getSelectedRow();
        if (selectedRow != -1)
        {
            int idProduto = (int) productos1.getValueAt(selectedRow, 0);
            String nomeProduto = (String) productos1.getValueAt(selectedRow, 1);
            String categoria = (String) productos1.getValueAt(selectedRow, 2);
            double preco = (double) productos1.getValueAt(selectedRow, 3);

            nomeTxt.setText(nomeProduto);
            precoTxt.setText(String.valueOf(preco));
            comboCat.setSelectedItem(categoria);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nomeTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        precoTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        filtro = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        guardar = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        apagar = new javax.swing.JButton();
        comboCat = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        productos1 = new javax.swing.JTable();
        actualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jLabel11.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel11.setText("ADMINISTRADORES");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(163, 174, 208));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("GESTOR DE PRODUCTOS");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        nomeTxt.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        nomeTxt.setForeground(new java.awt.Color(255, 0, 153));
        jPanel2.add(nomeTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 178, 31));

        jLabel5.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Nome do Producto");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 178, -1));

        precoTxt.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        precoTxt.setForeground(new java.awt.Color(255, 0, 153));
        jPanel2.add(precoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 178, 31));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Categoria");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 166, -1));

        filtro.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        filtro.setForeground(new java.awt.Color(0, 51, 51));
        filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Eletrônicos", "Eletrodomésticos", "Roupas e Acessórios", "Calçados", "Alimentos e Bebidas", "Produtos de Limpeza", "Móveis", "Decoração", "Brinquedos", "Livros e Revistas", "Material Escolar e de Escritório", "Beleza e Higiene Pessoal", "Esportes e Lazer", "Ferramentas e Equipamentos", "Automotivos", "Pet Shop", "Jardinagem e Ferramentas de Jardinagem", "Saúde e Bem-Estar", "Informática e Acessórios de Computador", "Utensílios Domésticos" }));
        filtro.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                filtroActionPerformed(evt);
            }
        });
        jPanel2.add(filtro, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, 181, 33));

        jLabel7.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Preço do Producto");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 178, -1));

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("FILTRAR CATEGORIAS:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 142, 190, 30));

        jLabel9.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText(" Lista de Productos");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, -1, -1));

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
        jPanel2.add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, 40));

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
        jPanel2.add(editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 490, 100, 37));

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
        jPanel2.add(apagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, 100, 38));

        comboCat.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        comboCat.setForeground(new java.awt.Color(0, 51, 51));
        comboCat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Eletrônicos", "Eletrodomésticos", "Roupas e Acessórios", "Calçados", "Alimentos e Bebidas", "Produtos de Limpeza", "Móveis", "Decoração", "Brinquedos", "Livros e Revistas", "Material Escolar e de Escritório", "Beleza e Higiene Pessoal", "Esportes e Lazer", "Ferramentas e Equipamentos", "Automotivos", "Pet Shop", "Jardinagem e Ferramentas de Jardinagem", "Saúde e Bem-Estar", "Informática e Acessórios de Computador", "Utensílios Domésticos" }));
        jPanel2.add(comboCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 181, 31));

        productos1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        productos1.setForeground(new java.awt.Color(0, 102, 102));
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
        productos1.setRowHeight(29);
        jScrollPane2.setViewportView(productos1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 230, 366, 329));

        actualizar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        actualizar.setForeground(new java.awt.Color(0, 51, 51));
        actualizar.setText("ACTUALIZAR TABELA");
        actualizar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                actualizarActionPerformed(evt);
            }
        });
        jPanel2.add(actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 570, -1, 37));

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

        jLabel3.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("VENDEDORES");

        jLabel12.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 51));
        jLabel12.setText("ADMINISTRADORES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jLabel3)
                .addGap(65, 65, 65)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void guardarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_guardarActionPerformed
    {//GEN-HEADEREND:event_guardarActionPerformed
        // TODO add your handling code here:

        String nome = nomeTxt.getText();
        String precoText = precoTxt.getText();
        String categoria = comboCat.getSelectedItem().toString();

        // Verificar se os campos não estão vazios
        if (nome.isEmpty() || precoText.isEmpty() || categoria.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
            return;
        }

        // Converter o preço para double
        try
        {
            double preco = Double.parseDouble(precoText);
            // Chamar o método para inserir produto
            inserirProduto(nome, preco, categoria);
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número!");
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_editarActionPerformed
    {//GEN-HEADEREND:event_editarActionPerformed
        // TODO add your handling code here:

        int selectedRow = productos1.getSelectedRow();
        if (selectedRow != -1)
        {
            int idProduto = (int) productos1.getValueAt(selectedRow, 0);
            String nome = nomeTxt.getText();
            String precoText = precoTxt.getText();
            String categoria = comboCat.getSelectedItem().toString();

            if (nome.isEmpty() || precoText.isEmpty() || categoria.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
            }
            else
            {
                try
                {
                    double preco = Double.parseDouble(precoText);
                    atualizarProduto(idProduto, nome, preco, categoria);
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(this, "Preço deve ser um número!");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar!");
        }
    }//GEN-LAST:event_editarActionPerformed

    private void apagarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_apagarActionPerformed
    {//GEN-HEADEREND:event_apagarActionPerformed
        // TODO add your handling code here:
        int selectedRow = productos1.getSelectedRow();
        if (selectedRow != -1) {
            int idProduto = (int) productos1.getValueAt(selectedRow, 0);
            apagarProduto(idProduto);
        } else {
            JOptionPane.showMessageDialog(Productos.this, "Selecione um produto para apagar!");
        }
    }//GEN-LAST:event_apagarActionPerformed

    private void filtroActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filtroActionPerformed
    {//GEN-HEADEREND:event_filtroActionPerformed
        // TODO add your handling code here:
        String filtro = this.filtro.getSelectedItem().toString();
        filtrarProdutos(filtro);
        
    }//GEN-LAST:event_filtroActionPerformed

    private void actualizarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_actualizarActionPerformed
    {//GEN-HEADEREND:event_actualizarActionPerformed
        // TODO add your handling code here:
          mostrarProductos();
    }//GEN-LAST:event_actualizarActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel1MouseClicked
    {//GEN-HEADEREND:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new LoginAdm().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel10MouseClicked
    {//GEN-HEADEREND:event_jLabel10MouseClicked
        // TODO add your handling code here:
        new visualizarVendas().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    public void filtrarProdutos(String filtro) {
    String sql = "SELECT id_produto, nome_produto, categoria, preco FROM produtos WHERE categoria = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, filtro);
        ResultSet rs = pstmt.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel) productos1.getModel();
        model.setRowCount(0); // Limpar a tabela antes de adicionar os resultados do filtro
        
        while (rs.next()) {
            int idProduto = rs.getInt("id_produto");
            String nomeProduto = rs.getString("nome_produto");
            String categoria = rs.getString("categoria");
            double preco = rs.getDouble("preco");
            model.addRow(new Object[]{idProduto, nomeProduto, categoria, preco});
        }
        
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
    // Adicione este método na mesma classe onde você definiu o método inserirProduto()

public void apagarProduto(int idProduto) {
    String sql = "DELETE FROM produtos WHERE id_produto = ?";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, idProduto);
        int rowsDeleted = pstmt.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(this, "Produto apagado com sucesso!");
            mostrarProductos(); // Atualizar a tabela após a exclusão
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível apagar o produto!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

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
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizar;
    private javax.swing.JButton apagar;
    private javax.swing.JComboBox<String> comboCat;
    private javax.swing.JButton editar;
    private javax.swing.JComboBox<String> filtro;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nomeTxt;
    private javax.swing.JTextField precoTxt;
    private javax.swing.JTable productos1;
    // End of variables declaration//GEN-END:variables
}
