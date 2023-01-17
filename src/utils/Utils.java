package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is an Utilities class for the ImageProcessing classes.
 */
public final class Utils {

  /**
   * Converts a buffered image to an ASCII ppm file.
   *
   * @param image given buffered image
   * @return a string
   */
  public static String convertBufferedToPPM(BufferedImage image) {

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

  /**
   * Converts a ASCII ppm file to a BufferedImage.
   *
   * @param imagePath the name of the ppm file
   * @return a BufferedImage
   */
  public static BufferedImage convertPPMtoBuffered(String imagePath) {
    Scanner sc;
    //System.out.println(filename);

    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      //System.out.println("File "+ filename + " not found!");
      throw new IllegalArgumentException("file: " + imagePath + " name not found!");
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

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Color color = new Color(r, g, b);
        bufferedImage.setRGB(j, i, color.getRGB());
      }
    }
    return bufferedImage;
  }

}

