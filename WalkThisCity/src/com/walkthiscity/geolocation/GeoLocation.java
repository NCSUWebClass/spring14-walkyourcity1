package com.walkthiscity.geolocation;

import com.google.appengine.api.urlfetch.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.walkthiscity.beans.PlacesBean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by travispomeroy on 4/26/14.
 */
public class GeoLocation
{
    private double lat;

    private double lon;

    private String address;

    private String apiKey = "AIzaSyDAE8nd_Tlb35ocQxqI8WmmKpzT9I0zLXU";

    public GeoLocation(double aLat, double aLon)
    {
        this.lat = aLat;
        this.lon = aLon;

        this.locate();
    }

    public GeoLocation(String aAddress)
    {
        this.address = aAddress.replaceAll(" ", "+").replaceAll("#", "");

        locateLatLong();
    }

    private void locateLatLong()
    {
        StringBuilder builderString = new StringBuilder();
        builderString.append("https://maps.googleapis.com");
        builderString.append("/maps/api/geocode/json?");
        builderString.append("address=" + this.address + "&");
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
        JsonArray array = o.get("results").getAsJsonArray();
        JsonObject add = array.get(0).getAsJsonObject();
        JsonObject geometry = add.get("geometry").getAsJsonObject().getAsJsonObject("location");
        this.lat = geometry.get("lat").getAsDouble();
        this.lon = geometry.get("lng").getAsDouble();
    }

    private void locate()
    {
        StringBuilder builderString = new StringBuilder();
        builderString.append("https://maps.googleapis.com");
        builderString.append("/maps/api/geocode/json?");
        builderString.append("latlng=" + this.lat + "," + this.lon + "&");
        builderString.append("result_type=street_address&");
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
        JsonArray array = o.get("results").getAsJsonArray();
        JsonObject add = array.get(0).getAsJsonObject();
        this.address = add.get("formatted_address").getAsString();
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
