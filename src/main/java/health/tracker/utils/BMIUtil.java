package health.tracker.utils;

public class BMIUtil
{
    public static Double calculateBMI(Double height, Double weight)
    {
        return weight/(height*height);
    }

    public static String interpretationBMI(Double bmi)
    {
        if(bmi < 18.5)
            return "Underweight";
        else if(bmi <= 24.9)
            return "Correct weight";
        else if(bmi <=  29.9)
            return "Overweight";
        else
            return "Obesity";
    }

    public static Double calculateCalorieRequirement(Double height, Double weight, Double age, boolean isFemale)
    {
        if(isFemale)
            return 655 + 9.6*weight + 1.8*height - 1.7*age;
        else
            return 66 + 13.7*weight + 5*height - 6.67*age;
    }
}
