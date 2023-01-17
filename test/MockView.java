import java.awt.image.BufferedImage;

import imagecontroller.Features;
import imageview.GUIView;

/**
 * A testing artifact for view.
 */
public class MockView implements GUIView {
  private StringBuilder log;

  /**
   * Constructs a mock view.
   *
   * @param a a stringbuilder.
   */
  MockView(StringBuilder a) {
    this.log = a;
  }

  @Override
  public void updateImage(BufferedImage image) {
    this.log.append(image);
  }

  @Override
  public void addFeatures(Features f) {
    this.log.append(f);
  }

  @Override
  public void renderMessage(String message) {
    this.log.append(message);
  }
}
