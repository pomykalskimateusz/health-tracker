package health.tracker.utils;

public class BMIUtil
{
    /**
     * @param height - Double param, which determine user height
     * @param weight - Double param, which determine user weight
     * @return Double - calculated BMI rate depending on height and weight
     */
    public static Double calculateBMI(Double height, Double weight)
    {
        return weight/((height/100)*(height/100));
    }

    /**
     * @param bmi - Double param, which determine users bmi rate
     * @return String - mapped bmi rate value to user friendly information
     */
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

    /**
     * @param height - Double param, which determine user height
     * @param weight - Double param, which determine user weight
     * @param age - Double param, which determine user age
     * @param isFemale - boolean param, which determine gender
     * @return Double - calculated value which determine calorie requirement for specific user
     */
    public static Double calculateCalorieRequirement(Double height, Double weight, Double age, boolean isFemale)
    {
        if(isFemale)
            return 655 + 9.6*weight + 1.8*height - 1.7*age;
        else
            return 66 + 13.7*weight + 5*height - 6.67*age;
    }
}
