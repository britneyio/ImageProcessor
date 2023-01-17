import org.junit.Test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

import imagemodel.ImageModel;
import imagemodel.PPMImageModel;
import imagemodel.VisualizationType;
import imagemodel.FlipType;


import static org.junit.Assert.assertEquals;

/**
 * Test class.
 */
public class PPMImageModelTest {

  // test the load()
  @Test
  public void testLoad() {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    assertEquals(768, model.getWidth());
    assertEquals(1024, model.getHeight());
  }

  // test for visualize() in the case that we are visualizing the red color of an image.
  @Test
  public void testVisualizeR() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel modelR = model.visualize(VisualizationType.R);
    Color colorModelR = modelR.getColor(10, 10);
    Color colorMod = model.getColor(10, 10);
    assertEquals(colorMod.getRed(), colorModelR.getRed());
    assertEquals(colorMod.getRed(), colorModelR.getGreen());
    assertEquals(colorMod.getRed(), colorModelR.getBlue());
  }

  // test for visualize() in the case that we are visualizing the green color of an image.
  @Test
  public void testVisualizeG() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel modelG = model.visualize(VisualizationType.G);
    Color colorModelG = modelG.getColor(10, 10);
    Color colorMod = model.getColor(10, 10);
    assertEquals(colorMod.getGreen(), colorModelG.getRed());
    assertEquals(colorMod.getGreen(), colorModelG.getGreen());
    assertEquals(colorMod.getGreen(), colorModelG.getBlue());
  }

  // test for visualize() in the case that we are visualizing the Value of an image.
  @Test
  public void testVisualizeValue() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
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
  public void testVisualizeIntensity() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
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
  public void testVisualizeLuma() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
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
  public void testHorizontalFlip() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
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
  public void testVerticalFlip() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
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
  public void testHorizontalVerticalFlip() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel horizontalVerticalFlip = model.flip(FlipType.HorizontalVertical);
    Color colorExisting = model.getColor(0, model.getWidth() - 1);
    Color colorVertical = horizontalVerticalFlip.
            getColor(horizontalVerticalFlip.getHeight() - 1, 0);
    Color colorExisting_0 = model.getColor(100, model.getWidth() - 101);
    Color colorVertical_0 = horizontalVerticalFlip.
            getColor(horizontalVerticalFlip.getHeight() - 101, 100);
    assertEquals(colorVertical, colorExisting);
    assertEquals(colorVertical_0, colorExisting_0);
  }

  // test for brighten()
  @Test
  public void testBrighten() throws FileNotFoundException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel modelBrightened = model.brighten(3);
    Color colorExisting = model.getColor(20, 20);
    Color colorBrightened = modelBrightened.getColor(20, 20);
    assertEquals(colorExisting.getRed() + 3, colorBrightened.getRed());
    assertEquals(colorExisting.getGreen() + 3, colorBrightened.getGreen());
    assertEquals(colorExisting.getBlue() + 3, colorBrightened.getBlue());
  }

  // test for brighten()
  @Test
  public void testDarken() {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel modelDarkened = model.darken(3);
    Color colorExisting = model.getColor(20, 20);
    Color colorDarkened = modelDarkened.getColor(20, 20);
    assertEquals(colorExisting.getRed() - 3, colorDarkened.getRed());
    assertEquals(colorExisting.getGreen() - 3, colorDarkened.getGreen());
    assertEquals(colorExisting.getBlue() - 3, colorDarkened.getBlue());
  }

  // test for save()

  private void testSave() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    model.save("testImage.ppm");
  }

  private void testHorizontalFlipSave() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel horizontalModel = model.flip(FlipType.Horizontal);
    horizontalModel.save("Horizontal_Koala.ppm");
  }


  private void testVerticalFlipSave() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel horizontalModel = model.flip(FlipType.Vertical);
    horizontalModel.save("Vertical_Koala.ppm");
  }


  private void testVerticalHorizontalFlipSave() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel verticalHorizontalModel = model.flip(FlipType.VerticalHorizontal);
    verticalHorizontalModel.save("VerticalHorizontal_Koala.ppm");
  }


  private void testHorizontalVerticalFlipSave() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("Koala.ppm");
    ImageModel horizontalVerticalModel = model.flip(FlipType.HorizontalVertical);
    horizontalVerticalModel.save("horizontalVertical_Koala.ppm");
  }

  // new image


  // test for brighten()
  @Test
  public void testBrightenNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel modelBrightened = model.brighten(3);
    Color colorExisting = model.getColor(20, 20);
    Color colorBrightened = modelBrightened.getColor(20, 20);
    assertEquals(255, colorBrightened.getRed());
    assertEquals(255, colorBrightened.getGreen());
    assertEquals(255, colorBrightened.getBlue());
    modelBrightened.save("snail_brighten.ppm");
  }

  // test for brighten()
  @Test
  public void testDarkenNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel modelDarkened = model.darken(3);
    Color colorExisting = model.getColor(20, 20);
    Color colorDarkened = modelDarkened.getColor(20, 20);
    assertEquals(252, colorDarkened.getRed());
    assertEquals(252, colorDarkened.getGreen());
    assertEquals(252, colorDarkened.getBlue());
    modelDarkened.save("snail_darken.ppm");
  }

  // test for save()

  private void testSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    model.save("testImage.ppm");
  }


  private void testHorizontalFlipSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.flip(FlipType.Horizontal);
    horizontalModel.save("Horizontal_snail.ppm");
  }


  private void testBlueSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.B);
    horizontalModel.save("Blue_snail.ppm");
  }


  private void testRedSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.R);
    horizontalModel.save("Red_snail.ppm");
  }


  private void testGreenSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.G);
    horizontalModel.save("Green_snail.ppm");
  }


  private void testLunaSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.Luma);
    horizontalModel.save("Luma_snail.ppm");
  }


  private void testIntensitySaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.Intensity);
    horizontalModel.save("intensity_snail.ppm");
  }


  private void testValueSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.visualize(VisualizationType.Value);
    horizontalModel.save("value_snail.ppm");
  }


  private void testVerticalFlipSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalModel = model.flip(FlipType.Vertical);
    horizontalModel.save("Vertical_snail.ppm");
  }


  private void testVerticalHorizontalFlipSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel verticalHorizontalModel = model.flip(FlipType.VerticalHorizontal);
    verticalHorizontalModel.save("VerticalHorizontal_snail.ppm");
  }


  private void testHorizontalVerticalFlipSaveNewImage() throws IOException {
    ImageModel model = new PPMImageModel();
    model.load("snail.ppm");
    ImageModel horizontalVerticalModel = model.flip(FlipType.HorizontalVertical);
    horizontalVerticalModel.save("horizontalVertical_snail.ppm");
  }
}
