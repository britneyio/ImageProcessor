package imagemodel;

/**
 * This enumerated type represents the different components of an image,
 * along with a string description associated with each of them.
 */
public enum VisualizationType {

  R("R"), G("G"), B("B"),
  Value("Value"), Intensity("Intensity"), Luma("Luma");

  private final String descriptor;

  VisualizationType(String descriptor) {
    this.descriptor = descriptor;
  }

  /**
   * Returns the string version of the visualization type.
   *
   * @return a string
   */
  public String toString() {
    return descriptor;
  }

}
