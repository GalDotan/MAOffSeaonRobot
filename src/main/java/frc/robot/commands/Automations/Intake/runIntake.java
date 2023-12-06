// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations.Intake;

import com.ma5951.utils.commands.MotorCommand;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeConstants;
import frc.robot.subsystems.Intake.Intake.GamePice;

public class runIntake extends CommandBase {
  /** Creates a new runIntake. */
  private Enum gamePice;
  private double power;
  private MotorCommand command;

  public runIntake(Enum gampice) {
    // Use addRequirements() here to declare subsystem dependencies.
    gamePice = gampice;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (gamePice == GamePice.Cone) {
      power = IntakeConstants.IntakePowerForCone;
    } else {
      power = IntakeConstants.IntakePowerForCube;
    }
    command = new MotorCommand(Intake.getInstance(), power, 0);
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
    command.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Intake.getInstance().getGamePice() == GamePice.Cube || 
    Intake.getInstance().getGamePice() == GamePice.Cone || 
    command.isFinished();
  }
}
