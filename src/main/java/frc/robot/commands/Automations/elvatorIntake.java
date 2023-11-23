// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Automations;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Automations.Elvator.setSetPoint;
import frc.robot.commands.Automations.Intake.runIntake;
import frc.robot.subsystems.Elvator.Elvator;
import frc.robot.subsystems.Intake.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class elvatorIntake extends SequentialCommandGroup {
  /** Creates a new elvatorIntake. */
  public double power;
  
  public void setIntakePower(Enum gamepice) {
    power = Intake.getInstance().getIntakePower(gamepice);
  }
  

  public elvatorIntake(Enum gamepice , Enum pose) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> setIntakePower(gamepice)),
      new setSetPoint(Elvator.getInstance().getHight(pose)),
      new runIntake(gamepice)
    );
  }
}
