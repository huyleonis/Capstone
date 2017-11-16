/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneproject;

import ats.dtos.LoginDTO;
import ats.swing.gui.Login;
import ats.swing.gui.MainForm;
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
//        LoginDTO dto = new LoginDTO("hieu", 1L, "Chi Hieu");
//        MainForm mainform = new MainForm(dto);
//        mainform.setVisible(true);
        
        Login login = new Login();
        login.setVisible(true);

    }

}
