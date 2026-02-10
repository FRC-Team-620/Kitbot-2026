// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import static frc.robot.Constants.DriveConstants.*;

public class DrivetrainSubsystem extends SubsystemBase {


  private final Spark leftMotor = new Spark(0);  // PWM port 0
    private final Spark rightMotor = new Spark(1); // PWM port 1

    public DrivetrainSubsystem() {
        rightMotor.setInverted(true); // Adjust based on wiring
    }

    public void driveArcade(double speed, double rotation) {
        var leftOutput = speed + rotation;
        var rightOutput = speed - rotation;

        leftMotor.set(leftOutput);
        rightMotor.set(rightOutput);
    }

    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }

  @Override
  public void periodic() {
  }

  // public void driveArcade(double xSpeed, double zRotation) {
  //   drive.arcadeDrive(xSpeed, zRotation);
  // }

}
