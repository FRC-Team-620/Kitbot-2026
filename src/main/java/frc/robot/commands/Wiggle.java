package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

// Wiggles the bot around rotationally to unstick balls
public class Wiggle extends Command {
	private DrivetrainSubsystem drivetrainSubsystem;
	private Timer timer;

	/** Creates a new Wiggle (..?) */
	public Wiggle(DrivetrainSubsystem driveSystem) {
		addRequirements(driveSystem);
		drivetrainSubsystem = driveSystem;
	}

	@Override
	public void initialize() {
		timer = new Timer();
	}

	@Override
	public void execute() {
		// Speed of wiggle is UNTESTED and will 100% need tweaking
		drivetrainSubsystem.driveArcade(0, Math.sin(timer.get()));
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}