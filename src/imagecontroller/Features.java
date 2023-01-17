package imagecontroller;

import java.io.File;


/**
 * This interfaces represents the requests from the user.
 */
public interface Features {


  /**
   * This method tells the model to change the current image
   * according to what button the user has pressed. It includes:
   * <p>
   *   <ul>
   *     <li>Sepia and Greyscale</li>
   *     <li>Blur and Sharpen</li>
   *     <li>Red, Blue and Green Components</li>
   *     <li>Vertical and Horizontal flips</li>
   *     <li>Luma, Value, and Intensity Components</li>
   *   </ul>
   * </p>
   * @param buttonName the name of the button currently being pressed
   */
  void imageTransformation(String buttonName);

  /**
   * This method tells the model to change the brightness of the current
   * image based on the given value.
   * @param value how much to brighten or darken an image
   */
  void brightnessChange(int value);

  /**
   * This method tells the model to downscale the image based
   * on the given width and height.
   * @param width a number less than the current width of the image
   * @param height a number less than the current height
   */
  void downscale(int width, int height);

  /**
   * This method loads a file and converts it to image to be
   * used by the model and outputted by the view.
   * @param f the given file from the JFileChooser
   */
  void load(File f);

  /**
   * This method saves the current image at the given
   * path.
   * @param path the path to save the current image
   */
  void save(String path);


}
