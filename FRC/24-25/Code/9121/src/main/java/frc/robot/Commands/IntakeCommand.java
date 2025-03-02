package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private final IntakeSubsystem intake;
    private final boolean isIntaking;

    public IntakeCommand(IntakeSubsystem intake, boolean isIntaking) {
        this.intake = intake;
        this.isIntaking = isIntaking;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        if (isIntaking) {
            intake.intake();  // Start intake
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
