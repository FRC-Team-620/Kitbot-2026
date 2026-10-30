package frc.robot.subsystems.drive;

import edu.wpi.first.math.util.Units;

public final class DriveConstants {
  // Motor controller IDs for drivetrain motors
  public static final int LEFT_LEADER_ID = 1;
  public static final int LEFT_FOLLOWER_ID = 2;
  public static final int RIGHT_LEADER_ID = 3;
  public static final int RIGHT_FOLLOWER_ID = 4;

  public static final double WHEEL_RADIUS_METERS = Units.inchesToMeters(3.0);

  // Current limit for drivetrain motors
  public static final int MOTOR_CURRENT_LIMIT = 60;

  // PID Values - need tuning!!
  public static final double KP = 0.1;
  public static final double KI = 0.0;
  public static final double KD = 0.0;
}