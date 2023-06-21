package com.example.appsiga240223;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class SchoolYearsCalculator {

    public static double[] calculate(String birth_date_string, String enrollment_date_string) {
        String birthDateString = birth_date_string;
        String enrollmentDateString = enrollment_date_string;

        // Convert the birth date and enrollment date strings into Date objects
        Date birthDate = parseDate(birthDateString);
        // Date enrollmentDate = parseDate(enrollmentDateString);

        // Calculate the student's age
        double age = calculateAge(birthDate);

        // Calculate the number of years the student has spent in each school unit
        double yearsInEarlyChildhoodUnit = calculateYearsInSchoolUnit(birthDate, 6.5, 11);
        double yearsInFundamentalUnit = calculateYearsInSchoolUnit(birthDate, 11, 15);
        double yearsInMiddleUnit = calculateYearsInSchoolUnit(birthDate, 15, 18);
        double yearsInUpperUnit = calculateYearsInSchoolUnit(birthDate, 18, 21);
        double yearsInAlumniUnit = calculateYearsInSchoolUnit(birthDate, 21, age);

        double[] years_collection = new double[5];

        years_collection[0] = yearsInEarlyChildhoodUnit;
        years_collection[1] = yearsInFundamentalUnit;
        years_collection[2] = yearsInMiddleUnit;
        years_collection[3] = yearsInUpperUnit;
        years_collection[4] = yearsInAlumniUnit;

        return years_collection;

        // Display the results
//        System.out.println("Anos na unidade infantil: " + yearsInEarlyChildhoodUnit);
//        System.out.println("Anos na unidade fundamental: " + yearsInFundamentalUnit);
//        System.out.println("Anos na unidade médio: " + yearsInMiddleUnit);
//        System.out.println("Anos na unidade superior: " + yearsInUpperUnit);
//        System.out.println("Anos na unidade alumni: " + yearsInAlumniUnit);
    }

    // Parse a date string in the format "dd/MM/yyyy"
    private static Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            // handle the exception here, e.g. logging or throwing a custom exception
            System.out.println("Ocorreu um erro: " + e.getMessage());
            return null;
        }
    }

    // Calculate age based on the birth date
    private static double calculateAge(Date birthDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    // Calculate years in a school unit based on the enrollment date and the unit's age range
    private static double calculateYearsInSchoolUnit(Date enrollmentDate, double startAge, double endAge) {
        double age = calculateAge(enrollmentDate);
        double yearsInUnit = age - startAge;
        if (yearsInUnit < 0) {
            yearsInUnit = 0;
        } else if (yearsInUnit >= endAge - startAge) {
            yearsInUnit = endAge - startAge;
        }
        return yearsInUnit;
    }
}
