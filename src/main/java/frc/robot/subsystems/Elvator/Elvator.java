// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Elvator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController.ArbFFUnits;
import com.ma5951.utils.MAShuffleboard;
import com.ma5951.utils.MAShuffleboard.pidControllerGainSupplier;
import com.ma5951.utils.subsystem.DefaultControlSubsystemInSubsystemControl;
import com.ma5951.utils.subsystem.MotorSubsystem;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PortMap;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.Intake.GamePice;

public class Elvator extends SubsystemBase implements DefaultControlSubsystemInSubsystemControl{
  /** Creates a new Cubeshotter. */
  private CANSparkMax master;
  private CANSparkMax slave;
  private static Elvator instance;
  private SparkMaxPIDController pidController;
  private MAShuffleboard board;
  private double setPoint;
  private RelativeEncoder encoder;
  private double scoringPose;

  public enum Pose {
    Low ,
    Mid ,
    High ,
    Floor ,
    Shelf
  }

  public Elvator() {
    master = new CANSparkMax(PortMap.Elvator.MasterID, MotorType.kBrushless);
    slave = new CANSparkMax(PortMap.Elvator.SlaveID, MotorType.kBrushless);
    encoder = master.getEncoder();
    encoder.setPositionConversionFactor(
      ElvatorConstants.positionConversionFactor);
    encoder.setVelocityConversionFactor(
        ElvatorConstants.positionConversionFactor / 60);
    encoder.setPosition(0);

    master.setIdleMode(IdleMode.kBrake);
    slave.setIdleMode(IdleMode.kBrake);

    master.setInverted(false);
    slave.follow(master, true);

    pidController = master.getPIDController();
    board = new MAShuffleboard("Elvator");
    
    pidController.setFeedbackDevice(encoder);
    pidController.setP(ElvatorConstants.kP);
    pidController.setI(ElvatorConstants.kI);
    pidController.setD(ElvatorConstants.kD);

    
  }

  public double getHight(Enum pose) {
    if (pose == Pose.High) {
      if (Intake.getInstance().getGamePice() == GamePice.Cone) {
        return ElvatorConstants.highPoseCone;
      } else {
        return ElvatorConstants.highPoseCube;
      }
    } else if (pose == Pose.Mid) {
      if (Intake.getInstance().getGamePice() == GamePice.Cone) {
        return ElvatorConstants.ConeMidPose;
      } else {
        return ElvatorConstants.CubeMidPose;
      }
    } else if (pose == Pose.Low) {
      return ElvatorConstants.lowPose;
    } else if (pose == Pose.Shelf) {
      return ElvatorConstants.ShelfPose;
    }else {
      return ElvatorConstants.minPose;
    }
  }

  public void resetPose(double position) {
    encoder.setPosition(position);
  }
  
  public double getCurrent() {
    return master.getOutputCurrent();
  }

  public void setScoringPose(double pose) {
    scoringPose = pose;
  }

  public double getScroingPose(){
    return scoringPose;
  }

  public double getPose() {
    return encoder.getPosition();
  }

  public void calculate(double setPoint) {
    pidController.setReference(
      setPoint, CANSparkMax.ControlType.kPosition);
  }

  @Override
  public boolean atPoint() {
    return Math.abs(getPose() - setPoint) <= ElvatorConstants.Tolorance;
  }
  
  public void setSetPoint(double setPoint) {
    this.setPoint = setPoint;
  }

  
  public double getSetPoint() {
    return setPoint;
  }

  @Override
  public void setVoltage(double voltage) {
      master.set(voltage / 12);
  }

  @Override
  public boolean canMove() {
    return setPoint >= ElvatorConstants.minPose && setPoint <= ElvatorConstants.maxPose;
  }

  public static Elvator getInstance() {
    if (instance == null) {
      instance = new Elvator();
    }
    return instance;
  }

  @Override
  public void periodic() {
  }
}
