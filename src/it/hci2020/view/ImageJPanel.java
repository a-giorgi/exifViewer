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

    public ImageJPanel(Model model){
        super();
        this.model = model;
    }

    private void setMessage(String message){
        this.removeAll();
        this.add(new JLabel(message, JLabel.CENTER),BorderLayout.CENTER);
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

            //adding the image to this view
            this.removeAll();
            this.setLayout(new BorderLayout());
            this.add( label, BorderLayout.CENTER );
            this.revalidate();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            setMessage("Image not available anymore!");
        }

    }
}
