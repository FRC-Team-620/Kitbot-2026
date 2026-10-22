// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Launch extends Command {
  /** Creates a shooter command with delayed feeding. */

  FuelSubsystem fuelSubsystem;
  private final Timer feederDelay = new Timer();
  private boolean feederStarted;

  public Launch(FuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

  // Start the launcher and ground feeder; keep the feeder off during spin-up.
  @Override
  public void initialize() {
    fuelSubsystem.stop();
    feederStarted = false;
    feederDelay.restart();
    fuelSubsystem.setGroundFeeder(GROUND_FEEDER_VOLTAGE);
    fuelSubsystem
        .setIntakeLauncherRoller(
            SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE));

  }

  // Start feeding after the launcher has had time to spin up.
  @Override
  public void execute() {
    if (!feederStarted && feederDelay.hasElapsed(SHOOTER_FEEDER_DELAY_SECONDS)) {
      fuelSubsystem.setFeederRoller(
          SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE));
      feederStarted = true;
    }
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    feederDelay.stop();
    fuelSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
