// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ma5951.utils.MAShuffleboard;
import com.ma5951.utils.subsystem.MotorSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PortMap;

public class Intake extends SubsystemBase implements MotorSubsystem{
  private static Intake intake;
  private MAShuffleboard board;
  private CANSparkMax intakeMotor;
  private DigitalInput sensor;
  private Enum gamePice;
  private Timer time;
  private boolean timerOn;


  public enum GamePice {
    Cone ,
    Cube , 
    None
  }

  public Intake() {
    intakeMotor = new CANSparkMax(PortMap.Intake.IntakeID, MotorType.kBrushless);
    sensor = new DigitalInput(PortMap.Intake.sensorID);
    board = new MAShuffleboard("Intake");
    
    
    intakeMotor.setIdleMode(IdleMode.kBrake);
      
  }

  public double getIntakePower(Enum gamepice) {
    if (gamepice == GamePice.Cone) {
      return IntakeConstants.IntakePowerForCone;
    } else {
      return IntakeConstants.IntakePowerForCube;
    }
  }

  public boolean getSensor() {
    return sensor.get();
  }

  public double getCurrent() {
    return intakeMotor.getOutputCurrent();
  }

  public void setGamePice(Enum gamepice) {
    gamePice = gamepice;
  }

  public Enum getGamePice() {
    return gamePice;
  }

  public boolean isPieceInIntake() {
    return getGamePice() == GamePice.Cone || getGamePice() == GamePice.Cube;
  }

  @Override
    public boolean canMove() {
        return true;
  }

  @Override
  public void setVoltage(double voltage) {
      intakeMotor.set(voltage / 12);
  }

  public static Intake getInstance() {
    if (intake == null) {
        intake = new Intake();  
    }
    return intake;
  }

  @Override
  public void periodic() {
    
    if ( getCurrent() > IntakeConstants.currentAmpThreshold && !timerOn) {
      time.reset();
      timerOn = true;
    }
  
    if (getCurrent() > IntakeConstants.currentAmpThreshold && time.hasElapsed(IntakeConstants.currentTimer)) {
      setGamePice(GamePice.Cone);
    }

    if (getSensor()) {
      setGamePice(GamePice.Cube);
    }

  }
}
