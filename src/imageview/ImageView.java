package imageview;

import java.io.IOException;

/**
 * This interface represents the view of the Image Processing Program.
 */
public interface ImageView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if the message wasn't able to be transmitted to the controller
   */
  void renderMessage(String message) throws IOException;
}
