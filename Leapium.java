import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Leapium {

  Robot _robot;

  public Leapium (Robot robot)
  {
    _robot = robot;
    robot.delay(5000);
  }

  public void keyPress(int event)
  {
    _robot.delay(100);
    _robot.keyPress(event);
  }

  public static void main(String[] args) {

    try {
      Leapium leap = new Leapium(new Robot());
      leap.keyPress(KeyEvent.VK_E);
      leap.keyPress(KeyEvent.VK_C);
      leap.keyPress(KeyEvent.VK_H);
      leap.keyPress(KeyEvent.VK_O);
      leap.keyPress(KeyEvent.VK_SPACE);
      leap.keyPress(KeyEvent.VK_Y);
      leap.keyPress(KeyEvent.VK_O);
      leap.keyPress(KeyEvent.VK_ENTER);

    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
} 
