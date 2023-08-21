package guru_aws;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void testAppExists () {
    try {
      Class.forName("guru_aws.App");
    } catch (ClassNotFoundException e) {
      fail("Should have a class named App.");
    }
  }
}
