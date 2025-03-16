package frc.robot;

import edu.wpi.first.units.measure.AngularAcceleration;

public class Constants {
    
    public static class ElevatorConstants{

        public static final int kCANElevator = 52;

        public static final int kMaxElevatorExtension = 32;
        public static final int kMinElevatorExtension = 0;

        public static final double kReef1 = 5;
        public static final double kReef2 =8;
        public static final double kReef3 = 16;  
        public static final double kStation= 3.85;
    }

    public static class IntakeConstants{

        public static final int kCANIntakeTop = 30;
        public static final int kCANIntakeBottom = 31;
    }

    public static class LEDConstants{
        public static final int kCANDle = 64;
    }
}
