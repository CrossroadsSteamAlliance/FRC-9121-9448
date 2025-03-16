package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.DutyCycleOut;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor;
    private final MotionMagicVoltage motionMagic;

    public static final double rotationsPerInch = 20;
    
    // Elevator limits (Rotations)
    public final double MAX_HEIGHT = Constants.ElevatorConstants.kMaxElevatorExtension * rotationsPerInch;

    private double zero;
    private double setpoint;
    
    public ElevatorSubsystem() {
        elevatorMotor = new TalonFX(Constants.ElevatorConstants.kCANElevator);
        motionMagic = new MotionMagicVoltage(0);
        
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        
        //DONT CHANGE
        config.MotorOutput.Inverted = config.MotorOutput.Inverted.Clockwise_Positive;
        
        // Motion Magic PID tuning 
        config.Slot0.kP = 0.2;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.01;
        config.Slot0.kV = 0.1;
        
        // Motion Magic constraints
        config.MotionMagic.MotionMagicCruiseVelocity = 3000;
        config.MotionMagic.MotionMagicAcceleration = 500;

        
        elevatorMotor.getConfigurator().apply(config);
        zeroElevator();
    }
    
    // Command the elevator to a specific height (in inches) using Motion Magic.
    public void setHeight(double set) {
       setpoint = set * rotationsPerInch;
        
        elevatorMotor.setControl(motionMagic.withPosition(set * rotationsPerInch));
    }
    
    public double getHeight() {
        return elevatorMotor.getPosition().getValueAsDouble() / rotationsPerInch;
    }  
    
    public boolean isAtSetpoint() {
       return getHeight() == setpoint / rotationsPerInch ? true : false;
    }
    
    // Fully stop the motor.
    public void stop() {
        elevatorMotor.setControl(new DutyCycleOut(0));
    }

    public void zeroElevator(){
        elevatorMotor.setPosition(0);
    }

    public double getElevatorZero(){
        return zero;
    }
    
    @Override
    public void periodic() {
        double currentHeight = getHeight();
        SmartDashboard.putNumber("Elevator Height (in)", currentHeight);
        SmartDashboard.putNumber("Elevator Raw Encoder", elevatorMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Elevator Power", elevatorMotor.get());
        SmartDashboard.putNumber("Elevator Setpoint", setpoint);
        SmartDashboard.putBoolean("Elevator At Setpoint", isAtSetpoint());
    }
}
