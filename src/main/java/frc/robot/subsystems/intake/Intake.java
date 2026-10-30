package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.subsystems.intake.IntakeConstants.*;

public class Intake extends SubsystemBase {
	private final IntakeIO io;
	private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

	public Intake(IntakeIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("Intake", inputs);
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
}