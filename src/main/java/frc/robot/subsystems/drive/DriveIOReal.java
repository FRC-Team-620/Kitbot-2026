package frc.robot.subsystems.drive;

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

public class DriveIOReal implements DriveIO {
	private SparkMax leftLeader = new SparkMax(DriveConstants.LEFT_LEADER_ID, MotorType.kBrushed);
	private SparkMax leftFollower = new SparkMax(DriveConstants.LEFT_FOLLOWER_ID, MotorType.kBrushed);
	private SparkMax rightLeader = new SparkMax(DriveConstants.LEFT_LEADER_ID, MotorType.kBrushed);
	private SparkMax rightFollower = new SparkMax(DriveConstants.RIGHT_FOLLOWER_ID, MotorType.kBrushed);
	
	private SparkClosedLoopController leftController = leftLeader.getClosedLoopController();
	private SparkClosedLoopController rightController = rightLeader.getClosedLoopController();
	
	public DriveIOReal() {
		var motorConfig = new SparkMaxConfig();
		motorConfig
			.idleMode(IdleMode.kBrake)
			.smartCurrentLimit(DriveConstants.MOTOR_CURRENT_LIMIT);

		var pidConfig = new ClosedLoopConfig();
		pidConfig.pid(DriveConstants.KP, DriveConstants.KI, DriveConstants.KD);
		motorConfig.apply(pidConfig);

		var encoderConfig = new EncoderConfig();
			encoderConfig.positionConversionFactor(2 * Math.PI);
		motorConfig.apply(encoderConfig);

		motorConfig.inverted(false);
		leftLeader.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
		motorConfig.inverted(true);
		rightLeader.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
		motorConfig.inverted(false).follow(leftLeader);
		leftFollower.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
		motorConfig.inverted(true).follow(rightLeader);
		rightLeader.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
	}

	@Override
	public void updateInputs(DriveIOInputs inputs) {
		inputs.leftPosition = Radians.of(leftLeader.getEncoder().getPosition());
		inputs.leftVelocity = RPM.of(leftLeader.getEncoder().getVelocity());
		inputs.leftVoltage = Volts.of(leftLeader.getAppliedOutput() * leftLeader.getBusVoltage());
		inputs.leftLeaderCurrent = Amps.of(leftLeader.getOutputCurrent());
		inputs.leftFollowerCurrent = Amps.of(leftFollower.getOutputCurrent());

		inputs.rightPosition = Radians.of(rightLeader.getEncoder().getPosition());
		inputs.rightVelocity = RPM.of(rightLeader.getEncoder().getVelocity());
		inputs.rightVoltage = Volts.of(rightLeader.getAppliedOutput() * rightLeader.getBusVoltage());
		inputs.rightLeaderCurrent = Amps.of(rightLeader.getOutputCurrent());
		inputs.rightFollowerCurrent = Amps.of(rightFollower.getOutputCurrent());
	}

	@Override
	public void setVoltage(double leftVolts, double rightVolts) {
		leftLeader.setVoltage(leftVolts);
		rightLeader.setVoltage(rightVolts);
	}

	@Override
	public void setVelocity(
		double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts
	) {
		leftController.setSetpoint(
			leftRadPerSec * (30 / Math.PI), ControlType.kVelocity, ClosedLoopSlot.kSlot0, leftFFVolts);
		rightController.setSetpoint(
			rightRadPerSec * (30 / Math.PI), ControlType.kVelocity, ClosedLoopSlot.kSlot0, rightFFVolts);
	}
}
