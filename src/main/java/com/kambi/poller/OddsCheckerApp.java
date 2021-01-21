package com.kambi.poller;

import com.kambi.poller.thread.MatchPoller;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * OddsCheckerApp starts the MatchPoller thread using Executors ScheduledThreadPool for every 10 seconds
 *
 * @author Parasuram
 */
public class OddsCheckerApp {

    public static void main(String[] args) throws IOException {
        Long eventId = 1l; //defaultEventId
        try {
            eventId = Long.parseLong(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please Enter Event Id");
            System.exit(0);
        }
        MatchPoller poller = new MatchPoller(eventId);
        ScheduledExecutorService schedular = Executors.newScheduledThreadPool(1);
        //Specify the Time Duration for the MatchPoller
        schedular.scheduleAtFixedRate(poller, 10, 10, TimeUnit.SECONDS);


    }


}
