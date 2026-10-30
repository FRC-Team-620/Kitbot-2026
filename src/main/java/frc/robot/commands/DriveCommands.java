// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.OperatorConstants.DRIVE_SCALING;
import static frc.robot.Constants.OperatorConstants.ROTATION_SCALING;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.drive.Drive;

public class DriveCommands {
  private DriveCommands() {}

  public static Command driveArcade(Drive drive, CommandXboxController controller) {
    return Commands.run(
      () -> {
        drive.driveArcade(
          (-controller.getLeftTriggerAxis() + controller.getRightTriggerAxis()) * DRIVE_SCALING, 
          -controller.getRightX() * ROTATION_SCALING
        );
      }, drive);
  }
}
