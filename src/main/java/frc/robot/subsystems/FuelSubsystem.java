// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;   
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;
public class FuelSubsystem extends SubsystemBase {
  private final Spark hopper;
  private final Spark launcher;
  private final Spark intake;

  public FuelSubsystem() {
    launcher = new Spark(LAUNCHER_MOTOR_ID);
    hopper = new Spark(HOPPER_MOTOR_ID);
    intake = new Spark(INTAKE_MOTOR_ID);


    launcher.setInverted(false);
    intake.setInverted(INTAKE_INVERTED);
    SmartDashboard.putNumber("Intaking feeder roller value", HOPPER_INTAKING_VOLTAGE);
    SmartDashboard.putNumber("Intaking intake roller value", INTAKE_INTAKING_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", HOPPER_LAUNCH_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHER_LAUNCH_VOLTAGE);
  }

  // A method to set the voltage of the intake roller
  public void setLauncherVoltage(double voltage) {
    launcher.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setHopperVoltage(double voltage) {
    hopper.setVoltage(voltage);
  }
  
  public void setIntakeVoltage(double voltage) {
    intake.setVoltage(voltage);
  }
  // A method to stop the rollers
  public void stop() {
    hopper.set(0);
    launcher.set(0);
    intake.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
