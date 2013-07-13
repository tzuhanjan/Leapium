import java.util.*;
import java.io.*;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.lang.reflect.Field;
public class Mappings {
  private final static String FILE_NAME = "chrome.settings";
  private final HashMap<String, String[]> _map;
  private final Robot _robot;
  Mappings(Robot robot) {
    _map = new HashMap<String, String[]>();
    _robot = robot;
    parse();
    /*
    _holdKeys = new HashSet<HoldKeys>();
    _holdKeys.add(HoldKeys.CTRL);
    _holdKeys.add(HoldKeys.SHIFT);
    _holdKeys.add(HoldKeys.ALT);
    */
  }
  
  public Map<String,String[]> getMap() {
    return _map;
  }

  private Map<String,String[]> parse() {
    try {
      FileInputStream fstream = new FileInputStream(FILE_NAME);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      String name;
      String[] value;
      String keys;
      while ((line = br.readLine()) != null) {
        name = line.split(",")[0];
        assert(Actions.actions.contains(name));
        value = line.split(",")[1].split("-");
        _map.put(name,value);
      }
    } catch (Exception e) {
      System.err.println(e);
    }

    return _map;
  }


  private int getAction(String action)
  {
    try {
      Field f = KeyEvent.class.getField("VK_" + action);
      return f.getInt(null);
    } catch (Exception e) {
      System.err.println(e);
      return 0;
    }
  }

  private void doActionHelper(String[] actions)
  {
    if (actions.length == 1) {
      _robot.keyPress(getAction(actions[0]));
      _robot.delay(15);
      _robot.keyRelease(getAction(actions[0]));
    } else if (actions.length == 2) {
      _robot.keyPress(getAction(actions[0]));
      _robot.keyPress(getAction(actions[1]));
      _robot.delay(15);
      _robot.keyRelease(getAction(actions[1]));
      _robot.keyRelease(getAction(actions[0]));
    } else if (actions.length == 3) {
      _robot.keyPress(getAction(actions[0]));
      _robot.keyPress(getAction(actions[1]));
      _robot.keyPress(getAction(actions[2]));
      _robot.delay(15);
      _robot.keyRelease(getAction(actions[2]));
      _robot.keyRelease(getAction(actions[1]));
      _robot.keyRelease(getAction(actions[0]));
    }
  }

  public void doAction(String action) {
    doActionHelper(_map.get(action));
  }
/*
  public enum HoldKeys {
    CTRL, SHIFT, ALT
  }
*/




}

