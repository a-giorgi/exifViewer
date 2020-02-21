package it.hci2020.controller;

import it.hci2020.model.Model;
import it.hci2020.utils.Constants;
import it.hci2020.utils.ImageProcessingUtils;
import it.hci2020.view.ImageJPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResizeListener extends ComponentAdapter { //It's a part of the controller

    private Timer resizeTimer; //I need this to resize image only once I've finish resizing the frame
    private ComponentEvent resizeEvent;
    private int resizeDelay = 50;
    private Model model;

    public void updateImagePanel(JPanel imagePanel) throws IOException, IllegalArgumentException, ClassCastException {
        BufferedImage imageraw = ImageIO.read(model.getData());
        if(imagePanel instanceof ImageJPanel) {
            int[] maxSizeAllowed = ImageProcessingUtils.getMaxAllowed(((ImageJPanel) imagePanel).getImageWrapper());
            if (model.getRotationDegrees() != 0) {
                imageraw = ImageProcessingUtils.rotateImage(imageraw, model.getRotationDegrees());
            }

            //wrapping the image into a JLabel
            ImageIcon scaledImage = new ImageIcon(ImageProcessingUtils.resizeImage(imageraw, maxSizeAllowed[0], maxSizeAllowed[1]), model.getData().getAbsolutePath());
            JLabel label = new JLabel("", scaledImage, JLabel.CENTER);

            //changing the image
            ((ImageJPanel) imagePanel).changeImage(label);
        }else{
            throw new ClassCastException("imagePanel must be an ImageJPanel instance");
        }
    }

    public ResizeListener(Model model){
        super();
        this.model = model;
        ActionListener resizeImage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JPanel imagePanel = (JPanel) resizeEvent.getComponent();
                if(imagePanel.isShowing()) {
                    try {
                        updateImagePanel(imagePanel);
                    }catch (IOException exception){
                        JOptionPane.showMessageDialog(imagePanel, "IOException: image not available anymore!","Error!",JOptionPane.ERROR_MESSAGE);
                        model.setData(null);
                    }catch (IllegalArgumentException exception){
                        JOptionPane.showMessageDialog(imagePanel, "Click on \"Change Image\" to select another file");
                    }catch (ClassCastException exception){
                        JOptionPane.showMessageDialog(imagePanel, exception.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }
            }
        };
        resizeTimer = new ResizeTimer(resizeDelay,resizeImage); //only when the timer ends I'll resize the image
        resizeTimer.setRepeats(false);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        resizeEvent = e;
        if(resizeTimer.isRunning()){
            resizeTimer.restart();
        }else{
            resizeTimer.start();
        }

    }

    @Override
    public void componentShown(ComponentEvent e) {
        super.componentShown(e);
        JPanel imagePanel = (JPanel) e.getComponent();
        try {
            updateImagePanel(imagePanel);
        }catch (IOException exception){
            JOptionPane.showMessageDialog(imagePanel, "IOException: image not available anymore!","Error!",JOptionPane.ERROR_MESSAGE);
            model.setData(null);
        }catch (IllegalArgumentException exception){
            JOptionPane.showMessageDialog(imagePanel, "Click on \"Change Image\" to select another file");
        }catch (ClassCastException exception){
            JOptionPane.showMessageDialog(imagePanel, exception.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
