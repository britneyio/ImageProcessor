ImageProcessor has been updated with a graphical user interface and has new additional features. The graphical user interface has button for the color transformation methods and flipping methods. Then there is a brightness slider ranging from -100 to 100. There are buttons for loading and saving, so that you can load any image that is a JPG, PPM, or PNG, the application doesn’t allow for any other file types. Saving can be done by typing the name of the file, if no file extension is given then the default extensions is png.  There is then an input box that only allows numbers above 0 to be inputted for downscaling. For errors there are visual popups for example when there isn’t a file there and there is attempt to use buttons. The program is fully functional. There were design changes throughout the model as there were some bugs. For example, when an image was sepiad then flipped the flip image would be the image before it had been sepiad. So I added that functionality to be able to continuously add. 

For the GUI design, I added a Features interface and a new GUIController that implements the Features interface and the ImageController interface. The Features interface has five methods, they include imageTransformation which depending on which button is clicked in the view then it calls the same method in the model. Then there is the brightness method that is connected to a JSlider in the view so whenever there is a change the given value is put into the brighten or darken methods. The downscale method uses a JSpinner in the view to grab values and then when the downscale button is called it is called in the model.  Then there are load and save methods that they are activated when the load and save buttons are pressed in the view. All of these methods are activated within the view’s addFeatures method, so that the view and controller are loosely coupled. 

In the view, there is an updateImage method, I designed the view to have a current image that is being displayed and so whenever there is a change in the image from model, the controller updates the view with the new image. There is an error pop up method that returns a JOptionpane with the error message telling the user what is wrong. I divided the view into a button Area, a histogram area, and an image area.
The cat image is from https://soundcloud.com/cat_naps.

For the histogram, I created a ImageHistogram class that extends a JPanel, that takes in the RGB values of the current image in the view and draws line based on the inputted values. 

The Downscale method was implemented by adding a downscale method to the AbstractModel class. Then in the view created a JSpinner for the width and height to be inputted and then a downscale button to activate the downscale.  

Log:
Edited the model and controller so the controller handles input and output by loading and saving images then sending it to the model, so that they loosely coupled. The model only handles the images directly.
Create a BufferedImageModel class to be able to edit buffered images. This class extends the AbstractModel. 
Created a AbstractModel for future additions and classes and extends the new ImageProcessingModel. Change the PPMModel to extend the AbstractModel.
Created ImageProcessingModel and extends ImageModel to add the new features.
Create Enums for FlipType and VisualizationType to reduce code duplication. 
Created PPMIMageModel to handle ASCII PPM models and implement all of the required methods. 
Created ImageModel interface that has all of the image component methods. 
Created ImageControllerImpl that implements the ImageController and the execute methods takes in all of the available commands that the model will handle. 
Created ImageController interface that included an execute method. 
Summary: 
So we started off by defining a separate model for each feature, in this case we had the RGB values (R, G, B), Luma, Intensity, etc.. The reason for this is that these are required features, and images can only be altered based on these requirements. Following this design decision, we then created a model with the base features (Assignment 4) and then we add the features by defining a new model which extends the above model, and we proceed to define the new functions. The advantage of this is that we have our application with the initial features, and then when wanting to add additional features to our program we can just create a new class that will handle the features that we would like to take care of. For instance, when we have to create methods for Sepia and Greyscaled, we just create an numeration type, followed by an extension of the ImageProcessingModel, and in this class we can proceed to implement our methods.



Image Processor
You will expect this view when opening the program:

"Hello! Here are the available commands to execute this program: "

"load-image image-path image-name"
"save-image image-path image-name"
"The Greyscale Functions:"
"red-component image-name destination-image-name"
"green-component image-name destination-image-name"
"blue-component image-name destination-image-name"
"value-component image-name destination-image-name"
"intensity-component image-name destination-image-name"
"luma-component image-name destination-image-name"
“End of Greyscale Functions" 
"brighten-image increment image-name destination-image-name"
"Flip Cases: "
"vertical-flip image-name destination-image-name"
"horizontal-flip image-name destination-image-name"
"vertical-horizontal-flip image-name destination-image-name"
"horizontal-vertical-flip image-name destination-image-name"
"sepia image-name destination-image-name"
"greyscale image-name destination-image-name"
"blur image-name destination-image-name"
"sharpen image-name destination-image-name"
“quit”

Above are all the possible commands for this program. 
Interfaces:

Imagecontroller - this interface is the controller and contains a method that takes the information and state of the model and transmit that information to the view and the computer (e.g. loading/saving images). 
ImageModel - The operations offered in this model, are the initial methods that we implemented, which contain the edit of images, and the methods to assist in saving and loading.
ImageProcessingModel - this interface is the model and contains all of the methods that are called in the text-based commands which is an extension of ImageModel.
Imageview - this interface prints out the commands and when images are correctly saved and loaded. 
Features - this interface represents the features of a graphical user interface. 
GUIView - this inteface represents a graphical user interface view, this interface extends the ImageView

ImageModel - Classes:
AbstractModel - This abstract class, is an abstraction of the original methods that we implemented in our previous (PPMImageModel). The reason for abstraction is that we have to allow our model to work along different formats of images, so this allows it to become universal.
BufferedImageModel - This class returns a BufferedImage of itself. 
PPMImageModel – This class extends the AbstractModel which contains the methods according to the previous assignment.



Controller - Classes:
ImageControllerImpl - this class implements the imagecontroller interface and uses a Scanner and Readable to take in user input. The controller also handles a HashMap of Images, so that there can be multiple images at a time. 
GUIController - this class implements the features class and interacts with the GUIview interface to connect the model images to be displayed on the view. 

ImageView - Classes:
ImageTextView - this class implements the imageview interface and this class takes in an Appendable and renders messages that are given to it. 
ImageMain - this class is the main program of the Image Processor
ImageGUIView - this class implements the the GUIView interface and displays the same information as the ImageTextView in a visual way. 

Enums:
Enum FlipType - this enumeration represents the different ways to flip an image
Enum VisualizationType - this enumeration represents the different ways to visualize a greyscale image


To run this program:
You can do one of the following:
1. java -jar Assignment-6.jar
2. java -jar Assignment-6.jar -text
3. java -jar Assignment-6.jar -file script.txt

Example of Text version: 
load-image Woman.ppm Woman
save woman.png Woman
red-component Woman redWoman
brighten-image Woman brightWoman
vertical-flip brightWoman verticalBrightWoman
save redWoman.png redWoman
save brightWoman.png brightWoman
save verticalBrightWoman.png verticalBrightWoman
…
quit
