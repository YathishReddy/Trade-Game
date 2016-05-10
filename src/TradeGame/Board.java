/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGame;

/**
 *
 * @author suryadevreddy
 */
import java.util.ArrayList;

import java.io.*;

/**
 * Stores the properties (countries) and utilities
 */
class Board
{
    ArrayList board;

    public Board ()
    {
	String[] place = read ("Resources/Tradegamerents.txt");
	String[] names = read ("Resources/Country.txt");
	board = new ArrayList ();
	for (int x = 0 ; x < 40 ; x++)
	{
	    int[] rents = getInfo (place [x]);
	    board.add (new Square (names [x], rents [0], rents [1], rents [2], rents [3], rents [4], rents [5], rents [6], rents [7], rents [8], rents [9], rents [10], rents [11], rents [12], rents [13], rents [14]));
	    // System.out.println ((Square) board.get (x));
	}
    }


    public ArrayList getBoard ()
    {
	return board;
    }


    public int size ()
    {
	return board.size ();
    }


    public void set (int x, Square s)
    {
    board.set (x, s);


    }


    public int[] getInfo (String s)
    {
	int[] rent = new int [15];
	String[] temp = s.split ("/");
	for (int x = 0 ; x < rent.length ; x++)
	{
	    try
	    {
		rent [x] = Integer.parseInt (temp [x]);
	    }
	    catch (Exception e)
	    {
		rent [x] = -1;
	    }
	}
	return rent;
    }


    public Square get (int x)
    {
	return (Square) board.get (x);
    }


    public String[] read (String fname)   //read text file
    {
   	InputStream m = this.getClass().getResourceAsStream(fname);
	String[] place;
	place = new String [40]; //new array of size 2
	try
	{
		BufferedReader input = new BufferedReader (new InputStreamReader(m, "UTF-8"));

//	    BufferedReader input = new BufferedReader (new FileReader (fname));
	    for (int x = 0 ; x < place.length ; x++) //loop to fill array
	    {
		place [x] = input.readLine (); //read line of file
	    }
	    input.close (); //close stream
	}


	catch (Exception e)
	{
	    System.out.println (e);
	    System.out.println ("Couldn't Find: " + fname);
	    for (int x = 0 ; x < place.length ; x++) //loop to fill array
	    {
		place [x] = ""; //read line of file
	    }
	}
	return place;
    }
}
