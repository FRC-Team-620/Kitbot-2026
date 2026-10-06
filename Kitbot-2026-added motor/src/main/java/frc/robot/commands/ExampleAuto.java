// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.*;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FuelSubsystem;

/** The existing autonomous routine: a short drive, then ten seconds of shooting. */
public class ExampleAuto extends SequentialCommandGroup {
  public ExampleAuto(DrivetrainSubsystem drivetrain, FuelSubsystem fuel) {
    addCommands(
        new AutoDrive(drivetrain, DRIVE_SPEED, 0).withTimeout(DRIVE_SECONDS),
        // Launch includes its 0.25-second middle-intake delay within these ten seconds.
        new Launch(fuel).withTimeout(SHOOT_SECONDS));
  }
}
