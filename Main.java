
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Controller.PolicyFlag;
import com.leapmotion.leap.Gesture.State;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

class GestureInfo {
	Pointable p;
	Vector direction;
	Vector position;
	
	public GestureInfo(Pointable p, Vector direction, Vector position) {
		super();
		this.p = p;
		this.direction = direction;
		this.position = position;
	}
	
}


public class Main extends Listener {
    Robot _robot;
    
    Map<Integer, GestureInfo> boundFingers = new HashMap();

    public Main() {
        try {
            _robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

  public void keyPress(int event)
  {
    _robot.delay(25);
    _robot.keyPress(event);
  }

    public void onInit(Controller controller) {
    }

    public void onConnect(Controller controller) {
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        
        Config config = controller.config();
        
        config.setFloat("Gesture.Swipe.MinLength", 50);
        config.setFloat("Gesture.Swipe.MinVelocity", 200);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    public void goDown(Pointable p, Vector position) {
    	if(!boundFingers.containsKey(p.id())) {
    		GestureInfo gi = new GestureInfo(p, Vector.down(), position);
    		boundFingers.put(p.id(), gi);
    	}
    	if(boundFingers.get(p.id()).direction.equals(Vector.down())) {
    		boundFingers.get(p.id()).position = position;
    		System.out.println("going down " + p.id());
	    	keyPress(KeyEvent.VK_DOWN);
    	}
    }
    
    public void goUp(Pointable p, Vector position) {
    	if(!boundFingers.containsKey(p.id())) {
    		GestureInfo gi = new GestureInfo(p, Vector.up(), position);
    		boundFingers.put(p.id(), gi);
    	}
    	if(boundFingers.get(p.id()).direction.equals(Vector.up())) {
    		boundFingers.get(p.id()).position = position;
    		System.out.println("going up " + p.id());
	    	keyPress(KeyEvent.VK_UP);
    	}
    }
    
    public void goDirection(Pointable f) {
    	GestureInfo gi = boundFingers.get(f.id());
    	Vector v = gi.direction;
    	if(v.equals(Vector.up())) {
    		goUp(f, gi.position);
    	}
    	if(v.equals(Vector.down())) {
    		goDown(f, gi.position);
    	}
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        PointableList fingers = frame.pointables();
        for( Pointable finger : fingers ) { 
        	if(boundFingers.containsKey(finger.id())) {
            	GestureInfo gi = boundFingers.get(finger.id());
        		goDirection(finger);
            	if( finger.tipPosition().distanceTo(gi.position) > 70) {
            		boundFingers.remove(finger.id());
            	}
        	}
        }
        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    // I am swiping down
                    if(swipe.direction().angleTo(Vector.down()) < Math.PI / 3) {
                    	goDown(swipe.pointable(), swipe.position());
                    }
                    if(swipe.direction().angleTo(Vector.up()) < Math.PI / 3) {
                    	goUp(swipe.pointable(), swipe.position());
                    }
                    System.out.println("Swipe id: " + swipe.id()
                               + ", " + swipe.state()
                               + ", position: " + swipe.position()
                               + ", direction: " + swipe.direction()
                               + ", speed: " + swipe.speed());
                    break;
                case TYPE_SCREEN_TAP:
                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
                    System.out.println("Screen Tap id: " + screenTap.id()
                               + ", " + screenTap.state()
                               + ", position: " + screenTap.position()
                               + ", direction: " + screenTap.direction());
                    break;
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    System.out.println("Key Tap id: " + keyTap.id()
                               + ", " + keyTap.state()
                               + ", position: " + keyTap.position()
                               + ", direction: " + keyTap.direction());
                    break;
                default:
                    System.out.println("Unknown gesture type.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.setPolicyFlags(PolicyFlag.POLICY_BACKGROUND_FRAMES);
        Main main = new Main();

        controller.addListener(main);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(main);
    }

}
