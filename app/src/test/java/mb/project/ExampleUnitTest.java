package mb.project;

import org.junit.Test;


import static org.junit.Assert.*;
// Local unit tests - an example.
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

  @Test
  public void assertTrue_example() throws Exception {
    assertTrue(true);
  }

  @Test
  public void smallTest() throws Exception{
    String testString = "";
    assertTrue(testString.isEmpty());
  }

}
