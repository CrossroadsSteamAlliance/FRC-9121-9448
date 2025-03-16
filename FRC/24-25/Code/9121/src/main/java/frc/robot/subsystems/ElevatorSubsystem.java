package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor;
    private final MotionMagicVoltage motionMagic;
    
    // Elevator limits (inches)
    public final double MAX_HEIGHT = Constants.ElevatorConstants.kMaxElevatorExtension;
    public final double MIN_HEIGHT = 0.0;

    public final double gearRatio = 75;

    private double zero = 0.0;
    
    public ElevatorSubsystem() {
        elevatorMotor = new TalonFX(Constants.ElevatorConstants.kCANElevator);
        motionMagic = new MotionMagicVoltage(0);
        
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = config.MotorOutput.Inverted.Clockwise_Positive;
        
        // Motion Magic PID tuning 
        config.Slot0.kP = 0.2;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.01;
        config.Slot0.kV = 0.1;
        
        // Motion Magic constraints
        config.MotionMagic.MotionMagicCruiseVelocity = 1000;
        config.MotionMagic.MotionMagicAcceleration = 500;
        
        // Soft limits to prevent overtravel
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = MIN_HEIGHT;
        
        elevatorMotor.getConfigurator().apply(config);
        elevatorMotor.setPosition(0); // Zero encoder at startup
        zero = elevatorMotor.getPosition().getValueAsDouble();
    }
    
    // Command the elevator to a specific height (in inches) using Motion Magic.
    public void setHeight(double set) {
       elevatorMotor.setControl(motionMagic.withPosition());
    }
    
    public double getHeight() {
        return elevatorMotor.getPosition().getValueAsDouble();
    }  
    
    public boolean isAtSetpoint() {
       return getHeight() >  ? true : false;
    }
    
    // Fully stop the motor.
    public void stop() {
        elevatorMotor.setControl(new DutyCycleOut(0));
    }

    public void zeroElevator(){
        elevatorMotor.setPosition(0);
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
