/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.swing.gui;

import ats.daos.TransactionDAO;
import ats.daos.VehiclePaymentDAO;
import ats.dtos.Transaction;
import ats.dtos.VehiclePayment;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CaretEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chi Hieu
 */
public class MainForm extends javax.swing.JFrame {

    private DefaultTableModel model;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

    }

    private void loadDataIntoJTable() throws Exception {
        model = new DefaultTableModel();
        //Set Column Title
        Vector column = new Vector();
        column.add("Transaction ID");
        column.add("License Plate");
        column.add("Username");
        column.add("Fee");
        column.add("Date");
        model.setColumnIdentifiers(column);
        TransactionDAO dao = new TransactionDAO();
        List<Transaction> list = dao.listOfTransaction();
        for (int i = 0; i < list.size(); i++) {
            Transaction tr = (Transaction) list.get(i);
            Vector row = new Vector();
            row.add(tr.getIdTransaction());
            row.add(tr.getLicensePlate());
            row.add(tr.getUsername());
            row.add(tr.getFee());
            row.add(tr.getDateOfTransaction());
            model.addRow(row);
        }

        tblHistory.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabHome = new javax.swing.JPanel();
        InfoPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLicensePlate = new javax.swing.JTextField();
        lbPirce = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTypeName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        btnManualPayment = new javax.swing.JButton();
        btnOpenCarrier = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tabHistory = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        tabReport = new javax.swing.JPanel();
        tabSetting = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new java.awt.CardLayout());

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1280, 720));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1280, 720));

        tabHome.setBackground(new java.awt.Color(153, 204, 255));
        tabHome.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tabHome.setPreferredSize(new java.awt.Dimension(1280, 720));

        InfoPane.setBackground(new java.awt.Color(204, 255, 255));
        InfoPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xe hiện tại", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 24), new java.awt.Color(102, 102, 255))); // NOI18N
        InfoPane.setMaximumSize(new java.awt.Dimension(270, 350));
        InfoPane.setMinimumSize(new java.awt.Dimension(270, 350));
        InfoPane.setPreferredSize(new java.awt.Dimension(270, 350));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Biển số xe:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Loại xe:");

        txtLicensePlate.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtLicensePlate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLicensePlate.setToolTipText("Nhập biển số xe");
        txtLicensePlate.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtLicensePlate.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtLicensePlateCaretUpdate(evt);
            }
        });
        txtLicensePlate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLicensePlateActionPerformed(evt);
            }
        });
        txtLicensePlate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLicensePlateKeyPressed(evt);
            }
        });

        lbPirce.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbPirce.setForeground(new java.awt.Color(255, 0, 51));
        lbPirce.setText("VNĐ");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Giá tiền:");

        lbTypeName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbTypeName.setForeground(new java.awt.Color(255, 0, 51));
        lbTypeName.setText("Loại xe");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Tình trạng:");

        lbStatus.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(255, 0, 51));
        lbStatus.setText("Đã thu phí");

        btnManualPayment.setFont(new java.awt.Font("Arial", 0, 22)); // NOI18N
        btnManualPayment.setText("Đã Thu");
        btnManualPayment.setToolTipText("Ấn để tiến hành thu phí");
        btnManualPayment.setName(""); // NOI18N
        btnManualPayment.setPreferredSize(new java.awt.Dimension(140, 40));
        btnManualPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManualPaymentActionPerformed(evt);
            }
        });
        btnManualPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnManualPaymentKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout InfoPaneLayout = new javax.swing.GroupLayout(InfoPane);
        InfoPane.setLayout(InfoPaneLayout);
        InfoPaneLayout.setHorizontalGroup(
            InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(InfoPaneLayout.createSequentialGroup()
                        .addComponent(txtLicensePlate, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnManualPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(InfoPaneLayout.createSequentialGroup()
                        .addGroup(InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPirce, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        InfoPaneLayout.setVerticalGroup(
            InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLicensePlate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManualPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPirce)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTypeName)
                .addGap(21, 21, 21)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        btnOpenCarrier.setFont(new java.awt.Font("Arial", 0, 22)); // NOI18N
        btnOpenCarrier.setText("Mở Cổng");
        btnOpenCarrier.setToolTipText("Ấn để tiến hành thu phí");
        btnOpenCarrier.setName(""); // NOI18N
        btnOpenCarrier.setPreferredSize(new java.awt.Dimension(140, 40));
        btnOpenCarrier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenCarrierActionPerformed(evt);
            }
        });
        btnOpenCarrier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnOpenCarrierKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 925, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout tabHomeLayout = new javax.swing.GroupLayout(tabHome);
        tabHome.setLayout(tabHomeLayout);
        tabHomeLayout.setHorizontalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addGroup(tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(InfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnOpenCarrier, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabHomeLayout.setVerticalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addComponent(InfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOpenCarrier, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thu Phí", tabHome);
        tabHome.getAccessibleContext().setAccessibleName("");

        tabHistory.setBackground(new java.awt.Color(153, 204, 255));

        tblHistory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHistory.setSelectionBackground(new java.awt.Color(255, 255, 153));
        jScrollPane1.setViewportView(tblHistory);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm theo số xe");

        txtSearch.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnSearch.setText("Tìm kiếm");

        javax.swing.GroupLayout tabHistoryLayout = new javax.swing.GroupLayout(tabHistory);
        tabHistory.setLayout(tabHistoryLayout);
        tabHistoryLayout.setHorizontalGroup(
            tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHistoryLayout.createSequentialGroup()
                .addGroup(tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabHistoryLayout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch))
                    .addGroup(tabHistoryLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        tabHistoryLayout.setVerticalGroup(
            tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabHistoryLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lịch Sử Giao Dịch", tabHistory);
        tabHistory.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout tabReportLayout = new javax.swing.GroupLayout(tabReport);
        tabReport.setLayout(tabReportLayout);
        tabReportLayout.setHorizontalGroup(
            tabReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1275, Short.MAX_VALUE)
        );
        tabReportLayout.setVerticalGroup(
            tabReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Báo Cáo và Phân Tích", tabReport);
        tabReport.getAccessibleContext().setAccessibleName("");
        tabReport.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout tabSettingLayout = new javax.swing.GroupLayout(tabSetting);
        tabSetting.setLayout(tabSettingLayout);
        tabSettingLayout.setHorizontalGroup(
            tabSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1275, Short.MAX_VALUE)
        );
        tabSettingLayout.setVerticalGroup(
            tabSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Cài Đặt", tabSetting);

        getContentPane().add(jTabbedPane1, "card2");
        jTabbedPane1.getAccessibleContext().setAccessibleName("JTabbedPane");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void btnManualPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManualPaymentActionPerformed
        JOptionPane.showMessageDialog(null, "Đã thu phí", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnManualPaymentActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed


    private void txtLicensePlateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLicensePlateActionPerformed

    }//GEN-LAST:event_txtLicensePlateActionPerformed

    private void txtLicensePlateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtLicensePlateCaretUpdate
        String licensePlate = txtLicensePlate.getText();
        VehiclePaymentDAO dao = new VehiclePaymentDAO();
        VehiclePayment vp = new VehiclePayment();
        try {
            vp = dao.searchPaymentByLicensePlate(licensePlate);
        } catch (Exception ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        if (licensePlate.length() > 0 && vp.getFee() > 0 && vp.getTypeName().length() > 0) {
            lbPirce.setText(formatter.format(vp.getFee()) + " đồng");
            lbTypeName.setText(vp.getTypeName());
        } else {
            lbPirce.setText("");
            lbTypeName.setText("");
            lbStatus.setText("");
        }
               
    }//GEN-LAST:event_txtLicensePlateCaretUpdate

    private void btnManualPaymentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnManualPaymentKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btnManualPayment.doClick();
        }
    }//GEN-LAST:event_btnManualPaymentKeyPressed

    private void txtLicensePlateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLicensePlateKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btnManualPayment.doClick();
        }
    }//GEN-LAST:event_txtLicensePlateKeyPressed

    private void btnOpenCarrierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenCarrierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOpenCarrierActionPerformed

    private void btnOpenCarrierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnOpenCarrierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOpenCarrierKeyPressed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoPane;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton btnManualPayment;
    private javax.swing.JButton btnOpenCarrier;
    private javax.swing.JButton btnSearch;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbPirce;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbTypeName;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel tabHistory;
    private javax.swing.JPanel tabHome;
    private javax.swing.JPanel tabReport;
    private javax.swing.JPanel tabSetting;
    private javax.swing.JTable tblHistory;
    private javax.swing.JTextField txtLicensePlate;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
