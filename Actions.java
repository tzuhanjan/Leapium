import java.util.HashMap;
import java.util.HashSet;

public class Actions {
  public static String SWIPE_DOWN = "SWIPE_DOWN"; 
  public static String SWIPE_UP = "SWIPE_UP";
  public static String SWIPE_LEFT = "SWIPE_LEFT";
  public static String SWIPE_RIGHT = "SWIPE_RIGHT";
  public static String SCREEN_TAP = "SCREEN_TAP";
  public static String CLOCKWISE = "CLOCKWISE";
  public static String COUNTER_CLOCKWISE = "COUNTER_CLOCKWISE";
  public static String KEY_TAP_ONE = "KEY_TAP_ONE";
  public static String KEY_TAP_TWO = "KEY_TAP_TWO";
  public static String KEY_TAP_THREE = "KEY_TAP_THREE";
  public static String KEY_TAP_FOUR = "KEY_TAP_FOUR";
  public static HashSet<String> actions;

  public Actions()
  {
    actions = new HashSet<String>();
    actions.add(SWIPE_DOWN);
    actions.add(SWIPE_UP);
    actions.add(SWIPE_LEFT);
    actions.add(SWIPE_RIGHT);
    actions.add(SCREEN_TAP);
    actions.add(CLOCKWISE);
    actions.add(COUNTER_CLOCKWISE);
    actions.add(KEY_TAP_ONE);
    actions.add(KEY_TAP_TWO);
    actions.add(KEY_TAP_THREE);
    actions.add(KEY_TAP_FOUR);
  }
}
