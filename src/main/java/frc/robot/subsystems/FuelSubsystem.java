// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.FuelConstants.*;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Owns the three ball-handling motors. Commands decide their sequence and timing. */
public class FuelSubsystem extends SubsystemBase {
  private final Spark shooterMotor = new Spark(SHOOTER_PWM);
  private final Spark middleIntakeMotor = new Spark(MIDDLE_INTAKE_PWM);
  private final Spark groundIntakeMotor = new Spark(GROUND_INTAKE_PWM);

  public FuelSubsystem() {
    shooterMotor.setInverted(false);
    groundIntakeMotor.setInverted(GROUND_INTAKE_INVERTED);
    SmartDashboard.putNumber(INTAKE_MIDDLE_DASHBOARD_KEY, MIDDLE_INTAKE_VOLTS);
    SmartDashboard.putNumber(SHOOT_MIDDLE_DASHBOARD_KEY, MIDDLE_SHOOT_VOLTS);
    SmartDashboard.putNumber(SHOOTER_DASHBOARD_KEY, SHOOTER_VOLTS);
  }

  /** PWM 4: launches balls; stays off while collecting balls. */
  public void setShooterVoltage(double volts) {
    shooterMotor.setVoltage(volts);
  }

  /** PWM 5: feeds the hopper during intake and reverses to feed the shooter. */
  public void setMiddleIntakeVoltage(double volts) {
    middleIntakeMotor.setVoltage(volts);
  }

  /** PWM 6: runs in the same direction during intake and shooting. */
  public void setGroundIntakeVoltage(double volts) {
    groundIntakeMotor.setVoltage(volts);
  }

  public void stop() {
    middleIntakeMotor.set(0);
    shooterMotor.set(0);
    groundIntakeMotor.set(0);
  }
}
