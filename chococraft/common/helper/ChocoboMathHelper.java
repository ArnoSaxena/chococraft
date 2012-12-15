package chococraft.common.helper;

public class ChocoboMathHelper
{
	// *** clamp ***
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
    
    // *** minimum limit ***
    // numeric float minimum limit
    public static float minLimit(float value, float min)
    {
    	return value < min ? min : value;
    }
    
    // numeric double minimum limit
    public static double minLimit(double value, double min)
    {
    	return value < min ? min : value;
    }
    
    // numeric double minimum limit
    public static int minLimit(int value, int min)
    {
    	return value < min ? min : value;
    }
    
    // *** maximum limit ***
    // numeric float maximum limit
    public static float maxLimit(float value, float max)
    {
    	return value > max ? max : value;
    }
    
    // numeric double maximum limit
    public static double maxLimit(double value, double max)
    {
    	return value > max ? max : value;
    }
    
    // numeric double maximum limit
    public static int maxLimit(int value, int max)
    {
    	return value > max ? max : value;
    }
}
