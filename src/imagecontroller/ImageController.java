package imagecontroller;


/**
 * This interface represents the controller of the
 * Image Processing Program.
 */
public interface ImageController {

  /**
   * Executes the program, takes input from the user and
   * sends it to the model and view.
   *
   * @throws IllegalStateException if the controller is unable
   *                               to successfully read input or transmit output.
   */
  void execute() throws IllegalStateException;

}
