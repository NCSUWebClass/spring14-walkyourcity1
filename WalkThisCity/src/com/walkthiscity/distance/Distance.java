package com.walkthiscity.distance;

import com.google.appengine.api.urlfetch.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by travispomeroy on 4/26/14.
 */
public class Distance
{
    private String distance;

    private String duration;

    private String origin;

    private String destination;

    private String value;

    private String apiKey = "AIzaSyDAE8nd_Tlb35ocQxqI8WmmKpzT9I0zLXU";

    public Distance(String aOrigin, String aDestination)
    {
        this.origin = aOrigin.replaceAll(" ", "+").replaceAll("#", "");
        this.destination = aDestination.replaceAll(" ", "+").replaceAll("#", "");
        this.getDistanceandDuration();
    }

    private void getDistanceandDuration()
    {
        StringBuilder builderString = new StringBuilder();
        builderString.append("https://maps.googleapis.com");
        builderString.append("/maps/api/distancematrix/json?");
        builderString.append("origins=" + this.origin + "&");
        builderString.append("destinations=" + this.destination + "&");
        builderString.append("units=imperial&");
        builderString.append("mode=walking&");
        builderString.append("sensor=false&");
        builderString.append("key=" + apiKey);
        String surl = builderString.toString();

        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

        URL url = null;
        try
        {
            url = new URL(surl);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        fetchOptions.doNotValidateCertificate();
        fetchOptions.setDeadline(60D);

        HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, fetchOptions);

        HTTPResponse httpResponse = null;
        try
        {
            httpResponse = urlFetchService.fetch(request);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String responseG = "";
        if (httpResponse.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            responseG = new String(httpResponse.getContent());
        }

        JsonObject o = new JsonParser().parse(responseG).getAsJsonObject();
        JsonArray array = o.get("rows").getAsJsonArray();
        JsonObject elements = array.get(0).getAsJsonObject();
        JsonObject distance = elements.getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("distance");
        this.distance = distance.get("text").getAsString();
        this.value = distance.get("value").getAsString();
        JsonObject duration = elements.getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("duration");
        this.duration = duration.get("text").getAsString();
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
