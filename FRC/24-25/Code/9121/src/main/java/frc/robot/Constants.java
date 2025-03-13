package frc.robot;

import edu.wpi.first.units.measure.AngularAcceleration;

public class Constants {
    
    public static class ElevatorConstants{

        public static final int kCANElevator = 52;

        public static final double kMaxElevatorVelocity = 5;
        public static final int kMaxElevatorExtension = 32;
        public static final double kMaxCurrentLimit = 40;
        public static final int kELEVATORACCELERATION = 160;
        public static final int kCRUISEVELOCITY = 80;
        public static final int kELEVATORJERK = 1600;

        public static final int kReef1 = 0;
        public static final int kReef2 = 15;
        public static final int kReef3 = 25;
        public static final int kReef4 = 30;
        public static final int kCoralStation = 5;
        public static final int kAlgae1 = 20;
        public static final int kAlgae2 = 27;
        public static final int kProcessor = 0;
    }

    public static class IntakeConstants{

        public static final int kCANIntakeTop = 30;
        public static final int kCANIntakeBottom = 31;

        public static final double kMaxVelocity = 4000;
    }

    public static class LEDConstants{
        public static final int kCANDle = 64;
    }
}
