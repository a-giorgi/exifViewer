package it.hci2020;

import it.hci2020.controller.ImageController;
import it.hci2020.model.Model;
import it.hci2020.utils.FileUtils;
import it.hci2020.view.Gui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class Main {
    public static void main(String[] args){

        Model model = Model.getInstance();

        Gui gui = new Gui(model);
        JFrame frame = new JFrame("ImageExifViewer");
        frame.setContentPane(gui.getTabbedPane1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.setVisible(true);

        ImageController controller = new ImageController(model, gui.getImagePanel(), gui.getMetadataPanel());
    }

}