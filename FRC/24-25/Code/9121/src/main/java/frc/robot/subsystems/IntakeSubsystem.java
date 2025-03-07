package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor1;
    private final SparkMax intakeMotor2;

    // Intake speed constants
    private static final double INTAKE_SPEED = 0.7;  // Full power for intake
    private static final double HOLD_SPEED = 0;    // Reduced power to hold game piece
    private static final double OUTTAKE_SPEED = -1.0; // Full power for outtaking

    public IntakeSubsystem(int motor1ID, int motor2ID) {
        intakeMotor1 = new SparkMax(motor1ID, MotorType.kBrushless);
        intakeMotor2 = new SparkMax(motor2ID, MotorType.kBrushless);

        intakeMotor2.isFollower();
        intakeMotor2.setInverted(true);
    }

    // Run intake at full speed
    public void intake() {
        intakeMotor1.set(INTAKE_SPEED);
    }

    // Hold game piece with reduced power
    public void hold() {
        intakeMotor1.set(HOLD_SPEED);
    }

    // Reverse motors to outtake
    public void outtake() {
        intakeMotor1.set(OUTTAKE_SPEED);
    }

    // Stop intake motors
    public void stop() {
        intakeMotor1.set(0);
    }
}
