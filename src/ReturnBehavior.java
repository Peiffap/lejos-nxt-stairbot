import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.*;
import lejos.nxt.*;

/**
 * returns the robot to base
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */

public class ReturnBehavior implements Behavior {
	
    private boolean suppressed;
    private Robot robot;
    
    public ReturnBehavior(Robot r) {
    	
        super();
        this.robot = r;
    }
    
    /**
     * @pre -
     * @post returns true if the robot has dumped the piano and is
     * 		 correctly positioned
     */
    
    public boolean takeControl() {
        return RampBehavior.used;
    }
    
    /**
     * @pre -
     * @post robot is back in base, program has been stopped
     */
    
    public void action() {
    	
        System.out.println("RETURN"); // useful for debugging
        suppressed = false;
        
        while (robot.sonar.getDistance() > 18) { 
            robot.pilot.forward();
        } // robot is facing left wall at 18cm from it
        
        robot.pilot.rotate(-90); // left
        robot.pilot.travel(55); // robot goes down the side of the field
        robot.pilot.arc(-15,-90); // gets positioned for final straight line
        
        while(robot.light.readValue() > 65) {
            robot.pilot.forward();
        } // robot has recognised the base
        
        robot.pilot.travel(5);
        
        LCD.clear();
        
        System.exit(0); 
    }
    
    /** 
     * @pre -
     * @post suppressed is true, action is stopped
     */
    
    public void suppress() {
        suppressed = true;
    }
}