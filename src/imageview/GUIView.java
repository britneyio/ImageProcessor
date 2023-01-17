package imageview;

import java.awt.image.BufferedImage;

import imagecontroller.Features;

/**
 * This class represents the GUI version of the view.
 */
public interface GUIView extends ImageView {

  /**
   * This method updates the current image that is shown
   * on the display.
   *
   * @param image the new image
   */
  void updateImage(BufferedImage image);

  /**
   * This method connects the controller and view,
   * so that the view can output the images from the model.
   *
   * @param f the features of the view
   */
  void addFeatures(Features f);

  /**
   * This method prints out an error message to the user.
   *
   * @param error an error message
   */
  void renderMessage(String error);

}
