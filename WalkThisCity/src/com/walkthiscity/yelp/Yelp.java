package com.walkthiscity.yelp;

/**
 * Created by travispomeroy on 4/5/14.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.walkthiscity.beans.Location;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Yelp
{
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;

    private OAuthService service;
    private Token accessToken;

    public Yelp ( String aConsumerKey, String aConsumerSecret, String aToken, String aTokenSecret )
    {
        this.consumerKey = aConsumerKey;
        this.consumerSecret = aConsumerSecret;
        this.token = aToken;
        this.tokenSecret = aTokenSecret;

        this.service = new ServiceBuilder().provider(YelpAPI2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
    }

    public List<Location> search( String searchTerm, double latitude, double longitude )
    {
        List<Location> locs;
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search" );
        request.addQuerystringParameter("term", searchTerm);
        request.addQuerystringParameter("ll", "" + latitude + "," + longitude);
        request.addQuerystringParameter("radius", "2");

        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        System.out.println(response.toString());
        JsonObject o = new JsonParser().parse(response.getBody()).getAsJsonObject();
        System.out.println(o.toString());
        JsonArray array = o.get("businesses").getAsJsonArray();
        Type listType = new TypeToken<List<Location>>() {}.getType();
        locs = new Gson().fromJson(o.get("businesses"), listType);
        for(int i = 0; i < locs.size(); i++)
        {
            Location loc = locs.get( i );
            JsonObject business = array.get( i ).getAsJsonObject();
            JsonArray addressArray = business.get( "location" ).getAsJsonObject().get( "display_address" ).getAsJsonArray();
            StringBuilder strBuilder = new StringBuilder();
            for(int j = 0; j < addressArray.size(); j++)
            {
                strBuilder.append( addressArray.get( j ));
                if(j % 2 != 0 || j == 0)
                    strBuilder.append( " " );
            }

            loc.setAddress( strBuilder.toString() );
            locs.get( i ).setAddress( strBuilder.toString().replace( "\"", "" ) );
        }

        System.out.println(locs.toString());
        return locs;
    }
    
    public List<Location> search(String searchTerm, String address)
    {
    	List<Location> locs;
    	OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search" );
        request.addQuerystringParameter("term", searchTerm);
        request.addQuerystringParameter("location", address);
        request.addQuerystringParameter("radius", "2");

        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        System.out.println(response.toString());
        JsonObject o = new JsonParser().parse(response.getBody()).getAsJsonObject();
        System.out.println(o.toString());
        JsonArray array = o.get("businesses").getAsJsonArray();
        Type listType = new TypeToken<List<Location>>() {}.getType();
        locs = new Gson().fromJson(o.get("businesses"), listType);
        for(int i = 0; i < locs.size(); i++)
        {
        	Location loc = locs.get( i );
        	JsonObject business = array.get( i ).getAsJsonObject();
        	JsonArray addressArray = business.get( "location" ).getAsJsonObject().get( "display_address" ).getAsJsonArray();
        	StringBuilder strBuilder = new StringBuilder();
        	for(int j = 0; j < addressArray.size(); j++)
        	{
        		strBuilder.append( addressArray.get( j ));
        		if(j % 2 != 0 || j == 0)
        			strBuilder.append( " " );
        	}
        	
        	loc.setAddress( strBuilder.toString() );
        	locs.get( i ).setAddress( strBuilder.toString().replace( "\"", "" ) );
        }

        System.out.println(locs.toString());
    	return locs;
    }
}
