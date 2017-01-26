import lejos.nxt.*;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.*;

/**
 * Creates a robot object to simplify sensor and motor control
 *            in the other classes
 * 
 * @author Gilles Peiffer, Angelina Peirce
 * @version 6 Dec. 2016
 */
public class Robot {
    public static UltrasonicSensor sonar;
    public static LightSensor light;
    public static DifferentialPilot pilot;
    public static OdometryPoseProvider odometry;
    public static Navigator navigator;
    public static Pose initialPose;
    
    public Robot() {
        final float WHEEL_DIAM = 5f; // wheel diameter
        final float WHEEL_DIST = 14.6f; // wheel spacing (changed according to tests)
        final boolean REVERSE = true; // makes up for FWD
        this.pilot = new DifferentialPilot(WHEEL_DIAM, WHEEL_DIST, Motor.A, Motor.B, REVERSE);
        this.pilot.setTravelSpeed(10.0);
        this.pilot.setRotateSpeed(60.0);
        this.sonar = new UltrasonicSensor(SensorPort.S3); // sonar
        this.light = new LightSensor(SensorPort.S4); // light sensor
        this.odometry = new OdometryPoseProvider(pilot); // uses odometry to locate the robot
        this.navigator = new Navigator(pilot); // alternative for odometry
    }
}