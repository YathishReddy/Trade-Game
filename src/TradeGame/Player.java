/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGame;
import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;

import java.io.*;

class Player
{
  private int number, money, currentPos, one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0, eight = 0, ownUtilities = 0, ownRailRoads = 0, counter = 0,doubles = 0;
  private ArrayList purchase, mortgages;
  private boolean ownAll, outOfJailCard1, outOfJailCard2, anyMoreToMortgage, ownOne, isInJail,isInGame; //own all of the same colour?
  private Image icon;
  
  public Player (int x)
  {
    number = x;//player number
    money = 1550;//starting money
    currentPos = 0;//starting position
    purchase = new ArrayList (1);//their purchases
    mortgages = new ArrayList ();//properties on mortgage
    if (number == 1)//if they are player number 1
      icon = loadImage ("Resources/hat.gif");
    else if (number == 2)
      icon = loadImage ("Resources/car.gif");
    else if (number == 3)
      icon = loadImage ("Resources/iron.gif");
    else
      icon = loadImage ("Resources/boot.gif");
    outOfJailCard1 = false;//do not have jail card1
    outOfJailCard2 = false;//do not have jail card 2
    anyMoreToMortgage = false;//do they have stuff in mortgage
    ownOne = false;//own any property
    isInJail = false;
    isInGame = true;
    
  }
  
  
  public Player (Player P)
  {
    number = P.number;
    money = P.money;
    currentPos = P.currentPos;
    purchase = P.purchase;
    icon = P.icon;
    outOfJailCard1 = P.outOfJailCard1;
    outOfJailCard2 = P.outOfJailCard2;
    one = P.one;
    two = P.two;
    three = P.three;
    four = P.four;
    five = P.five;
    six = P.six;
    seven = P.seven;
    eight = P.eight;
    mortgages = P.mortgages;
    anyMoreToMortgage = P.anyMoreToMortgage;
    ownOne = P.ownOne;
    ownUtilities = P.ownUtilities;
    ownRailRoads = P.ownRailRoads;
    isInJail = P.isInJail;
    counter = P.counter;
    isInGame =P.isInGame;
    doubles = P.doubles;
  }
  
  
  public int getMoney ()
  {
    return money;
  }
  
  public void setDoubles(int num)
  {
  doubles = num;
  }
  
  public int getDoubles()
  {
  return doubles;
  }
  public void setIsInGame(boolean b)
  {
    isInGame = b;
  }
  
