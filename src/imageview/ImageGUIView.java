package imageview;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import imagecontroller.Features;

/**
 * This class is the graphical version of the ImageProcessing program
 * view.
 */
public class ImageGUIView implements GUIView {
  private JButton sepiaButton;
  private JButton greyscaleButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton lumaButton;
  private JButton valueButton;
  private JButton intensityButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton verticalButton;
  private JButton horizontalButton;
  private JButton downscale;
  private JSpinner spinnerWidth;
  private JSpinner spinnerHeight;
  private final JButton load;
  private final JButton save;
  private BufferedImage currentImage;
  private final JLabel imageLabel;

  private JFrame frame = new JFrame();
  private int imageHeight = 0;
  private int imageWidth = 0;
  private JPanel dialogBoxesPanel;
  private JSlider brightness;

  private final JPanel buttonArea;

  private final ImageHistogram histogram;
  private final JFileChooser fchooser;
  JLabel pictureHeight = new JLabel("Height: " + this.imageHeight);
  JLabel pictureWidth = new JLabel("Width: " + this.imageWidth);

  /**
   * Initializes the view.
   *
   * @param caption the title of the JFrame.
   */
  public ImageGUIView(String caption) {
    frame = new JFrame();
    frame.setTitle(caption);
    //This sets the size of the program.
    frame.setSize(JFrame.MAXIMIZED_BOTH, JFrame.MAXIMIZED_BOTH);
    //panel area:

    //This Holds the image

    //holds the image
    JPanel imageArea = new JPanel();
    //these buttons for opening and saving images
    this.load = new JButton("Load an Image");
    this.save = new JButton("Save current image");
    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    JPanel imagePanelButtonPanel = new JPanel();

    imagePanelButtonPanel.add(pictureWidth);
    imagePanelButtonPanel.add(pictureHeight);

    imagePanelButtonPanel.add(this.load);
    imagePanelButtonPanel.add(this.save);
    imageLabel = new JLabel();
    ImageIcon icon = new ImageIcon();
    JScrollPane imageScrollPane = new JScrollPane();
    imageLabel.setIcon(icon);
    imageScrollPane.setViewportView(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(500, 500));

    imagePanel.add(imageScrollPane);
    imageArea.add(imagePanel, BorderLayout.CENTER);


    //This is the place to choose files
    fchooser = new JFileChooser(".");
    fchooser.setFileFilter(new FileNameExtensionFilter(
            "JPG, PNG, PPM Images", "jpg", "png", "ppm"));


    //holds the histogram
    JPanel histogramArea = new JPanel();
    imageArea.setPreferredSize(new Dimension(600, 600));
    //imageArea.add(histogramArea, BorderLayout.LINE_END)
    histogramArea.setBackground(Color.darkGray);
    histogramArea.setLayout(new GridLayout(0, 1));
    histogram = new ImageHistogram();
    histogram.setPreferredSize(new Dimension(255, 500));
    histogram.setBackground(Color.white);
    histogramArea.add(histogram);
    buttonArea = new JPanel(new GridLayout(3, 6));

    frame.add(imagePanelButtonPanel, BorderLayout.PAGE_START);
    frame.add(imageArea, BorderLayout.CENTER);
    frame.add(histogramArea, BorderLayout.LINE_END);
    frame.add(buttonArea, BorderLayout.PAGE_END);
    setButtonArea();


    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.pack();
    frame.setVisible(true);

  }


