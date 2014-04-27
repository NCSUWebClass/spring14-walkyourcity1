package com.walkthiscity.beans;

public class Location
{
	private String name = "No Name Provided";
		
	private String display_address = "No Address Provided";

	private String rating = "No Rating Provided";
	
	private String phone = "No Phone Provided";

    private String distance = "No Distance Provided";

    private String fastTime = "No Distance Provided";

    private String moderateTime = "No Distance Provided";

    private String slowTime = "No Distance Provided";

    private String walkscore = "No Walkscore Provided";

    private String walkDescption = "No Description Provided";

	public String getName()
	{
		return name;
	}

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String aDistance)
    {
        this.distance = aDistance;
    }

    public String getFastTime() {
        return fastTime;
    }

    public String getModerateTime() {
        return moderateTime;
    }

    public String getSlowTime() {
        return slowTime;
    }

    public void setFastTime(String fastTime) {
        this.fastTime = fastTime;
    }

    public void setModerateTime(String moderateTime) {
        this.moderateTime = moderateTime;
    }

    public void setSlowTime(String slowTime) {
        this.slowTime = slowTime;
    }

    public void setName( String name )
	{
		if(name == null)
			return;
		this.name = name;
	}

	public String getAddress()
	{
		return display_address;
	}

	public void setAddress( String address )
	{
		if(address == null)
			return;
		this.display_address = address;
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

    public String getWalkscore() {
        return walkscore;
    }

    public void setWalkscore(String walkscore) {
        this.walkscore = walkscore;
    }

    public String getWalkDescption() {
        return walkDescption;
    }

    public void setWalkDescption(String walkDescption) {
        this.walkDescption = walkDescption;
    }

    public String toString()
	{
		return name + "  " + display_address + "  " + rating + "  " + phone;
	}
}
