package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.opencv.features2d.Feature2D;

import com.pathplanner.lib.auto.NamedCommands;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor1;
    private final SparkMax intakeMotor2;
    private final SimpleMotorFeedforward feedforward;

    // Intake speed constants
    private final double HOLD_SPEED = 0;    // Reduced power to hold game piece
    private final double OUTTAKE_SPEED = 1.0; // Full power for outtaking

    private final double motorKV = 0.15;
    private final double motorKS = 0.04;

    public IntakeSubsystem() {
        intakeMotor1 = new SparkMax(IntakeConstants.kCANIntakeTop, MotorType.kBrushless);
        intakeMotor2 = new SparkMax(IntakeConstants.kCANIntakeBottom, MotorType.kBrushless);

        feedforward = new SimpleMotorFeedforward(motorKS, motorKV);

        intakeMotor2.isFollower();
        intakeMotor2.setInverted(true);
    }

    // Run intake at full speed
    public void intake(double INTAKE_SPEED) {
        intakeMotor1.set(feedforward.calculate(INTAKE_SPEED));
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

    @Override
    public void periodic(){
        SetMotorDashboard();
    }

    private void SetMotorDashboard(){
        SmartDashboard.putBoolean("Intake Running", (intakeMotor1.get()>0.1 || intakeMotor1.get()<0.1) ? true : false);
        SmartDashboard.putNumber("Intake Power", intakeMotor1.get());
    }

    private void SetNamedCommands(){
        
    }
}
