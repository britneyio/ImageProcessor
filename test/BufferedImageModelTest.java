import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import imagemodel.BufferedImageModel;
import imagemodel.FlipType;
import imagemodel.ImageModel;
import imagemodel.ImageProcessingModel;
import imagemodel.VisualizationType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests the BufferedImageModel.
 */
public class BufferedImageModelTest {
  private ImageProcessingModel model;

  // will use jpg for all tests
  @Before
  public void setup() throws IOException {
    this.model = new BufferedImageModel();
    BufferedImage image = ImageIO.read(new File("star.jpg"));
    this.model.putImage("star", image);
    this.model.setPath("star");
  }

  @Test
  public void testGetImage() {
    BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
    this.model.putImage("boot", image);
    assertEquals(image, this.model.getImage("boot"));
    assertEquals(20, this.model.getImage("boot").getWidth());
  }

  @Test
  public void getImages() {
    assertTrue(this.model.getImages().containsKey("star"));
  }


  @Test
  public void setPath() {
    this.model.setPath("star");
    assertEquals(this.model.getImage("star"),this.model.getLastImage());
  }

  @Test
  public void testGetHeightWidth() {
    assertEquals(25, this.model.getImage("star").getHeight());
    assertEquals(25, this.model.getHeight());
    assertEquals(50, this.model.getImage("star").getWidth());
    assertEquals(50, this.model.getWidth());
  }

  @Test
  public void testPutImage() {
    BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
    this.model.putImage("newImage", image);
    assertEquals(this.model.getImages().get("newImage"), image);

  }

  @Test
  public void testGetColor() {
    Color color = new Color(254, 255, 255);
    assertEquals(color, this.model.getColor(0, 24));

  }

  @Test
  public void testSepia() {
    BufferedImage original = this.model.getImage("star");
    this.model.sepia();
    BufferedImage image = this.model.getLastImage();
    this.model.putImage("sepiaStar", image);
    assertEquals(25, image.getHeight());
    assertEquals(50, image.getWidth());
    //get original color at pixel 0,0
    int originalColor = original.getRGB(0, 24);
    //got this color from gimp of image
    Color color = new Color(254, 255, 255);
    assertEquals(color.getRGB(), originalColor);
    //math of sepia:
    int r = 254;
    int g = 255;
    int b = 255;
    int newRed = handleMinMax((0.393 * r + 0.769 * g
            + 0.189 * b));
    int newGreen = handleMinMax((0.349 * r + 0.686 * g
            + 0.168 * b));
    int newBlue = handleMinMax((0.272 * r + 0.534 * g
            + 0.131 * b));
    Color newColor = new Color(newRed, newGreen, newBlue);
    assertEquals(newColor.getRGB(), image.getRGB(0, 24));
  }

  private int handleMinMax(double value) {
    if (value < 0) {
      return 0;
    } else if (value >= 255) {
      return 255;
    }
    return (int) value;
  }

  @Test
  public void testBlur() {
    BufferedImage original = this.model.getImage("star");
    this.model.blur();
    BufferedImage image = this.model.getLastImage();
    this.model.putImage("sepiaStar", image);
    assertEquals(25, image.getHeight());
    assertEquals(50, image.getWidth());
    //get original color at pixel 0,0
    int originalColor = original.getRGB(0, 24);
    //got this color from gimp of image
    Color color = new Color(254, 255, 255);
    assertEquals(color.getRGB(), originalColor);
    //math of blur:

    double redD = 254 * 1.0 / 16.0;
    double blueD = 255 * 1.0 / 16.0;
    double greenD = 255 * 1.0 / 16.0;


    Color newColor = new Color(handleMinMax(redD)
            , handleMinMax(greenD),
            handleMinMax(blueD));

    assertEquals(newColor.getRGB(), image.getRGB(0, 24));
  }

  @Test
  public void testSharpen() {
    BufferedImage original = this.model.getImage("star");
    this.model.sharpen();
    BufferedImage image = this.model.getLastImage();
    this.model.putImage("sepiaStar", image);
    assertEquals(25, image.getHeight());
    assertEquals(50, image.getWidth());
    //get original color at pixel 0,0
    int originalColor = original.getRGB(0, 24);
    //got this color from gimp of image
    Color color = new Color(254, 255, 255);
    assertEquals(color.getRGB(), originalColor);
    //math of sharpen:

    double redD = 254 * -1.0 / 8.0;
    double blueD = 255 * -1.0 / 8.0;
    double greenD = 255 * -1.0 / 8.0;


    Color newColor = new Color(handleMinMax(redD)
            , handleMinMax(greenD),
            handleMinMax(blueD));

    assertEquals(newColor.getRGB(), image.getRGB(0, 24));
  }

  @Test
  public void testGreyscale() {
    BufferedImage original = this.model.getImage("star");
    this.model.greyscale();
    BufferedImage image = this.model.getLastImage();
    this.model.putImage("sepiaStar", image);
    assertEquals(25, image.getHeight());
    assertEquals(50, image.getWidth());
    //get original color at pixel 0,0
    int originalColor = original.getRGB(0, 24);
    //got this color from gimp of image
    Color color = new Color(254, 255, 255);
    assertEquals(color.getRGB(), originalColor);
    //math of blur:
    int r = 254;
    int g = 255;
    int b = 255;

    double value = 0.2126 * r +
            0.7152 * g +
            0.0722 * b;
    int luma = (int) value;

    Color newColor = new Color(luma, luma, luma);
    assertEquals(newColor.getRGB(), image.getRGB(0, 24));

  }

