package com.nerdery.umbrella.data.api.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nerdery.umbrella.data.model.ForecastCondition;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;

/**
 * Converts Weather Underground's hourly forecast data to Java objects
 *
 * @author bherbst
 */
public class ForecastParser implements JsonDeserializer<ForecastCondition> {
    @Override
    public ForecastCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ForecastCondition condition = new ForecastCondition();

        // Parse out root data
        JsonObject root = json.getAsJsonObject();
        condition.icon = root.get("icon").getAsString();

        // Parse out temperature data
        JsonObject temp = root.getAsJsonObject("temp");
        String tempEnglishString = temp.get("english").getAsString();
        String tempMetricString = temp.get("metric").getAsString();
        condition.tempFahrenheit = Float.valueOf(tempEnglishString);
        condition.tempCelsius = Float.valueOf(tempMetricString);
        condition.condition = root.get("condition").getAsString();
        Timber.d(root.toString());

        // Parse out time data
        JsonObject fcttime = root.getAsJsonObject("FCTTIME");
        condition.displayTime = fcttime.get("civil").getAsString();
        long epochSeconds = fcttime.get("epoch").getAsLong();
        condition.time = new Date(epochSeconds * 1000);

        condition.calendar = Calendar.getInstance();
        condition.calendar.setTime(condition.time);

        return condition;
    }
}
