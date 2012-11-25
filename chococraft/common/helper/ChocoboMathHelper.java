package chococraft.common.helper;

public class ChocoboMathHelper
{
    // numeric float clamp
    public static float clamp(float value, float min, float max)
    {
        return (value < min ? min : (value > max ? max : value));
    }
    
    // numeric double clamp
    public static double clamp(double value, double min, double max)
    {
        return (value < min ? min : (value > max ? max : value));
    }
    
    // numeric integer clamp
    public static int clamp(int value, int min, int max)
    {
        return (value < min ? min : (value > max ? max : value));
    }
}
