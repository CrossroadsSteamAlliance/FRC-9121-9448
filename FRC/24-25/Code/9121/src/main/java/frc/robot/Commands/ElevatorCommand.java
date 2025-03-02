package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.Joystick;

public class ElevatorCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final Joystick controller;
    private final double setpoint;

    public ElevatorCommand(ElevatorSubsystem elevator, Joystick controller) {
        this.elevator = elevator;
        this.controller = controller;
        this.setpoint = -1; // No preset, joystick control
        addRequirements(elevator);
    }

    public ElevatorCommand(ElevatorSubsystem elevator, double setpoint) {
        this.elevator = elevator;
        this.setpoint = setpoint;
        this.controller = null; // Preset height
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        if (controller != null) {
            double speed = -controller.getRawAxis(1); // Joystick up/down
            elevator.setHeight(elevator.getHeight() + speed * 0.5); // Adjust increment size
        } else {
            elevator.setHeight(setpoint);
        }
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }

    @Override
    public boolean isFinished() {
        return controller == null && Math.abs(elevator.getHeight() - setpoint) < 0.5;
    }
}
