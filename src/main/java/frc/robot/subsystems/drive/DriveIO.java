package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public interface DriveIO {
  @AutoLog
	public static class DriveIOInputs {
    public Angle leftPosition = Radians.of(0.0);
    public AngularVelocity leftVelocity = RPM.of(0.0);
    public Voltage leftVoltage = Volts.of(0.0);
    public Current leftLeaderCurrent =  Amps.of(0.0);
    public Current leftFollowerCurrent =  Amps.of(0.0);

    public Angle rightPosition = Radians.of(0.0);
    public AngularVelocity rightVelocity = RPM.of(0.0);
    public Voltage rightVoltage = Volts.of(0.0);
    public Current rightLeaderCurrent =  Amps.of(0.0);
    public Current rightFollowerCurrent =  Amps.of(0.0);
	}

	/** Updates the set of loggable inputs. */
  public default void updateInputs(DriveIOInputs inputs) {}

  /** Run open loop at the specified voltage. */
  public default void setVoltage(double leftVolts, double rightVolts) {}

  /** Run closed loop at the specified velocity. */
  public default void setVelocity(
      double leftRadPerSec, double rightRadPerSec, double leftFFVolts, double rightFFVolts) {}
}
