package it.hci2020.view;

import it.hci2020.model.Aspects;
import it.hci2020.model.Model;
import javax.swing.*;
import java.awt.*;


public class Gui{ //this is the wrapper for my views
    JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel imagePanel;
    private JPanel metadataPanel;

    public Gui(Model model){
        //creating the first view
        imagePanel = new ImageJPanel(model);
        imagePanel.setLayout(new BorderLayout());
        tabbedPane1.add("Image Viewer",imagePanel);
        //observing two aspects
        model.attach((ImageJPanel) imagePanel, Aspects.CHANGE_FILE);
        model.attach((ImageJPanel) imagePanel, Aspects.ROTATION);

        //creating the second view
        metadataPanel = new MetadataJPanel(model);
        tabbedPane1.add("Metadata Viewer",metadataPanel);
        //observing one aspect
        model.attach((MetadataJPanel) metadataPanel, Aspects.CHANGE_FILE);

        //showing the instructions
        String instruction = "<html>Click the left mouse button on the image to choose another file <br> Click the right mouse button on the image to open the rotation menu <br> Press the \"R\" key on the image to rotate it 90° clockwise<br> Press the \"L\" key on the image to rotate it 90° counter clockwise</html>";
        JOptionPane.showMessageDialog(null, instruction);

    }

    public JTabbedPane getTabbedPane1() {
        return tabbedPane1;
    }

    public JPanel getImagePanel() {
        return imagePanel;
    }

    public JPanel getMetadataPanel(){
        return metadataPanel;
    }


}
