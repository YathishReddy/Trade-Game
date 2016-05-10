 package TradeGame;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

import java.io.*;
import java.util.ArrayList;

class DrawArea extends JPanel
        
{
  ArrayList board;
  Player[] list;
  public DrawArea (Player[] l, Board tiles)
  {
    this.setPreferredSize (new Dimension (700, 703));
    board = tiles.getBoard ();
    list = l;
  }
  
  
  @Override
  public void paintComponent (Graphics g)
  {
    Image img;
    img = loadImage ("Resources/Board4.png");
    g.drawImage (img, 0, 0, null);
    Player[] p = Tradegame.getPlayerList ();
    Board tiles = Tradegame.getBoard ();
    for (int x = 0 ; x < Tradegame.getPlayerNumber () + 1 ; x++)
    {
      Square s = (Square) (tiles.get (p [x].getCurrentPos ()));
      int x1 = ((s.getRight () + s.getLeft ()) / 2) - 73;
      int y = ((s.getUp () + s.getDown ()) / 2) - 60;
      // System.out.println (x1 + "" + y);
       g.drawImage (p [x].getIcon (), x1,y, null);
       //g.setColor(Color.yellow);
       //g.setFont(new Font("Dialog",Font.BOLD,20));
       //g.drawString("Player "+ (p[x].getNum()+1),x1-20,y-20);
       g.setColor(Color.black);
    }
    Image house = loadImage ("Resources/house.gif");
    Image hotel = loadImage("Resources/hotel.gif");
    for (int x = 0 ; x < tiles.size () ; x++)
    {
      Square s = (Square) (tiles.get (x));
      int x2 = s.getHousesOwned ();
      if (x2 == 0)
      {
	for (int x12 = 0 ; x12 < x2 ; x12++)
	{
	  int posx = x12 * 15;
	  int posy = x12 * 15;
	  // System.out.println (s.getName () + ", #" + s.getHousesOwned ());
	  if (s.getIndex () > 30)
	    g.drawImage (house, s.getLeft () - 53, (s.getDown () - 35) + posy, null);
	  else if (s.getIndex () > 20)
	    g.drawImage (house, (s.getLeft () - 53) + posx, s.getDown () + 35, null);
	  
	  else if (s.getIndex () > 10)
	    g.drawImage (house, s.getRight () - 73, (s.getUp () - 48) - posy, null);
	  else
	    g.drawImage (house, (s.getRight () - 113) + posx, s.getUp () - 120, null);
	  
	}
	
      }
      if (s.getHotelOwned())
      {
      if (s.getIndex () > 30)
	    g.drawImage (hotel, s.getLeft () - 58, s.getDown () - 30, null);
	  else if (s.getIndex () > 20)
	    g.drawImage (hotel, s.getLeft () - 40, s.getDown () +12, null);
	  
	  else if (s.getIndex () > 10)
	    g.drawImage (hotel, s.getRight () - 86, s.getUp () - 87, null);
	  else
	    g.drawImage (hotel, s.getRight () - 93 , s.getUp () - 133, null);
      }
      
    }
    
  }
    public Image loadImage (String file)  // this method reads and loads the image
    {
      try
      {
    	  InputStream m = this.getClass().getResourceAsStream(file);

          return ImageIO.read (m); //In Deck folder  
      }
      catch (IOException e)
      {
	System.out.println ("Error: File " + file + " not found.");
	return null;
      }
    }
  }
  
  
