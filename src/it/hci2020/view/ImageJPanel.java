package it.hci2020.view;

import it.hci2020.model.Model;
import it.hci2020.utils.ImageProcessingUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageJPanel extends JPanel implements Observer {
    private Model model;
    private JPanel imageWrapper;
    private JButton changeFile;
    private JButton rotateLeft;
    private JButton rotateRight;

    public void setButtons(JButton changeFile,JButton rotateLeft,JButton rotateRight){
        this.changeFile = changeFile;
        this.rotateRight = rotateRight;
        this.rotateLeft = rotateLeft;
    }

    public ImageJPanel(){
        super();
    }

    public JButton getChangeFile() {
        return changeFile;
    }

    public JButton getRotateLeft() {
        return rotateLeft;
    }

    public JButton getRotateRight() {
        return rotateRight;
    }

    public void setImageWrapper(JPanel imageWrapper) {
        this.imageWrapper = imageWrapper;
    }

    public JPanel getImageWrapper() {
        return imageWrapper;
    }


    private void setMessage(String message){
        imageWrapper.removeAll();
        imageWrapper.add(new JLabel(message, JLabel.CENTER),BorderLayout.CENTER);
        imageWrapper.revalidate();
        this.revalidate();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void changeImage(JLabel label){
        imageWrapper.removeAll();

        //adding the image to this view
        imageWrapper.add( label, BorderLayout.CENTER );
        imageWrapper.revalidate();
        this.revalidate();

    }

    @Override
    public void update() {
        try {
            BufferedImage imageRaw = ImageIO.read(model.getData());
            if(model.getRotationDegrees()!=0){
                imageRaw = ImageProcessingUtils.rotateImage(imageRaw,model.getRotationDegrees());
            }

            //resizing the image to the ImagePanel (but capped to maxSizeAllowed, see Class Constants)
            int[] maxValuesAllowed = ImageProcessingUtils.getMaxAllowed(this);
            Image scaledImage = ImageProcessingUtils.resizeImage(imageRaw,maxValuesAllowed[0],maxValuesAllowed[1]);

            //wrapping the image inside a JLabel
            ImageIcon image = new ImageIcon(scaledImage, model.getData().getAbsolutePath());
            JLabel label = new JLabel("", image, JLabel.CENTER);
            imageWrapper.setLayout(new BorderLayout());
            changeImage(label);


        } catch (IOException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            setMessage("<html><b style=\"color:red\">Image not available anymore!</b></html>");
        }

    }
}
