package frc.robot.subsystems.launcher;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.launcher.LauncherConstants.*;

import org.littletonrobotics.junction.AutoLogOutput;

public class LauncherIOSim implements LauncherIO {
	//Honestly idk if these numbers are correct
	private DCMotorSim motor = new DCMotorSim(
		LinearSystemId.createDCMotorSystem(DCMotor.getCIM(1), .004, GEAR_RATIO),
		DCMotor.getCIM(1)
	);
	private PIDController pidController = new PIDController(KP, KI, KD);

	private double voltage = 0.0;

	private AngularVelocity targetVelocity = RPM.of(0);
	
	public LauncherIOSim() {}

	@Override
	public void updateInputs(LauncherIOInputs inputs) {
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
	public void setVelocity(double rpm, double ffvolts) {
		this.targetVelocity = RPM.of(rpm);
		motor.setInputVoltage(
			pidController.calculate(motor.getAngularVelocityRPM(), targetVelocity.magnitude()) + ffvolts);
	}

	@Override
	public void stop() {
		this.targetVelocity = RPM.of(0.0);
		setVoltage(0.0);
	}

	@AutoLogOutput(key = "Launcher/targetVelocity", unit = "RadiansPerSecond")
	@Override
	public double getTargetVelocityRadPerS() {
		return targetVelocity.in(RadiansPerSecond);
	}
}
