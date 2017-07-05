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
 * @author Ji Young Yun and Yue Kuang
 * @version July 5, 2017
 */

public class compute_num_people_getFood_waitingTime{

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        String line = "";

        // name of input file
        String fileName = "ppl_time_MWF.csv";
        
        // read input csv file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        // name of output file 
        File outputFile = new File("ppl_time_MWF_wait_getFood_avgwaitingTime.csv");
        PrintWriter printWriter = new PrintWriter(outputFile);
        
        // arraylist that contains average waiting time
        ArrayList<Integer> avg_time = new ArrayList<Integer> ();

        // open hours of survival center: 11:00 - 14:00 (M/W/F) and 16:00 - 19:00 (T/TH)
        String startTime = "10:59"; 
        String endTime = "14:00";
        
        // ArrayList that will contain the times 
        ArrayList<Date> time = new ArrayList<Date>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        // parse the starting and ending time of open hours
        Date timeStart = format.parse(startTime);
        Date timeEnd = format.parse(endTime);

        // create a double array that would go through the csv file
        // change depending on MWF/TTH
        String [][] lineArray = new String[349][];

        // create a variable for going through lineArray
        int count = 0;

        // use calendar to start the time 
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(timeStart);

        // set the interval of the time values to one minute
        while(calendar.getTime().before(timeEnd)) {
            calendar.add(Calendar.MINUTE, 1);
            time.add(calendar.getTime());
        }

        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(",");
            lineArray[count] = split;
            count +=1;
        }
        
        // loop through the time arraylist
        for(int j=0; j< time.size(); j++){
            // get the current time
            Date curr_time = time.get(j);

            // create arraylist that holds the values for the time difference
            ArrayList<Integer> intake_checkout = new ArrayList<Integer>();
            for(int i=0; i<lineArray.length; i++){
                
                // put values of needed columns into variables
                String arrival = lineArray[i][4]; // arrival time
                String intake = lineArray[i][5]; // intake time
                String checkout = lineArray[i][6]; // checkout time

                // parse using the format created
                Date arrival_time = format.parse(arrival);
                Date intake_time = format.parse(intake);
                Date checkout_time = format.parse(checkout);

                // compareTo returns -1 if smaller, 1 if larger, 0 if equal
                int comp1 = arrival_time.compareTo(curr_time);
                int comp2 = checkout_time.compareTo(curr_time);

                // if statement for the value to increase at each interval
                if(comp1 <= 0 && comp2 > 0) {
                    // calculate the time difference (either between intake and arrival or checkout and intake)
                    long waitingTime_getFood = (checkout_time.getTime() - intake_time.getTime())/ (60 * 1000) % 60;
                    
                    // convert to integer
                    int waitingTime_getFood_int = (int)waitingTime_getFood;
                    
                    // add the waiting time values to arraylist
                    intake_checkout.add(waitingTime_getFood_int); 
                }
            }
            
            // calculate sum
            int sum = 0;

            for(int k=0; k<intake_checkout.size(); k++){
                sum = sum + intake_checkout.get(k);   
            }

            // calculate the average, using the sum 
            int average = sum/arrival_intake.size();

            // add those average values to the arraylist
            avg_time.add(average);
        }

        // StringBuilder used for output csv file
        StringBuilder sb = new StringBuilder();

        // loop over the array that contains the number of people and append
        for (int i = 0; i < avg_time.size(); i++) {
            String row = String.valueOf(avg_time.get(i));
            sb.append(row);
            sb.append('\n');
        }
        
        // print to output csv
        printWriter.write(sb.toString());
        printWriter.close();
    }
}