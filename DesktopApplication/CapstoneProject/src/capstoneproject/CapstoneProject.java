/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneproject;

import ats.swing.gui.Login;
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
        String theme = "com.jtattoo.plaf.texture.TextureLookAndFeel";  //theme for FORM
        UIManager.setLookAndFeel(theme);

        Login login = new Login();
        login.setVisible(true);

    }

}
