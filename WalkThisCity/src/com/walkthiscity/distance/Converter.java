package com.walkthiscity.distance;

/**
 * Created by travispomeroy on 4/26/14.
 */
public class Converter
{
    public static String convertTime(int time)
    {
        StringBuilder string = new StringBuilder();

        int hours = time / 60;
        int minutes = time % 60;
        if(hours > 0)
        {
            string.append(hours);
            string.append(" hours ");
        }
        string.append(minutes);
        string.append(" mins");
        return string.toString();
    }
}
