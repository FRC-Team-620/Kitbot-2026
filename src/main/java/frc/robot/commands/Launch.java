// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.FuelConstants.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FuelSubsystem;

/** Shoots while X is held: start the shooter, then feed balls after spin-up. */
public class Launch extends Command {
  private final FuelSubsystem fuel;
  private final Timer startupTimer = new Timer();
  private boolean delayedMotorStarted;

  public Launch(FuelSubsystem fuel) {
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
    fuel.setGroundIntakeVoltage(GROUND_INTAKE_VOLTS);
    fuel.setShooterVoltage(SmartDashboard.getNumber(SHOOTER_DASHBOARD_KEY, SHOOTER_VOLTS));
  }

  @Override
  public void execute() {
    // A timer lets the scheduler keep driving the robot while we wait.
    if (!delayedMotorStarted && startupTimer.hasElapsed(SHOOT_MIDDLE_DELAY_SECONDS)) {
      fuel.setMiddleIntakeVoltage(
          SmartDashboard.getNumber(SHOOT_MIDDLE_DASHBOARD_KEY, MIDDLE_SHOOT_VOLTS));
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
