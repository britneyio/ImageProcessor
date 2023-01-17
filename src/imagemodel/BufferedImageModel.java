package imagemodel;

import java.awt.image.BufferedImage;

/**
 * This class extends the AbstractModel class and implements
 * the ImageProcessingModel. This class processes
 * and edits BufferedImages.
 */
public class BufferedImageModel extends AbstractModel
        implements ImageProcessingModel {

  @Override
  protected ImageModel returnModel(BufferedImage image) {
    this.images.add(image);
    return this;
  }


}
