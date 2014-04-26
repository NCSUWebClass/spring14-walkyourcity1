package com.walkthiscity.places;

import com.google.appengine.api.urlfetch.*;
import com.google.api.client.http.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.walkthiscity.beans.PlacesBean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Places 
{
	private String apiKey;
	
	public Places(String aApiKey)
	{
		apiKey = aApiKey;
	}
	
	public List<PlacesBean> search(String searchTerm, double lat, double lon) throws URISyntaxException, IOException
    {
//		final HttpClient    client          = new DefaultHttpClient();
    	List<PlacesBean> locs = new ArrayList<>();
//    	OAuthRequest request = new OAuthRequest(Verb.GET, "https://maps.googleapis.com/maps/api/place/nearbysearch/json" );
//        request.addQuerystringParameter("location", latlong);
//        request.addQuerystringParameter("radius", "5");
//        request.addQuerystringParameter("keyword", searchTerm);
//        request.addQuerystringParameter("key", apiKey);
//      
    	StringBuilder builderString = new StringBuilder();
    	builderString.append("https://maps.googleapis.com");
    	builderString.append("/maps/api/place/textsearch/json?");
    	builderString.append("location=" + lat + "," + lon + "&");
    	builderString.append("radius=5&");
    	builderString.append("query=" + searchTerm + "&");
    	builderString.append("sensor=false&");
    	builderString.append("key=" + apiKey);
    	
//        URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath(builderString.toString());
//        URI uri = new URI(builderString.toString());
//        
////        builder.addParameter("location", loc);
////        builder.addParameter("radius", "5");
////        builder.addParameter("keyword", searchTerm);
////        builder.addParameter("sensor", "false");
////        builder.addParameter("key", apiKey);
//
//        final HttpUriRequest requestII = new HttpGet(uri);
//
//        final HttpResponse execute = client.execute(requestII);
//
//        final String response = EntityUtils.toString(execute.getEntity());
//      
//        System.out.println(response.toString());
//        JsonObject o = new JsonParser().parse(response).getAsJsonObject();
//        JsonArray array = o.get("results").getAsJsonArray();
//        Type listType = new TypeToken<List<PlacesBean>>() {}.getType();
//        locs = new Gson().fromJson(o.get("results"), listType);
//        for(int i = 0; i < locs.size(); i++)
//        {
////        	Location loc = locs.get( i );
////        	JsonObject business = array.get( i ).getAsJsonObject();
////        	JsonArray addressArray = business.get( "name" ).getAsJsonObject().get( "formatted_address" ).getAsJsonArray();
////        	StringBuilder strBuilder = new StringBuilder();
////        	for(int j = 0; j < addressArray.size(); j++)
////        	{
////        		strBuilder.append( addressArray.get( j ));
////        		if(j % 2 != 0 || j == 0)
////        			strBuilder.append( " " );
////        	}
////        	
////        	loc.setAddress( strBuilder.toString() );
////        	locs.get( i ).setAddress( strBuilder.toString().replace( "\"", "" ) );
//        }
        
        String surl = builderString.toString();

        URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

        URL url = new URL(surl);

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        fetchOptions.doNotValidateCertificate();
        fetchOptions.setDeadline(60D);

        HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, fetchOptions);
        
        HTTPResponse httpResponse = urlFetchService.fetch(request);
        String responseG = "";
        if (httpResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
          responseG = new String(httpResponse.getContent());
        } 
        
        System.out.println("===================================");
        System.out.println(responseG);
        
      JsonObject o = new JsonParser().parse(responseG).getAsJsonObject();
      JsonArray array = o.get("results").getAsJsonArray();
      Type listType = new TypeToken<List<PlacesBean>>() {}.getType();
      locs = new Gson().fromJson(o.get("results"), listType);
      for(int i = 0; i < locs.size(); i++)
      {
//      	Location loc = locs.get( i );
//      	JsonObject business = array.get( i ).getAsJsonObject();
//      	JsonArray addressArray = business.get( "name" ).getAsJsonObject().get( "formatted_address" ).getAsJsonArray();
//      	StringBuilder strBuilder = new StringBuilder();
//      	for(int j = 0; j < addressArray.size(); j++)
//      	{
//      		strBuilder.append( addressArray.get( j ));
//      		if(j % 2 != 0 || j == 0)
//      			strBuilder.append( " " );
//      	}
//      	
//      	loc.setAddress( strBuilder.toString() );
//      	locs.get( i ).setAddress( strBuilder.toString().replace( "\"", "" ) );
      }
        
        return locs;
    }
	
	public static void main(String[] args)
	{
		Places places = new Places("AIzaSyDAE8nd_Tlb35ocQxqI8WmmKpzT9I0zLXU");
		
		try {
			places.search("burgers", 35.77198, -78.67385);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
