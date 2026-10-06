// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

/** Drives at fixed inputs until the autonomous routine's timeout expires. */
public class AutoDrive extends Command {
  private final DrivetrainSubsystem drivetrain;
  private final double forwardSpeed;
  private final double turnSpeed;

  public AutoDrive(DrivetrainSubsystem drivetrain, double forwardSpeed, double turnSpeed) {
    this.drivetrain = drivetrain;
    this.forwardSpeed = forwardSpeed;
    this.turnSpeed = turnSpeed;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
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
