package frankmani ;

import robocode.Robot;


public class frankmani extends Robot {
	int others; // Number of other robots in the game
	static int corner = 0; // Which corner we are currently using
	// static so that it keeps it between rounds.
	boolean stopWhenSeeRobot = false; // See goCorner()

	// Current move directions
	private final Set<Direction> directions = new HashSet<Direction>();

	// Called when the robot must run
	public void run() {

		// Sets the colors of the robot
		// body = black, gun = white, radar = red
		setColors(Color.BLACK, Color.WHITE, Color.RED);

		// Loop forever
		for (;;) {
			// Move the robot compared to the distance to move
			setAhead(distanceToMove());

			// Turn the body to it points in the correct direction
			setTurnRight(angleToTurnInDegrees());

			// Turns the gun toward the current aim coordinate (x,y) controlled by
			// the current mouse coordinate
			double angle = normalAbsoluteAngle(Math.atan2(aimX - getX(), aimY - getY()));

			setTurnGunRightRadians(normalRelativeAngle(angle - getGunHeadingRadians()));

			// Fire the gun with the specified fire power, unless the fire power = 0
			if (firePower > 0) {
				setFire(firePower);
			}

			// Execute all pending set-statements
			execute();

			// Next turn is processed in this loop..
		}
	}

/**
	 * onHitRobot:  If it's our fault, we'll stop turning and moving,
	 * so we need to turn again to keep spinning.
	 */
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		if (e.isMyFault()) {
			turnRight(10);
		}
	}

	/**
	 * TrackFire's run method
	 */
	public void run() {
		// Set colors
		setBodyColor(Color.white);
		setGunColor(Color.white);
		setRadarColor(Color.white);

		// Initially, we'll move when life hits 80
		trigger = 80;
		// Add a custom event named "trigger hit",
		addCustomEvent(new Condition("triggerhit") {
			public boolean test() {
				return (getEnergy() <= trigger);
			}
		});
	}
	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// If the other robot is close by, and we have plenty of life,
		// fire hard!
		if (e.getDistance() < 50 && getEnergy() > 50) {
			fire(3);
		} // otherwise, fire 1.
		else {
			fire(1);
		}
		// Call scan again, before we turn the gun
		scan();
	}

	/**
	 * onHitByBullet:  Turn perpendicular to the bullet, and move a bit.
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

		ahead(dist);
		dist *= -1;
		scan();
	}

	/**
	 * onHitRobot:  Aim at it.  Fire Hard!
	 */
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3);
	}
}