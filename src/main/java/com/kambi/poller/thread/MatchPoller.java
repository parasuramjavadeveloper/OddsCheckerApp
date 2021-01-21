package com.kambi.poller.thread;

import com.google.gson.Gson;
import com.kambi.poller.models.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * MatchPoller to poll the liveEvents for every ten seconds
 *
 * @author Parasuram
 */
public class MatchPoller implements Runnable {

    boolean isEventName = true;
    OkHttpClient client = new OkHttpClient();
    private Long eventId = 1l;
    int count = 0;


    public MatchPoller(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * Use OKHttpClient to send GET API poll request.
     *
     * @throws IOException
     */
    public void getEvents() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Request request = new Request.Builder()
                    .url("https://eu-offering.kambicdn.org/offering/v2018/ubse/event/live/open.json").build();
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(
                    response.body().string());
            LiveEvents liveEvents = new Gson().fromJson(json.toString(), LiveEvents.class);
            List<LiveEvent> events = liveEvents.getLiveEvents();
            int size = events.size();
            if (size > 0) {
                events.forEach(liveEvent -> {
                    Event event = liveEvent.getEvent();
                    if (event != null && event.getId().equals(eventId)) {
                        count++;
                        String eventName = event.getName();;
                        Optional<String> matchTag = event.getTags().stream().filter(tag -> tag.equalsIgnoreCase("MATCH")).findFirst();
                        if (matchTag.isPresent()) {
                            if (isEventName) {
                                isEventName = false;
                                System.out.println("Event : " + eventName);
                            }
                            MainBetOffer mainBetOffer = liveEvent.getMainBetOffer();
                            if (mainBetOffer != null &&  mainBetOffer.getOutcomes()!=null && mainBetOffer.getOutcomes().size() > 0) {
                                List<Outcome> outcomes = mainBetOffer.getOutcomes();
                                stringBuffer.append("[" + getTimeAndDate() + "] | ");
                                   outcomes.forEach(outcome -> {
                                        stringBuffer.append(outcome.getLabel() + ":  "
                                                + highOrderDecimals(outcome.getOdds())
                                                + " | ");
                                    });
                                System.out.println(stringBuffer.toString());
                            } else {
                                System.out.println("No Odds found for this event \t" + eventName);
                            }

                        } else {
                            System.out.println("No Events with Tag MATCH Found for the given EventId \t" + eventId);
                        }
                    }
                });
                if (count < 1) {
                    System.out.println("No Events found for this eventId\t" + eventId);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void run() {
        try {
            getEvents();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String highOrderDecimals(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value / 1000);

    }

    private String getTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }
}
