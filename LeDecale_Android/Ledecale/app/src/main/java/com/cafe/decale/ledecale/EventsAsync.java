package com.cafe.decale.ledecale;

import android.os.AsyncTask;

import com.cafe.decale.ledecale.Utils.EstablishConnection;
import com.cafe.decale.ledecale.model.Booking;
import com.cafe.decale.ledecale.model.EventObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.EOFException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by manut on 28/09/2017.
 */

public class EventsAsync extends AsyncTask<String, Void, HashMap<Integer, ArrayList<EventObject>> >{
    public EventsAsync(EventsAsync.Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onLoaded(HashMap<Integer,ArrayList<EventObject>> events);

        void onError();
    }

    private EventsAsync.Listener mListener;
    HashMap<Integer, ArrayList<EventObject>> eventObjects = new HashMap<>();

    @Override
    protected HashMap<Integer,ArrayList<EventObject> >doInBackground(String... params) {
        String rawResponse;
        ArrayList<EventObject> events = new ArrayList<>();
        try {
            rawResponse = EstablishConnection.loadJson(params[0], "week/horaire/"+ params[1]);

            JSONObject jsonObject = (JSONObject) new JSONTokener(rawResponse).nextValue();
            if(jsonObject.has("days")){
                JSONArray jsonArray = (JSONArray) jsonObject.get("days");
                for (int i = 0; i < jsonArray.length(); i++) {
                    if(!jsonArray.getJSONObject(i).get("durations").equals(null))
                    events.addAll(getEventsFromJson((JSONArray)jsonArray.getJSONObject(i).get("durations")));
                }
            }
            return this.eventObjects;
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
            return this.eventObjects;
        } catch (ParseException e) {
            e.printStackTrace();
            return  this.eventObjects;
        }
    }

    private ArrayList<EventObject> getEventsFromJson(JSONArray jsonDurations) throws JSONException, ParseException {
        ArrayList<EventObject> events = new ArrayList<>();
        Long dateStart = 0L, dateEnd = 0L;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for(int i = 0; i<jsonDurations.length(); i++){
            JSONObject duration = (JSONObject) jsonDurations.get(i);
            if(duration.has("startDate") && duration.has("endDate")) {
                dateStart = (duration.get("startDate")).equals(null) ? 0L : (Long) duration.get("startDate");
                dateEnd = (duration.get("endDate")).equals(null) ? 0L : (Long) duration.get("endDate");
            }
            if(dateEnd == 0L && dateStart == 0L){
                events.add(new EventObject(null, null));
            }
            else {
                events.add(new EventObject(dateFormatted(dateStart), dateFormatted(dateEnd)));
                }
            }
        this.eventObjects.put(dateFormatted(dateStart).getDate(), events);
        return events;
    }
    private Date dateFormatted(Long millis) throws ParseException {
        return new Date(millis);
    }
    @Override
    protected void onPostExecute(HashMap<Integer, ArrayList<EventObject>> events) {

        if (events!= null && events.size()!= 0) {

            mListener.onLoaded(events);

        } else {

            mListener.onError();
        }
    }
}
