// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import static frc.robot.Constants.DriveConstants.*;

public class DrivetrainSubsystem extends SubsystemBase {

    private final Spark leftMotor = new Spark(3);  // PWM port 0
    private final Spark rightMotor = new Spark(1); // PWM port 1
    private final Spark backLeftMotor = new Spark(2);  // PWM port 2
    private final Spark backRightMotor = new Spark(0); // PWM port 3
    private final DifferentialDrive drive;
  
    public DrivetrainSubsystem() {
        rightMotor.setInverted(true); // Adjust based on wiring
        backRightMotor.setInverted(true);

        drive = new DifferentialDrive(leftMotor, rightMotor);
        drive.setSafetyEnabled(false);
    }

    public void driveArcade(double xSpeed, double zRotation) {
        drive.arcadeDrive(xSpeed, zRotation);
        backLeftMotor.set(leftMotor.get());
        backRightMotor.set(rightMotor.get());
    }


    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }

  @Override
  public void periodic() {
  }
}
