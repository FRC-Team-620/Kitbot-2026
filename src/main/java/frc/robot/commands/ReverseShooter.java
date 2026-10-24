package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.FuelConstants;
import frc.robot.subsystems.FuelSubsystem;

public class ReverseShooter extends Command {
	private FuelSubsystem fuelSubsystem;

	public ReverseShooter(FuelSubsystem fuelSubsystem) {
		this.fuelSubsystem = fuelSubsystem;
	}

	@Override
	public void execute() {
		fuelSubsystem.setLauncherVoltage(FuelConstants.LAUNCHER_REVERSE_VOLTAGE);
	}

	@Override
  public void end(boolean interrupted) {
    fuelSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
