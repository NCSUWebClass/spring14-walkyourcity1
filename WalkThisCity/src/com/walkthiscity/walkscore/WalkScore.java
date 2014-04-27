package com.walkthiscity.walkscore;

import com.google.appengine.api.urlfetch.*;
import com.google.gson.*;
import java.util.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by travispomeroy on 4/26/14.
 */
public class WalkScore
{
    public static List<String> getWalkScore(double lat, double lng, String address)
    {
        String apiKey = "2fb71714b0536a0415764317d2a42a88";
        List<String> list = new ArrayList<>();
        String formattedAddress = address.replaceAll(" ", "%20");

        StringBuilder builderString = new StringBuilder();
        builderString.append("http://api.walkscore.com");
        builderString.append("/score?");
        builderString.append("format=json&");
        builderString.append("address=" + formattedAddress + "&");
        builderString.append("lat=" + lat + "&");
        builderString.append("lon=" + lng + "&");
        builderString.append("wsapikey=" + apiKey);

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
        JsonElement element = o.get("walkscore");
        JsonElement elementII = o.get("description");
        if(element == null)
        {
            list.add("No Walkscore Provided");
            list.add("No Description Provided");
        }
        else
        {
            list.add(element.toString());
            list.add(elementII.toString());
        }

        return  list;
    }
}
