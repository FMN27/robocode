package frankmani";

import robocode.*;


public class frankmani extends Robot {
	public void run() {
		setBodyColor(Color.grey);
		setGunColor(Color.green);
		setRadarColor(Color.green);
		setBulletColor(Color.red);
		setScanColor(Color.yellow);

		//loop
		while (true) {
			//the robots do a zig zag
			for (int i = 0; i < 5; i++){
				ahead(110);
				turnRight(55);
				ahead(110);
				turnLeft(55);
			}


			turnRight(360);

            //the repetition of the robot's movement mimics a type of zig-zag
			ahead(110);
			turnRight(55);
		}
	}"
    //fires when the robots detects another robot on the battlefield"
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(power:10);
    }

	
