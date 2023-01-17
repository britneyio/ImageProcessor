import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import imagecontroller.ImageController;
import imagecontroller.ImageControllerImpl;
import imagecontroller.ImageGUIController;
import imagemodel.AbstractModel;
import imagemodel.BufferedImageModel;
import imagemodel.ImageProcessingModel;
import imageview.GUIView;
import imageview.ImageGUIView;
import imageview.ImageView;
import imageview.ImageTextView;

/**
 * This class represents the image processor program.
 */
public final class ImageMain {

  /**
   * Plays the program.
   *
   * @param args the inputted arguments.
   */
  public static void main(String[] args) throws IOException {


    int index = 0;
    File file = null;
    String commandLineFileContent = "";
    Readable read = new InputStreamReader(System.in);
    Appendable appendable = System.out;
    if (args.length > 0) {


      if (args[0].equalsIgnoreCase("-text")) {


        ImageView view = new ImageTextView(appendable);
        ImageProcessingModel model = new BufferedImageModel();
        ImageController controller = new ImageControllerImpl(read, view, model);
        controller.execute();
      } else if (args[0].equalsIgnoreCase("-file")) {
        File f = new File(args[1]);

        Scanner sc = new Scanner(f);
        sc.useDelimiter("\\Z");
        //commandLineFileContent = new String(Files.readAllBytes(Paths.get(args[1])));
        read = new StringReader(sc.next());
        ImageView view = new ImageTextView(appendable);
        ImageProcessingModel model = new BufferedImageModel();
        ImageController controller = new ImageControllerImpl(read, view, model);
        controller.execute();

      }
    }
    else {
      GUIView view = new ImageGUIView("hello");
      AbstractModel model = new BufferedImageModel();
      ImageGUIController controller = new ImageGUIController(model,view);
    }







  }
}
