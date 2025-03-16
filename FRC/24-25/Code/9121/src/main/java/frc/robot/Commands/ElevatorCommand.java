package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final double setpoint;
    
    public ElevatorCommand(ElevatorSubsystem elevator, double setpoint) {
        this.elevator = elevator;
        this.setpoint = setpoint;
        addRequirements(elevator);
    }
    
    @Override
    public void initialize() {
        elevator.setHeight(setpoint);
    }
    
    @Override
    public boolean isFinished() {
        return elevator.isAtSetpoint();
    }
    
    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }
}
