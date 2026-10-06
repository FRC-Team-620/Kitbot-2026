// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.FuelConstants.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FuelSubsystem;

/** Collects while Y is held: start the middle intake, then the ground intake. */
public class Intake extends Command {
  private final FuelSubsystem fuel;
  private final Timer startupTimer = new Timer();
  private boolean delayedMotorStarted;

  public Intake(FuelSubsystem fuel) {
    this.fuel = fuel;
    // Intake and shooting share motors, so only one may control them at a time.
    addRequirements(fuel);
  }

  @Override
  public void initialize() {
    // Clear the previous mode's outputs and reset the delay on every button press.
    fuel.stop();
    delayedMotorStarted = false;
    startupTimer.restart();
    fuel.setMiddleIntakeVoltage(
        SmartDashboard.getNumber(INTAKE_MIDDLE_DASHBOARD_KEY, MIDDLE_INTAKE_VOLTS));
  }

  @Override
  public void execute() {
    // A timer lets the scheduler keep driving the robot while we wait.
    if (!delayedMotorStarted && startupTimer.hasElapsed(INTAKE_GROUND_DELAY_SECONDS)) {
      fuel.setGroundIntakeVoltage(GROUND_INTAKE_VOLTS);
      delayedMotorStarted = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    // Runs on release, interruption by another command, or autonomous timeout.
    startupTimer.stop();
    fuel.stop();
  }

  @Override
  public boolean isFinished() {
    return false; // The button binding or autonomous timeout ends this command.
  }
}
