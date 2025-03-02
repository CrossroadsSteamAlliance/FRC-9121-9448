package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.signals.NeutralModeValue;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    private final TalonFX elevatorMotor;
    private final MotionMagicVoltage motionMagic;

    // Constants (Adjust for your elevator)
    private static final double STAGE_MULTIPLIER = 2.0; // Cascade ratio (2:1, 3:1, etc.)
    private static final double GEAR_RATIO = 27.0; // Motor-to-stage gear ratio
    private static final double MAX_HEIGHT = Constants.ElevatorConstants.kMaxElevatorExtension; // Inches
    private static final double MIN_HEIGHT = 0.0;
    private static final double TICKS_PER_INCH = (2048 * GEAR_RATIO) / STAGE_MULTIPLIER; 

    public ElevatorSubsystem(int motorID) {
        elevatorMotor = new TalonFX(motorID);
        motionMagic = new MotionMagicVoltage(0);

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // PID & Motion Magic Settings (Tune as needed)
        config.Slot0.kP = 0.1;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;
        config.Slot0.kV = 0.12;

        config.MotionMagic.MotionMagicCruiseVelocity = 20000; // Tune for speed
        config.MotionMagic.MotionMagicAcceleration = 40000; // Tune for acceleration

        // Soft limits
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = MAX_HEIGHT * TICKS_PER_INCH;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = MIN_HEIGHT * TICKS_PER_INCH;

        elevatorMotor.getConfigurator().apply(config);
        elevatorMotor.setPosition(0); // Zero encoder at startup
    }

    public void setHeight(double heightInches) {
        double targetPos = heightInches * TICKS_PER_INCH;
        elevatorMotor.setControl(motionMagic.withPosition(targetPos));
    }

    public double getHeight() {
        return elevatorMotor.getPosition().getValue().in(Radians) / TICKS_PER_INCH;
    }

    public void stop() {
        elevatorMotor.set(0);
    }

    public void resetEncoder() {
        elevatorMotor.setPosition(0);
    }
}
