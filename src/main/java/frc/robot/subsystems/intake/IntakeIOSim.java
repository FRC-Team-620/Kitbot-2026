package frc.robot.subsystems.intake;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.intake.IntakeConstants.*;

public class IntakeIOSim implements IntakeIO {
	//Honestly idk if these numbers are correct
	private DCMotorSim motor = new DCMotorSim(
		LinearSystemId.createDCMotorSystem(DCMotor.getCIM(1), .004, GEAR_RATIO),
		DCMotor.getCIM(1)
	);

	private double voltage = 0.0;
	
	public IntakeIOSim() {}

	@Override
	public void updateInputs(IntakeIOInputs inputs) {
		motor.update(.02);
		motor.setInputVoltage(voltage);

		inputs.voltage = Volts.of(motor.getInputVoltage());
		inputs.current = Amps.of(motor.getCurrentDrawAmps());
		inputs.velocity = RPM.of(motor.getAngularVelocityRPM());
		inputs.position = Rotations.of(motor.getAngularPositionRotations());
	}

	@Override
	public void setVoltage(double volts) {
		voltage = MathUtil.clamp(volts, -12.0, 12.0);
	}

	@Override
	public void stop() {
		setVoltage(0.0);
	}
}
