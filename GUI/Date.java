/**
 * /**
 * Homework 3 William Sayre , wjs9ej Sources : Big Java Book, Stack Overflow (Cited where used)
 */

import java.util.HashSet;
public class Date {
    /**
     * Stores Argument date which was passed in
     */ 
    private String stringDate;
    /**
     * Months which have 31 days
     */ 
    private static HashSet<String> thirtyOneMonths = new HashSet<>();
    /**
     * Months which have 30 days
     */ 
    private static HashSet<String> thirtyMonths = new HashSet<>();
    /**
     * If stringDate is valid date, converts year into integer
     */ 
    public int year;
    /**
     * If stringDate is valid date, converts day into integer
     */ 
    public int day;
    /**
     * If stringDate is valid date, converts month into integer
     */ 
    public int month;
    /**
     * If stringDate is valid date, creates integer which allows for comparison of dates
     */ 
    public int intDate;

    /**
     * Date object Constructor, takes in a string date, tests whether its valid. 
     * If valid, generates int forms of the year, day month and total date, and
     * stores them in Class fields.
     */
    public Date(String dateTaken) {
        this.stringDate = dateTaken;
        thirtyOneMonths.add("01"); 
        thirtyOneMonths.add("03");
        thirtyOneMonths.add("05");
        thirtyOneMonths.add("07"); //fills up set of months with 31 days
        thirtyOneMonths.add("08");
        thirtyOneMonths.add("10");
        thirtyOneMonths.add("12");
        thirtyMonths.add("04");
        thirtyMonths.add("06");
        thirtyMonths.add("09");
        thirtyMonths.add("11"); //fills set of months with 30 days
        if (this.isValidDate()) {//tests validity of date
            int dateLength = dateTaken.length();
            String month = dateTaken.substring(5, 7);
            String year = dateTaken.substring(0, 4);
            String day = dateTaken.substring(dateLength - 2);//creates substrings containing month, year and date components of date
            int intMonth = Integer.valueOf(month); 
            int intYr = Integer.valueOf(year);
            int intDay = Integer.valueOf(day);//converts year, month and date to ints
            this.year = intYr;
            this.day = intDay;
            this.month = intMonth; //initializes year, day and month fields 
            this.intDate = (intYr * 1000) + (intMonth * 100) + (intDay); //creates integer value for date which will allow any date range to be compared. Multiplies Yr by 1000, and month by 100 to days/months from skewing date range calculations. Without this, 1 day is worth same amount of time as 1 year   
        }
        else {
            this.year = 0;
            this.day = 0;
            this.month = 0; //if validity test fails sets fields to 0
            this.intDate = 0; 
        }



    }

    /**
     * "Getter" which returns the int date 
     * @return int date
     */

    public int getIntDate() {
        return this.intDate;

    }
    /**
     * "Getter" which returns the String date 
     * @return String date
     */

    public String getStringDate() {
        return this.stringDate;

    }
    /**
     * "Getter" which returns the int form of day 
     * @return day
     */

    public int getDay() {
        return this.day;

    }
    /**
     * "Getter" which returns the int form of year 
     * @return year
     */   

    public int getYear() {
        return this.year;

    }

    /**
     * "Getter" which returns the int form of month 
     * @return month
     */ 
    public int getMonth() {
        return this.month;

    }

    /**
     * Helper function which tests whether a String is 
     * eligible to be converted into an int. Got idea from 
     * Stack overflow (https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java)
     * @return true if s can be converted into int, falase otherwise
     */ 
    public static boolean intEligible(String s){ 
        try
        {
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException nfe) {
            return false;
        }



    }
    /**
     * This function tests whether this Date's stringDate is in valid date form
     * First tests whether stringDate is the correct length, and whether it has
     * - marks in correct places. Then slices substrings which should contain
     * month, day and year if date is valid. Uses intEligible helper method to test 
     * whether these substrings are eligible to be converted into ints. If they are,
     * converts them into ints. Tests whether string year is leap year (Source of Algorithm https://www.mathsisfun.com/leap-years.html),
     * if so, maximum number of valid days for february month of this date is increased to 29. 
     * Tests to see whether int month, and year are within acceptable range of values.
     * Then, depending on which month date falls into, tests whether int day is valid. 
     * If every test passe, returns true. Otherwise returns false.
     * @return true if every test passes, false otherwise 
     */ 
    public boolean isValidDate() {
        int dateLength = stringDate.length();
        int febMax = 28;
        if ((dateLength == 10) && (stringDate.charAt(4) == '-') && (stringDate.charAt(7) == '-')) {//does string have correct structure 
            String month = new String (stringDate.substring(5, 7));
            String year = new String (stringDate.substring(0, 4));
            String day = new String (stringDate.substring(dateLength - 2)); //splice out m/d/y
            if (intEligible(month) && intEligible(year) && intEligible(day)) {//apply helper to m/d/y
                int intMonth = Integer.valueOf(month); 
                int intYr = Integer.valueOf(year);
                int intDay = Integer.valueOf(day);
                if ((intYr % 4 == 0) && ((intYr % 100 != 0) || ((intYr % 100 == 0) && (intYr % 400 == 0))))  { //leap year
                    febMax = 29;
                }
                if ((intMonth <= 12) && (intMonth >0) && (intYr >= 1901)) { //are m and y valid
                    if (thirtyMonths.contains(month) && intDay <= 30) { //if month has 30 days, is d valid
                        return true;
                    }
                    else if (thirtyOneMonths.contains(month) && intDay <= 31){ //if month has 31 days, is d valid
                        return true;
                    }
                    else if((month.equals("02")) && intDay <= febMax) { //if month is february, is d valid
                        return true;
                    }
                }
            }
        }

        return false; //return false if any test fails
    }
}




