// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations.Intake;

import com.ma5951.utils.commands.MotorCommand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeConstants;
import frc.robot.subsystems.Intake.Intake.GamePice;

public class ejectAutomation extends CommandBase {
  /** Creates a new ejectAutomation. */
  private Enum gamePice;
  private double power;

  public ejectAutomation(Enum gamePice) {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (gamePice == GamePice.Cone) {
      power = IntakeConstants.EjectPowerForCone;
    } else {
      power = IntakeConstants.EjectPowerForCube;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    new MotorCommand(Intake.getInstance(), power, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.getInstance().setGamePice(GamePice.None);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
