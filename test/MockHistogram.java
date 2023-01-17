import imageview.Histogram;

/**
 * This is a testing artifact for Histogram.
 */
public class MockHistogram implements Histogram {
  private StringBuilder log;

  /**
   * Constructs a mock histogram.
   *
   * @param log given a stringbuilder.
   */
  public MockHistogram(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setValues(int[][] values) {
    this.log.append(values);
  }
}
