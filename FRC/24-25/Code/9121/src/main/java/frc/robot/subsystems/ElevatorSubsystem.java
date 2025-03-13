package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.signals.NeutralModeValue;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.LEDConstants;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor;
    private final MotionMagicVoltage motionMagic;

    private static final double STAGE_MULTIPLIER = 2.0;
    private static final double GEAR_RATIO = 27.0;
    private static final double MAX_HEIGHT = Constants.ElevatorConstants.kMaxElevatorExtension;
    private static final double MIN_HEIGHT = 0.0;
    private static final double TICKS_PER_INCH = (2048 * GEAR_RATIO) / STAGE_MULTIPLIER;

    public ElevatorSubsystem() {
        elevatorMotor = new TalonFX(Constants.ElevatorConstants.kCANElevator);
        motionMagic = new MotionMagicVoltage(null); // ✅ Fix: Pass the motor reference

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // ✅ Tune Motion Magic PID
        config.Slot0.kP = 0.2; // Increased P for better response
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.01; // Small D to dampen oscillations
        config.Slot0.kV = 0.1; 

        config.MotionMagic.MotionMagicCruiseVelocity = 18000; // Reduced for stability
        config.MotionMagic.MotionMagicAcceleration = 36000;

        // ✅ Soft Limits
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = MAX_HEIGHT * TICKS_PER_INCH;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = MIN_HEIGHT * TICKS_PER_INCH;

        elevatorMotor.getConfigurator().apply(config);

        // ✅ Encoder Zeroing (consider CANcoder or limit switch)
        elevatorMotor.setPosition(0);
    }

    public void setHeight(double heightInches) {
        double targetPos = heightInches * TICKS_PER_INCH;
        elevatorMotor.setControl(motionMagic.withPosition(targetPos));
    }

    public double getHeight() {
        return elevatorMotor.getPosition().getValue().magnitude() / TICKS_PER_INCH; // ✅ Fix: No radians
    }

    public void stop() {
        elevatorMotor.setControl(new DutyCycleOut(0)); // ✅ Fix: Proper stop command
    }

    public void resetEncoder() {
        elevatorMotor.setPosition(0);
    }

    public void setLEDstate(){
        
    }

    @Override
    public void periodic(){
        SetMotorDashboard();
    }

    private void SetMotorDashboard(){
        SmartDashboard.putBoolean("Elevator Running", elevatorMotor.isAlive());
        SmartDashboard.putNumber("Elevator Height", getHeight());
        SmartDashboard.putNumber("Elevator Raw Encoder", elevatorMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Elevator Power", elevatorMotor.get());
    }
}
