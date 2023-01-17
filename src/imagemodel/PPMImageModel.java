package imagemodel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * This class is an implementation of the ImageModel class
 * it takes in ASCII PPM images and outputs ASCII PPM images.
 */
public class PPMImageModel extends AbstractModel {

  private Color[][] pixels;


  /**
   * Constructor that does not take any arguments, with nothing initialized (empty image).
   */
  public PPMImageModel() {
    //empty constructor
  }

  @Override
  protected ImageModel returnModel(BufferedImage image) {
    this.images.add(image);
    return this;
  }


  private PPMImageModel(Color[][] pixels) {
    this.pixels = pixels;
  }


  @Override
  public void load(String filename) throws IllegalArgumentException {
    Scanner sc;
    //System.out.println(filename);

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      //System.out.println("File "+ filename + " not found!");
      throw new IllegalArgumentException("file name not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3") && !token.equals("P6")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    pixels = new Color[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Color color = new Color(r, g, b);
        pixels[i][j] = color;
      }
    }
  }

  /**
   * Creates an image with the specified visualization.
   *
   * @param v the component
   * @return the generated image model
   */
  @Override
  public ImageModel visualize(VisualizationType v) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] visualization = new Color[width][height];
    Color color = null;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = 0;
        switch (v) {
          case R: // RED
            value = pixels[j][i].getRed();
            break;
          case G: // GREEN
            value = pixels[j][i].getGreen();
            break;
          case B: // BLUE
            value = pixels[j][i].getBlue();
            break;
          case Value: // VALUE
            int max = Math.max(pixels[j][i].getRed(), pixels[j][i].getGreen());
            value = Math.max(max, pixels[j][i].getBlue());
            break;
          case Intensity: // INTENSITY
            IntStream stream = IntStream.of(pixels[j][i].getRed(),
                    pixels[j][i].getGreen(),
                    pixels[j][i].getBlue());
            OptionalDouble averageDouble = stream.average();
            value = (int) averageDouble.getAsDouble();
            break;
          case Luma: // LUMA
            double lumaDouble = 0.2126 * (pixels[j][i].getRed()) +
                    0.7152 * (pixels[j][i].getGreen()) +
                    0.0722 * (pixels[j][i].getBlue());
            value = (int) lumaDouble;
            break;
          default:
            throw new IllegalArgumentException("not a visualization type");
        }
        color = new Color(value, value, value);
        visualization[j][i] = color;
      }
    }
    ImageModel image = new PPMImageModel(visualization);
    return image;
  }

  /**
   * Rotate an image based on the given flip mode.
   *
   * @param flipType one of horizontal, vertical, horizontalvertical, or verticalhorizontal.
   * @return new instance of Image Model
   */
  @Override
  public ImageModel flip(FlipType flipType) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] flippedImage = new Color[width][height];

    switch (flipType) {
      case Horizontal: // HORIZONTALLY
        flippedImage = flipHorizontally(pixels);
        break;
      case Vertical: // VERTICALLY
        flippedImage = flipVertically(pixels);
        break;
      case HorizontalVertical:
        flippedImage = flipHorizontally(pixels);
        flippedImage = flipVertically(flippedImage);
        break;
      case VerticalHorizontal:
        flippedImage = flipVertically(pixels);
        flippedImage = flipHorizontally(flippedImage);
        break;
      default:
        throw new IllegalArgumentException("not a flip type");
    }
    return new PPMImageModel(flippedImage);
  }

  /**
   * flip an image horizontally.
   *
   * @param pixels the current state of the image that is to be flipped
   * @return horizontally flipped image
   */
  // In the vertical case, we are flipping the columns from 0 to width-0, 1 to width - 1,
  // 2 to width - 2, so and so forth, till we have reached the width/2
  private Color[][] flipHorizontally(Color[][] pixels) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] flippedPixels = new Color[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        Color leftSideColor = new Color(pixels[j][i].getRed(),
                pixels[j][i].getGreen(),
                pixels[j][i].getBlue());
        Color rightSideColor = new Color(pixels[width - j - 1][i].getRed(),
                pixels[width - j - 1][i].getGreen(),
                pixels[width - j - 1][i].getBlue());
        flippedPixels[j][i] = rightSideColor;
        flippedPixels[width - j - 1][i] = leftSideColor;
      }
    }
    return flippedPixels;
  }

  /**
   * flip an image vertically.
   *
   * @param pixels the current state of the image that is to be flipped
   * @return vertically flipped image
   */
  // In the vertical case, we are flipping the rows from 0 to height - 0, 1 to height - 1,
  // 2 to width - 2, so and so forth, till we have reached the height / 2
  private Color[][] flipVertically(Color[][] pixels) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] flippedPixels = new Color[width][height];
    for (int i = 0; i < height / 2; i++) {
      for (int j = 0; j < width; j++) {
        Color leftSideColor = new Color(pixels[j][i].getRed(),
                pixels[j][i].getGreen(),
                pixels[j][i].getBlue());
        Color rightSideColor = new Color(pixels[j][height - i - 1].getRed(),
                pixels[j][height - i - 1].getGreen(),
                pixels[j][height - i - 1].getBlue());
        flippedPixels[j][i] = rightSideColor;
        flippedPixels[j][height - i - 1] = leftSideColor;
      }
    }
    return flippedPixels;
  }

  /**
   * Brighten an image by the given constant.
   *
   * @param brightenValue the value added to each of the rgb contents of an image to brighten
   * @return brightened image
   */
  // take the value of how much we want to brighten, we want to take that number and add it to
  // each pixel. Take the existing pixel values, and add the given number. We will create a new
  // pixel array, and to that pixel array we are creating a new instance of each color, and
  // assigning it to the corresponding image. While creating the color instance,
  // we have to make sure we are appending
  // it to the red value, green value, and blue value.
  @Override
  public ImageModel brighten(int brightenValue) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] brightenedPixels = new Color[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int red = pixels[i][j].getRed() + brightenValue;
        int green = pixels[i][j].getGreen() + brightenValue;
        int blue = pixels[i][j].getBlue() + brightenValue;
        if (red > 255 || red < 0) {
          red = pixels[i][j].getRed();
        }
        if (green > 255 || green < 0) {
          green = pixels[i][j].getGreen();
        }
        if (blue > 255 || blue < 0) {
          blue = pixels[i][j].getBlue();
        }
        Color color = new Color(red, green, blue);
        brightenedPixels[i][j] = color;
      }
    }
    ImageModel image = new PPMImageModel(brightenedPixels);
    return image;
  }

  /**
   * Darken an image by the given constant.
   *
   * @param darkenValue the value subtracted from each of the rgb contents of an image to darken
   * @return darkened image
   */
  @Override
  public ImageModel darken(int darkenValue) {
    int height = pixels[0].length;
    int width = pixels.length;
    Color[][] darkenedPixels = new Color[width][height];

    for (int j = 0; j < width; j++) {
      for (int i = 0; i < height; i++) {
        {
          int red = pixels[j][i].getRed() - darkenValue;
          int green = pixels[j][i].getGreen() - darkenValue;
          int blue = pixels[j][i].getBlue() - darkenValue;
          if (red < 0) {
            red = pixels[j][i].getRed();
          }
          if (green < 0) {
            green = pixels[j][i].getGreen();
          }
          if (blue < 0) {
            blue = pixels[j][i].getBlue();
          }

          Color color = new Color(red, green, blue);
          darkenedPixels[j][i] = color;

        }
      }
    }
    ImageModel image = new PPMImageModel(darkenedPixels);
    return image;
  }


  /**
   * get the height of a pixel.
   *
   * @return the height of the pixel
   */
  @Override
  public int getHeight() {
    return this.pixels[0].length; // return the length of the first column,
    // to get the height of the image.
  }

  /**
   * get the width of a pixel.
   *
   * @return the width of the pixel
   */
  @Override
  public int getWidth() {
    return this.pixels.length;   // return the length of the first row,
    // to get the width of the image.
  }

  /**
   * get the color of the pixel.
   *
   * @param row    the row of the pixel
   * @param column the row of the column
   * @return the color of the given pixel(row, col)
   */
  @Override
  public Color getColor(int row, int column) {
    Color color = pixels[column][row];
    return color;
  }

}
