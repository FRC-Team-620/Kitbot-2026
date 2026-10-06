// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Wiring and tuning values for this robot. PWM ports are roboRIO signal ports, not CAN IDs. */
public final class Constants {
  private Constants() {}

  public static final class DriveConstants {
    private DriveConstants() {}
    public static final int FRONT_LEFT_PWM = 3;
    public static final int REAR_LEFT_PWM = 2;
    public static final int FRONT_RIGHT_PWM = 1;
    public static final int REAR_RIGHT_PWM = 0;
  }

  public static final class FuelConstants {
    private FuelConstants() {}
    public static final int SHOOTER_PWM = 4;
    public static final int MIDDLE_INTAKE_PWM = 5;
    public static final int GROUND_INTAKE_PWM = 6;
    public static final boolean GROUND_INTAKE_INVERTED = false;

    // These signs match the tested motor directions. The middle intake reverses to shoot.
    public static final double MIDDLE_INTAKE_VOLTS = 12;
    public static final double MIDDLE_SHOOT_VOLTS = -9;
    public static final double SHOOTER_VOLTS = -10.6;
    public static final double GROUND_INTAKE_VOLTS = 12;

    // Intake starts the middle roller first. Shooting starts the shooter first.
    public static final double INTAKE_GROUND_DELAY_SECONDS = 0.25;
    public static final double SHOOT_MIDDLE_DELAY_SECONDS = 0.25;

    // Keep the existing dashboard names so the team's tuning controls still work.
    public static final String INTAKE_MIDDLE_DASHBOARD_KEY = "Intaking feeder roller value";
    public static final String SHOOT_MIDDLE_DASHBOARD_KEY = "Launching feeder roller value";
    public static final String SHOOTER_DASHBOARD_KEY = "Launching launcher roller value";
  }

  public static final class OperatorConstants {
    private OperatorConstants() {}
    // Slots in Driver Station's USB list, not physical USB sockets on the laptop.
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
    public static final double DRIVE_SCALING = 0.7;
    public static final double ROTATION_SCALING = 0.8;
  }

  public static final class AutoConstants {
    private AutoConstants() {}
    public static final double DRIVE_SPEED = 0.5;
    public static final double DRIVE_SECONDS = 0.25;
    public static final double SHOOT_SECONDS = 10;
  }
}
