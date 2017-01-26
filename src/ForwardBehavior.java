import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;

/**
 * Implements the Behavior class and allows the robot to move forward
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */

public class Forward implements Behavior {
	
    private boolean suppressed = false;
    private Robot robot;
    
    public ForwardBehavior (Robot r) {
		super();
		this.robot = r;
	}
	
    /**
     * @pre -
	 * @post returns true (activatable by default)
     */
    
    public boolean takeControl() {
        return true;
    }
    
    /**
     * @pre -
	 * @post robot has moved until suppress was called
     */
    
    public void action(){
        System.out.println("FORWARD"); // useful for debugging
        
        suppressed = false; 
        
        robot.pilot.setTravelSpeed(10.0);
        robot.pilot.forward();
        
        while (!suppressed) {
            Thread.yield(); // yields the cpu
        }
        
        robot.pilot.stop();
        
        LCD.clear(); // clears the screen
    }
    
    /** 
	 * @pre -
	 * @post suppressed is true, action is stopped
     */
    
    public void suppress() {
        this.suppressed = true;
    }
}