  //this method sets all the buttons
  private void setButtonArea() {
    //button area
    //color transformation
    this.sepiaButton = new JButton("Sepia");
    this.sepiaButton.setActionCommand("sepia");
    this.greyscaleButton = new JButton("Greyscale");
    this.greyscaleButton.setActionCommand("greyscale");
    this.redButton = new JButton("Red Component");
    this.redButton.setActionCommand("red");
    this.greenButton = new JButton("Green Component");
    this.greenButton.setActionCommand("green");
    this.blueButton = new JButton("Blue Component");
    this.blueButton.setActionCommand("blue");
    this.lumaButton = new JButton("Luma");
    this.lumaButton.setActionCommand("luma");
    this.valueButton = new JButton("Value");
    this.valueButton.setActionCommand("value");
    this.intensityButton = new JButton("Intensity");
    this.intensityButton.setActionCommand("intensity");
    //image filtering
    this.blurButton = new JButton("Blur");
    this.blurButton.setActionCommand("blur");
    this.sharpenButton = new JButton("Sharpen");
    this.sharpenButton.setActionCommand("sharpen");
    //image flipping
    this.verticalButton = new JButton("Vertical flip");
    this.verticalButton.setActionCommand("vertical");
    this.horizontalButton = new JButton("Horizontal flip");
    this.horizontalButton.setActionCommand("horizontal");
    this.downscale = new JButton("Downscale image");
    this.downscale.setActionCommand("downscale");
    buttonArea.add(sepiaButton);
    buttonArea.add(greyscaleButton);
    buttonArea.add(redButton);
    buttonArea.add(greenButton);
    buttonArea.add(blueButton);
    buttonArea.add(lumaButton);
    buttonArea.add(valueButton);
    buttonArea.add(intensityButton);
    buttonArea.add(blurButton);
    buttonArea.add(sharpenButton);
    buttonArea.add(verticalButton);
    buttonArea.add(horizontalButton);


    //brightness
    final int min = -100;
    final int max = 100;
    final int init = 0;
    brightness = new JSlider(JSlider.HORIZONTAL, min, max, init);
    brightness.setMajorTickSpacing(10);
    //brightness.setMinorTickSpacing(1);
    brightness.setPaintTicks(true);
    //create brightness label table
    Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
    labelTable.put(min, new JLabel("-100"));
    labelTable.put(init, new JLabel("0"));
    labelTable.put(max, new JLabel("100"));
    brightness.setLabelTable(labelTable);
    brightness.setPaintLabels(true);
    brightness.setName("Brightness");
    buttonArea.add(brightness);


    //this is spinner for choosing to downscale an image
    JLabel width = new JLabel("Width: ");
    JLabel height = new JLabel("Height: ");
    this.spinnerWidth = new JSpinner(new SpinnerNumberModel(1, 1, 2000, 1));
    this.spinnerHeight = new JSpinner(new SpinnerNumberModel(1, 1, 2000, 1));
    width.setLabelFor(spinnerWidth);
    height.setLabelFor(this.spinnerHeight);
    JFormattedTextField heightField = ((JSpinner.NumberEditor)
            this.spinnerHeight.getEditor()).getTextField();
    ((NumberFormatter) heightField.getFormatter()).setAllowsInvalid(false);
    JFormattedTextField widthField = ((JSpinner.NumberEditor)
            this.spinnerWidth.getEditor()).getTextField();
    ((NumberFormatter) widthField.getFormatter()).setAllowsInvalid(false);
    this.buttonArea.add(width);
    this.buttonArea.add(this.spinnerWidth);
    this.buttonArea.add(height);
    this.buttonArea.add(this.spinnerHeight);
    buttonArea.add(downscale);

  }


  //this method sends the path of the image to the controller
  private String saveImage() {
    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JLabel fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
    String message = JOptionPane.showInputDialog(frame, "Enter image name: ");
    return message;

  }

  @Override
  public void updateImage(BufferedImage image) {
    this.currentImage = image;
    imageLabel.setIcon(new ImageIcon(currentImage));
    this.setHistogramValues();
    this.pictureHeight.setText("Height: " + this.currentImage.getHeight());
    this.pictureWidth.setText("Width: " + this.currentImage.getWidth());
  }


  //this sets the histogram values
  private void setHistogramValues() {
    int[][] list = new int[this.currentImage.getHeight()][this.currentImage.getWidth()];
    for (int i = 0; i < this.currentImage.getHeight(); i++) {
      for (int j = 0; j < this.currentImage.getWidth(); j++) {
        int c = this.currentImage.getRGB(j, i);
        list[i][j] = c;
      }
    }
    histogram.setValues(list);
  }

  //loads an image
  private File loadImage() {
    int retvalue = fchooser.showOpenDialog(frame);
    File file = null;
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      file = fchooser.getSelectedFile();
      //f.load(file);
      //fileOpenDisplay.setText(file.getAbsolutePath());

    }
    return file;
  }


  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(frame, message,
            "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);

  }

  @Override
  public void addFeatures(Features f) {
    //this loads the first image
    //f.load(loadImage());
    //this is the list of buttons
    JButton[] listOfButton = new JButton[]
    {sepiaButton, greyscaleButton, redButton, greenButton, blurButton, blueButton, lumaButton,
      valueButton, intensityButton, sharpenButton, verticalButton, horizontalButton};
    //this for loop sets each button when pressed it will do its corresponding
    //method in the model based on the actionCommand
    for (JButton button : listOfButton) {
      button.addActionListener(e -> {
        f.imageTransformation(button.getActionCommand());
      });
    }

    //this method loads a new file when the load button is pressed
    this.load.addActionListener(e -> {
      File file = loadImage();
      f.load(file);

      //fileOpenDisplay.setText(file.getAbsolutePath());
    });
    //this method saves the current image when the save button is pressed
    this.save.addActionListener(evt -> f.save(this.saveImage()));
    //this method changes the brightness based on the slider
    this.brightness.addChangeListener(e -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        int fps = (int) source.getValue();
        if (fps != 0) {
          f.brightnessChange(fps);
        }
      }
    });

    //this takes in values from the width spinner
    this.spinnerWidth.addChangeListener(e -> {
      this.imageWidth = (Integer) this.spinnerWidth.getValue();
    });

    //this takes in values from the height spinner
    this.spinnerHeight.addChangeListener(e -> {
      this.imageHeight = (Integer) this.spinnerHeight.getValue();
    });

    //this method calls the downscale method in the model
    this.downscale.addActionListener(e -> f.downscale(this.imageWidth, this.imageHeight));

  }


}
