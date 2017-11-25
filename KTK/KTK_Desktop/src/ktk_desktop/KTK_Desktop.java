/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktk_desktop;

import javax.swing.UIManager;
import ktk.swing.gui.MainForm;

/**
 *
 * @author Chi Hieu
 */
public class KTK_Desktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        String theme = "com.jtattoo.plaf.texture.TextureLookAndFeel";  //theme for FORM
        UIManager.setLookAndFeel(theme);
        
        MainForm mainform = new MainForm();
        mainform.setVisible(true);
    }
    
}
