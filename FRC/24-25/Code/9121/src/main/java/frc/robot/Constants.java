package frc.robot;

import edu.wpi.first.units.measure.AngularAcceleration;

public class Constants {
    
    public static class ElevatorConstants{

        public static final int kCANElevator = 52;

        public static final int kMaxElevatorExtension = 32;
        public static final int kMinElevatorExtension = 0;

        public static final int kReef1 = 10;
        public static final int kReef2 = 25;
        public static final int kReef3 = 32;
    }

    public static class IntakeConstants{

        public static final int kCANIntakeTop = 30;
        public static final int kCANIntakeBottom = 31;

        public static final double kMaxSpeed = 0.8;
    }

    public static class LEDConstants{
        public static final int kCANDle = 64;
    }
}
