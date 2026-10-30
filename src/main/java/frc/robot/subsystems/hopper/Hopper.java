package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.subsystems.hopper.HopperConstants.*;

public class Hopper extends SubsystemBase {
	private final HopperIO io;
	private final HopperIOInputsAutoLogged inputs = new HopperIOInputsAutoLogged();

	public Hopper(HopperIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("Hopper", inputs);
	}

	public void setVoltage(double volts) {
		io.setVoltage(volts);
	}

	public void stop() {
		io.stop();
	}

	public Command intake() {
		return Commands.run(() -> this.setVoltage(INTAKING_VOLTAGE));
	}

	public Command eject() {
		return Commands.run(() -> this.setVoltage(EJECTING_VOLTAGE));
	}

	public Command launch() {
		return Commands.run(() -> this.setVoltage(LAUNCHING_VOLTAGE));
	}
}
