package frc.robot.subsystems.launcher;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.subsystems.launcher.LauncherConstants.*;

public class Launcher extends SubsystemBase {
	private final LauncherIO io;
	private final LauncherIOInputsAutoLogged inputs = new LauncherIOInputsAutoLogged();

	public Launcher(LauncherIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("Launcher", inputs);
	}

	public void setVoltage(double volts) {
		io.setVoltage(volts);
	}

	public void setVelocity(double rpm, double ffvolts) {
		io.setVelocity(rpm, ffvolts);
	}

	public void stop() {
		io.stop();
	}

	public Command eject() {
		return Commands.run(() -> this.setVoltage(EJECTING_VOLTAGE));
	}

	public Command launch() {
		return Commands.run(() -> this.setVelocity(LAUNCHING_RPM, FF_PER_RPM_VOLTAGE * LAUNCHING_RPM));
	}
}
