// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Four PWM drive motors, using the robot's tested inversion and rear-motor mapping. */
public class DrivetrainSubsystem extends SubsystemBase {
  private final Spark frontLeftMotor = new Spark(FRONT_LEFT_PWM);
  private final Spark frontRightMotor = new Spark(FRONT_RIGHT_PWM);
  private final Spark rearLeftMotor = new Spark(REAR_LEFT_PWM);
  private final Spark rearRightMotor = new Spark(REAR_RIGHT_PWM);
  private final DifferentialDrive drive;

  public DrivetrainSubsystem() {
    frontRightMotor.setInverted(true);
    rearRightMotor.setInverted(true);
    drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
  }

  public void driveArcade(double forwardSpeed, double turnSpeed) {
    drive.arcadeDrive(forwardSpeed, turnSpeed);
    // Preserve the working rear-motor output mapping, including right-side inversion.
    rearLeftMotor.set(frontLeftMotor.get());
    rearRightMotor.set(frontRightMotor.get());
  }
}
