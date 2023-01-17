package imagecontroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import javax.imageio.ImageIO;

import utils.Utils;
import imagemodel.ImageProcessingModel;
import imagemodel.FlipType;
import imagemodel.VisualizationType;
import imageview.ImageView;

/**
 * This class represents the implementation of controller for Image Processing
 * program.
 */
public class ImageControllerImpl implements ImageController {

  private ImageProcessingModel model;

  private Readable read;

  private ImageView view;

  /**
   * Constructs the controller.
   *
   * @param readable sends input to the controller
   * @param view     output sent to from the controller
   * @throws IllegalArgumentException if the readable or view is null
   */
  public ImageControllerImpl(Readable readable, ImageView view, ImageProcessingModel model)
          throws IllegalArgumentException {
    if (readable == null || view == null) {
      throw new IllegalArgumentException("Field cannot be null");
    }
    this.model = model;
    this.read = readable;
    this.view = view;
  }


  @Override
  public void execute() throws IllegalStateException {
    Scanner sc = new Scanner(read);
    String path = null;
    String imageName = null;
    String destinationImageName = null;
    int increment = 0;
    try {
      displayCommands();
      displayImages();
      while (sc.hasNext()) {
        String command = sc.next();
        switch (command) {

          case "load-image": // LOAD-METHOD
            path = sc.next();
            imageName = sc.next();
            try {
              loadImage(path, imageName);
              view.renderMessage("Image successfully loaded.");
            } catch (FileNotFoundException e) {
              view.renderMessage("Could not load the image.");
            }
            break;

          case "save-image": // SAVE-METHOD
            imageName = sc.next();
            path = sc.next();
            saveImage(path, imageName);
            view.renderMessage("Image successfully saved.");
            break;

          // GREYSCALE METHOD
          case "red-component": // GREYSCALE-RED
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.R);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Red-component greyscale image successfully created");
            break;
          case "green-component": // GREYSCALE-GREEN
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.G);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Green-component greyscale image successfully created");
            break;
          case "blue-component": // GREYSCALE-BLUE
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.B);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Blue-component greyscale image successfully created");
            break;
          case "value-component": // GREYSCALE-VALUE
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.Value);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Value-component greyscale image successfully created");
            break;
          case "intensity-component": // GREYSCALE-INTENSITY
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.Intensity);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Intensity-component greyscale image successfully created");
            break;
          case "luma-component": // GREYSCALE-LUMA
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.visualize(VisualizationType.Luma);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Luma-component greyscale image successfully created");
            break;
          // END OF GREYSCALE METHOD

          case "brighten-image": // BRIGHTEN/DARKEN METHOD
            increment = sc.nextInt();
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.brighten(increment);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage(String.format("Image brightened by %s increments", increment));
            break;

          case "darken-image": // BRIGHTEN/DARKEN METHOD
            increment = sc.nextInt();
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.darken(increment);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage(String.format("Image brightened by %s increments", increment));
            break;

          // FLIP CASES
          case "vertical-flip":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.flip(FlipType.Vertical);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Image flipped vertically");
            break;
          case "horizontal-flip":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.flip(FlipType.Horizontal);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Image flipped horizontally");
            break;
          case "vertical-horizontal-flip":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.flip(FlipType.VerticalHorizontal);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Vertically flipped image was flipped horizontally");
            break;
          case "horizontal-vertical-flip":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.flip(FlipType.HorizontalVertical);
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Horizontally flipped image was flipped vertically");
            break;

          case "blur":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.blur();
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Blue image create successfully");

            break;
          case "sharpen":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.sharpen();
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Sharpen image create successfully");
            break;

          case "greyscale":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.greyscale();
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Greyscale image create successfully");
            break;
          case "sepia":
            imageName = sc.next();
            destinationImageName = sc.next();
            this.model.setPath(imageName);
            this.model.sepia();
            this.model.putImage(destinationImageName, this.model.getLastImage());
            view.renderMessage("Sepia image create successfully");
            break;


          case "quit":
            return;
          default:
            view.renderMessage("Invalid command, please refer to instructions above.");
        }
        displayImages();
      }
    } catch (IOException e) {
      throw new IllegalStateException("there was an error with transmission: " + e.getMessage());
    }
  }


  // Private helper methods for the controller to handle the switch cases.

  private void loadImage(String path, String imageName) {
    int extIndex = path.lastIndexOf('.');
    String ext = "";
    if (extIndex > 0) {
      ext = path.substring(extIndex + 1);
    }

    BufferedImage image;


    try {
      if (ext.equalsIgnoreCase("ppm")) {
        image = Utils.convertPPMtoBuffered(path);
      } else {
        File file = new File(path);
        image = ImageIO.read(file);
      }
      this.model.putImage(imageName, image);
    } catch (IOException e) {
      //do something
      throw new IllegalArgumentException("error: image wasn't loaded");


    }


  }



  /**
   * Method to save an image.
   *
   * @param path      Target image file path
   * @param imageName ImageName used in the application
   * @throws IOException Throws exception is there is any I/O exception
   */
  private void saveImage(String path, String imageName) throws IOException {
    BufferedImage image = model.getImage(imageName);
    int extIndex = path.lastIndexOf('.');
    String ext = "";
    if (extIndex > 0) {
      ext = path.substring(extIndex + 1);

    } else {
      // default
      ext = "png";
    }

    File imageFile = new File(path);
    if (ext.equals("ppm")) {
      try {
        FileWriter file = new FileWriter(path);
        file.write(convertBufferedToPPM(image));
        file.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("not able to write to the file");
      }
    } else {
      ImageIO.write(image, ext, imageFile);
    }
    this.view.renderMessage("SAVED " + path);

  }

  //converts a buffered image to a PPM
  private String convertBufferedToPPM(BufferedImage image) {

    StringBuilder b = new StringBuilder();
    b.append("P3" + System.lineSeparator());
    b.append(image.getWidth() + " " + image.getHeight() + System.lineSeparator());
    b.append(255 + System.lineSeparator());

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int rgb = image.getRGB(j, i);
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = (rgb) & 0xff;
        b.append(red + " " + green + " " + blue + " \n");
      }
    }
    return b.toString();
  }


  //displays the list of available images
  private void displayImages() throws IOException {
    view.renderMessage(System.lineSeparator() + "Available Images: " + System.lineSeparator());
    model.getImages().forEach((key, value) -> {
      try {
        view.renderMessage(key + System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("not able transmit available images");
      }
    });
  }

  //displays the list of commands
  private void displayCommands() throws IOException {
    view.renderMessage("Hello! Here are the available commands to execute this program: "
            + System.lineSeparator()
            + System.lineSeparator() + "load-image image-path image-name"
            + System.lineSeparator() + "save-image image-path image-name"
            + System.lineSeparator() + System.lineSeparator() + "The Greyscale Functions:"
            + System.lineSeparator() + "red-component image-name destination-image-name"
            + System.lineSeparator() + "green-component image-name destination-image-name"
            + System.lineSeparator() + "blue-component image-name destination-image-name"
            + System.lineSeparator() + "value-component image-name destination-image-name"
            + System.lineSeparator() + "intensity-component image-name destination-image-name"
            + System.lineSeparator() + "luma-component image-name destination-image-name"
            + System.lineSeparator() + "End of Greyscale Functions" + System.lineSeparator()
            + System.lineSeparator() + "brighten-image increment image-name destination-image-name"
            + System.lineSeparator() + System.lineSeparator() + "Flip Cases: "
            + System.lineSeparator() + "vertical-flip image-name destination-image-name"
            + System.lineSeparator() + "horizontal-flip image-name destination-image-name"
            + System.lineSeparator() + "vertical-horizontal-flip image-name destination-image-name"
            + System.lineSeparator() + "horizontal-vertical-flip image-name destination-image-name"
            + System.lineSeparator() + "sepia image-name destination-image-name"
            + System.lineSeparator() + "blur image-name destination-image-name"
            + System.lineSeparator() + "sharpen image-name destination-image-name"
            + System.lineSeparator() + "greyscale image-name destination-image-name"
            + System.lineSeparator() + "quit"
            + System.lineSeparator() + System.lineSeparator()
            + System.lineSeparator() + "Enter commands: "
    );
  }
}
