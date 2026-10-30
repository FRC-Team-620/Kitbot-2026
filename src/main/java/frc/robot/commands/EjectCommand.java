// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.launcher.Launcher;

public class EjectCommand {
  public static Command eject(Hopper hopper, Launcher launcher) {
    return Commands.parallel(
      hopper.eject(),
      launcher.eject()
    );
  }
}