/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *///package csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;

/**
 *
 * @author Ji Young Yun
 */

public class compute_num_people_wait_food{

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        String line = "";

        // name of input file
        String fileName = "ppl_time_TTH.csv";
        
        // read input csv file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        // name of output file 
        File outputFile = new File("ppl_time_TTH_wait_to_check_in_computed.csv");
        PrintWriter printWriter = new PrintWriter(outputFile);
        
        int[] num_people = new int[181];
        
        // open hours of survival center: 11:00 - 14:00 (M/W/F) and 16:00 - 19:00 (T/TH)
        String startTime = "15:59"; 
        String endTime = "19:00";
        
        // ArrayList that will contain the times 
        ArrayList<Date> time = new ArrayList<Date>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        // parse the starting and ending time of open hours
        Date timeStart = format.parse(startTime);
        Date timeEnd = format.parse(endTime);

        // use calendar to start the time 
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(timeStart);

        // set the interval of the time values to one minute
        while(calendar.getTime().before(timeEnd)) {
            calendar.add(Calendar.MINUTE, 1);
            time.add(calendar.getTime());
        }

        // number of columns in the input csv file
        String[] col = new String[9];

        // when there is anything to read in the file
        while ((line = bufferedReader.readLine()) != null) {

            // split the csv file with commas
            col = line.split(",");

            // put values of needed columns into variables
            String arrival = col[4]; // arrival time
            String intake = col[5]; // intake time
            String checkout = col[6]; // checkout time

            // parse using the format created
            Date arrival_time = format.parse(arrival);
            Date intake_time = format.parse(intake);
            Date checkout_time = format.parse(checkout);

            // loop over the ArrayList that contains the times
            for(int i=0; i<time.size(); i++){

                // time for comparing
                Date curr_time = time.get(i);

                // compareTo returns -1 if smaller, 1 if larger, 0 if equal
                int comp1 = intake_time.compareTo(curr_time);
                int comp2 = checkout_time.compareTo(curr_time);

                // if statement for the value to increase at each interval
                if(comp1 <= 0 && comp2 > 0) {
                    num_people[i] = num_people[i] + 1;
                }
            }
        }

        // StringBuilder used for output csv file
        StringBuilder sb = new StringBuilder();

        // loop over the array that contains the number of people and append
        for (int i = 0; i < num_people.length; i++) {
            String row = String.valueOf(num_people[i]);
            sb.append(row);
            sb.append('\n');
        }
        
        // print to output csv
        printWriter.write(sb.toString());
        printWriter.close();
    }
}