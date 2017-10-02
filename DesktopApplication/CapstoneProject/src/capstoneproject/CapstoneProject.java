/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneproject;

import ats.connection.RequestServer;
import ats.connection.Todo;
import ats.dtos.VehiclePayment;
import ats.swing.gui.MainForm;
import java.util.PriorityQueue;
import java.util.Queue;
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

//        Todo todo = new Todo();
//        Queue<VehiclePayment> qe = todo.ToDo(1);
//
//        while (true) {
////            for (int i = 0; i < qe.size(); i++) {
//                
//                if(!qe.isEmpty()){
//                    VehiclePayment current = qe.poll();
//                    System.out.println(current.getTypeName());
//                    Thread.sleep(100);
//                }
//                
////            }
//
//        }
        //System.out.println("null");
    }
}
