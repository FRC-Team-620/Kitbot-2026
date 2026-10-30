package frc.robot.subsystems.launcher;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public interface LauncherIO {
	@AutoLog
  class LauncherIOInputs {
    public Voltage voltage = Volts.of(0.0);
    public AngularVelocity velocity = RPM.of(0.0);
    public Angle position = Radians.of(0.0); 
    public Current current = Amp.of(0.0);
    public Temperature temp = Celsius.of(0.0);
  }

  default void updateInputs(LauncherIOInputs inputs) {}

  default void setVoltage(double volts) {}

  default void setVelocity(double rpm, double ffvolts) {}

  default void stop() {}

  default double getTargetVelocityRadPerS() { return 0.0; }
}
