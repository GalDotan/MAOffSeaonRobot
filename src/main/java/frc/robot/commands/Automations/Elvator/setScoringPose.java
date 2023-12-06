// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations.Elvator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Elvator.Elvator;
import frc.robot.subsystems.Elvator.ElvatorConstants;
import frc.robot.subsystems.Elvator.Elvator.Pose;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeConstants;
import frc.robot.subsystems.Intake.Intake.GamePice;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class setScoringPose extends SequentialCommandGroup {
  /** Creates a new setScoringPose. */
  

  public setScoringPose(Pose pose) {
    addCommands(
      new InstantCommand(() -> Elvator.getInstance().setScoringPose(Elvator.getInstance().getHightfromPose(pose)))
    );
  }
}
