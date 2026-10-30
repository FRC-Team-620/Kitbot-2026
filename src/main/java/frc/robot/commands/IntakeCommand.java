// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.intake.Intake;

public class IntakeCommand {
  private IntakeCommand() {}

  public static Command intake(Intake intake, Hopper hopper) {
    return Commands.parallel(
      Commands.sequence(
        Commands.runOnce(() -> intake.stop(), intake),
        hopper.intake()
      ),
      Commands.sequence(
        Commands.waitSeconds(.25),
        intake.intake()
      )
    ).finallyDo(
      () -> {
        intake.stop();
        hopper.stop();
      }
    );
  }
}
