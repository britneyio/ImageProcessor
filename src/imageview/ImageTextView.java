package imageview;

import java.io.IOException;

/**
 * This class represents an text based implementation of the
 * Image Processing view.
 */
public class ImageTextView implements ImageView {

  private Appendable appendable;

  /**
   * Constructs the view when given a valid appendable.
   *
   * @param appendable to append messages
   * @throws IllegalArgumentException if the appendable is null
   */
  public ImageTextView(Appendable appendable)
          throws IllegalArgumentException {
    if (appendable == null) {
      throw new IllegalArgumentException("appendable is null");
    }

    this.appendable = appendable;
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   */
  @Override
  public void renderMessage(String message) throws IOException {
    appendable.append(message);

  }
}
