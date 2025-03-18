package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final boolean isIntaking;
    private final double intakePower;

    public IntakeCommand(IntakeSubsystem intake, boolean isIntaking, double dec) {
        this.intake = intake;
        this.isIntaking = isIntaking;
        this.intakePower = dec;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        if(isIntaking && intakePower == 0.5){
            intake.intake();
        }else if(isIntaking && intakePower == 1){
            intake.intakeFull();
        } else {
            intake.outtake(); // Start outtake
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.hold(); // Switch to hold mode instead of stopping
    }

    @Override
    public boolean isFinished() {
        return false; // Runs until button is released
    }
}
