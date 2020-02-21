package it.hci2020.controller;

import it.hci2020.model.Model;
import it.hci2020.utils.FileUtils;
import it.hci2020.view.ImageJPanel;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class ImageController {
    private Model model;
    private JPanel imageView;
    private JPanel metadataView;

    public ImageController(Model model, JPanel imageView, JPanel metadataView){
        this.model = model;
        this.imageView = imageView;
        this.metadataView = metadataView;
        try {
            setUpListeners();
        }catch(ClassCastException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        if(!chooseFile(imageView)){
            System.exit(0);
        }
    }

    public boolean chooseFile(JPanel view){
        //preparing the component for the file selection
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(FileUtils.getJpgFileFilter());
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = fileChooser.getSelectedFile();
            if((FileUtils.getFileExtension(selected).toLowerCase().equals("jpg"))||(FileUtils.getFileExtension(selected).toLowerCase().equals("jpeg"))) {
                model.setData(fileChooser.getSelectedFile());
                view.requestFocus();
                return true;
            }else {
                JOptionPane.showMessageDialog(view, "Wrong file type");
                view.requestFocus();
                return false;
            }
        }else{
            view.requestFocus();
            return false;
        }
    }

    public void setUpListeners() throws ClassCastException{
        //Resize Listener for resize the image when the frame is resized
        ResizeListener rs = new ResizeListener(model);
        imageView.addComponentListener(rs);
        //Key Listener to detect hotkey Rotation
        KeyListener rotation = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int deg = 0;
                if(keyEvent.getKeyCode()==82){// R key for 90 degrees rotation
                    deg = 90;
                }
                if(keyEvent.getKeyCode()==76){// L key for 270 degrees rotation
                    deg = -90;
                }
                if(deg!=0){
                    model.setRotationDegrees((deg + model.getRotationDegrees())%360); //Updating the model for the Image
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        };
        imageView.addKeyListener(rotation);

        imageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //For the right click I'll open a menu for rotation
                if (SwingUtilities.isRightMouseButton(e)){
                    //creating the menu for rotation
                    JPopupMenu menu = new JPopupMenu();
                    //First option in menu
                    JMenuItem leftRotation = new JMenuItem("Rotate 90° left");
                    leftRotation.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.setRotationDegrees((-90 + model.getRotationDegrees())%360);
                        }
                    });
                    //Second option in menu
                    JMenuItem rightRotation = new JMenuItem("Rotate 90° right");
                    rightRotation.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            model.setRotationDegrees((90 + model.getRotationDegrees())%360);
                        }
                    });
                    menu.add(leftRotation);
                    menu.add(rightRotation);
                    //Showing the menu where the user clicks
                    menu.show(e.getComponent(),e.getX(),e.getY());
                }
                //For the left click I'll allow the user to select another image with the JFileChooser
                /** FUNCTION DISABLED use the provided button instead */
                /*
                if (SwingUtilities.isLeftMouseButton(e)) {
                    chooseFile(imageView);
                }
                */
            }
        });

        //setting up the button listeners
        if(imageView instanceof ImageJPanel){
            ((ImageJPanel)imageView).getChangeFile().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chooseFile(imageView);
                }
            });
            ((ImageJPanel)imageView).getRotateLeft().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.setRotationDegrees((-90 + model.getRotationDegrees())%360);
                }
            });
            ((ImageJPanel)imageView).getRotateRight().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.setRotationDegrees((90 + model.getRotationDegrees())%360);
                }
            });
        }else{
            throw new ClassCastException("imagePanel must be an ImageJPanel instance!");
        }
    }


}