  @Test
  public void testLoadNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    assertEquals(256, model.getWidth());
    assertEquals(256, model.getHeight());
  }

  // test for visualize() in the case that we are visualizing the red color of an image.
  @Test
  public void testVisualizeRNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel modelR = model.visualize(VisualizationType.R);
    Color colorModelR = modelR.getColor(10, 10);
    Color colorMod = model.getColor(10, 10);
    assertEquals(colorMod.getRed(), colorModelR.getRed());
    assertEquals(colorMod.getRed(), colorModelR.getGreen());
    assertEquals(colorMod.getRed(), colorModelR.getBlue());
  }

  // test for visualize() in the case that we are visualizing the green color of an image.
  @Test
  public void testVisualizeGNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel modelG = model.visualize(VisualizationType.G);
    Color colorModelG = modelG.getColor(10, 10);
    Color colorMod = model.getColor(10, 10);
    assertEquals(colorMod.getGreen(), colorModelG.getRed());
    assertEquals(colorMod.getGreen(), colorModelG.getGreen());
    assertEquals(colorMod.getGreen(), colorModelG.getBlue());
  }

  // test for visualize() in the case that we are visualizing the Value of an image.
  @Test
  public void testVisualizeValueNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel modelValue = model.visualize(VisualizationType.Value);
    Color colorModelV = modelValue.getColor(10, 10);
    Color colorExisting = model.getColor(10, 10);
    int max = Math.max(colorExisting.getRed(), colorExisting.getGreen());
    int value = Math.max(max, colorExisting.getBlue());
    assertEquals(value, colorModelV.getRed());
    assertEquals(value, colorModelV.getGreen());
    assertEquals(value, colorModelV.getBlue());
  }

  // test for visualize() in the case that we are visualizing the Intensity of an image.
  @Test
  public void testVisualizeIntensityNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel modelIntensity = model.visualize(VisualizationType.Intensity);
    Color colorModelI = modelIntensity.getColor(10, 10);
    Color colorExisting = model.getColor(10, 10);
    IntStream stream = IntStream.of(colorExisting.getRed(),
            colorExisting.getGreen(),
            colorExisting.getBlue());
    OptionalDouble averageDouble = stream.average();
    int intensity = (int) averageDouble.getAsDouble();
    assertEquals(intensity, colorModelI.getRed());
    assertEquals(intensity, colorModelI.getGreen());
    assertEquals(intensity, colorModelI.getBlue());
  }

  // test for visualize() in the case that we are visualizing the Luma of an image.
  @Test
  public void testVisualizeLumaNewImage() throws FileNotFoundException {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel modelLuma = model.visualize(VisualizationType.Luma);
    Color colorModelL = modelLuma.getColor(10, 10);
    Color colorExisting = model.getColor(10, 10);
    double lumaDouble = 0.2126 * (colorExisting.getRed()) +
            0.7152 * (colorExisting.getGreen()) +
            0.0722 * (colorExisting.getBlue());
    int luma = (int) lumaDouble;
    assertEquals(luma, colorModelL.getRed());
    assertEquals(luma, colorModelL.getGreen());
    assertEquals(luma, colorModelL.getBlue());
  }

  // test for flip() in the horizontal case
  @Test
  public void testHorizontalFlipNewImage() {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel horizontalFlip = model.flip(FlipType.Horizontal);
    Color colorExisting = model.getColor(0, 0);
    Color colorHorizontal = horizontalFlip.getColor(0, horizontalFlip.getWidth() - 1);
    Color colorExisting_0 = model.getColor(0, 1);
    Color colorHorizontal_0 = horizontalFlip.getColor(0, horizontalFlip.getWidth() - 2);
    assertEquals(colorHorizontal, colorExisting);
    assertEquals(colorHorizontal_0, colorExisting_0);
  }

  // test for flip() in the vertical case
  @Test
  public void testVerticalFlipNewImage() {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel verticalFlip = model.flip(FlipType.Vertical);
    Color colorExisting = model.getColor(0, 0);
    Color colorVertical = verticalFlip.getColor(verticalFlip.getHeight() - 1, 0);
    Color colorExisting_0 = model.getColor(1, 0);
    Color colorVertical_0 = verticalFlip.getColor(verticalFlip.getHeight() - 2, 0);
    assertEquals(colorVertical, colorExisting);
    assertEquals(colorVertical_0, colorExisting_0);
  }

  // test for flip() in the vertical case
  @Test
  public void testHorizontalVerticalFlipNewImage() {
    ImageModel model = new BufferedImageModel();
    model.load("star.jpg");
    ImageModel horizontalVerticalFlip = model.flip(FlipType.HorizontalVertical);
    Color colorExisting = model.getColor(0, model.getWidth() - 1);
    Color colorVertical = horizontalVerticalFlip
            .getColor(horizontalVerticalFlip.getHeight() - 1, 0);
    Color colorExisting_0 = model.getColor(100, model.getWidth() - 101);
    Color colorVertical_0 = horizontalVerticalFlip
            .getColor(horizontalVerticalFlip.getHeight() - 101, 100);
    assertEquals(colorVertical, colorExisting);
    assertEquals(colorVertical_0, colorExisting_0);
  }

}