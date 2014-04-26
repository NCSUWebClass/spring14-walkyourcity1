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
import com.walkthiscity.places.Places;
import com.walkthiscity.yelp.Yelp;

public class PlacesServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PlacesServlet()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String searchTerm = request.getParameter( "searchTerm" );
		String lat = request.getParameter( "lat" );
		String lon = request.getParameter( "lon" );
		
		String apiKey = "AIzaSyDAE8nd_Tlb35ocQxqI8WmmKpzT9I0zLXU";

		Places places = new Places(apiKey);
		
        List<PlacesBean> placesBean = null;
		try {
			placesBean = places.search( searchTerm, Double.parseDouble(lat), Double.parseDouble(lon) );
		} catch (NumberFormatException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        request.setAttribute( "places", placesBean );
        
        request.getRequestDispatcher( "index.jsp" ).forward( request, response );
	}
}
