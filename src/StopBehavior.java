import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;

/**
 * Shuts down the robot and program
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */

public class StopBehavior implements Behavior {
	
    private Robot robot;
    
	/**
	 * @pre -
	 * @post creates a StopBehavior object
	 */
    
    public StopBehavior(Robot r) {
		super();
		this.robot = r;
	}
	
    /**
     * @pre -
	 * @post returns true if the ESCAPE or ENTER button is pressed
     */
    
    public boolean takeControl() {
        return Button.ESCAPE.isDown() || Button.ENTER.isDown();
    }
    
    /**
     * @pre -
	 * @post robot stopped, program shut down
     */
    
    public void action() {
    	
        robot.pilot.stop();
        System.exit(0);
    }
    
    /** 
	 * @pre -
	 * @post no effect
     */
    
    public void suppress() {}
}