package it.hci2020.view;

import it.hci2020.model.Aspects;
import it.hci2020.model.Model;

import javax.jws.WebParam;
import javax.swing.*;
import java.awt.*;


public class Gui{ //this is the wrapper for my views
    JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel imagePanel;
    private JButton changeFile;
    private JButton rotateLeft;
    private JButton rotateRight;
    private JPanel buttonWrapper;
    private JPanel imageWrapper;
    private JPanel metadataPanel;

    public Gui(Model model){
        //creating the first view
        buttonWrapper.setLayout(new WrapLayout());

        if(imagePanel instanceof ImageJPanel){
            ((ImageJPanel) imagePanel).setModel(model);
            ((ImageJPanel) imagePanel).setButtons(changeFile,rotateLeft,rotateRight);
            ((ImageJPanel) imagePanel).setImageWrapper(imageWrapper);

            //observing two aspects
            model.attach((ImageJPanel) imagePanel, Aspects.CHANGE_FILE);
            model.attach((ImageJPanel) imagePanel, Aspects.ROTATION);
        }else{
            JOptionPane.showMessageDialog(null, "imagePanel MUST be an ImageJPanel instance!","Error!",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        if(metadataPanel instanceof MetadataJPanel){
            ((MetadataJPanel) metadataPanel).setModel(model);

            //observing one aspect
            model.attach((MetadataJPanel) metadataPanel, Aspects.CHANGE_FILE);
        }else{
            JOptionPane.showMessageDialog(null, "metadataPanel MUST be a MetadataJPanel instance!","Error!",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


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


    private void createUIComponents() {
        // TODO: place custom component creation code here
        imagePanel = new ImageJPanel();
        metadataPanel = new MetadataJPanel();

    }
}