  public boolean getIsInGame()
  {
    return isInGame ;
  }
  public int getCounter ()
  {
    return counter;
  }
  
  
  public void setCounter (int n)
  {
    counter = counter + n;
    if (counter==3)
    {
      isInJail=false;
      counter=0;
    }
  }
  
  
  public void setMoney (int m)
  {
    money = m;
  }
  
  
  public void setIsInJail (boolean b)
  {
    isInJail = b;
  }
  
  
  public boolean isInJail ()
  {
    return isInJail;
  }
  
  
  public ArrayList getMortgages ()
  {
    return mortgages;
  }
  
  
  public void addRailRoad (int num)
  {
    ownRailRoads = ownRailRoads + num;
    
  }
  
  
  public void addUtilities (int num)
  {
    ownUtilities = ownUtilities + num;
    
  }
  
  
  public int getRailRoad ()
  {
    return ownRailRoads;
  }
  
  
  public int getUtilities ()
  {
    return ownUtilities;
  }
  
  
  public boolean isOwnOne ()
  {
    return ownOne;
  }
  
  
  public void setOwnOne (boolean b)
  {
    ownOne = b;
  }
  
  
  public boolean isAnyMoreToMortgage ()
  {
    return anyMoreToMortgage;
  }
  
  
  public void setAnyMoreToMortgage (boolean b)
  {
    anyMoreToMortgage = b;
    
  }
  
  
  public void setPurchases (ArrayList list)
  {
    purchase = list;
  }
  
  
  public int getCurrentPos ()
  {
    return currentPos;
  }
  
  
  public void setCurrentPos (int c)
  {
    currentPos = c;
  }
  
  
  public int getNum ()
  {
    return number;
  }
  
  
  public Image getIcon ()
  {
    return icon;
  }
  
  
  public int getOne ()
  {
    return one;
  }
  
  
  public int getTwo ()
  {
    return two;
  }
  
  
  public int getThree ()
  {
    return three;
  }
  
  
  public int getFour ()
  {
    return four;
  }
  
  
  public int getFive ()
  {
    return five;
  }
  
  
  public int getSix ()
  {
    return six;
  }
  
  
  public int getSeven ()
  {
    return seven;
  }
  
  
  public int getEight ()
  {
    return eight;
  }
  
  
  public void addPurchase (Square s)
  {
    int x = s.getGroup ();
    
    if (x == 1)
      one++;
    else if (x == 2)
      two++;
    else if (x == 3)
      three++;
    else if (x == 4)
      four++;
    else if (x == 5)
      five++;
    else if (x == 6)
      six++;
    else if (x == 7)
      seven++;
    else if (x == 8)
      eight++;
    purchase.add (s);
    
  }
  
  
  public void unMortgage (Square s)
  {
    int x = s.getGroup ();
    if (x == 1)
      one++;
    else if (x == 2)
      two++;
    else if (x == 3)
      three++;
    else if (x == 4)
      four++;
    else if (x == 5)
      five++;
    else if (x == 6)
      six++;
    else if (x == 7)
      seven++;
    else if (x == 8)
      eight++;
    
    
  }
  
  
  public void removeMortgages (Square s)
  {
    for (int x = 0 ; x < mortgages.size () ; x++)
    {
      Square temp = (Square) mortgages.get (x);
      if (temp.getName ().equals (s.getName ()))
      {
	mortgages.remove (x);
      }
    }
  }
  
  
  public void addMortgages (Square s)
  {
    mortgages.add (s);
  }
  
  
  public void set (Square s)
  {
    int i = s.getGroup ();
    if (i == 1)
      one = -1;
    else if (i == 2)
      two = -1;
    else if (i == 3)
      three = -1;
    else if (i == 4)
      four = -1;
    else if (i == 5)
      five = -1;
    else if (i == 6)
      six = -1;
    else if (i == 7)
      seven = -1;
    else if (i == 8)
      eight = -1;
    
  }
  
  
  public void determine ()
  {
    one = 0;
    two = 0;
    three = 0;
    four = 0;
    five = 0;
    six = 0;
    seven = 0;
    eight = 0;
    for (int x = 0 ; x < purchase.size () ; x++)
    {
      Square temp = (Square) (purchase.get (x));
      if (temp.isMortgaged () == false)
      {
	unMortgage (temp);
      }
    }
  }
  
  
  public void setPurchasess (ArrayList list)
  {
    purchase = list;
  }
  
  
  public boolean check (int x)
  {
    int y;
    if (x == 1)
      y = getOne ();
    else if (x == 2)
      y = getTwo ();
    else if (x == 3)
      y = getThree ();
    else if (x == 4)
      y = getFour ();
    else if (x == 5)
      y = getFive ();
    else if (x == 6)
      y = getSix ();
    else if (x == 7)
      y = getSeven ();
    else
      y = getEight ();
    
    if (y == 2 && (x == 1 || x == 8))
      return true;
    else if (y < 3)
      return false;
    else
      return true;
  }
  
  
  public boolean isOwnAll ()
  {
    if (one == 2 || eight == 2 || two == 3 || three == 3 || four == 3 || five == 3 || six == 3 || seven == 3)
      return true;
    else
      return false;
  }
  
  
  public ArrayList getPurchase ()
  {
    return purchase;
  }
  
  
  public void setJailCard1 (boolean bool)
  {
    outOfJailCard1 = bool;
  }
  
  
  public void setJailCard2 (boolean bool)
  {
    outOfJailCard2 = bool;
  }
  
  
  public boolean getJailCard1 ()
  {
    return outOfJailCard1;
  }
  
  
  public boolean getJailCard2 ()
  {
    return outOfJailCard2;
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



