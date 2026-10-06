# Kitbot 2026 — team 620

This cleanup preserves the directions, delays, and controls confirmed working on the robot.

## Open and deploy

Extract the ZIP, then open the inner project folder in WPILib VS Code. The folder you open must directly contain `build.gradle`, `src`, and `.wpilib`. Deploy using the WPILib command.

## Controls

Driver Station USB slot 0 is the driver: right trigger drives forward, left trigger reverses, and the right stick steers. Speed scaling is 0.7 and steering scaling is 0.8.

USB slot 1 is the operator: hold X (button 3) to shoot; hold Y (button 4) to intake. Release to stop the ball motors. There is no eject binding. Both commands require the same subsystem, so they cannot operate the ball motors simultaneously. If one interrupts the other, release and press the desired button again.

| Mode | Shooter, PWM 4 | Middle intake, PWM 5 | Ground intake, PWM 6 |
| --- | --- | --- | --- |
| Y: intake | Off | +12 V immediately | +12 V after 0.25 seconds |
| X: shoot | -10.6 V immediately | -9 V after 0.25 seconds | +12 V immediately |

The ground intake picks up balls and passes them to the middle intake and hopper. For shooting, the middle intake reverses to take balls out of the hopper; the ground intake keeps its original direction and passes them toward the shooter.

These are commanded default voltages. Actual available voltage depends on the battery. Existing SmartDashboard controls can override the middle-intake and shooter voltages. Values are read when the relevant motor starts, not continuously. Ground-intake voltage and delays are set in Constants.java.

## Where to edit

- `Constants.java`: PWM wiring, voltage signs and magnitudes, delays, controller slots, and autonomous timing.
- `RobotContainer.java`: X/Y bindings and default commands.
- `commands/Intake.java`: middle intake first, delayed ground intake, shooter off.
- `commands/Launch.java`: shooter and ground intake first, delayed middle intake.
- `subsystems/FuelSubsystem.java`: named methods for the three motors.
- `commands/Drive.java`: trigger and steering inputs.
- `subsystems/DrivetrainSubsystem.java`: existing drivetrain PWM mapping and inversions.
- `commands/ExampleAuto.java`: drive with input +0.5 for 0.25 seconds, then run Launch for 10 seconds, including its startup delay.

Drive PWM ports are front left 3, rear left 2, front right 1, rear right 0. The cleanup preserves the tested drive output logic.

## Notes for the next programmer

Motor signs describe electrical commands, not universal clockwise/counterclockwise directions. Preserve the tested signs unless the hardware changes. PWM Spark objects do not configure CAN current limits; obsolete current-limit constants have been removed. The unused SpinUp and LaunchSequence commands were removed because Launch already handles the current spin-up delay. The unused autonomous chooser was also removed; the robot continues to use ExampleAuto directly.

The shared fuel subsystem prevents intake and shooting from fighting over outputs. Each command stops all three motors before starting, resets its timer on every activation, and stops all three on release, interruption, or timeout. Timer checks do not block the driver controls.
