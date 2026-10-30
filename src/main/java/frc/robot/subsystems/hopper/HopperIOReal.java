package frc.robot.subsystems.hopper;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.hopper.HopperConstants.*;

public class HopperIOReal implements HopperIO {
	private SparkMax motor = new SparkMax(MOTOR_ID, MotorType.kBrushed);
	
	public HopperIOReal() {
		var motorConfig = new SparkMaxConfig();
		motorConfig
			.idleMode(IdleMode.kBrake)
			.smartCurrentLimit(MOTOR_CURRENT_LIMIT);
		
		var encoderConfig = new EncoderConfig();
		encoderConfig.positionConversionFactor(2 * Math.PI / GEAR_RATIO);
		motorConfig.apply(encoderConfig);

		motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
	}

	@Override
	public void updateInputs(HopperIOInputs inputs) {
		inputs.voltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
		inputs.current = Amps.of(motor.getOutputCurrent());
		inputs.velocity = RPM.of(motor.getEncoder().getVelocity());
		inputs.position = Rotations.of(motor.getEncoder().getPosition());
		inputs.temp = Celsius.of(motor.getMotorTemperature());
	}

	@Override
	public void setVoltage(double volts) {
		motor.setVoltage(volts);
	}

	@Override
	public void stop() {
		setVoltage(0.0);
	}
}
