// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.RobotConfig;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.ElevatorCommand;
import frc.robot.Commands.IntakeCommand;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Utils.ControllerUtils;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity


    //public OrchestraPlayer music;

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final ElevatorSubsystem elevator = new ElevatorSubsystem();

    private final IntakeSubsystem intake = new IntakeSubsystem();

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain(); 
    
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        //loadMusic();
        elevator.runOnce(() -> elevator.zeroElevator());
        
        configureBindings();

        getNamedCommands();

        autoChooser = AutoBuilder.buildAutoChooser();

        configureAutoChooser();

        SmartDashboard.putData(autoChooser);
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        joystick.povRight().onTrue(new ElevatorCommand(elevator, ElevatorConstants.kReef2));
        joystick.povUp().onTrue(new ElevatorCommand(elevator, ElevatorConstants.kReef1));
        joystick.povDown().onTrue(new ElevatorCommand(elevator, 0));


        joystick.x().onTrue(new IntakeCommand(intake, true, -1)).onFalse(new RunCommand(() -> intake.stop(), intake));
        joystick.b().onTrue(new IntakeCommand(intake, true, 1)).onFalse(new RunCommand(() -> intake.stop(), intake));;

        // reset the field-centric heading on left bumper press
        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }
      
    private Command blueMobilityAuton(){
        return drivetrain.applyRequest(()-> drive.withVelocityX(0.6 * MaxSpeed)).withTimeout(3.5)
        .andThen(drivetrain.applyRequest(()-> drive.withVelocityX(0)));
    }

    private Command redMobilityAuton(){
        return drivetrain.applyRequest(()-> drive.withVelocityX(-0.6 * MaxSpeed)).withTimeout(3.5)
        .andThen(drivetrain.applyRequest(()-> drive.withVelocityX(0)));
    }


    private void configureAutoChooser() {
        autoChooser.addOption("Blue Mobility", blueMobilityAuton());
        autoChooser.addOption("Red Mobility", redMobilityAuton());
    }

    private void getNamedCommands(){
        NamedCommands.registerCommand("CoralL2", new ElevatorCommand(elevator, ElevatorConstants.kReef3));
        NamedCommands.registerCommand("Outtake", new IntakeCommand(intake, true, -1));
        NamedCommands.registerCommand("CoralL0", new ElevatorCommand(elevator, ElevatorConstants.kReef3));
        NamedCommands.registerCommand("StopIntake", new IntakeCommand(intake, false, 0));
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
      }



   


       /*private void loadMusic(){
        List<TalonFX> m = List.of(
        new TalonFX(2),
        new TalonFX(40),
        new TalonFX(41),
        new TalonFX(43),
        new TalonFX(45),
        new TalonFX(46),
        new TalonFX(47),
        new TalonFX(49));

        music = new OrchestraPlayer(m, "output.chrp");

        music.playSong();
    }*/
}
