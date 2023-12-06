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
  private GamePice gamePice;
  //private Timer time;
  private boolean timerOn;
  private String gamepic;
  private double currentTime;
  private double Lastcheck;

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
    intakeMotor.setInverted(false);

    gamePice = GamePice.None;
      
    currentTime = Timer.getFPGATimestamp();

    timerOn = false;
  }

  public double getIntakePower(GamePice gamepice) {
    if (gamepice == GamePice.Cone) {
      return IntakeConstants.IntakePowerForCone;
    } else {
      return IntakeConstants.IntakePowerForCube;
    }
  }

  public double getEjectPower(GamePice gamepice) {
    if (gamepice == GamePice.Cone) {
      return IntakeConstants.EjectPowerForCone;
    }
    return IntakeConstants.EjectPowerForCube;
  }


  public boolean getSensor() {
    return !sensor.get();
  }

  public double getCurrent() {
    return intakeMotor.getOutputCurrent();
  }

  public void setGamePice(GamePice gamepice) {
    gamePice = gamepice;
  }

  public GamePice getGamePice() {
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

    currentTime = Timer.getFPGATimestamp();
    
    if (getCurrent() > IntakeConstants.currentAmpThreshold && !timerOn) {
      Lastcheck = Timer.getFPGATimestamp();
      timerOn = true;
    }

    if (getCurrent() > IntakeConstants.currentAmpThreshold && 
      currentTime >= Lastcheck + IntakeConstants.currentTimer
      && timerOn) {
      setGamePice(GamePice.Cone);
      timerOn = false;
    }



    if (getSensor()) {
      setGamePice(GamePice.Cube);
    }

    

    if (getGamePice() == GamePice.Cone) {
      gamepic = "cone";
    } else if (getGamePice() == GamePice.Cube) {
      gamepic = "cube";
    } else {
      gamepic = "None";
    }
    
    board.addString("GamePice", gamepic);
    board.addNum("GetCurrent", getCurrent());
  }
}
