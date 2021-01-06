package com.kambi.poller.thread;

import com.google.gson.Gson;
import com.kambi.poller.models.Event;
import com.kambi.poller.models.LiveEvent;
import com.kambi.poller.models.LiveEvents;
import com.kambi.poller.models.MainBetOffer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * MatchPoller to poll the liveEvents for every ten seconds
 * @author Parasuram
 */
public class MatchPoller implements Runnable {

    boolean isEventName = true;
    OkHttpClient client = new OkHttpClient();
    private Long eventId = 1l;

    public MatchPoller(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * Use OKHttpClient to send GET API poll request.
     */
    public void getEvents() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        try {
            Request request = new Request.Builder()
                    .url("https://eu-offering.kambicdn.org/offering/v2018/ubse/event/live/open.json").build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            JSONObject json = new JSONObject(
                    body.string());
            LiveEvents liveEvents = new Gson().fromJson(json.toString(), LiveEvents.class);
            if (liveEvents.getLiveEvents() != null) {
                int size = liveEvents.getLiveEvents().size();
                int count = 0;
                for (int i = 0; i < size; i++) {
                    if (liveEvents.getLiveEvents().get(i) != null) {
                        if (liveEvents.getLiveEvents().get(i).getEvent().getId().equals(eventId)) {
                            count++;
                            LiveEvent liveEvent = liveEvents.getLiveEvents().get(i);
                            Event event = liveEvent.getEvent();
                            Optional<String> matchTag = event.getTags().stream().filter(tag -> tag.equalsIgnoreCase("MATCH")).findAny();
                            if (matchTag.isPresent()) {
                                if (isEventName) {
                                    isEventName = false;
                                    System.out.println("Event : " + liveEvent.getEvent().getName());
                                }
                                MainBetOffer mainBetOffer = liveEvent.getMainBetOffer();
                                int outComesLenth = 0;
                                if (mainBetOffer != null && mainBetOffer.getOutcomes() != null) {
                                    outComesLenth = mainBetOffer.getOutcomes().size();
                                    stringBuffer.append("[" + getTimeAndDate() + "] | ");
                                    if (outComesLenth > 0) {
                                        for (int j = 0; j < outComesLenth; j++) {
                                            mainBetOffer.getOutcomes().get(j);
                                            stringBuffer.append(mainBetOffer.getOutcomes().get(j).getLabel() + ":  "
                                                    + supressHighOrderDecimals(mainBetOffer.getOutcomes().get(j).getOdds())
                                                    + " | ");
                                        }
                                        System.out.println(stringBuffer.toString());
                                    }
                                } else {
                                    System.out.println("No Odds found for the event \t" + liveEvent.getEvent().getName());
                                }

                            } else {
                                System.out.println("No Events with Tag MATCH Found for the given EventId \t" + eventId);
                            }
                        }
                    }
                }
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

    public static String supressHighOrderDecimals(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value / 1000);

    }

    public static String getTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }
}
