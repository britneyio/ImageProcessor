package imagemodel;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * This class represents any possible type of ImageProcessingModel.
 */
public abstract class AbstractModel implements ImageProcessingModel {
  protected Map<String, BufferedImage> imagesAndNames;
  protected Stack<BufferedImage> images;
  protected String path;
  protected BufferedImage image;

  /**
   * Constructs an abstract model.
   */
  public AbstractModel() {
    this.imagesAndNames = new HashMap<String, BufferedImage>();
    this.path = "";
    this.images = new Stack<>();
  }


  /**
   * Returns the current type of model that extends
   * the AbstractModel class.
   *
   * @param image the image to be added to the model
   * @return an ImageModel
   */
  protected abstract ImageModel returnModel(BufferedImage image);


  @Override
  public void load(String path) throws IllegalArgumentException {
    if (this.imagesAndNames.containsKey(path)) {
      throw new IllegalArgumentException("Illegal arguments. " +
              "image is null or there is an image with the same name");
    }
    this.setPath(path);
  }

  @Override
  public BufferedImage getImage(String imageName) {
    if (!this.imagesAndNames.containsKey(imageName)) {
      throw new IllegalArgumentException(imageName + ", image doesn't exist");
    }
    return imagesAndNames.get(imageName);
  }

  @Override
  public Map<String, BufferedImage> getImages() {
    Map<String, BufferedImage> newMap = new HashMap<>();
    for (Map.Entry<String, BufferedImage> e : imagesAndNames.entrySet()) {
      newMap.put(e.getKey(), e.getValue());
    }
    return newMap;
  }

  @Override
  public void setPath(String filename) {
    Objects.requireNonNull(filename);
    this.path = filename;
  }

  @Override
  public BufferedImage getLastImage() {
    return this.images.peek();
  }

  @Override
  public void putImage(String s, BufferedImage i) {
    this.imagesAndNames.put(s, i);
    this.images.add(i);
  }


