package frc.robot.subsystems.hopper;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.*;
import static edu.wpi.first.units.Units.*;

public interface HopperIO {
  @AutoLog
  class HopperIOInputs {
    public Voltage voltage = Volts.of(0.0);
    public AngularVelocity velocity = RPM.of(0.0);
    public Angle position = Rotations.of(0.0); 
    public Current current = Amp.of(0.0);
    public Temperature temp = Celsius.of(0.0);
  }

  default void updateInputs(HopperIOInputs inputs) {}

  default void setVoltage(double volts) {}

  default void stop() {}
}
