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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * A chance card
 */
class Ccard
{
    int fee = 0;
    int id = 0;
    String name = "";
    String info = "";
    BufferedImage img;
    public Ccard (int num, int players)
    {
	id = num;
	int m = 1;
	String line = get (num);
	String[] parts = line.split ("/");
	String s_fee = parts [0];
	if (num == 5 || num == 6)
	{
	   m = players;
	}
	fee = ((int) Integer.parseInt (s_fee))*m;
	if (num == 5 || num == 6)
	    fee = fee * players;
	info = parts [1];
	
	
	try //tries to load images
	{
	    img = loadImg ("Resources/CPICS/C" + id + ".png");
	}
	catch (IOException e)
	{
	    e.printStackTrace ();
	}
    }


    private  BufferedImage loadImg (String name) throws IOException //Loads the image
    {
    	InputStream m = this.getClass().getResourceAsStream(name);

        return ImageIO.read (m); //In Deck folder    
    }

    public BufferedImage getImg()
    {
    return img;
    }
    public String get (int num)
    {
	String[] lines = read ("Resources/Ccards.txt");
	String line = lines [num];
	return line;
    }


    public String getInfo ()
    {
	return info;
    }


    public int getFee ()
    {
	return fee;
    }

    
    public int getid ()
    {
	return id;
    }


    public String getName ()
    {
	return name;
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

