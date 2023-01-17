import org.junit.Test;

import java.io.File;
import java.io.IOException;

import java.io.StringReader;


import imagecontroller.ImageController;
import imagecontroller.ImageControllerImpl;
import imagemodel.BufferedImageModel;
import imagemodel.ImageProcessingModel;
import imageview.ImageView;
import imageview.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Test class.
 */
public class ImageControllerTest {

  @Test(expected = IOException.class)
  public void ViewRenderMessageFail() throws IOException {
    Appendable a = new MockAppendable();
    ImageView view = new ImageTextView(a);
    view.renderMessage("hello");
  }

  @Test(expected = IllegalArgumentException.class)
  public void NullAppendable() {
    ImageView view = new ImageTextView(null);
  }

  @Test(expected = IllegalStateException.class)
  public void ControllerViewMessageFail() {
    Appendable appendable = new MockAppendable();
    Readable read = new StringReader("load-image snail.ppm testImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
  }

  @Test
  public void NullModel() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    assertEquals(1,model.getImages().size());
  }

  @Test
  public void testLoadImage() {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("testImage");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testSaveImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "save-image savedImage.png testImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    File file = new File("savedImage.png");
    assertEquals(true, file.exists());
  }


  @Test
  public void testGreyscaleImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "red-component testImage redGreyscale");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("redGreyscale");
    assertEquals(true, resultBoolean);
  }


  @Test
  public void testApplyBrightness() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "brighten-image 3 testImage brightened-image-3");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("brightened-image-3");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testFlipImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "vertical-flip testImage verticallyFlipped-image");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("verticallyFlipped-image");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testSepiaImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "sepia testImage SepiaImage " +
            "save-image SepiaImage.png SepiaImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("SepiaImage");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testGreyScaleImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image snail.ppm testImage " +
            "greyscale testImage GreyScaleImage " +
            "save-image GreyScaleImage.png GreyScaleImage");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("GreyScaleImage");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testLoadSaveImage() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("load-image save1.png testImage " +
            "save-image save2.png testImage ");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("testImage");
    assertEquals(true, resultBoolean);
  }

  @Test
  public void testInvalidCommand() throws IOException {
    Appendable appendable = new StringBuilder();
    Readable read = new StringReader("verti");
    ImageView view = new ImageTextView(appendable);
    ImageProcessingModel model = new BufferedImageModel();
    ImageController controller = new ImageControllerImpl(read, view, model);
    controller.execute();
    String result = appendable.toString();
    boolean resultBoolean = result.contains("Invalid command, please refer to instructions above.");
    assertEquals(true, resultBoolean);
  }


}
