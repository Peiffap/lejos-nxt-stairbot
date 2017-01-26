import lejos.robotics.subsumption.*;
import lejos.robotics.localization.*;
import lejos.nxt.*;

/**
 * Directs the robot while going up and down the ramp
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */

public class RampBehavior implements Behavior {
    private Robot robot;
    private boolean suppressed;
    public static boolean used;
    
    public RampBehavior(Robot r) {
        super();
        this.robot = r;
    }
    
    public boolean takeControl() {
        return (robot.odometry.getPose().getY() >= 45f) && !used;
    }
    
    public void action(){
        System.out.println("RAMP"); // useful for debugging
        robot.pilot.arc(-15,-90); // turns left (inverted axis)
        robot.pilot.setTravelSpeed(5); 
        
        while(robot.light.readValue() > 50) {
            robot.pilot.forward();
        } // robot has seen the black line indicating the ramp/stairs
        
        robot.pilot.travel(-12); // robot is next to ramp facing left
        robot.pilot.rotate(90); // robot is facing ramp 
        
        while (robot.light.readValue() > 45) {
            robot.pilot.forward(); 
        } // robot is right in front of ramp
  
        robot.pilot.setTravelSpeed(30);
        robot.pilot.travel(40); // passes the bump between ramp/ground
        robot.pilot.setTravelSpeed(10);
        robot.pilot.travel(40); // drives further up ramp
        robot.pilot.rotate(180); // turns around
        
        Motor.C.backward(); // 3rd motor drops piano
            
        try {
            Thread.sleep(1337); // gives the piano some time to fall
        }
        catch (InterruptedException e) { // if interrupted, stops the motor
            Motor.C.stop();
        }
            
        Motor.C.stop(); // stops the motor
        
        robot.pilot.travel(50); // goes down to avoid behavior interference
        
        while(robot.light.readValue() > 15) {
            robot.pilot.forward(); // further goes down ramp
        }
            
        robot.pilot.arc(15,90); // pilot is now facing left, ready to return to base
        robot.pilot.stop();
        
        used = true; // stops method from being reused
        
        LCD.clear(); // clears LCD screen
    }
    
    public void suppress() {
         suppressed = true; // suppresses method
    }
    
    public boolean done() {
        return !robot.pilot.isMoving();
    }
}