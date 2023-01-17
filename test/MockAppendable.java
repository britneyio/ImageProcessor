import java.io.IOException;

/**
 * Testing artifact.
 */
public class MockAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("bad bad");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    return null;
  }

  @Override
  public Appendable append(char c) throws IOException {
    return null;
  }
}
