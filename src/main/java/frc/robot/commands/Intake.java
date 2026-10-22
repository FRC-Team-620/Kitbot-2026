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
public class Intake extends Command {
  /** Creates a new Intake. */

  FuelSubsystem fuelSubsystem;
  private final Timer groundFeederDelay = new Timer();
  private boolean groundFeederStarted;

  public Intake(FuelSubsystem fuelSystem) {
    addRequirements(fuelSystem);
    this.fuelSubsystem = fuelSystem;
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for intaking
  @Override
  public void initialize() {
    fuelSubsystem.stop();
    groundFeederStarted = false;
    groundFeederDelay.restart();
    fuelSubsystem.setFeederRoller(SmartDashboard.getNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE));
  }

  // Start the ground feeder after the intake feeder is running.
  @Override
  public void execute() {
    if (!groundFeederStarted && groundFeederDelay.hasElapsed(GROUND_FEEDER_DELAY_SECONDS)) {
      fuelSubsystem.setGroundFeeder(GROUND_FEEDER_VOLTAGE);
      groundFeederStarted = true;
    }
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    groundFeederDelay.stop();
    fuelSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
