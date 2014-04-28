package com.walkthiscity.servers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.walkthiscity.beans.Location;
import com.walkthiscity.beans.PlacesBean;
import com.walkthiscity.distance.Converter;
import com.walkthiscity.distance.Distance;
import com.walkthiscity.geolocation.GeoLocation;
import com.walkthiscity.places.Places;
import com.walkthiscity.walkscore.WalkScore;
import com.walkthiscity.yelp.Yelp;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SearchServlet()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String searchTerm = request.getParameter( "searchTerm" );
		String address = request.getParameter( "address" );
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
		
		String consumerKey = "MKqt7bs7CF8sPSLKrzwNiA";
        String consumerSecret = "E7PcEVXxlk_WgN_SRPoiQX3D180";
        String token = "rLAYR_IrrAJHR3n0b3TZzZ-HgivGLOsz";
        String tokenSecret = "PU-asmfbOSfz0kqHtietYdYfuqM";

        Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);

        System.out.println("address" + address);
        System.out.println("lat" + lat);
        System.out.println("lng" + lng);
        List<Location> locs;
        if(lat != null && lng != null && (address == null || address.equals("")))
        {
            GeoLocation closeLoc = new GeoLocation(Double.parseDouble(lat), Double.parseDouble(lng));
            address = closeLoc.getAddress();
            locs = yelp.search( searchTerm, Double.parseDouble(lat), Double.parseDouble(lng) );
        }
        else
        {
            locs = yelp.search(searchTerm, address);
        }

        GeoLocation geo = new GeoLocation(address);

        String placesAPIKey = "AIzaSyDAE8nd_Tlb35ocQxqI8WmmKpzT9I0zLXU";

        Places places = new Places(placesAPIKey);

        List<PlacesBean> placesBean = null;
        try
        {
            placesBean = places.search( searchTerm, geo.getLat(), geo.getLon() );
        }
        catch (URISyntaxException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(Location loc : locs)
        {
            Distance dis = new Distance(address, loc.getAddress());
            loc.setDistance(dis.getDistance());
            loc.setSlowTime(dis.getDuration());
            Double moderateTime = new Double(Double.parseDouble(dis.getValue()) * .000621371 * 15);
            loc.setModerateTime(Converter.convertTime(moderateTime.intValue()));
            Double fastTime = new Double(Double.parseDouble(dis.getValue()) * .000621371 * 10);
            loc.setFastTime(Converter.convertTime(fastTime.intValue()));
            GeoLocation geoLoc = new GeoLocation(loc.getAddress());
            List<String> walk = WalkScore.getWalkScore(geoLoc.getLat(), geoLoc.getLon(), loc.getAddress());
            loc.setWalkscore(walk.get(0));
            loc.setWalkDescption(walk.get(1));
        }

        for(PlacesBean place : placesBean)
        {
            Distance dis = new Distance(address, place.getAddress());
            place.setDistance(dis.getDistance());
            place.setSlowTime(dis.getDuration());
            Double moderateTime = new Double(Double.parseDouble(dis.getValue()) * .000621371 * 15);
            place.setModerateTime(Converter.convertTime(moderateTime.intValue()));
            Double fastTime = new Double(Double.parseDouble(dis.getValue()) * .000621371 * 10);
            place.setFastTime(Converter.convertTime(fastTime.intValue()));
            GeoLocation geoLoc = new GeoLocation(place.getAddress());
            List<String> walk = WalkScore.getWalkScore(geoLoc.getLat(), geoLoc.getLon(), place.getAddress());
            place.setWalkscore(walk.get(0));
            place.setWalkDescription(walk.get(1));
        }

        request.setAttribute( "places", placesBean );
        
        request.setAttribute( "locations", locs );
        
        request.getRequestDispatcher( "index.jsp" ).forward( request, response );
	}
}
