package UI;
import Entidades.Cliente;
import Entidades.Producto;
import Mocks.UsuarioDAOMock;
import Mocks.ProductosDAOMock;
import BaseDatos.IUsuarioDAO;
import BaseDatos.IProductosDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdministradorMain extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdministradorMain.class.getName());
    private final IUsuarioDAO clienteDAO;
    private final IProductosDAO productoDAO;

    public AdministradorMain() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Instanciamos los DAOs mock (temporales)
        clienteDAO = new UsuarioDAOMock();
        productoDAO = new ProductosDAOMock();

        // Cargar datos iniciales
        cargarTablaClientes();
        cargarTablaProductos();
    }
private void cargarTablaProductos() {
    String[] columnas = {"ID", "Nombre", "Precio", "Stock"};
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0);

    for (var p : productoDAO.obtenerProductosPorNombre("")) {
        Object[] fila = {p.getId(), p.getNombre(), p.getPrecio(), p.getStock()};
        modelo.addRow(fila);
    }

    tablaProductos.setModel(modelo);
}

private void cargarTablaClientes() {
    String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Teléfono"};
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0);

    for (var c : clienteDAO.obtenerClientes(0)) {
        Object[] fila = {c.getId(), c.getNombre(), c.getApellido(), c.getEmail(), c.getTelefono()};
        modelo.addRow(fila);
    }

    tablaClientes.setModel(modelo);
}

     private void buscarProducto() {
        String texto = txtBuscarProducto.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de producto.");
            return;
        }
        try {
            int id = Integer.parseInt(texto);
            Producto producto = productoDAO.obtenerProductoPorID(id);
            if (producto != null) {
                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"ID", "Nombre", "Precio", "Stock"}, 0
                );
                model.addRow(new Object[]{producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getStock()});
                tablaProductos.setModel(model);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el producto con ID: " + id);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        }
    }
     private void buscarCliente() {
        String texto = txtBuscarCliente.getText().trim();
        //Agregar despues, por mientras sera buscar un limite de clientes
        
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un email o nombre para buscar.");
            return;
        }
        
        int limite = Integer.parseInt(texto);

        List<Cliente> resultados = clienteDAO.obtenerClientes(limite);
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Email", "Teléfono"}, 0
        );
        for (Cliente c : resultados) {
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getEmail(), c.getTelefono()});
        }
        tablaClientes.setModel(model);
    }
     private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
            return;
        }
        int id = (int) tablaProductos.getValueAt(fila, 0);
        //productoDAO.eliminarProducto(id);
        cargarTablaProductos();
        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
    }
     private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
            return;
        }
        int id = (int) tablaClientes.getValueAt(fila, 0);
        //clienteDAO.eliminarCliente(id);
        cargarTablaClientes();
        JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnAgregarProducto = new javax.swing.JButton();
        btnEditarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnVerCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnBuscarCliente = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtBuscarCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblVentasTotales = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnActualizarReportes = new javax.swing.JButton();
        btnExportarExcel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 153, 153));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaProductos);

        btnAgregarProducto.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnAgregarProducto.setText("AGREGAR PRODUCTO");

        btnEditarProducto.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnEditarProducto.setText("EDITAR PRODUCTO");

        btnEliminarProducto.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnEliminarProducto.setText("ELIMINAR PRODUCTO");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnBuscarProducto.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnBuscarProducto.setText("BUSCAR PRODUCTO");
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });

        txtBuscarProducto.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel1.setText("ID DEL PRODUCTO :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarProducto)
                    .addComponent(btnBuscarProducto)
                    .addComponent(btnEliminarProducto)
                    .addComponent(btnEditarProducto))
                .addGap(13, 13, 13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnEditarProducto)
                .addGap(31, 31, 31)
                .addComponent(btnEliminarProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscarProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(btnAgregarProducto)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("PRODUCTOS", jPanel1);

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaClientes);

        btnVerCliente.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnVerCliente.setText("VER CLIENTE");
        btnVerCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnEliminarCliente.setText("ELIMINAR CLIENTE");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnBuscarCliente.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnBuscarCliente.setText("BUSCAR CLIENTE");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel2.setText("Ingresar email o nombre :");

        txtBuscarCliente.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarCliente)
                        .addComponent(jLabel2))
                    .addComponent(btnEliminarCliente)
                    .addComponent(btnVerCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnVerCliente)
                        .addGap(41, 41, 41)
                        .addComponent(btnEliminarCliente)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCliente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CLIENTES", jPanel2);

        lblVentasTotales.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblVentasTotales.setText("INGRESOS TOTALES : S/XXX.XX");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        btnActualizarReportes.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnActualizarReportes.setText("ACTUALIZAR REPORTES");
        btnActualizarReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarReportesActionPerformed(evt);
            }
        });

        btnExportarExcel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btnExportarExcel.setText("EXPORTAR EXCEL");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnActualizarReportes)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportarExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(lblVentasTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(lblVentasTotales)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizarReportes)
                            .addComponent(btnExportarExcel))
                        .addGap(15, 15, 15))))
        );

        jTabbedPane1.addTab("REPORTES", jPanel4);

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("MINIMARKET ADMIN");

        btnCerrarSesion.setBackground(new java.awt.Color(102, 102, 102));
        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(0, 255, 204));
        btnCerrarSesion.setText("CERRAR SESION");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCerrarSesion)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrarSesion)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerClienteActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
    int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar sesión?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            //Cambiar lo de abajo
            //new LoginFrame().setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed

    String texto = txtBuscarProducto.getText().trim();
    if (texto.isEmpty()) {
        cargarTablaProductos();
        return;
    }

    try {
        int id = Integer.parseInt(texto);
        var producto = productoDAO.obtenerProductoPorID(id);
        if (producto != null) {
            javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(
                    new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
            modelo.addRow(new Object[]{producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getStock()});
            tablaProductos.setModel(modelo);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese un ID válido");
    }

    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
String texto = txtBuscarCliente.getText().trim().toLowerCase();
    if (texto.isEmpty()) {
        cargarTablaClientes();
        return;
    }

    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "Email", "Teléfono"}, 0);

    for (var c : clienteDAO.obtenerClientes(0)) {
        if (c.getNombre().toLowerCase().contains(texto) || c.getEmail().toLowerCase().contains(texto)) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getApellido(), c.getEmail(), c.getTelefono()});
        }
    }

    tablaClientes.setModel(modelo);       // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
int fila = tablaProductos.getSelectedRow();
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto primero");
        return;
    }

    int id = (int) tablaProductos.getValueAt(fila, 0);
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "¿Eliminar producto ID " + id + "?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        var p = productoDAO.obtenerProductoPorID(id);
        if (p != null) {
            productoDAO.obtenerProductosPorNombre("").remove(p);
            cargarTablaProductos();
            javax.swing.JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
        }
    }      // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
eliminarCliente();    // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnActualizarReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarReportesActionPerformed
 double totalVentas = 0;
    for (var p : productoDAO.obtenerProductosPorNombre("")) {
        totalVentas += p.getPrecio() * p.getStock(); // Ejemplo simple
    }
    lblVentasTotales.setText("INGRESOS TOTALES: S/" + String.format("%.2f", totalVentas));
    }//GEN-LAST:event_btnActualizarReportesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdministradorMain().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarReportes;
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnEditarProducto;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnExportarExcel;
    private javax.swing.JButton btnVerCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblVentasTotales;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarProducto;
    // End of variables declaration//GEN-END:variables
}
