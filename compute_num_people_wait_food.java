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
        String fileName = "ppl_time_MWF.csv";
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        File outputFile = new File("ppl_time_MWF_get_food_computed.csv");
        PrintWriter printWriter = new PrintWriter(outputFile);
        int[] num_people = new int[181];
        String startTime = "10:59";
        String endTime = "14:00";
        ArrayList<Date> time = new ArrayList<Date>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        Date timeStart = format.parse(startTime);
        Date timeEnd = format.parse(endTime);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(timeStart);
        while(calendar.getTime().before(timeEnd)) {
            calendar.add(Calendar.MINUTE, 1);
            time.add(calendar.getTime());
        }

        String[] col = new String[9];

        while ((line = bufferedReader.readLine()) != null) {
            col = line.split(",");
            String arrival = col[4]; // arrival time
            String intake = col[5]; // intake time
            String checkout = col[6]; // checkout time
            Date arrival_time = format.parse(arrival);
            Date intake_time = format.parse(intake);
            Date checkout_time = format.parse(checkout);
            for(int i=0; i<time.size(); i++){
                Date curr_time = time.get(i);
                int comp1 = intake_time.compareTo(curr_time);
                int comp2 = checkout_time.compareTo(curr_time);
                if(comp1 <= 0 && comp2 > 0) {
                    num_people[i] = num_people[i] + 1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < num_people.length; i++) {
            String row = String.valueOf(num_people[i]);
            sb.append(row);
            sb.append('\n');
        }
        
        printWriter.write(sb.toString());
        printWriter.close();
    }
}