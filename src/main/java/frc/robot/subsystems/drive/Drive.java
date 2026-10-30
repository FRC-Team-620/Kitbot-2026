// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
	private final DriveIO io;
	private final DriveIOInputsAutoLogged inputs = new DriveIOInputsAutoLogged();

	public Drive(DriveIO io) {
		this.io = io;
	}

	@Override
  public void periodic() {
  	io.updateInputs(inputs);
		Logger.processInputs("Drive", inputs);
  }

	public void driveArcade(double xSpeed, double zRotation) {
		DifferentialDrive.WheelSpeeds wheelSpeeds = 
			DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, true);
		io.setVoltage(
			wheelSpeeds.left * RobotController.getBatteryVoltage(),
			wheelSpeeds.right * RobotController.getBatteryVoltage()
		);
	}

	public void stop() {
		driveArcade(0, 0);
	}
}
