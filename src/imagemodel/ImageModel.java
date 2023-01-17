package imagemodel;

import java.awt.Color;
import java.io.IOException;

/**
 * This interface represents the operations offered by the ImageModel.
 */
public interface ImageModel {

  /**
   * Load an image from the given path.
   *
   * @param path the image file path
   * @throws IllegalArgumentException if the image doesn't exist
   */
  void load(String path) throws IllegalArgumentException;

  /**
   * Creates an image with the specified visualization.
   *
   * @return the generated image model
   */
  ImageModel visualize(VisualizationType v);

  /**
   * Rotate an image based on the given flip mode.
   *
   * @param flipType one of horizontal, vertical, horizontalvertical, or verticalhorizontal.
   * @return new instance of Image Model
   */
  ImageModel flip(FlipType flipType);

  /**
   * Brighten an image by the given constant.
   *
   * @param brightenValue the value added to each of the rgb contents of an image to brighten
   * @return brightened image
   */
  ImageModel brighten(int brightenValue);

  /**
   * Darken an image by the given constant.
   *
   * @param darkenValue the value subtracted from each of the rgb contents of an image to darken
   * @return darkened image
   */
  ImageModel darken(int darkenValue);

  /**
   * Saves the image to local computer.
   *
   * @param path filepath
   */
  void save(String path) throws IOException;

  /**
   * get the height of a pixel.
   *
   * @return the height of the pixel
   */
  int getHeight();

  /**
   * get the width of a pixel.
   *
   * @return the width of the pixel
   */
  int getWidth();

  /**
   * get the color of the pixel.
   *
   * @param row    the row of the pixel
   * @param column the row of the column
   * @return the color of the given pixel(row, col)
   */
  Color getColor(int row, int column);
}


