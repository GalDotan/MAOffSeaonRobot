// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations.Elvator;

import com.ma5951.utils.commands.MotorCommand;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elvator.Elvator;
import frc.robot.subsystems.Elvator.ElvatorConstants;

public class resetPose extends CommandBase {
  /** Creates a new resetPose. */
  private Command command;
  public resetPose() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    command =new MotorCommand(Elvator.getInstance(), ElvatorConstants.lowerPower, 0);
    command.initialize();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    command.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Elvator.getInstance().resetPose(0);
    command.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Elvator.getInstance().getCurrent() > ElvatorConstants.currentAmpThreshold || command.isFinished();
  }
}
