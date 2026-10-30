package frc.robot.subsystems.launcher;

public class LauncherConstants {
	public static final int MOTOR_ID = 4;

	public static final double GEAR_RATIO = 34.0 / 66.0;

	public static final int MOTOR_CURRENT_LIMIT = 60;

	public static final double LAUNCHING_VOLTAGE = 9; //will be removed once rpm-based launching is implemented
	public static final double EJECTING_VOLTAGE = -6;

	public static final double LAUNCHING_RPM = 2000; //PLACEHOLDER
	public static final double FF_PER_RPM_VOLTAGE = .0045; //PLACEHOLDER

	// PID values - all temporary, need tuning
	public static final double KP = .1;
	public static final double KI = 0.0;
	public static final double KD = 0.0;
}
