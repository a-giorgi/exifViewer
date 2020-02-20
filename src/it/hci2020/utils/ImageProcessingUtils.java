package it.hci2020.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class ImageProcessingUtils {
    public static int[] getMaxAllowed(JPanel jpanel){
        int maxHeightAllowed = jpanel.getHeight();
        int maxWidthAllowed = jpanel.getWidth();
        if(maxWidthAllowed > Constants.maxSizeAllowed){
            maxWidthAllowed = Constants.maxSizeAllowed;
        }
        if(maxHeightAllowed > Constants.maxSizeAllowed){
            maxHeightAllowed = Constants.maxSizeAllowed;
        }
        return new int[]{ maxWidthAllowed, maxHeightAllowed };
    }

    public static Image resizeImage(BufferedImage image, int x, int y){
        if(((float)x/y)>((float)image.getWidth()/image.getHeight())){
            x = -1;
        }else{
            y = -1;
        }
        return image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
    }

    public static BufferedImage rotateImage(BufferedImage image, int deg) {
        // Rotating the image using quadrants
        int quadrants = ((int) Math.floor(deg / 90.0)) % 4;
        int width = image.getWidth();
        int height = image.getHeight();

        // If I'm rotating k90 deg with k even, I have to swap width and height
        boolean swap = quadrants % 2 != 0;
        int newWidth = swap ? height : width;
        int newHeight = swap ? width : height;

        // Using AffineTransform to apply rotation
        AffineTransform transform = new AffineTransform();
        transform.translate(newWidth / 2.0, newHeight / 2.0);
        transform.quadrantRotate(quadrants);
        transform.translate(-width / 2.0, -height / 2.0);

        BufferedImageOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR); // 1:1 mapping

        return operation.filter(image, null);
    }

}
