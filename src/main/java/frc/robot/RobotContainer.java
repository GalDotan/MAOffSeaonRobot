// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Automations.elvatorIntake;
import frc.robot.commands.Automations.Elvator.resetPose;
import frc.robot.commands.Automations.Elvator.setScoringPose;
import frc.robot.commands.Automations.Elvator.setSetPoint;
import frc.robot.commands.Automations.Intake.ejectAutomation;
import frc.robot.commands.Automations.Intake.runIntake;
import frc.robot.subsystems.Elvator.Elvator;
import frc.robot.subsystems.Elvator.ElvatorConstants;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.Intake.GamePice;
import frc.robot.subsystems.Elvator.Elvator.Pose;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public static CommandPS4Controller ps4Controller;

  public RobotContainer() {
    // Configure the trigger bindings
    ps4Controller = new CommandPS4Controller(0);
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    ps4Controller.povUp().onTrue(new setScoringPose(Pose.High));
    ps4Controller.povRight().onTrue(new setScoringPose(Pose.Mid));
    ps4Controller.povDown().onTrue(new setScoringPose(Pose.Low));

    ps4Controller.circle().whileTrue(new setSetPoint(Elvator.getInstance().getScroingPose()));
    ps4Controller.triangle().whileTrue(new ejectAutomation(Intake.getInstance().getGamePice()))
    .whileFalse(new setSetPoint(ElvatorConstants.minPose));
    ps4Controller.cross().whileTrue(new elvatorIntake(GamePice.Cone, Pose.Shelf));

    ps4Controller.L1().whileTrue(new runIntake(GamePice.Cube));
    ps4Controller.L1().whileTrue(new runIntake(GamePice.Cone));

    ps4Controller.square().whileTrue(new resetPose());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomousgi
    return null;
  }
}
