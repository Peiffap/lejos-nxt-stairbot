import lejos.robotics.navigation.*;
import lejos.robotics.localization.*;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;

/**
 * Initializes and controls the interrupt mechanism and thus the robot
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */

public class Controller {
	
    /**
     * pre -
     * post robot is being controlled by a predefined number of behaviors
     */
    
    public static void main(String[] args) {
    	
        Robot robot = new Robot(); // creates Robot object
        Pose initialPose = new Pose (0,0,90); // allows odometry use
        
        // Color calibration
        System.out.println("ENTER: BLACK");
        Button.ENTER.waitForPressAndRelease();
        robot.light.calibrateLow(); // sets current sensor reading as absolute black
        LCD.clear();
        
        System.out.println("ENTER: WHITE");
        Button.ENTER.waitForPressAndRelease();
        robot.light.calibrateHigh(); // sets current sensor reading as absolute white
        LCD.clear();
        
        System.out.println("START?");
        Button.ENTER.waitForPressAndRelease(); // waits for button press before starting
        LCD.clear();
        
        robot.odometry.setPose(initialPose); // sets initial heading (+Y axis)
        
        robot.pilot.rotate(90);
        robot.pilot.travel(15);
        // robot is facing right, slightly outside of base
        
        // tasks
        Behavior forward = new ForwardBehavior(robot);
        Behavior stop = new StopBehavior(robot);
        Behavior avoid = new AvoidBehavior(robot);
        Behavior ramp = new RampBehavior(robot);
        Behavior return = new ReturnBehavior(robot);
        Behavior[] tasks = {forward,avoid,return,ramp,stop}; // array of behaviors, increasing priority
        
        Arbitrator arbitrator = new Arbitrator(tasks); // interrupt-based mechanism
        arbitrator.start(); // starts checking for interrupts and acting accordingly
    }
}