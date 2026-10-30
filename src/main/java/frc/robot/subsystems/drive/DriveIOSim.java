package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.drive.DriveConstants.*;

public class DriveIOSim implements DriveIO {
	private DifferentialDrivetrainSim sim = 
		DifferentialDrivetrainSim.createKitbotSim(
			KitbotMotor.kDualCIMPerSide, KitbotGearing.k10p71, 
			KitbotWheelSize.kSixInch, null
		);

	private PIDController leftController = new PIDController(KP, KI, KD);
  private PIDController rightController = new PIDController(KP, KI, KD);

	private double leftAppliedVolts = 0.0;
	private double rightAppliedVolts = 0.0;
	
	public DriveIOSim() {}

	@Override
	public void updateInputs(DriveIOInputs inputs) {
		sim.update(.02);
		sim.setInputs(leftAppliedVolts, rightAppliedVolts);

		inputs.leftPosition = Radians.of(sim.getLeftPositionMeters() / WHEEL_RADIUS_METERS);
		inputs.leftVelocity = RPM.of(sim.getLeftVelocityMetersPerSecond() / WHEEL_RADIUS_METERS);
		inputs.leftVoltage = Volts.of(leftAppliedVolts);
		inputs.leftLeaderCurrent = Amps.of(sim.getLeftCurrentDrawAmps());
		inputs.leftFollowerCurrent = Amps.of(sim.getLeftCurrentDrawAmps());

		inputs.rightPosition = Radians.of(sim.getRightPositionMeters() / WHEEL_RADIUS_METERS);
		inputs.rightVelocity = RPM.of(sim.getRightVelocityMetersPerSecond() / WHEEL_RADIUS_METERS);
		inputs.rightVoltage = Volts.of(rightAppliedVolts);
		inputs.rightLeaderCurrent = Amps.of(sim.getRightCurrentDrawAmps());
		inputs.rightFollowerCurrent = Amps.of(sim.getRightCurrentDrawAmps());
	}

	@Override
	public void setVoltage(double leftVolts, double rightVolts) {
		this.leftAppliedVolts = MathUtil.clamp(leftVolts, -12.0, 12.0);
		this.rightAppliedVolts = MathUtil.clamp(rightVolts, -12.0, 12.0);
	}

	@Override
	public void setVelocity(
		double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts
	) {
		setVoltage(
			leftController.calculate(
				sim.getLeftVelocityMetersPerSecond() / WHEEL_RADIUS_METERS, leftRadPerSec) + leftFFVolts,
			rightController.calculate(
				sim.getRightVelocityMetersPerSecond() / WHEEL_RADIUS_METERS, rightRadPerSec) + rightFFVolts
		);
	}
}
