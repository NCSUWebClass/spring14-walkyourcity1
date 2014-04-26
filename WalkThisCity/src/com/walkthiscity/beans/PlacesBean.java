package com.walkthiscity.beans;

public class PlacesBean
{
	private String name = "No Name Provided";
		
	private String formatted_address = "No Address Provided";

	private String rating = "No Rating Provided";
	
	private String phone = "No Phone Provided";

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		if(name == null)
			return;
		this.name = name;
	}

	public String getAddress()
	{
		return formatted_address;
	}

	public void setAddress( String address )
	{
		if(address == null)
			return;
		this.formatted_address = address;
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating( String rating )
	{
		if(rating == null)
			return;
		this.rating = rating;
	}

	public String getPhoneNumber()
	{
		return phone;
	}

	public void setPhoneNumber( String phoneNumber )
	{
		if(phoneNumber == null)
			return;
		this.phone = phoneNumber;
	}
	
	public String toString()
	{
		return name + "  " + formatted_address + "  " + rating + "  " + phone;
	}
}
