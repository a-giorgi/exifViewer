package it.hci2020.model;

import java.io.File;

public class Model extends ObservableSubject{
    private File data;
    private static Model instance;
    private int rotationDegrees = 0;

    private Model(){ //Private constructor: I don't want this class to be extended
        data = null;
    }

    public static Model getInstance(){
        if(instance==null){
            instance = new Model();
        }
        return instance;
    }

    public File getData() {
        return data;
    }

    public void setData(File data){
        this.data = data;
        rotationDegrees = 0;
        try{
            notify(Aspects.CHANGE_FILE);
        }catch (NoSuchFieldException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void setRotationDegrees(int degrees){
        try {
            if (data.exists()) {
                rotationDegrees = degrees;
                try {
                    notify(Aspects.ROTATION);
                } catch (NoSuchFieldException exception) {
                    System.out.println(exception.getMessage());
                }
            } else {
                rotationDegrees = 0;
                setData(null);
            }
        }catch(NullPointerException exception){
            System.out.println("Model has no data!");
        }
    }

    public int getRotationDegrees() {
        return rotationDegrees;
    }

}
