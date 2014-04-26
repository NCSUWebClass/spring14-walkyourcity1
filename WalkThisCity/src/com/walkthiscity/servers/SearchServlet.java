package com.walkthiscity.servers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.walkthiscity.beans.Location;
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
		
		String consumerKey = "MKqt7bs7CF8sPSLKrzwNiA";
        String consumerSecret = "E7PcEVXxlk_WgN_SRPoiQX3D180";
        String token = "rLAYR_IrrAJHR3n0b3TZzZ-HgivGLOsz";
        String tokenSecret = "PU-asmfbOSfz0kqHtietYdYfuqM";

        Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
		
        List<Location> locs = yelp.search( searchTerm, address );
        
        request.setAttribute( "locations", locs );
        
        request.getRequestDispatcher( "index.jsp" ).forward( request, response );
	}
}
