package GUI;

import ImageProcess.Recognition;
import Util.FileUtils;
import Util.MultipartUploadUtility;
import Util.RequestServer;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hp
 */
public class Camera extends javax.swing.JFrame {

    // Definitions
    private DaemonThread myThread = null;
    private Recognition recognition;
    private VideoCapture webSource = null;
    Mat frame = new Mat();
    private String currentPlate = "";

    // class of thread
    private class DaemonThread implements Runnable {

        private volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            showImage(frame, pnlCamera);

                            String licensePlate = recognition.process(frame);
                            txtResult.setText(licensePlate);
                            validatePlate(licensePlate, frame);

                            //List<Mat> imgs = Recognition.processImage(frame);
                            //showImage(imgs.get(0), pnlGrayImg);
                            //showImage(imgs.get(1), pnlThreshImg);
                            //showImage(imgs.get(2), pnlSuspImg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates new form Camera
     */
    public Camera() {
        initComponents();
        btnStop.setEnabled(false);
        recognition = new Recognition();

        scrollPanel.setLayout(new GridLayout(0, 1)); 
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
    }

    private void showImage(Mat img, JPanel panel) throws IOException {

        int w = panel.getWidth();
        int h = panel.getHeight();

        Mat tmp = img.clone();
        Imgproc.resize(tmp, tmp, new Size(w, h));
        
        BufferedImage buffImg = ImageIO.read(FileUtils.convertImageToInputStream(tmp));
        Graphics g = panel.getGraphics();
        g.drawImage(buffImg, 0, 0, null);
    }        

    private Mat loadImage(File f) {
        Mat img = Imgcodecs.imread(f.getAbsolutePath());
        return img;
    }

    private void validatePlate(String plate, Mat img) {
        String input = plate.toUpperCase();
        String pattern = "^\\d{2}[A-Z]\\d{4,5}$";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        if (m.matches()) {
            if (!input.equals(currentPlate)) {
                currentPlate = input;
                
                JLabel label = new JLabel();
                label.setText("- " + input);                
                
                scrollPanel.add(label);
                scrollPanel.revalidate();
                scrollPanel.repaint();
                
                try {
                    MultipartUploadUtility rs = new MultipartUploadUtility("transaction/camera1/detect/" + input, "UTF-8");                    
                    rs.addFilePart("img", input + ".jpg");
                    byte[] imgBytes = FileUtils.convertImageToByteArray(img);
                    rs.writeFileBytes(imgBytes, 0, imgBytes.length);
                    
                    Thread request = new Thread(rs);
                    request.setDaemon(true);
                    request.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCamera = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnBrowse = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtResult = new javax.swing.JLabel();
        btnStop = new javax.swing.JButton();
        scrollPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Monitoring Vehicle");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onFrameClose(evt);
            }
        });

        pnlCamera.setBackground(new java.awt.Color(255, 255, 204));
        pnlCamera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlCamera.setForeground(new java.awt.Color(204, 204, 204));
        pnlCamera.setPreferredSize(new java.awt.Dimension(460, 460));

        javax.swing.GroupLayout pnlCameraLayout = new javax.swing.GroupLayout(pnlCamera);
        pnlCamera.setLayout(pnlCameraLayout);
        pnlCameraLayout.setHorizontalGroup(
            pnlCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
        pnlCameraLayout.setVerticalGroup(
            pnlCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        btnStart.setText("Start Monitor");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Recognized Text:");

        txtResult.setText("-");

        btnStop.setText("Stop Monitor");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        scrollPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("List found plates"));

        javax.swing.GroupLayout scrollPanelLayout = new javax.swing.GroupLayout(scrollPanel);
        scrollPanel.setLayout(scrollPanelLayout);
        scrollPanelLayout.setHorizontalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        scrollPanelLayout.setVerticalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtResult))
                                .addGap(0, 85, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtResult)
                        .addGap(32, 32, 32)
                        .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        webSource = new VideoCapture(1);
        myThread = new DaemonThread();
        myThread.runnable = true;
        Thread t = new Thread(myThread);
        t.setDaemon(true);

        t.start();
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        myThread.runnable = false;

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        webSource.release();
    }//GEN-LAST:event_btnStopActionPerformed

    private void onFrameClose(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onFrameClose
        // TODO add your handling code here:
        if (myThread != null) {
            btnStopActionPerformed(null);
        }
    }//GEN-LAST:event_onFrameClose

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./src/images"));
        fileChooser.setMultiSelectionEnabled(false);
        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            Mat img = loadImage(f);
            try {
                showImage(img, pnlCamera);
                String licensePlate = recognition.process(img);
                txtResult.setText(licensePlate);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Camera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Camera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Camera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Camera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String theme = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";  //theme for FORM
                try {
                    UIManager.setLookAndFeel(theme);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Camera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlCamera;
    private javax.swing.JPanel scrollPanel;
    private javax.swing.JLabel txtResult;
    // End of variables declaration//GEN-END:variables
}
