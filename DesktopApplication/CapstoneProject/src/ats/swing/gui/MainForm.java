/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.swing.gui;

import ats.daos.TransactionDAO;
import ats.daos.VehicleDAO;
import ats.dtos.LoginDTO;
import ats.dtos.VehicleDTO;
import ats.dtos.VehiclePayment;
import ats.request.LoginRequest;
import ats.request.ManualPaymentRequest;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Chi Hieu
 */
public class MainForm extends javax.swing.JFrame {

    String localhost = "";
//    Timer timer = new Timer(5000, new MyTimerActionListener());

    /**
     * Khởi tạo mainform
     */
    public MainForm(LoginDTO loginDTO) {
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        lbId.setVisible(false);
        txtHello.setText("Xin chào, " + loginDTO.getFullname());
        Long role = loginDTO.getRole();

        /*Kiểm tra role*/
//        if (role == 2) {
//            panePayment.setVisible(false);
//        }

        /*SET Localhost*/
        txtLocalhost.setText(loginDTO.getLocalhost());
    }

    private void clearForm() throws IOException {
        txtLicensePlate.setText("");
        //lbId.setText("");
        lbPirce.setText("-");
        lbTypeName.setText("-");

        File f = new File("./src/picture/default_text.png");
        Image image = ImageIO.read(f);
        this.lbPicture.setIcon(new ImageIcon(image));
    }

    //AutoPaymentRequest apr = new AutoPaymentRequest();
    //list = apr.getListVehicleUnpaid(localhost);
//    class MyTimerActionListener implements ActionListener {
//
//        public void actionPerformed(ActionEvent e) {
//            //int idLane = Integer.parseInt(txtLane.getText().trim());
////            List<VehiclePayment> list = apr.getListVehicleUnpaid(localhost);
//            lbId.setText("Có " + 0 + " người Chưa thanh toán");
//        }
//    }
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
        lbId = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        panelCamera = new javax.swing.JPanel();
        lbPicture = new javax.swing.JLabel();
        txtHello = new javax.swing.JLabel();
        btnManualPayment = new javax.swing.JButton();
        tabSetting = new javax.swing.JPanel();
        panePayment = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtStation = new javax.swing.JFormattedTextField();
        txtLane = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStationName = new javax.swing.JTextField();
        paneBasic = new javax.swing.JPanel();
        txtLogOut = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtLocalhost = new javax.swing.JTextField();
        btnCheckConnection = new javax.swing.JButton();
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

        tabHome.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tabHome.setPreferredSize(new java.awt.Dimension(1280, 720));

        InfoPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xe hiện tại", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 24), new java.awt.Color(0, 51, 255))); // NOI18N
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
        lbPirce.setText("-");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Giá tiền:");

        lbTypeName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lbTypeName.setForeground(new java.awt.Color(255, 0, 51));
        lbTypeName.setText("-");

        lbId.setText("id");

