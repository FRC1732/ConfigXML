public class Motor {

	private boolean isInverted;
	private int CANnumber;
	private boolean isFollower;

	public Motor(boolean isInverted, int CANnumber, boolean isFollower) {
		this.isInverted = isInverted;
		this.CANnumber = CANnumber;
		this.isFollower = isFollower;
	}

	@Override
	public String toString() {
		return "Motor [isInverted=" + isInverted + ", CANnumber=" + CANnumber + ", isFollower=" + isFollower
				+ ", toString()=" + super.toString() + "]";
	}

}