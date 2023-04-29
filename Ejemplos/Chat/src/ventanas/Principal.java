/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventanas;


import herramientas.Texto;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.TableView;
import red.Cliente;

/**
 *
 * @author luiss
 */
public class Principal extends javax.swing.JFrame {
    Inicio vInicio;    
    Cliente cliente;
    Texto texto = new Texto();
    /**
     * Creates new form Principal
     */
    
    public Principal() {        
        initComponents();
    }
    
    //public Principal(String usuario, String ipS, int puertoS, Inicio vInicio) {
    public Principal(Cliente cliente, Inicio vInicio) {
        this.vInicio = vInicio;
                
        this.cliente = cliente;
        this.setTitle( this.cliente.getUsuario().getNombre() + " - " + this.cliente.getId() );
        initComponents();
        
        servicioCliente.start();
    }
    
    //Hilo para recepción de mensajes
    Thread servicioCliente = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] mensajeDelServidor = new String[3];
                        
                        while (true) {                            
                            String mensaje = cliente.recibirMensaje();
                            
                            //Auxiliar. Puede borrarse
                            System.out.println("MSG Recibido en clase Cliente");
                            
                            mensajeDelServidor = cliente.analizarMensajeServidor(mensaje);
                            
                            if (mensajeDelServidor[0].equals("OK")) {
                                
                                String[] m = {mensajeDelServidor[1],mensajeDelServidor[2]};
                                
                                switch (mensajeDelServidor[1]) {
                                    case "Servidor: ":
                                        cargarTexto(m, "servidor");
                                        break;
                                    case "USUARIOS":
                                        String[] usuarios = texto.analizarListaUsuarios(m[1]);
                                        actualizarListaUsuarios(usuarios);
                                        break;
                                    default:
                                        cargarTexto(m,"yo");
                                }
                            } else {
                                //En caso de errores, se notifican mediante un mensaje modal
                                JOptionPane.showMessageDialog(
                                    rootPane, mensajeDelServidor[0]+":"+mensajeDelServidor[1],
                                    "Error",JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }
                        
                    } catch (Exception e) {
                        System.out.println("ERROR en servicioCliente de clase Principal");
                        System.out.println("ERROR: " + e.getMessage());
                        for (StackTraceElement ste : e.getStackTrace()) {
                            System.out.println(ste.toString());
                        }
                        
                    }
                }
            }
        );
    
    
    /**
     * Recarga la lista de usuarios de acuerdo a un listado desde el servidor
     * @param listadoUsuarios 
     */
    private void actualizarListaUsuarios(String[] listadoUsuarios){
        
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0);
        
        
        for (String nombre : listadoUsuarios) {
            if ( ! this.cliente.getUsuario().getNombre().equals(nombre)) {
                modelo.addRow(new Object[]{nombre});
            }
        }
    }
    
    
    
    /**
     * Permite cargar mensajes recibidos en el panel de mensajes
     * @param txt
     * @param modo 
     */
    public void cargarTexto(String[] txt, String modo){
        //this.txta_listaMensajes.append(texto+"\n");
        int posFinal = 0;
        SimpleAttributeSet[] atributos = texto.atributosDeTexto();
        try {
            switch (modo) {
                case "servidor":
                    posFinal = jTextPane1.getStyledDocument().getLength();
                    jTextPane1.getStyledDocument().insertString(posFinal, txt[0], atributos[0]);
                    posFinal = jTextPane1.getStyledDocument().getLength();
                    jTextPane1.getStyledDocument().insertString(posFinal, txt[1]+"\n", atributos[1]);
                    break;
                case "yo":
                    posFinal = jTextPane1.getStyledDocument().getLength();
                    jTextPane1.getStyledDocument().insertString(posFinal, txt[0], atributos[3]);
                    posFinal = jTextPane1.getStyledDocument().getLength();
                    jTextPane1.getStyledDocument().insertString(posFinal, txt[1]+"\n", atributos[2]);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
        }
        
    }
    
    /**
     * Permite cargar el mensaje del cuadro de texto y enviarlo
     */
    private void enviarMensaje(){
        String mensaje = txt_mensaje.getText();
        cliente.enviarSolicitud("MSG::PUB",mensaje);
        txt_mensaje.setText("");
        String[] m = {"Yo: ",mensaje};
        cargarTexto(m,"yo");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_mensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnEnviar.setText("Enviar");
        btnEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEnviarMouseClicked(evt);
            }
        });
        btnEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEnviarKeyPressed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
        }

        jTextPane1.setEditable(false);
        jScrollPane4.setViewportView(jTextPane1);

        jButton1.setText("Cambiar Nombre");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEnviar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnviarMouseClicked
        // TODO add your handling code here:
        enviarMensaje();
    }//GEN-LAST:event_btnEnviarMouseClicked

    private void btnEnviarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEnviarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            enviarMensaje();
        }
    }//GEN-LAST:event_btnEnviarKeyPressed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField txt_mensaje;
    // End of variables declaration//GEN-END:variables
}