        btnClear.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnClear.setText("Làm mới (F5)");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        btnClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnClearKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout InfoPaneLayout = new javax.swing.GroupLayout(InfoPane);
        InfoPane.setLayout(InfoPaneLayout);
        InfoPaneLayout.setHorizontalGroup(
            InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtLicensePlate, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbId, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPirce, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        InfoPaneLayout.setVerticalGroup(
            InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InfoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLicensePlate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTypeName)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbPirce)
                .addGap(52, 52, 52)
                .addComponent(lbId)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        panelCamera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelCameraLayout = new javax.swing.GroupLayout(panelCamera);
        panelCamera.setLayout(panelCameraLayout);
        panelCameraLayout.setHorizontalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCameraLayout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(lbPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        panelCameraLayout.setVerticalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCameraLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(lbPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        txtHello.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        txtHello.setText("Xin Chào, ");

        btnManualPayment.setBackground(new java.awt.Color(153, 204, 255));
        btnManualPayment.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        btnManualPayment.setForeground(new java.awt.Color(51, 51, 51));
        btnManualPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/750765_hand_512x512.png"))); // NOI18N
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

        javax.swing.GroupLayout tabHomeLayout = new javax.swing.GroupLayout(tabHome);
        tabHome.setLayout(tabHomeLayout);
        tabHomeLayout.setHorizontalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addGroup(tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtHello))
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(btnManualPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(InfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(panelCamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabHomeLayout.setVerticalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabHomeLayout.createSequentialGroup()
                        .addComponent(txtHello)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnManualPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thu Phí", tabHome);
        tabHome.getAccessibleContext().setAccessibleName("");

        panePayment.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin thu phí", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 24), new java.awt.Color(0, 51, 255))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Làn xe");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Mã số trạm");

        txtStation.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtStation.setText("1");
        txtStation.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        txtLane.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtLane.setText("1");
        txtLane.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Tên trạm");

        txtStationName.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout panePaymentLayout = new javax.swing.GroupLayout(panePayment);
        panePayment.setLayout(panePaymentLayout);
        panePaymentLayout.setHorizontalGroup(
            panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panePaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLane, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStation, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStationName, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        panePaymentLayout.setVerticalGroup(
            panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panePaymentLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtStation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtStationName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtLane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paneBasic.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cài đặt cơ bản", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 24), new java.awt.Color(0, 51, 255))); // NOI18N
        paneBasic.setMaximumSize(new java.awt.Dimension(538, 298));
        paneBasic.setMinimumSize(new java.awt.Dimension(538, 298));
        paneBasic.setOpaque(false);
        paneBasic.setPreferredSize(new java.awt.Dimension(538, 298));
        paneBasic.setRequestFocusEnabled(false);

        txtLogOut.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtLogOut.setText("Đăng nhập với tài khoản khác");
        txtLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLogOutActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Địa chỉ máy chủ");

        txtLocalhost.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtLocalhost.setText("http://localhost:8080");
        txtLocalhost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocalhostActionPerformed(evt);
            }
        });

        btnCheckConnection.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCheckConnection.setText("Kiểm tra kết nối");
        btnCheckConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckConnectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneBasicLayout = new javax.swing.GroupLayout(paneBasic);
        paneBasic.setLayout(paneBasicLayout);
        paneBasicLayout.setHorizontalGroup(
            paneBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBasicLayout.createSequentialGroup()
                .addGroup(paneBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneBasicLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(paneBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(paneBasicLayout.createSequentialGroup()
                                .addComponent(txtLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCheckConnection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(paneBasicLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtLogOut)))
                .addGap(114, 114, 114))
        );
        paneBasicLayout.setVerticalGroup(
            paneBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneBasicLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLocalhost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout tabSettingLayout = new javax.swing.GroupLayout(tabSetting);
        tabSetting.setLayout(tabSettingLayout);
        tabSettingLayout.setHorizontalGroup(
            tabSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSettingLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(paneBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(panePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(397, 397, 397))
        );
        tabSettingLayout.setVerticalGroup(
            tabSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panePayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(408, Short.MAX_VALUE))
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

    private void btnCheckConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckConnectionActionPerformed
        String localhost = txtLocalhost.getText().trim();
        LoginRequest lp = new LoginRequest();
        boolean check = lp.checkConnection(localhost);
        if (check) {
            JOptionPane.showMessageDialog(null, "Kết nối thành công!", "Kiểm tra kết nối", JOptionPane.OK_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "Kết nối thất bại! Xin thử lại", "Kiểm tra kết nối", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_btnCheckConnectionActionPerformed

    private void txtLocalhostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocalhostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalhostActionPerformed

    private void txtLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLogOutActionPerformed
        this.setVisible(false);
        GlobalVar.login.setVisible(true);
    }//GEN-LAST:event_txtLogOutActionPerformed

    /**
     * Event ấn nút Enter của button "Đã thu"
     *
     * @return
     */
    private void btnManualPaymentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnManualPaymentKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnManualPayment.doClick();
        }
    }//GEN-LAST:event_btnManualPaymentKeyPressed

    /**
     * Event của button "Đã thu" Staff nhập biển số xe và gửi request lên server
     * yêu cầu thanh toán thủ công
     *
     * input licensePlate, idLane
     *
     * @return typeVehicle, price
     */
    private void btnManualPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManualPaymentActionPerformed

        ManualPaymentRequest rs = new ManualPaymentRequest();
        TransactionDAO dao = new TransactionDAO();
        String licensePlate = txtLicensePlate.getText().toUpperCase().trim();
        String id = lbId.getText().trim();
        localhost = txtLocalhost.getText().trim();
        int landId = Integer.parseInt(txtLane.getText().trim());
        try {
            if ("".equals(id)) {
                VehiclePayment vp = rs.insertManualPayment(licensePlate, landId, localhost);
                if ("".equals(vp.getId())) {
                    boolean check = dao.insertTransaction(licensePlate, landId, null);
                    if (check) {
                        clearForm();
                    }
                } else {
                    //rs.insertManualPayment(licensePlate, landId, localhost);
                    rs.finishManualPayment(vp.getId(), localhost);
                    clearForm();
                }
            } else {
                rs.finishManualPayment(id, localhost);
                clearForm();
            }

        } catch (Exception ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnManualPaymentActionPerformed

    private void btnClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnClearKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            btnClear.doClick();
        }
    }//GEN-LAST:event_btnClearKeyPressed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        try {
            clearForm();
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtLicensePlateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLicensePlateKeyPressed

        // Sự kiện Ấn nút F5
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            btnClear.doClick();
        }
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            
//        }

    }//GEN-LAST:event_txtLicensePlateKeyPressed

    private void txtLicensePlateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLicensePlateActionPerformed
        localhost = txtLocalhost.getText().trim();
        String licensePlate = txtLicensePlate.getText().toUpperCase().trim();
        int stationId = Integer.parseInt(txtStation.getText().trim());
        int idLane = Integer.parseInt(txtLane.getText().trim());
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        //VehicleDAO dao = new VehicleDAO();
        ManualPaymentRequest mpr = new ManualPaymentRequest();
        if (licensePlate.matches("^\\d{2}[A-Z]\\d{4,5}$")) {
            try {

                /*Bắt đầu lấy dữ liệu của xe để show lên màn hình*/
                VehicleDTO vehicleDTO = mpr.getInfoVehicle(licensePlate, stationId, localhost);
                if (vehicleDTO != null) {
                    if (vehicleDTO.getPrice() != null) {
                        lbPirce.setText(formatter.format(vehicleDTO.getPrice()) + " đồng");
                    } else {
                        lbPirce.setText("-");
                    }
                    if (vehicleDTO.getTypeVehicle() != null) {
                        lbTypeName.setText(vehicleDTO.getTypeVehicle());
                    } else {
                        lbTypeName.setText("-");
                    }

                } else {
                    lbPirce.setText("-");
                    lbTypeName.setText("-");
                }

                /*Kiểm tra giao dịch đã tồn tại trên hệ thống chưa*/
                //int vehicleId = dao.findVehicleByLicensePlate(licensePlate);
                VehiclePayment vp = mpr.getCapturedTransaction(licensePlate, stationId, localhost);
                String photo = vp.getPhoto();
                String transactionId = vp.getId();

                if (transactionId != null) {
                    lbId.setText(transactionId);
                } else {
                    lbId.setText("");
                }

                if (photo != null) {
                    //                    //loading gif
                    //                    File defaultImg = new File("./src/picture/giphy.gif");
                    //                    Image imageLoading = ImageIO.read(defaultImg);
                    //                    this.lbPicture.setIcon(new ImageIcon(imageLoading));
                    //
                    //                    Thread.sleep(1000);
                    String path = localhost + "/imgs/plates/" + photo;
                    System.out.println("Get Image from " + path);

                    //Download image
                    URL url = new URL(path);
                    Image image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    //JLabel label = new JLabel(new ImageIcon(image));
                    this.lbPicture.setIcon(new ImageIcon(image));
                } else {
                    File defaultImg = new File("./src/picture/default_text.png");
                    Image image = ImageIO.read(defaultImg);
                    this.lbPicture.setIcon(new ImageIcon(image));
                }
            } catch (Exception ex) {
                Logger.getLogger(MainForm.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_txtLicensePlateActionPerformed

    /**
     * Event lấy dữ liệu xe gồm loại xe, giá xe từ biển số xe do Staff nhập vào
     *
     * input licensePlate, idLane
     *
     * @return typeVehicle, price
     */
    private void txtLicensePlateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtLicensePlateCaretUpdate

    }//GEN-LAST:event_txtLicensePlateCaretUpdate

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                LoginDTO dto = new LoginDTO("hieu", 1L, "Chi Hieu");
//                new MainForm(dto).setVisible(true);
//
//            }
//        });
//
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoPane;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton btnCheckConnection;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnManualPayment;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbPicture;
    private javax.swing.JLabel lbPirce;
    private javax.swing.JLabel lbTypeName;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JPanel paneBasic;
    private javax.swing.JPanel panePayment;
    private javax.swing.JPanel panelCamera;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel tabHome;
    private javax.swing.JPanel tabSetting;
    private javax.swing.JLabel txtHello;
    private javax.swing.JFormattedTextField txtLane;
    private javax.swing.JTextField txtLicensePlate;
    private javax.swing.JTextField txtLocalhost;
    private javax.swing.JButton txtLogOut;
    private javax.swing.JFormattedTextField txtStation;
    private javax.swing.JTextField txtStationName;
    // End of variables declaration//GEN-END:variables

}
