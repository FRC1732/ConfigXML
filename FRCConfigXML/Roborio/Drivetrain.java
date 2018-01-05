import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Drivetrain {

	public Motor left1;
	public Motor left2;
	public Motor left3;
	public Motor right1;
	public Motor right2;
	public Motor right3;

	public Drivetrain() {
		// do other stuff in the constructor that wouldn't get redone
	}

	public void configure(Element drivetrainConfig) {
		// If the user did mess up the xml format there will be an unchecked exception
		// and the user can fix it
		try {
			left1 = makeMotor(drivetrainConfig.getElementsByTagName("MotorLeft1").item(0));
			left2 = makeMotor(drivetrainConfig.getElementsByTagName("MotorLeft2").item(0));
			left3 = makeMotor(drivetrainConfig.getElementsByTagName("MotorLeft3").item(0));
			right1 = makeMotor(drivetrainConfig.getElementsByTagName("MotorRight1").item(0));
			right2 = makeMotor(drivetrainConfig.getElementsByTagName("MotorRight2").item(0));
			right3 = makeMotor(drivetrainConfig.getElementsByTagName("MotorRight3").item(0));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("YOU MESSED UP THE XML FORMAT. TRY AGAIN");
		}
	}

	private Motor makeMotor(Node motorConfig) {
		Element e = (Element) motorConfig;
		int CANNumber = Integer.parseInt(e.getElementsByTagName("CANnumber").item(0).getTextContent());
		boolean isInverted = Boolean.parseBoolean(e.getElementsByTagName("isInverted").item(0).getTextContent());
		boolean isFollower = Boolean.parseBoolean(e.getElementsByTagName("isFollower").item(0).getTextContent());
		return new Motor(isInverted, CANNumber, isFollower);
	}

	@Override
	public String toString() {
		return String.format("DriveTrain:%nL1:%s%nL2:%s%nL3:%s%nR1:%s%nR2:%s%nR3:%s%n", left1, left2, left3, right1,
				right2, right3);
	}

}
