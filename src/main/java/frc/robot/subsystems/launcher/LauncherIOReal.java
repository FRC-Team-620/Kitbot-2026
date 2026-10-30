package frc.robot.subsystems.launcher;

import org.littletonrobotics.junction.AutoLogOutput;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.launcher.LauncherConstants.*;

import edu.wpi.first.units.measure.*;

public class LauncherIOReal implements LauncherIO {
	private SparkMax motor = new SparkMax(MOTOR_ID, MotorType.kBrushed);
	
	private SparkClosedLoopController controller = motor.getClosedLoopController();

	private AngularVelocity targetVelocity = RPM.of(0);
	
	public LauncherIOReal() {
		var motorConfig = new SparkMaxConfig();
		motorConfig
			.idleMode(IdleMode.kBrake)
			.smartCurrentLimit(MOTOR_CURRENT_LIMIT);

		var pidConfig = new ClosedLoopConfig();
		pidConfig.pid(KP, KI, KD);
		motorConfig.apply(pidConfig);

		var encoderConfig = new EncoderConfig();
		encoderConfig.positionConversionFactor(2 * Math.PI / GEAR_RATIO);
		motorConfig.apply(encoderConfig);

		motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
	}

	@Override
	public void updateInputs(LauncherIOInputs inputs) {
		inputs.voltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
		inputs.current = Amps.of(motor.getOutputCurrent());
		inputs.velocity = RPM.of(motor.getEncoder().getVelocity());
		inputs.position = Radians.of(motor.getEncoder().getPosition());
		inputs.temp = Celsius.of(motor.getMotorTemperature());
	}

	@Override
	public void setVoltage(double volts) {
		motor.setVoltage(volts);
	}

	@Override
	public void setVelocity(double rpm, double ffvolts) {
		this.targetVelocity = RPM.of(rpm);
		controller.setSetpoint(rpm, ControlType.kVelocity, ClosedLoopSlot.kSlot0, ffvolts);
	}

	@Override
	public void stop() {
		this.targetVelocity = RPM.of(0.0);
		setVoltage(0.0);
	}

	@AutoLogOutput(key = "Launcher/targetVelocity", unit = "rad/s")
	@Override
	public double getTargetVelocityRadPerS() {
		return targetVelocity.in(RadiansPerSecond);
	}
}
