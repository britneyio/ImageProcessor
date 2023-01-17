package imageview;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class represents an ImageHistogram.
 */
public class ImageHistogram extends JPanel implements Histogram {

  private Map<Integer, Integer> redComp;
  private Map<Integer, Integer> blueComp;
  private Map<Integer, Integer> greenComp;
  private Map<Integer, Integer> averageComp;
  private int[][] values;

  /**
   * This method sets the values of the histogram from the view's current image.
   *
   * @param values 2D array of the values
   */
  @Override
  public void setValues(int[][] values) {
    if (values == null) {
      throw new IllegalArgumentException("values is empty");
    }
    this.values = values;
    int[][] red = new int[values[0].length][values.length];
    int[][] green = new int[values[0].length][values.length];
    int[][] blue = new int[values[0].length][values.length];

    int[][] average = new int[values[0].length][values.length];

    redComp = new HashMap<>();
    blueComp = new HashMap<>();
    greenComp = new HashMap<>();
    averageComp = new HashMap<>();
    for (int i = 0; i < values[0].length; i++) {
      for (int j = 0; j < values.length; j++) {
        red[i][j] = ((values[j][i] >> 16) & 0xff);
        green[i][j] = ((values[j][i] >> 8) & 0xff);
        blue[i][j] = ((values[j][i]) & 0xff);
        int avg = ((values[j][i] >> 16) & 0xff)
                + ((values[j][i] >> 8) & 0xff) + ((values[j][i]) & 0xff);
        average[i][j] = (avg / 3);
      }

    }
    for (int i = 0; i < 256; i++) {
      int finalI = i;
      redComp.put(i, (int) Arrays.stream(red)
              .flatMapToInt(Arrays::stream)
              .filter(x -> x == finalI).count());
      blueComp.put(i, (int) Arrays.stream(blue)
              .flatMapToInt(Arrays::stream)
              .filter(x -> x == finalI)
              .count());
      greenComp.put(i, (int) Arrays.stream(green)
              .flatMapToInt(Arrays::stream)
              .filter(x -> x == finalI)
              .count());
      averageComp.put(i, (int) Arrays.stream(average)
              .flatMapToInt(Arrays::stream)
              .filter(x -> x == finalI)
              .count());


    }
    repaint();
  }

  /**
   * This method paints the histogram onto the screen.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    if (this.values == null) {
      return;
    }
    for (int i = 0; i < 256; i++) {
      g.setColor(Color.red);
      g.drawLine(i, getHeight(), i, getHeight() - (redComp.get(i)) / 10);
      g.setColor(Color.blue);
      g.drawLine(i, getHeight(), i, getHeight() - (blueComp.get(i)) / 10);
      g.setColor(Color.green);
      g.drawLine(i, getHeight(), i, getHeight() - (greenComp.get(i)) / 10);
      g.setColor(Color.gray);
      g.drawLine(i, getHeight(), i, getHeight() - (averageComp.get(i)) / 10);

    }
  }


}
