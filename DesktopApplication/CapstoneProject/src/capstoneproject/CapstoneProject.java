/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneproject;

import ats.connection.RequestServer;
import ats.swing.gui.MainForm;

/**
 *
 * @author Chi Hieu
 */
public class CapstoneProject {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception{
//        MainForm mainform = new MainForm();
//        mainform.setVisible(true);
        RequestServer rs = new RequestServer();
        rs.sendGet();
    }

}
