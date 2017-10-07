/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneproject;

import ats.connection.AutoPaymentRequest;
import ats.connection.ManualPaymentRequest;
import ats.connection.LoopRequest;
import ats.dtos.VehiclePayment;
import ats.swing.gui.MainForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

/**
 *
 * @author Chi Hieu
 */
public class CapstoneProject {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */

    public static void main(String[] args) throws Exception {
        String theme = "com.jtattoo.plaf.texture.TextureLookAndFeel";
        UIManager.setLookAndFeel(theme);
        MainForm mainform = new MainForm();
        mainform.setVisible(true);
//        LoopRequest todo = new LoopRequest();
//    Queue<VehiclePayment> qe = todo.list();
//    for(VehiclePayment vp : qe){
//        System.out.println(vp.getId());
//    }
        
    }

}
