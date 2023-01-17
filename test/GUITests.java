import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imagecontroller.Features;
import imagecontroller.ImageGUIController;
import imagemodel.AbstractModel;
import imagemodel.BufferedImageModel;
import imageview.GUIView;
import imageview.ImageHistogram;

import static org.junit.Assert.assertEquals;


/**
 * This class tests all the non-Swing public methods.
 */
public class GUITests {

  //controller tests
  @Test
  public void testImageTransformation() {
    AbstractModel model = new BufferedImageModel();
    StringBuilder log = new StringBuilder();
    GUIView view = new MockView(log);
    Features f = new ImageGUIController(model,view);
    f.load(new File("star.jpg"));
    BufferedImage image = model.getLastImage();
    assertEquals(log.toString(), image.toString());
    f.imageTransformation("sepia");
    BufferedImage image2 = model.getLastImage();
    assertEquals(log.toString(), image.toString() + image2.toString());
  }

  @Test
  public void testSave() throws IOException {
    AbstractModel model = new BufferedImageModel();
    StringBuilder log = new StringBuilder();
    GUIView view = new MockView(log);
    Features f = new ImageGUIController(model,view);
    File file =  new File("star.jpg");
    BufferedImage fileImage = ImageIO.read(file);
    f.load(file);
    BufferedImage image = model.getImage("star.jpg");
    assertEquals(image, fileImage);
  }

  @Test
  public void testLoad() throws IOException {
    AbstractModel model = new BufferedImageModel();
    StringBuilder log = new StringBuilder();
    GUIView view = new MockView(log);
    Features f = new ImageGUIController(model,view);
    File file =  new File("star.jpg");
    BufferedImage fileImage = ImageIO.read(file);
    f.load(file);
    BufferedImage image = model.getImage("star.jpg");
    assertEquals(image, fileImage);
  }



  @Test
  public void testBrightness() {
    AbstractModel model = new BufferedImageModel();
    StringBuilder log = new StringBuilder();
    GUIView view = new MockView(log);
    Features f = new ImageGUIController(model,view);
    File file =  new File("star.jpg");
    f.load(file);
    BufferedImage image = model.getLastImage();
    assertEquals(new Color(254,255,255).getRGB(), image.getRGB(0,24));
    f.brightnessChange(-10);
    BufferedImage image2 = model.getLastImage();
    assertEquals(new Color(244,245,245).getRGB(), image2.getRGB(0,24));
  }


  //Util class tests
  @Test
  public void testConvertBItoPPM() throws IOException {

    File file =  new File("star.jpg");
    BufferedImage image = ImageIO.read(file);
    String ppm = utils.Utils.convertBufferedToPPM(image);

    assertEquals(ppm.charAt(4), new Color(image.getRGB(0,0)).getRed());
  }

  @Test
  public void testConvertPPMtoBI() throws IOException {
    BufferedImage image = utils.Utils.convertPPMtoBuffered("bluestar.ppm");
    File f = new File("bluestar.ppm");

    assertEquals(f.toString().charAt(4), new Color(image.getRGB(0,0)).getRed());
  }

  //Histogram class test
  @Test
  public void testSetValues() {
    ImageHistogram h = new ImageHistogram();
    int[][] values = new int[2][2];
    h.setValues(values);
    assertEquals(2, h.getHeight());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullValues() {
    ImageHistogram h = new ImageHistogram();
    h.setValues(null);
  }



  //model tests
  @Test
  public void testDownscale() {
    AbstractModel model = new BufferedImageModel();
    StringBuilder log = new StringBuilder();
    GUIView view = new MockView(log);
    Features f = new ImageGUIController(model,view);
    File file =  new File("star.jpg");
    f.load(file);
    BufferedImage image = model.getLastImage();
    f.downscale(10,10);
    BufferedImage imageAfter = model.getLastImage();
    assertEquals(image.getHeight(), 25);
    assertEquals(image.getWidth(),50);
    assertEquals(imageAfter.getWidth(), 10);
    assertEquals(imageAfter.getHeight(), 10);
  }

}
