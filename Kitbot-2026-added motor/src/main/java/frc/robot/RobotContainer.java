// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.OperatorConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Drive;
import frc.robot.commands.ExampleAuto;
import frc.robot.commands.Intake;
import frc.robot.commands.Launch;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FuelSubsystem;

/** Connects the controllers to the commands that operate the robot. */
public class RobotContainer {
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final FuelSubsystem fuel = new FuelSubsystem();
  private final CommandXboxController driver = new CommandXboxController(DRIVER_CONTROLLER_PORT);
  private final CommandXboxController operator = new CommandXboxController(OPERATOR_CONTROLLER_PORT);

  public RobotContainer() {
    // Operator slot 1: hold Y (button 4) to collect; hold X (button 3) to shoot.
    operator.y().whileTrue(new Intake(fuel));
    operator.x().whileTrue(new Launch(fuel));

    drivetrain.setDefaultCommand(new Drive(drivetrain, driver));
    fuel.setDefaultCommand(fuel.run(fuel::stop));
  }

  public Command getAutonomousCommand() {
    return new ExampleAuto(drivetrain, fuel);
  }
}
