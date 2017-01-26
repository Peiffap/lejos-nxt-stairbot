import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;

/**
 * Controls the robot in case of certain sonar and light inputs
 * 
 * @author Gilles Peiffer, Angelina Peirce 
 * @version 6 Dec. 2016
 */
public class AvoidBehavior implements Behavior {
	
    private Robot robot;
    private boolean suppressed;
    
    /*
     * @pre -
     * @post Creates an AvoidBehavior
     */
    
    public AvoidBehavior(Robot r) {
        super();
        this.robot=r;
    }

    /*
     * @pre -
     * @post retourne true si la distance captee par l UltrasonicSensor est inferieure a 25cm.
     */
    
    public boolean takeControl() {
        return robot.sonar.getDistance() < 25 || (robot.light.readValue()>25 && robot.light.readValue()<75);
    } // returns true if either one of both sensors have useful input
    
    /*
     * @pre -
     * @post robot has avoided the obstacle
     */
    public void action() {
        
        System.out.println("AVOID");
        
        if (robot.sonar.getDistance() < 25) { // sonar input
            robot.pilot.rotate(-90.0); // turns left
        }
        
        if(robot.light.readValue()>25 && robot.light.readValue()<65){
            // light sensor input (Gray)
        	
        	robot.pilot.travel(-10); // avoids hitting the obstacle while turning
            robot.pilot.rotate(-90.0); // left
            robot.pilot.travel(20);
            robot.pilot.rotate(90); // right
            robot.pilot.travel(30);
            robot.pilot.rotate(90); // right
            robot.pilot.travel(20);
            robot.pilot.rotate(-90); // left
        }
        
        while (!suppressed && robot.pilot.isMoving()) {
            Thread.yield(); // yields cpu
        }
        
        robot.pilot.stop();
        
        LCD.clear();
    }
    
    /*
     * @pre -
     * @post suppressed is true, action is stopped
     */
    
    public void suppress() {
        suppressed = true;
    }
}
