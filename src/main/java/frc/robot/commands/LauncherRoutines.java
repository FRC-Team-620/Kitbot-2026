// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.launcher.Launcher;

public class LauncherRoutines {
  private LauncherRoutines() {}

  /** Spin up launcher system. After letting this run for a while, 
   * call {@link #launch(Intake, Hopper, Launcher)} to actually launch fuel  */
  public static Command spinUp(Intake intake, Launcher launcher) {
    return Commands.parallel(
        intake.intake(),
        launcher.launch()
    ).finallyDo(
      () -> {
        intake.stop();
        launcher.stop();
      }
    );
  }

  /** Launch fuel. Should not be used without calling {@link #spinUp(Intake, Launcher)} 
   * before to let the launcher spin up */
  public static Command launch(Intake intake, Hopper hopper, Launcher launcher) {
    return Commands.parallel(
        intake.intake(),
        hopper.launch(),
        launcher.launch()
    ).finallyDo(
      () -> {
        intake.stop();
        hopper.stop();
        launcher.stop();
      }
    );
  }
}
