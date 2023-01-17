package imagecontroller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import javax.imageio.ImageIO;

import imagemodel.AbstractModel;
import imagemodel.FlipType;
import imagemodel.VisualizationType;
import imageview.GUIView;

/**
 * This class implements the GUI inputs and outputs.
 */
public class ImageGUIController implements Features {
  private final AbstractModel model;
  private final GUIView view;
  private BufferedImage currentImage;
  private String currentImageName;

  /**
   * This constructs a GUI controller.
   * @param model the model to implement the program
   * @param view the view to display the program
   */
  public ImageGUIController(AbstractModel model, GUIView view) {
    this.model = model;
    this.view = view;
    //adds the features of the gui like button pressing and the brightness slider
    view.addFeatures(this);
  }



  //currently will use switch statement
  //switch to hashmap/data later
  @Override
  public void imageTransformation(String buttonName) {
    switch (buttonName) {
      case "sepia":
        this.model.sepia();
        break;
      case "greyscale":
        this.model.greyscale();
        break;
      case "luma":
        this.model.visualize(VisualizationType.Luma);
        break;
      case "value":
        this.model.visualize(VisualizationType.Value);
        break;
      case "intensity":
        this.model.visualize(VisualizationType.Intensity);
        break;
      case "vertical":
        this.model.flip(FlipType.Vertical);
        break;
      case "horizontal":
        this.model.flip(FlipType.Horizontal);
        break;
      case "red":
        this.model.visualize(VisualizationType.R);
        break;
      case "blue":
        this.model.visualize(VisualizationType.B);
        break;
      case "green":
        this.model.visualize(VisualizationType.G);
        break;
      case "blur":
        this.model.blur();
        break;
      case "sharpen":
        this.model.sharpen();
        break;
      case "undo":
        break;
      default:
        this.view.renderMessage("no such button");

    }

    this.currentImage = this.model.getLastImage();
    this.view.updateImage(this.currentImage);

  }

  @Override
  public void brightnessChange(int value) {
    if (value < 0) {
      this.model.darken(-value);
    }
    if (value > 0) {
      this.model.brighten(value);
    }
    this.currentImage = this.model.getLastImage();
    this.view.updateImage(this.currentImage);
  }

  @Override
  public void downscale(int width, int height) {
    try {
      this.model.downscale(width, height);
      this.currentImage = this.model.getLastImage();
      this.view.updateImage(this.currentImage);
    } catch (Exception e) {
      this.view.renderMessage("Image couldn't be downscale: " + e.getMessage());
    }

  }


  @Override
  public void load(File f) {
    if (f == null) {
      this.view.renderMessage("File is null");
    } else {
      BufferedImage image = null;
      if (f.getName().contains("ppm")) {
        try {
          image = utils.Utils.convertPPMtoBuffered(f.getAbsolutePath());
        } catch (Exception e) {
          this.view.renderMessage(e.getMessage());
        }
      } else {
        try {
          image = ImageIO.read(f);
        } catch (Exception e) {
          this.view.renderMessage("Image couldn't be read: " + e.getMessage());
        }
      }

      this.currentImageName = f.getName();
      this.model.putImage(this.currentImageName, image);
      this.model.setPath(this.currentImageName);
      this.currentImage = image;
      if (image != null) {
        this.view.updateImage(image);
      }

    }
  }


  @Override
  public void save(String path) {
    BufferedImage image = this.currentImage;
    int extIndex = path.lastIndexOf('.');
    String ext = "";
    if (extIndex > 0) {
      ext = path.substring(extIndex + 1);

    } else {
      // default
      ext = "png";
      path = path + "." + ext;
    }

    File imageFile = new File(path);
    if (ext.equals("ppm")) {
      try {
        FileWriter file = new FileWriter(path);
        file.write(utils.Utils.convertBufferedToPPM(image));
        file.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("not able to write to the file");
      }
    } else {
      try {
        ImageIO.write(image, ext, imageFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }


}
