package it.hci2020.view;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;
import it.hci2020.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class MetadataJPanel extends JPanel implements Observer {
    private Model model;
    private String mapsUrl = null;
    private JTable metadataTable;

    public MetadataJPanel(Model model){
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
            //first of all I'm removing all old components
            this.removeAll();
            //reading the metadata
            Metadata metadata = JpegMetadataReader.readMetadata(model.getData());
            HashMap<String, String> metadataMap = new HashMap<String, String>();
            for (Directory directory : metadata.getDirectories()) {
                metadataMap.put("<html><b style='color:red'>" + directory.getName() + "</html></b>", "");
                for (Tag tag : directory.getTags()) {
                    metadataMap.put(tag.getTagName(), tag.getDescription());
                }
            }
            //populate multidimensional array with HashMap
            String[][] data = new String[metadataMap.size()][2];
            int i = 0;
            for (Map.Entry<String, String> entry : metadataMap.entrySet()) {
                data[i][0] = entry.getKey();
                data[i][1] = entry.getValue();
                i++;
            }
            //setting up the metadata table
            metadataTable = new JTable(data, new String[]{"Tag", "Description"});
            metadataTable.setDefaultEditor(Object.class, null); //this will prevent data to be edited
            //preparing the container for the tables
            JScrollPane tableContainer = new JScrollPane();
            this.setLayout(new BorderLayout());
            this.add(tableContainer,BorderLayout.CENTER);
            tableContainer.setViewportView(metadataTable);
            //displaying metadata Table
            metadataTable.setFillsViewportHeight(true);

            // Now checking for Geolocation tags
            Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
            for (GpsDirectory gpsDirectory : gpsDirectories) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    mapsUrl = "https://www.google.com/maps/search/?api=1&query="+geoLocation.getLatitude()+','+geoLocation.getLongitude();
                    JButton gpsButton = new JButton("<html><b style='color:red'>Geolocalization</b></html>");
                    gpsButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                java.awt.Desktop.getDesktop().browse(java.net.URI.create(mapsUrl));
                            }catch(IOException exception){
                                System.out.println(exception.getMessage());
                            }
                        }
                    });
                    this.add(gpsButton,BorderLayout.SOUTH);
                }else{
                    mapsUrl = null;
                }
            }

        }catch(IOException | ImageProcessingException exception){
            setMessage("Error: unable to read metadata!");
        }catch(NullPointerException exception){
            setMessage("Pick another image!");
        }
    }
}
