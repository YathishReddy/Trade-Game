package TradeGame;

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.imageio.*;

import java.io.*;
class Square
{
    private int index, rent, house1, house2, house3, house4, hotel, left, right, up, down, group, price, mortgage, groupSize, housesOwned, house_price;
    private boolean hotelOwned, ownAll, mortgaged;
    private String name;

    public Square (String n, int i, int p, int m, int r, int h1, int h2, int h3, int h4, int h, int g, int x, int X, int y, int Y, int hp)
    {
	name = n;
	rent = r;
	house1 = h1;
	house2 = h2;
	house3 = h3;
	house4 = h4;
	hotel = h;
	group = g;
	price = p;
	index = i;
	mortgage = m;
	left = x;
	right = X;
	down = y;
	up = Y;
	hotelOwned = false;
	housesOwned = 0;
	ownAll = false;
	house_price = hp;
	mortgaged = false;

	if (group == 1 || group == 8)
	    groupSize = 2;
	else
	    groupSize = 3;
    }


    public Square (Square s)
    {
	name = s.name;
	rent = s.rent;
	house1 = s.house1;
	house2 = s.house2;
	house3 = s.house3;
	house4 = s.house4;
	hotel = s.hotel;
	group = s.group;
	price = s.price;
	index = s.index;
	mortgage = s.mortgage;
	left = s.left;
	right = s.right;
	up = s.up;
	down = s.down;
	hotelOwned = s.hotelOwned;
	housesOwned = s.housesOwned;
	ownAll = s.ownAll;
	groupSize = s.groupSize;
	house_price = s.house_price;
	mortgaged = s.mortgaged;
    }


    public String getName ()
    {
	return name;
    }


    public int getRent ()
    {
	return rent;
    }


    public int getHousePrice ()
    {
	return house_price;
    }


    public boolean isMortgaged ()
    {
	return mortgaged;
    }


    public void setMortgaged (boolean b)
    {
	mortgaged = b;
    }


    public void setRent ()
    {
	switch (housesOwned)
	{
	    case 1:
		rent = house1;
		break;
	    case 2:
		rent = house2;
		break;
	    case 3:
		rent = house3;
		break;
	    case 4:
		rent = house4;
		break;
	}
	if (hotelOwned)
	    rent = hotel;
    }


    public int getHouse1 ()
    {
	return house1;
    }


    public int getHouse2 ()
    {
	return house2;
    }


    public int getHouse3 ()
    {
	return house3;
    }


    public int getHouse4 ()
    {
	return house4;
    }


    public int getGroup ()
    {
	return group;
    }


    public int getPrice ()
    {
	return price;
    }


    public int getHotel ()
    {
	return hotel;
    }


    public int getMortgage ()
    {
	return mortgage;
    }


    public int getIndex ()
    {
	return index;
    }


    public int getRight ()
    {
	return right;
    }


    public int getLeft ()
    {
	return left;
    }


    public int getUp ()
    {
	return up;
    }


    public int getDown ()
    {
	return down;
    }


    public int getGroupSize ()
    {
	return groupSize;
    }


    public int getHousesOwned ()
    {
	return housesOwned;
    }


    public void setHousesOwned (int n)
    {
	if (housesOwned + n > 4)
	{
	    housesOwned = 4;
	}
	else
	{
	    housesOwned = housesOwned + n;
	}

    }


    public boolean getHotelOwned ()
    {
	return hotelOwned;
    }


    public void setHotelOwned ()
    {
	hotelOwned = true;
	housesOwned = 0;
    }


    public void setHotelOwned (boolean b)
    {
	hotelOwned = b;
	housesOwned = 0;

    }

   
    @Override
    public String toString ()
    {
	return ("Name: " + name + "\nPrice: " + price + "\nIndex: " + index + "\nRent: " + rent + "\nHouse1: " + house1 + "\nHouse2: " + house2 + "\nHouse3: " + house3 + "\nHouse4: " + house4 + "\nHotel: " + hotel + "\nGroup: " + group + "\nHouses Onwed:" + housesOwned);
    }



    public Image loadImage (String path)
    {
	try
	{
		InputStream m = this.getClass().getResourceAsStream(path);

        return ImageIO.read (m); //In Deck folder  
	}
	catch (Exception e)
	{
	    System.out.println ("File " + path + " not found.");
	    return null;
	}
    }
}

