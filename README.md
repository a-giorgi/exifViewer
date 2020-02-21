# Image + EXIF Viewer
**Overview**

HCI 2019-2020
Programming Assignment

![viewer.png](/images/viewer.png)


This sofware is made in Java for Windows devices, and to be executed it requires the Java Runtime Environment ([https://www.java.com/it/download/](https://www.java.com/it/download/)) to be installed.

It follows the Model View Controller Paradigm and its made of:

- a data structure containing the image to display and its rotation (model)
- a GUI containing two separate views, one to dispay the image and another one to show the metadata tags (view)
- a controller which coordinates the intercations between the GUI and the data structure (controller)

**Structure (semplified UML)**

![uml.png](/images/uml.png)
 
The red arrows show the relationships between model, views and controller.


**Assignment Goals:**

-  **Visualization of images:** at the application startup, through a JFileChooser, the user can select a JPG image that will be displayed by the GUI. The image is resized up to a maximum of 512 pixels per side. This limit is defined within the Constants class in the utils package.
-  **Visualization of image EXIF data:** the metadata are extracted through the use of the Java Extractor metadata library [1] and are displayed on the screen in a GUI view through the use of a JTable in a JScrollPane
-  **Rescaling:** the view containing the image supports resizing (however always setting the maximum limit at 512 pixels per side). The buttons are also resized and rearranged thanks to WrapLayout [3]
-  **Image rotation:** by pressing with the right mouse button on the image, the user can open a menu that allows the rotation by 90 ° to the right or left. In the same way, it&#39;s possible to carry out the same rotations by pressing the R (right rotation) and L (left rotation) keys or the dedicated buttons


**Extra Credits**

-  **Geolocalization:** if the geolocation tags are available within the exif parameters, a button that allows to show the location on the browser (a Google Maps link is opened) is displayed under the metadata table. This is achieved using the class Gps Directory of metadata extractor.
-  **View multiple images:** the user can change the image by pressing the "Change File" button.


**Implementation**

**1) Two separate views.** The Gui class acts as a wrapper for two separate views. Each one has a different job: the ImageJPanel class acts as image viewer that allows rotation and image selection; the MetadataJPanel class acts as exif viewer, showing up a JTable filled with all available metadata tags.

**2) Observer for notification.** to notify any status changes to the views, the Observer (pull) design pattern has been implemented. In particular, the &quot;aspect of notification&quot; variant documented in [2] (page 300) was used. This way it&#39;s possible to separately notify the two views while maintaining the link between model and the views abstract.

**3) Singleton for Model**. To ensure that the model is only loaded once, the Singleton Design Pattern [2] is used.

**4) Exception management**. If the file is not available anymore, all fired exceptions are catched to allow the user to pick another file and continue using the software.


**Bibliography**

[1] Drew Noakes, _Metadata Extractor Java Library_,
[https://github.com/drewnoakes/metadata-extractor](https://github.com/drewnoakes/metadata-extractor)/

[2] Erich Gamma, Richard Helm, Raplh Johnson, John Vlissides, _Design Patterns Element of Reusable Object-Oriented Software_, Pearson Education 2008 edizione italiana

[3] WrapLayout Java Tips Weblog, https://tips4java.wordpress.com/2008/11/06/wrap-layout/


## License
Licensed under the term of [MIT License](http://en.wikipedia.org/wiki/MIT_License). See attached file LICENSE.
