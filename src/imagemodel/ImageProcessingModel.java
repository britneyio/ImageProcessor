package imagemodel;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * This interface represents an ImageProcessingModel with new features.
 */
public interface ImageProcessingModel extends ImageModel {
  /**
   * Method to get an image.
   *
   * @param imageName the name of the image to get
   * @return Returns the buffered image
   */
  BufferedImage getImage(String imageName);

  /**
   * Set the path of the given filename.
   *
   * @param filename the string to set the path to
   */
  void setPath(String filename);


  /**
   * Puts the given image in to the Model's hashmap.
   *
   * @param s the name of the image.
   * @param i the image to be added.
   */
  void putImage(String s, BufferedImage i);

  /**
   * Return the last image from the stack.
   *
   * @return a buffered image.
   */
  BufferedImage getLastImage();

  /**
   * Method to get the list of images.
   *
   * @return Return the list of buffered images
   */
  Map<String, BufferedImage> getImages();

  /**
   * Returns a model with a Sepia tone image.
   *
   * @return an ImageModel
   */
  ImageModel sepia();

  /**
   * Returns a model with a greyscale image.
   *
   * @return an ImageModel
   */
  ImageModel greyscale();

  /**
   * Returns a model with a blurred image.
   *
   * @return an ImageModel
   */
  ImageModel blur();

  /**
   * Returns a model with a sharpened image.
   *
   * @return an ImageModel
   */
  ImageModel sharpen();


}
