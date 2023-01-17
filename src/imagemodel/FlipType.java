package imagemodel;

/**
 * FlipType represents the different type of ways that an image
 * can be flipped.
 */
public enum FlipType {

  Horizontal("Horizontal"),
  Vertical("Vertical"),
  HorizontalVertical("HorizontalVertical"),
  VerticalHorizontal("VerticalHorizontal");

  private final String descriptor;

  FlipType(String descriptor) {
    this.descriptor = descriptor;
  }

  /**
   * Returns the string of the FlipType.
   *
   * @return a string
   */
  public String toString() {
    return descriptor;
  }
}
