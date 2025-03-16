package frc.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.opencv.features2d.Feature2D;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor1;
    private final SparkMax intakeMotor2;
    private final SimpleMotorFeedforward feedforward;

    // Intake speed constants
    private final double INTAKE_SPEED = 0.8;
    private final double HOLD_SPEED = 0;    // Reduced power to hold game piece
    private final double OUTTAKE_SPEED = 0.8; // Full power for outtaking

    private final double motorKV = 0.15;
    private final double motorKS = 0.04;

    public IntakeSubsystem() {
        intakeMotor1 = new SparkMax(IntakeConstants.kCANIntakeTop, MotorType.kBrushless);
        intakeMotor2 = new SparkMax(IntakeConstants.kCANIntakeBottom, MotorType.kBrushless);

        feedforward = new SimpleMotorFeedforward(motorKS, motorKV);

    }

    // Run intake at full speed
    public void intake() {
        intakeMotor1.set(-INTAKE_SPEED);
        intakeMotor2.set(INTAKE_SPEED);
    }

    // Hold game piece with reduced power
    public void hold() {
        intakeMotor1.set(HOLD_SPEED);
        intakeMotor2.set(HOLD_SPEED);
    }

    // Reverse motors to outtake
    public void outtake() {
        intakeMotor1.set(OUTTAKE_SPEED);
        intakeMotor2.set(-OUTTAKE_SPEED);
    }

    // Stop intake motors
    public void stop() {
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }

    @Override
    public void periodic(){
        SetMotorDashboard();
    }

    public double getPower(){
        return intakeMotor1.getBusVoltage();
    }

    private void SetMotorDashboard(){
        SmartDashboard.putBoolean("Intake Running", getPower() > 0 || getPower() < 0 ? true : false);
        SmartDashboard.putNumber("Intake Power", getPower());
    }
}