  @Override
  public ImageModel visualize(VisualizationType v) {
    this.image = this.imagesAndNames.get(this.path);

    int height = image.getHeight();
    int width = image.getWidth();

    //BufferedImage newImage = new BufferedImage(width, height, image.getType());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int value = 0;
        Color color = new Color(image.getRGB(j, i));
        switch (v) {
          case R: // RED
            value = color.getRed();
            break;
          case G: // GREEN
            value = color.getGreen();
            break;
          case B: // BLUE
            value = color.getBlue();
            break;
          case Value: // VALUE
            int max = Math.max(color.getRed(), color.getGreen());
            value = Math.max(max, color.getBlue());
            break;
          case Intensity: // INTENSITY
            IntStream stream = IntStream.of(color.getRed(),
                    color.getGreen(),
                    color.getBlue());
            OptionalDouble averageDouble = stream.average();
            value = (int) averageDouble.getAsDouble();
            break;
          case Luma: // LUMA
            double lumaDouble = 0.2126 * (color.getRed()) +
                    0.7152 * (color.getGreen()) +
                    0.0722 * (color.getBlue());
            value = (int) lumaDouble;
            break;
          default:
            throw new IllegalArgumentException("not a visualization type");
        }
        Color newColor = new Color(value, value, value);
        image.setRGB(j, i, newColor.getRGB());
      }
    }
    return returnModel(image);
  }


  @Override
  public ImageModel flip(FlipType flipType) {
    this.image = this.imagesAndNames.get(this.path);
    BufferedImage flippedImage = null;
    switch (flipType) {
      case Horizontal: // HORIZONTALLY
        this.image = flipHorizontally(image);
        break;
      case Vertical: // HORIZONTALLY
        this.image = flipVertically(image);
        break;
      case HorizontalVertical:
        this.image = flipHorizontally(image);
        this.image = flipVertically(image);
        break;
      case VerticalHorizontal:
        this.image = flipVertically(image);
        this.image = flipHorizontally(image);
        break;
      default:
        throw new IllegalArgumentException("not a flip type");
    }

    return returnModel(this.image);
  }

  private BufferedImage flipHorizontally(BufferedImage image) {
    if (image == null) {
      throw new IllegalArgumentException("flipH: image is null");
    }
    int height = image.getHeight();
    int width = image.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        int leftSideColor = image.getRGB(j, i);
        int rightSideColor = image.getRGB(width - j - 1, i);

        image.setRGB(j, i, rightSideColor);
        image.setRGB(width - j - 1, i, leftSideColor);
      }
    }
    return image;
  }

  private BufferedImage flipVertically(BufferedImage image) {
    if (image == null) {
      throw new IllegalArgumentException("flipV: image is null");
    }
    int height = image.getHeight();
    int width = image.getWidth();
    //BufferedImage newImage = new BufferedImage(width, height, image.getType());
    for (int i = 0; i < height / 2; i++) {
      for (int j = 0; j < width; j++) {
        int topColor = image.getRGB(j, i);

        int bottomColor = image.getRGB(j, height - i - 1);

        image.setRGB(j, i, bottomColor);
        image.setRGB(j, height - i - 1, topColor);
      }
    }
    return image;
  }

  @Override
  public ImageModel brighten(int brightenValue) {
    return returnModel(brightnessHelper(brightenValue));
  }

  @Override
  public ImageModel darken(int darkenValue) {
    return returnModel(brightnessHelper(-darkenValue));
  }

  protected int handleMinMax(double value) {
    if (value < 0) {
      return 0;
    } else if (value >= 255) {
      return 255;
    }
    return (int) value;
  }

  private BufferedImage brightnessHelper(int value) {
    this.image = this.imagesAndNames.get(this.path);
    int height = image.getHeight();
    int width = image.getWidth();
    int[] rgb;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        rgb = image.getRaster().getPixel(i, j, new int[3]);
        int red = handleMinMax(rgb[0] + value);
        int green = handleMinMax(rgb[1] + value);
        int blue = handleMinMax(rgb[2] + value);

        int[] colors = {red, green, blue};

        this.image.getRaster().setPixel(i, j, colors);
      }
    }
    return this.image;
  }

  @Override
  public void save(String path) {
    //throw exception; remove functionality
    throw new IllegalArgumentException("save method not possible");
  }

  @Override
  public int getHeight() {
    return this.imagesAndNames.get(this.path).getHeight();
  }

  @Override
  public int getWidth() {
    return this.imagesAndNames.get(this.path).getHeight();
  }

  @Override
  public Color getColor(int row, int column) {
    return new Color(this.imagesAndNames.get(this.path).getRGB(column, row));
  }

  private BufferedImage filter(double[][] kernel) {
    this.image = this.imagesAndNames.get(this.path);


    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        //pixel
        //red channel
        double redD = 0;
        double greenD = 0;
        double blueD = 0;
        for (int x = 0; x < kernel.length; x++) {
          for (int y = 0; y < kernel.length; y++) {
            int modelx = (i - kernel.length / 2 + x + image.getWidth()) % image.getWidth();
            int modely = (j - kernel.length / 2 + y + image.getHeight()) % image.getHeight();
            int rgb = image.getRGB(modelx, modely);
            int red = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue = (rgb) & 0xff;
            double pixel = kernel[x][y];
            redD += red * pixel;
            blueD += blue * pixel;
            greenD += green * pixel;
          }
        }

        Color color = new Color(handleMinMax(redD)
                , handleMinMax(greenD),
                handleMinMax(blueD));
        this.image.setRGB(i, j, color.getRGB());
      }

    }
    //this.images.add(newImage);
    return this.image;
  }

  @Override
  public ImageModel blur() {
    double[][] kernel = new double[3][3];
    kernel[0][0] = 1.0 / 16.0;
    kernel[0][1] = 1.0 / 8.0;
    kernel[0][2] = 1.0 / 16.0;
    kernel[1][0] = 1.0 / 8.0;
    kernel[1][1] = 1.0 / 4.0;
    kernel[1][2] = 1.0 / 8.0;
    kernel[2][0] = 1.0 / 16.0;
    kernel[2][1] = 1.0 / 8.0;
    kernel[2][2] = 1.0 / 16.0;
    return returnModel(filter(kernel));
  }

  @Override
  public ImageModel sharpen() {
    double[][] kernel = new double[6][6];
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        if (i == 0 || i == 5) {
          kernel[i][j] = -1.0 / 8.0;

        }

        if (i != 0 && i != 5) {
          kernel[i][j] = 1.0 / 4.0;

        }


      }
    }
    kernel[2][2] = 1;
    return returnModel(filter(kernel));
  }

  @Override
  public ImageModel sepia() {
    BufferedImage image = this.imagesAndNames.get(this.path);
    BufferedImage newImage = new BufferedImage(image.getWidth(),
            image.getHeight(), image.getType());

    int height = image.getHeight();
    int width = image.getWidth();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);

        int a = (rgb >> 24) & 0xff;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;

        // calculate newRed, newGreen, newBlue
        int newRed = handleMinMax((0.393 * r + 0.769 * g
                + 0.189 * b));
        int newGreen = handleMinMax((0.349 * r + 0.686 * g
                + 0.168 * b));
        int newBlue = handleMinMax((0.272 * r + 0.534 * g
                + 0.131 * b));


        // set new RGB value
        rgb = (a << 24) | (newRed << 16) | (newGreen << 8) | newBlue;

        newImage.setRGB(i, j, rgb);
      }
    }
    //this.images.add(newImage);
    return returnModel(newImage);
  }

  @Override
  public ImageModel greyscale() {
    BufferedImage image = this.imagesAndNames.get(this.path);
    BufferedImage newImage = new BufferedImage(image.getWidth(),
            image.getHeight(), image.getType());

    int height = image.getHeight();
    int width = image.getWidth();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);

        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;

        double value = 0.2126 * r +
                0.7152 * g +
                0.0722 * b;
        int luma = (int) value;

        Color color = new Color(luma, luma, luma);

        newImage.setRGB(i, j, color.getRGB());
      }
    }
    //this.images.add(newImage);
    return returnModel(newImage);
  }


  /**
   * This method downscales an image to a smaller size.
   *
   * @param width  the new width of the image
   * @param height the new height of the image
   * @return an ImageModel
   */
  public ImageModel downscale(int width, int height) {
    this.image = this.imagesAndNames.get(this.path);
    int currentImageWidth = this.image.getWidth();
    int currentImageHeight = this.image.getHeight();
    int imageType = this.image.getType();
    if ((width > currentImageWidth) || (height > currentImageHeight)) {
      throw new IllegalArgumentException("width/height are larger than the image");
    }

    double scaleH = (double) currentImageHeight / (double) height;
    double scaleW = (double) currentImageWidth / (double) width;
    BufferedImage image = this.image;
    BufferedImage downscaledImage = new BufferedImage(width, height, imageType);
    int pixelA;
    int pixelB;
    int pixelC;
    int pixelD;
    Color cA;
    Color cB;
    Color cC;
    Color cD;
    double m;
    double n;
    Color cP;
    double mRed;
    double nRed;
    double mBlue;
    double nBlue;

    for (int i = 0; i < width; i++) {

      for (int j = 0; j < height; j++) {
        double x = scaleW * i;
        double y = scaleH * j;

        pixelA = image.getRGB((int) Math.floor(x), (int) Math.floor(y));
        pixelB = image.getRGB((int) x + 1, (int) Math.floor(y));
        pixelC = image.getRGB((int) Math.floor(x), (int) y + 1);
        pixelD = image.getRGB((int) x + 1, (int) y + 1);
        cB = new Color(pixelB);
        cA = new Color(pixelA);
        cC = new Color(pixelC);
        cD = new Color(pixelD);
        mRed = cB.getRed() * (x - Math.floor(x)) + cA.getRed() * (x + 1 - x);
        nRed = cD.getRed() * (x - Math.floor(x)) + cC.getRed() * (x + 1 - x);
        int red = (int) (nRed * (y - Math.floor(y)) + mRed * (y + 1 - y));
        mBlue = cB.getBlue() * (x - Math.floor(x)) + cA.getBlue() * (x + 1 - x);
        nBlue = cD.getBlue() * (x - Math.floor(x)) + cC.getBlue() * (x + 1 - x);
        int blue = (int) (nBlue * (y - Math.floor(y)) + mBlue * (y + 1 - y));
        m = cB.getGreen() * (x - Math.floor(x)) + cA.getGreen() * (x + 1 - x);
        n = cD.getGreen() * (x - Math.floor(x)) + cC.getGreen() * (x + 1 - x);
        int green = (int) (n * (y - Math.floor(y)) + m * (y + 1 - y));
        cP = new Color(handleMinMax(red), handleMinMax(green), handleMinMax(blue));
        downscaledImage.setRGB(i, j, cP.getRGB());
      }


    }

    return returnModel(downscaledImage);
  }

}
