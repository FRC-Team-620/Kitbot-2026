// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.OperatorConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DrivetrainSubsystem;

/** Driver slot 0: right trigger forward, left trigger reverse, right stick steering. */
public class Drive extends Command {
  private final DrivetrainSubsystem drivetrain;
  private final CommandXboxController driver;

  public Drive(DrivetrainSubsystem drivetrain, CommandXboxController driver) {
    this.drivetrain = drivetrain;
    this.driver = driver;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    double forwardSpeed = (driver.getRightTriggerAxis() - driver.getLeftTriggerAxis()) * DRIVE_SCALING;
    double turnSpeed = -driver.getRightX() * ROTATION_SCALING;
    // Refresh every scheduler cycle to keep DifferentialDrive's watchdog fed.
    drivetrain.driveArcade(forwardSpeed, turnSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.driveArcade(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
