// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.OperatorConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.EjectCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.LauncherRoutines;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DriveIO;
import frc.robot.subsystems.drive.DriveIOReal;
import frc.robot.subsystems.drive.DriveIOSim;
import frc.robot.subsystems.hopper.Hopper;
import frc.robot.subsystems.hopper.HopperIO;
import frc.robot.subsystems.hopper.HopperIOReal;
import frc.robot.subsystems.hopper.HopperIOSim;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOReal;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.launcher.Launcher;
import frc.robot.subsystems.launcher.LauncherIO;
import frc.robot.subsystems.launcher.LauncherIOReal;
import frc.robot.subsystems.launcher.LauncherIOSim;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final Drive drive;

  private final Intake intake;
  private final Hopper hopper;
  private final Launcher launcher;
  
  // The driver's controller
  private final CommandXboxController driverController = new CommandXboxController(
      DRIVER_CONTROLLER_PORT);

  // The operator's controller
  private final CommandXboxController operatorController = new CommandXboxController(
      OPERATOR_CONTROLLER_PORT);
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    switch (Constants.CURRENT_MODE) {
      case REAL:
        //Real robot
        drive = new Drive(new DriveIOReal());
        intake = new Intake(new IntakeIOReal());
        hopper = new Hopper(new HopperIOReal());
        launcher = new Launcher(new LauncherIOReal());
        break;
      
      case SIM:
        //Sim mode
        drive = new Drive(new DriveIOSim());
        intake = new Intake(new IntakeIOSim());
        hopper = new Hopper(new HopperIOSim());
        launcher = new Launcher(new LauncherIOSim());
        break;
      
      default:
        //Replay
        drive = new Drive(new DriveIO() {});
        intake = new Intake(new IntakeIO() {});
        hopper = new Hopper(new HopperIO() {});
        launcher = new Launcher(new LauncherIO() {});
        break;
    }

    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)}
   * constructor with an arbitrary predicate, or via the named factories in
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
   * for {@link CommandXboxController Xbox}/
   * {@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // While left bumper on the operator controller is held, intake fuel.
    operatorController.leftBumper().whileTrue(IntakeCommand.intake(intake, hopper));
    // While right bumper on the operator controller is held, spin up launcher.
    operatorController.rightBumper().whileTrue(LauncherRoutines.spinUp(intake, launcher));
    // While X on the operator controller is pressed, shoot fuel
    operatorController.x().whileTrue(LauncherRoutines.launch(intake, hopper, launcher));
    // While on the operator controller is held, eject fuel
    operatorController.b().whileTrue(EjectCommand.eject(hopper, launcher));

    drive.setDefaultCommand(DriveCommands.driveArcade(drive, driverController));
    intake.setDefaultCommand(Commands.runOnce(() -> intake.stop(), intake));
    hopper.setDefaultCommand(Commands.runOnce(() -> hopper.stop(), hopper));
    launcher.setDefaultCommand(Commands.runOnce(() -> launcher.stop(), launcher));
}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
