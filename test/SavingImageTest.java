import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import imagecontroller.ImageController;
import imagecontroller.ImageControllerImpl;
import imagemodel.BufferedImageModel;
import imagemodel.ImageProcessingModel;
import imageview.ImageTextView;
import imageview.ImageView;

import static org.junit.Assert.assertTrue;

/**
 * Saves the images.
 */
public class SavingImageTest {

  @Test
  public void convertPPMTOJPG() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.ppm star " +
            "save-image star newStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("newStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void convertJPGTOPPM() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "save-image star newStar.ppm quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("newStar.ppm");
    assertTrue(resultBoolean);
  }

  @Test
  public void convertJPGTOPNG() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "save-image star newStar.png quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("newStar.png");
    assertTrue(resultBoolean);
  }

  @Test
  public void convertJPGTOBMP() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "save-image star newStar.bmp quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("newStar.bmp");
    assertTrue(resultBoolean);
  }

  @Test
  public void testGreyScaleImage() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "greyscale star greyStar " + "save-image greyStar greyStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("greyStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testSepiaImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "sepia star sepiaStar " + "save-image sepiaStar sepiaStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("sepiaStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testBlurImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "blur star blurStar " + "save-image blurStar blurStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("blurStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testSharpenImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "sharpen star sharpStar " + "save-image sharpStar sharpStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("sharpStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testBrightenImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "brighten-image 30 star brightStar " + "save-image brightStar brightStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("brightStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testDarkenImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "darken-image 30 star darkStar " + "save-image darkStar darkStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("darkStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testHorizontalFlipImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "horizontal-flip star hStar " + "save-image hStar hStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("hStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testVerticalFlipImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "vertical-flip star vStar " + "save-image vStar vStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("vStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testHorizontalVerticalImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "horizontal-vertical-flip star hvStar " + "save-image hvStar hvStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("hvStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testVerticalHorizontalImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "vertical-horizontal-flip star vhStar "
            + "save-image vhStar vhStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("vhStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testLumaImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "luma-component star lumaStar "
            + "save-image lumaStar lumaStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("lumaStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testIntensityImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "intensity-component star intenseStar "
            + "save-image intenseStar intenseStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("intenseStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testValueImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "value-component star vhStar "
            + "save-image vhStar vhStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("vhStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testRedImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "red-component star redStar "
            + "save-image redStar redStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("redStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testBlueImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "blue-component star blueStar "
            + "save-image blueStar blueStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("blueStar.jpg");
    assertTrue(resultBoolean);
  }

  @Test
  public void testGreenImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image star.jpg star " +
            "green-component star greenStar "
            + "save-image greenStar greenStar.jpg quit");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("greenStar.jpg");
    assertTrue(resultBoolean);
  }


}
