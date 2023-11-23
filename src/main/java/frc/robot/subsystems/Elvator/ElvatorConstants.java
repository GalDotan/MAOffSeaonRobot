package frc.robot.subsystems.Elvator;



public class ElvatorConstants {

    public static final double Tolorance = 0.03;
    public static final double Pully_radios = 0.021;
    public static final double Pully_Circumference = Pully_radios * 2 * Math.PI;
    public static final double Gear_Ratio = 70d / 9d;
    public static final double TicksPerRotation = 0;
    public static final double offset = 0;
    public static final double maxPose = 1.61 + offset;
    public static final double minPose = offset;
    public static final double positionConversionFactor = ((2 * Math.PI * Pully_radios)
            / Gear_Ratio) * 1.11111111111111111111;


    public static final double kP = 3.3;
    public static final double kI = 0;
    public static final double kD = 6;

    public static final double lowerPower = -0.3;
    public static final double currentAmpThreshold = 20;
   
   
    public static final double ShelfPose = 1.36 + offset ;
    
    public static final double lowPose = 0.21 + offset;
    public static final double highPoseCone = 1.50 + offset;
    public static final double highPoseCube = 1.56 + offset;
    public static final double ConeMidPose = 0.99 + offset;
    public static final double CubeMidPose = 1.07 + offset;



}
