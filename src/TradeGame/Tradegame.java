package TradeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.*;

import java.io.*;

// index/name/price/mortgage/rent/rent1/rent2/rent3/rent4/hotel

class Tradegame extends JFrame implements MouseMotionListener, MouseListener
{
	private static Player[] list;//list of players
	private static int playerNumber, playerTurn = 0, roll_num, num, num2, kickedOutPlayers = 0;//variables
	private int[] squares;//array of boxs
	private static Player Current;//player currently used
	private static JLabel location, number, thing, player, display, money;//lables to display information
	private static Board tiles;//tiles in the game
	private Square Current_Pos = new Square ("Go", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 659, 748, 644, 732, 0), Current_Place;
	private static BufferedImage img;
	private static JFrame frame, stats, buildWindow = new JFrame ();//windows
	private static CDeck cdeck;//chance cards
	private static CcDeck deck;//community chest chards
	private static JButton roll, endTurn, build, mortgage, sell, trade;
	private Graphics g;
	private JTextArea history;//history of events
	static Tradegame game;
	private ButtonGroup group;//mortgage
	private JRadioButton[] places;


	public Tradegame ()
	{
		//create deck of action cards
		cdeck = new CDeck (playerNumber);
		deck = new CcDeck (playerNumber);
		history = new JTextArea (7, 25);
		// System.out.println (history.getText ());

		//history information
		history.setBackground (new Color (180, 177, 204));
		history.setEditable (false);
		history.setFont (new Font ("Dialog", Font.PLAIN, 15));
		JLabel hist = new JLabel ("HISTORY");
		hist.setFont (new Font ("Dialog", Font.BOLD, 25));

		//initialize components
		trade = new JButton ("Trade");
		trade.setEnabled (false);
		sell = new JButton ("Sell");
		sell.setEnabled (false);
		sell.addActionListener (new Listener ());
		JScrollPane scrollpane = new JScrollPane (history, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel content = new JPanel (new GridBagLayout ());
		roll = new JButton ("Roll!");
		JButton info = new JButton ("Info");
		mortgage = new JButton ("Mortgage");
		mortgage.setEnabled (false);
		endTurn = new JButton ("End Turn");
		endTurn.setEnabled (false);
		build = new JButton ("Build");
		build.setEnabled (false);
		tiles = new Board ();

		DrawArea board = new DrawArea (list, tiles);
		Current_Place = new Square ("Go", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 659, 748, 644, 732, 0);
		display = new JLabel ();
		Font f = new Font ("Dialog", Font.PLAIN, 20);
		display.setFont (f);
		img = new BufferedImage (300, 325, BufferedImage.TYPE_INT_ARGB);
		g = img.getGraphics ();
		drawCard (Current_Pos, g);
		Current = new Player (list [0]);
		location = new JLabel ();
		location.setFont (f);
		number = new JLabel ();
		location.setFont (f);
		thing = new JLabel ();
		thing.setFont (f);
		player = new JLabel ();
		player.setFont (new Font ("Dialog", Font.BOLD, 30));
		money = new JLabel ();
		money.setFont (f);

		//addaction listener to buttons
		build.addActionListener (new Listener ());
		roll.addActionListener (new Listener ());
		info.addActionListener (new Listener ());
		endTurn.addActionListener (new Listener ());
		trade.addActionListener (new Listener ());
		mortgage.addActionListener (new Listener ());

		//add squares to the game
		squares = new int [40];
		for (int x = 0 ; x < squares.length ; x++)
			squares [x] = 9;
		newTurn ();//start a new turn



		//format the layout of the screen
		GridBagConstraints c = new GridBagConstraints ();
		c.fill = GridBagConstraints.HORIZONTAL; //set fill to horizontal
		c.gridheight = 18;
		c.gridwidth = 10;
		c.gridx = 0;
		c.gridy = 0;
		content.add (board, c);
		c.gridheight = 4;
		c.gridwidth = 7;
		c.gridy = 0;
		c.gridx = 10;
		content.add (display, c);
		c.fill = GridBagConstraints.NONE; //set fill to horizontal
		c.anchor = GridBagConstraints.LINE_START;
		c.gridheight = 1;
		c.gridwidth = 10;
		c.gridy = 4;
		content.add (player, c);
		c.gridwidth = 1;
		c.gridy = 9;
		content.add (new JLabel (" "), c);
		c.gridy = 10;
		content.add (sell, c);
		c.gridx = 11;
		content.add (build, c);
		c.gridx = 12;
		content.add (trade, c);
		c.gridx = 13;
		content.add (mortgage, c);
		c.gridwidth = 10;
		c.gridx = 10;
		c.gridy = 5;
		content.add (number, c);
		c.gridy = 6;
		content.add (location, c);
		c.gridy = 7;
		content.add (thing, c);
		c.gridy = 8;
		content.add (money, c);
		c.gridy = 11;
		content.add (new JLabel (" "), c);
		c.gridy = 12;
		content.add (hist, c);
		c.gridy = 13;
		c.gridwidth = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		content.add (scrollpane, c);
		setContentPane (content);
		c.gridwidth = 1;
		c.gridy = 14;
		content.add (roll, c);
		c.gridx = 11;
		content.add (info, c);
		c.gridx = 12;
		content.add (endTurn, c);


		//set properites of the window
		setSize (1100, 750);
		setResizable(false);
		setLocationRelativeTo (null);
		addMouseMotionListener (this);
		addMouseListener (this);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //close
		setTitle ("TRADE GAME");
		repaint ();

	}


	public static void main (String[] args)
	{
		//set up the game
		//ask user for number of players
		JOptionPane players = new JOptionPane ();
		String[] number = {"2", "3", "4"};
		String selection = (String) players.showInputDialog (null, "How many Players?", "Setup", JOptionPane.QUESTION_MESSAGE, null, number, "1");
		if (selection == null)//if they pick cancel
		{
			System.exit (0);
		}
		playerNumber = Integer.parseInt (selection);//get the number of players
		list = new Player [playerNumber];//make list of players with the number inputted
		for (int x = 0 ; x < playerNumber ; x++)//make new players
		{
			list [x] = new Player (x);
		}
		playerNumber--;
		game = new  Tradegame();//create instance of monopoly game	
		game.setVisible (true);//
	}


	public void move (int num)//called when dice is rolled
	{
		JOptionPane msg = new JOptionPane ();

		if (Current.getCurrentPos () + num >= 40)//if the user passes go
		{
			Current.setCurrentPos ((Current.getCurrentPos () + num) - 40);
			msg.showMessageDialog (this, "You Passed Go! Collect $200.");
			Current.setMoney (Current.getMoney () + 200);//give more money
			money.setText (" You have $" + Current.getMoney ());//update money
		}
		else
			Current.setCurrentPos (Current.getCurrentPos () + num);//increment the square number

		Square spot = (Square) tiles.get (Current.getCurrentPos ());//get current position
		location.setText (" You are on square " + Current.getCurrentPos ());
		Current_Place = new Square ((Square) tiles.get (Current.getCurrentPos ()));
		drawCard (Current_Place, g);
		thing.setText (" You are in " + spot.getName ());//update information
		list [playerTurn] = new Player (Current);//change the current player
		repaint ();
		if (squares [Current.getCurrentPos ()] == 9 && Current_Place.getGroup () != 0)//if the place is not already owned
			buy (Current_Place);
		else if (Current_Place.getGroup () == 0)//if the tile is not a property
		{
			otherSquare (Current_Place);
		}
		else
			alreadyOwn (Current_Place);//someone already owns it

		money.setText (" You have $" + Current.getMoney ());//update the view
		repaint ();

	}


	public static void callCard (Square s)//if user lands on a chance/community card
	{
		JOptionPane msg = new JOptionPane ();
		if (s.getIndex () == 2 || s.getIndex () == 17 || s.getIndex () == 33)// get money cards
		{
			int num = (int) (Math.random () * deck.getSize () + 0);
			CcCard c1 = (CcCard) deck.deal (num);
			BufferedImage img2 = c1.getImg ();//load images
			String name = c1.getName ();
			String info = c1.getInfo ();
			int fee = c1.getFee ();
			int id = c1.getid ();
			msg.showMessageDialog (game, "Community Chest Card!", "Community Chest Card", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img2));//show the message
			if (Current.getMoney () >= fee)
			{
				Current.setMoney (Current.getMoney () + fee);
				money.setText (" You have $" + Current.getMoney ());
			}
			else//if the user cannot pay fee
			{
				kick ();//user is out of the game
			}
			if (id == 5 || id == 6)//user owes money to other players
			{
				Player[] players = getPlayerList ();//get the player list
				for (int x = 0 ; x < players.length ; x++)//goes through list of players
				{
					if (players [x] != Current)
					{
						players [x].setMoney (players [x].getMoney () - (fee / playerNumber));
					}
				}
			}
			else if (id == 4)//go to jail card
			{
				Current.setCurrentPos (10);
				Square spot = (Square) tiles.get (Current.getCurrentPos ());
				location.setText (" You are on square " + Current.getCurrentPos ());
				thing.setText (" You are in " + spot.getName ());//determines location
				list [playerTurn] = new Player (Current);
				Current.setIsInJail (true);
				determineIfHaveCard ();//determine if they have out of jail card

			}
			else if (id == 0)//displays message
			{
				Current.setCurrentPos (0);
				Square spot = (Square) tiles.get (Current.getCurrentPos ());
				location.setText (" You are on square " + Current.getCurrentPos ());//determines location
				thing.setText (" You are in " + spot.getName ());
				list [playerTurn] = new Player (Current);

			}

			else if (id == 3)//get out of jail free card
			{
				Current.setJailCard1 (true);

				//part of inventory? list of cards?
			}


		}
		else//chance card
		{
			int num = (int) (Math.random () * cdeck.getSize () + 0);
			Ccard card = (Ccard) cdeck.deal (num);
			String name = card.getName ();
			String info = card.getInfo ();
			int fee = card.getFee ();
			int id = card.getid ();
			BufferedImage img2 = card.getImg ();
			msg.showMessageDialog (game, "Chance Card!", "Chance Card", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img2));

			if (Current.getMoney () >= fee)
			{
				Current.setMoney (Current.getMoney () + fee);
				money.setText (" You have $" + Current.getMoney ());
			}
			else
			{
				kick ();
			}

			if (id == 5 || id == 6)
			{
				Player[] players = getPlayerList ();
				for (int x = 0 ; x < players.length ; x++)
				{
					if (players [x] != Current)
					{
						players [x].setMoney (players [x].getMoney () - (fee / playerNumber));
					}
				}
			}

			else if (id == 4)//go to jail 
			{
				Current.setCurrentPos (10);
				Square spot = (Square) tiles.get (Current.getCurrentPos ());
				location.setText (" You are on square " + Current.getCurrentPos ());
				thing.setText (" You are in " + spot.getName ());
				list [playerTurn] = new Player (Current);
				Current.setIsInJail (true);
				determineIfHaveCard ();

			}
			else if (id == 3)//get the chance out of jail card
			{
				Current.setJailCard2 (true);
			}

			else if (id == 0)//sets the location
			{
				Current.setCurrentPos (0);
				Square spot = (Square) tiles.get (Current.getCurrentPos ());
				location.setText (" You are on square " + Current.getCurrentPos ());
				thing.setText (" You are in " + spot.getName ());
				list [playerTurn] = new Player (Current);//saves the data
			}
		}
	}


	public static void determineIfHaveCard ()//determines 
	{
		JOptionPane msg2 = new JOptionPane ();
		if (Current.getJailCard1 () == true)//if they have get out of jail free card
		{
			int x = msg2.showOptionDialog (game, "Do you want to use your get out of jail card?", "Card", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (x == JOptionPane.YES_OPTION)
			{
				Current.setIsInJail (false);//not in jail anymore
				Current.setJailCard1 (false);//loses the get out of jail free card
			}
			else//ends the users turn
			{
				newTurn ();//next turn
			}
		}

		else if (Current.getJailCard2 () == true)//have the get out of free card for chance
		{
			int x = msg2.showOptionDialog (game, "Do you want to use your get out of jail card?", "Card", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (x == JOptionPane.YES_OPTION)
			{
				Current.setIsInJail (false);//not in jail anymore
				Current.setJailCard2 (false);//does not have card anymore
			}
			else
			{
				newTurn ();//turn is over
			}
		}

		else
		{
			newTurn ();//turn is over
		}
	}


	public static void otherSquare (Square s)//other squares, not properties
	{
		JOptionPane msg = new JOptionPane ();
		int i = s.getIndex ();

		if (i == 10 || i == 20)//if landed on free parking
		{

			msg.showMessageDialog (game, "You landed on " + s.getName () + "!", "Roll", JOptionPane.QUESTION_MESSAGE, new ImageIcon (img));
		}

		else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36)//landed on any of the community chance cards
		{
			callCard (s);

		}
		else if (i == 30)//landed on go to jail
		{
			msg.showMessageDialog (game, "You landed on " + s.getName () + "!" + "\nGO TO JAIL!", "Roll", JOptionPane.QUESTION_MESSAGE, new ImageIcon (img));
			Current.setCurrentPos (10);//move the character to jail
			Current.setIsInJail (true);//are in jail
			Square spot = (Square) tiles.get (Current.getCurrentPos ());//get the current spot
			location.setText (" You are on square " + Current.getCurrentPos ());
			thing.setText (" You are in " + spot.getName ());
			list [playerTurn] = new Player (Current);
			determineIfHaveCard ();//see if they have get out of jail free card

		}
		else if (i == 4 || i == 38)//landed on taxes
		{
			if (i == 38)//income tax
			{
				msg.showMessageDialog (game, "You landed on " + s.getName () + "!" + "\nPay $120", "Roll", JOptionPane.QUESTION_MESSAGE, new ImageIcon (img));
				if (Current.getMoney () >= 120)
				{
					Current.setMoney (Current.getMoney () - 120);
					money.setText (" You have $" + Current.getMoney ());
				}
				else
				{
					kick ();
				}
			}
			if (i == 2)//property tax
			{
				msg.showMessageDialog (game, "You landed on " + s.getName () + "!" + "\nPay $100", "Roll", JOptionPane.QUESTION_MESSAGE, new ImageIcon (img));
				if (Current.getMoney () >= 100)
				{
					Current.setMoney (Current.getMoney () - 100);
					money.setText (" You have $" + Current.getMoney ());
				}
				else
				{
					kick ();//if the user does not have anymore money they are out of the game
				}
			}


		}


	}


	public static Player getCurrent ()
	{
		return Current;
	}


	public static Player[] getPlayerList ()
	{
		return list;
	}


	public static int getPlayerNumber ()
	{
		return playerNumber;
	}


	public static void newTurn ()//called to start a new turn
	{
		if (kickedOutPlayers == (playerNumber))//if one player is left
		{
			JOptionPane msg = new JOptionPane ();
			int a = 0;
			for (int x = 0 ; x < list.length ; x++)
			{
				if (list [x].getIsInGame ())
				{
					a = x;
					x = 80;
				}
			}
			msg.showMessageDialog (game, "Player " + (a + 1) + " has won!");//win game
			System.exit (0);//close game

		}
		else//continue game, no one has won
		{
			roll.setEnabled (true);//set button enabled
			if (buildWindow.isVisible ())
			{
				buildWindow.setVisible (false);
			}
			list [playerTurn] = new Player (Current);
			playerTurn++;
			if (playerTurn > playerNumber)
				playerTurn = 0;
			Current = new Player (list [playerTurn]);
			//if players are in jail skip their turn
			if (Current.isInJail ())
			{
				Current.setCounter (1);
				newTurn ();
			}
			if (Current.getIsInGame () == false)
			{
				newTurn ();
			}
			if (Current.getMoney () < 0)
			{
				kick ();
			}
			ArrayList listz = Current.getMortgages ();//get the list of mortgaged properties
			if (Current.isAnyMoreToMortgage () || listz.size () > 0)
			{
				mortgage.setEnabled (true);
				trade.setEnabled (true);
			}
			else
			{
				mortgage.setEnabled (false);
				trade.setEnabled (false);
			}
			if (Current.isOwnAll ())
			{
				build.setEnabled (true);
			}
			else
			{
				build.setEnabled (false);
			}
			Square spot = (Square) tiles.get (Current.getCurrentPos ());
			number.setText ("");
			player.setText (" Player " + (playerTurn + 1) + "'s turn");
			location.setText (" You are on square " + Current.getCurrentPos ());
			thing.setText (" You are in " + spot.getName ());
			money.setText (" You have $" + Current.getMoney ());
		}
	}


	public static Board getBoard ()
	{
		return tiles;
	}


	public void buy (Square s)//buy properties
	{
		JOptionPane msg = new JOptionPane ();
		Object[] options = {"Buy", "Cancel"};//options
		int x = msg.showOptionDialog (this, "You landed on " + s.getName () + "!", "Roll", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon (img), options, null);

		if (x == JOptionPane.YES_OPTION && s.getPrice () <= Current.getMoney ())//if they have enough money to buy it
		{
			String t = s.getName ();
			t = t.toUpperCase ();
			if (history.getText ().equals (""))
				history.append ("Player " + (Current.getNum () + 1) + " bought " + t + ".");
			else
				history.append ("\nPlayer " + (Current.getNum () + 1) + " bought " + t + ".");

			Current.addPurchase (s);
			if (s.getGroup () == 11) //railroad group
			{

				Current.addRailRoad (1);
			}
			else if (s.getGroup () == 21) //it's a utility
			{

				Current.addUtilities (1);

			}
			squares [Current.getCurrentPos ()] = playerTurn;
			ArrayList list = Current.getPurchase ();


			if (list.size () > 0)
			{
				mortgage.setEnabled (true);
				Current.setAnyMoreToMortgage (true);
				trade.setEnabled (true);
			}
			if (Current.isOwnAll ())
			{
				build.setEnabled (true);

			}
			else
			{
				build.setEnabled (false);
			}

			// System.out.println (Current.getPurchase ());

			Current.setMoney (Current.getMoney () - s.getPrice ());
			money.setText (" You have $" + Current.getMoney ());
			x = JOptionPane.CANCEL_OPTION;


		}

		else if (x == JOptionPane.YES_OPTION) //if you want to buy it
		{
			msg.showMessageDialog (this, "You do not have enough money to make that purchase.");

		}
		if (x == JOptionPane.NO_OPTION || (x == JOptionPane.YES_OPTION && s.getPrice () > Current.getMoney ())) //if you don't want to buy or don't have enough money
		{
			boolean continues = false;
			JOptionPane msg1 = new JOptionPane ();
			int bid = s.getPrice();
			int playnum = 0;
			msg1.showMessageDialog (this, "We will now be auctioning off " + s.getName () + ". \n The bidding starts off at "+s.getPrice()+"$");
			int[] copy = new int [playerNumber + 1];
			Player temp = list [playnum];
			int winner = 0;

			for (int x1 = 0 ; x1 < copy.length ; x1++)
			{
				copy [x1] = x1;
			}
			do //start the bidding process !
			{
				for (int x1 = 0 ; x1 < copy.length ; x1++)
				{
					boolean gobid = true;
					continues = false;
					temp = list [x1];
					if (copy [x1] != -1 && (temp.getNum () != Current.getNum ()))
					{
						while (gobid == true)
						{
							String s1;
							String temps = s.getName ();
							temps = temps.toUpperCase ();
							if (winner == 0)
								s1 = msg.showInputDialog (this, "Player " + (temp.getNum () + 1) + " please enter in a bid for " + temps + "\nor press cancel to opt out." + " You have $" + temp.getMoney () + "." + "\nThe current price for " + s.getName () + " is $" + bid + ".");
							else
								s1 = msg.showInputDialog (this, "Player " + (temp.getNum () + 1) + " please enter in a bid for " + temps + "\nor press cancel to opt out." + " You have $" + temp.getMoney () + "." + "\nThe current price for " + s.getName () + " is $" + bid + "\nmade by player " + winner + ".");
							if (s1 == null)
							{
								copy [temp.getNum ()] = -1;
								gobid = false;
							}
							else
							{
								try
								{
									int num = Integer.parseInt (s1);
									if (num > temp.getMoney ())
										msg1.showMessageDialog (this, "You do not have enough money");
									else if (bid >= num)
										msg1.showMessageDialog (this, "Please enter in an amount greater" + "\nthan the current bid of $" + bid + "."); //get bid value
									else
									{
										bid = num;
										gobid = false;
										winner = x1 + 1;
									}
								}
								catch (Exception e)
								{
									msg1.showMessageDialog (this, "Error, invalid input");
								}
							}
						}

						int k = 0;
						for (int y1 = 0 ; y1 < copy.length ; y1++)
						{
							if (copy [y1] != -1)
							{
								k++;
							}
							// System.out.println ("K" + k);
						}
						if (k > 2)
						{
							continues = true;
						}
						else
						{
							x1 = 80;
						}


					}

				}
			}
			while (continues == true)
				;
			if (winner == 0) //no one bought
				msg1.showMessageDialog (this, "No bids were made.  The property is returned to the bank.");
			else //someone bought, so give property
			{
				String temp2 = "Player " + winner + " wins the auction.  They have" + "\nclaimed " + s.getName () + " for $" + bid + "!";
				msg1.showMessageDialog (this, temp2);
				if (history.getText ().equals (""
                                ))
					temp2 = "Player " + winner + " wins the auction." + "\n     They have claimed " + s.getName () + " for $" + bid + "!";
				else
					temp2 = "\nPlayer " + winner + " wins the auction." + "\n     They have claimed " + s.getName () + " for $" + bid + "!";
				history.append (temp2);
				list [winner - 1].addPurchase (s);
				if (s.getGroup () == 11) //railroads
				{
					list [winner - 1].addRailRoad (1);
				}
				else if (s.getGroup () == 21)//utilities
				{
					list [winner - 1].addUtilities (1);

				}
				list [winner - 1].setMoney (list [winner - 1].getMoney () - bid);
			}


		}

	}

	/**
	 * Checks to see if a Square is already owned by someone or is free
	 * @param s The Square object we are checking
	 */
	public void alreadyOwn (Square s)
	{
		JOptionPane msg = new JOptionPane (); // the dialog to pop up when checking
		if (squares [s.getIndex ()] == playerTurn) // you own this property
		{
			msg.showMessageDialog (this, "Property Already Bought!" + "\nYou own this property.", "Property Owned", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img));

		}
		else if (s.isMortgaged ()) //this property is mortgaged
		{
			msg.showMessageDialog (this, "Property Already Bought!" + "\nBut it is mortgaged by player" + (squares [s.getIndex ()] + 1) + "!" + "\nYou do not have to pay rent.", "Property Owned", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img));
		}
		else
		{
			int rent = 0;
			if (s.getGroup () == 11) //railroads
			{
				int xs = Current.getRailRoad ();

				if (xs == 1)
					rent = 25;
				else if (xs == 2)
					rent = 50;
				else if (xs == 3)
					rent = 100;
				else
					rent = 200;
			}

			if (s.getGroup () == 11)
			{
				int xs = Current.getRailRoad ();
				if (xs == 1)
					rent = 25;
				else if (xs == 2)
					rent = 50;
				else if (xs == 4)
					rent = 100;
				else
					rent = 200;
			}
			else if (s.getGroup () == 21)
			{
				int xs = Current.getUtilities ();
				if (xs == 1)
					rent = (roll_num * 4);
				else
					rent = (roll_num * 10);
			}
			if (s.getGroup () != 11 && s.getGroup () != 21)
			{
				rent = s.getRent ();
				msg.showMessageDialog (this, "Property Already Bought!" + "\nIt is owned by Player" + (squares [s.getIndex ()] + 1) + "!" + "\nYou must pay $" + rent + " rent." + "\nbecause they own " + s.getHousesOwned () + " house(s).", "Property Owned", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img));
			}
			else
			{
				msg.showMessageDialog (this, "Property Already Bought!" + "\nIt is owned by Player" + (squares [s.getIndex ()] + 1) + "!" + "\nYou must pay $" + rent + " rent.", "Property Owned", JOptionPane.PLAIN_MESSAGE, new ImageIcon (img));

			}
			if (Current.getMoney () >= rent)
			{
				Current.setMoney (Current.getMoney () - rent);
				money.setText (" You have $" + Current.getMoney ());
				list [squares [s.getIndex ()]].setMoney (list [squares [s.getIndex ()]].getMoney () + s.getRent ());
			}
			else
			{
				kick ();
			}

		}
	}

	/**
	 * For when you lose (get kicked out of the game)
	 */
	public static void kick ()
	{
		JOptionPane msg = new JOptionPane ();
		msg.showMessageDialog (game, "Oh no! You have gone bankrupt!" + "\nBetter luck next time!");
		Current.setIsInGame (false);
		kickedOutPlayers++;
		newTurn ();
	}


	public void drawCard (Square s, Graphics g)
	{
		int i = s.getIndex ();
		g.setColor (Color.white);
		g.fillRect (30, 15, 225, 260);
		g.setColor (Color.black);
		g.drawRect (30, 15, 225, 260);
		if (s.isMortgaged ())
		{
			g.setFont (new Font ("Arial Black", Font.PLAIN, 22));
			String temp = s.getName ();
			temp = temp.toUpperCase ();
			int q = 70;
			if (temp.length () != 7)
			{
				q = q - ((temp.length () - 7) * 6);
			}
			g.drawString (temp, q, 120);
			g.setFont (new Font ("Dialog", Font.PLAIN, 18));
			g.drawString ("MORTGAGED", 85, 180);
			g.drawString ("For $" + s.getMortgage (), 110, 195);
			g.setFont (new Font ("Dialog", Font.PLAIN, 12));
			g.drawString ("Card must be turned this side", 65, 215);
			g.drawString ("up if property if mortgaged", 75, 230);

		}
		else if (s.isMortgaged () == false)
		{
			if (i != 0 && i != 2 && i != 4 && i != 5 && i != 7 && i != 10 && i != 12 && i != 15 && i != 17 && i != 20 && i != 22 && i != 25 && i != 28 && i != 30 && i != 33 && i != 35 && i != 36 && i != 38)
			{
				g.setColor (determineColor (s.getGroup ()));
				g.fillRect (40, 25, 205, 40);
				g.setColor (Color.black);
				g.drawRect (40, 25, 205, 40);
				g.setFont (new Font ("Dialog", Font.BOLD, 20));
				int x1 = getCo (s.getName (), s);
				g.drawString (s.getName (), x1, 55);
				x1 = 115;
				g.setFont (new Font ("Dialog", Font.PLAIN, 15));
				g.drawString ("Price $" + s.getPrice () + " Rent $" + s.getRent (), 90, 90);
				g.drawLine (38, 100, 245, 100);
				g.drawString ("With 1 House", 40, 120);
				g.drawString ("With 2 Houses", 40, 142);
				g.drawString ("With 3 Houses", 40, 164);
				g.drawString ("With 4 Houses", 40, 186);
				g.drawString ("With HOTEL", 40, 208);
				g.drawString ("$" + s.getHouse1 (), 215, 120);
				g.drawString ("$" + s.getHouse2 (), 210, 142);
				g.drawString ("$" + s.getHouse3 (), 210, 164);
				g.drawString ("$" + s.getHouse4 (), 210, 186);
				g.drawString ("$" + s.getHotel (), 210, 208);
				g.drawLine (38, 218, 245, 218);
				g.drawString ("One house costs $" + s.getHousePrice (), 60, 238);
				g.drawString ("Mortgage value $" + s.getMortgage (), 70, 260);
				g.drawLine (38, 270, 245, 270);
			}
			else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36 || i == 20) //commun
			{
				Image icon;
				g.setFont (new Font ("Dialog", Font.BOLD, 30));
				if (i == 7 || i == 22 || i == 36)
				{
					icon = loadImage ("Resources/chance2.gif");
					g.drawString ("CHANCE", 75, 55);
					g.drawImage (icon, 90, 65, null);

				}
				else if (i == 20)
				{
					icon = loadImage ("Resources/freepark.gif");
					g.drawString ("Free Parking", 55, 55);
					g.drawImage (icon, 55, 65, null);
				}
				else
				{
					icon = loadImage ("Resources/chest.gif");
					g.drawString ("Community", 60, 55);
					g.drawString ("Chest", 85, 80);
					g.drawImage (icon, 50, 105, null);

				}
				// if one "Utility" is owned rent is 4 times amount shown on dice.  If both "Utilites" are owned rent is 10 times amount shown on dice.
				// Mortgage Value $75
				//
				// Rent   $25
				// If 2 R.R.'s are owned    50
				// If 3 R.R.'s are owned   100
				// If 4 R.R.'s are owned   200
				//
				// Mortgage Value $100
			}
			else if (i == 28 || i == 12)
			{

				g.setFont (new Font ("Dialog", Font.BOLD, 20));
				Image icon;
				if (i == 28)
				{
					icon = loadImage ("Resources/utility.png");
					g.drawString ("WATER WORKS", 65, 130);
					g.drawImage (icon, 95, 25, null);
				}
				else
				{
					icon = loadImage ("Resources/utility2.gif");
					g.drawString (" HYDRO ELECTRIC", 50, 130);
					g.drawImage (icon, 110, 25, null);

				}
				g.drawLine (38, 110, 245, 110);
				g.drawLine (38, 140, 245, 140);
				g.setFont (new Font ("Dialog", Font.PLAIN, 15));
				g.drawString ("   If one 'Utility' is owned rent is", 40, 160);
				g.drawString ("4 times amount shown on dice.", 40, 180);
				g.drawString ("   If both 'Utilities' are owned", 40, 200);
				g.drawString ("rent is 10 times amount on dice.", 40, 220);
				g.drawString ("Mortgage Value               $75", 40, 260);

			}

			else if (i == 10)
			{
				Image jail = loadImage ("Resources/jail.gif");
				g.drawImage (jail, 40, 60, null);
				g.setFont (new Font ("Book Antiqua", Font.PLAIN, 45));
				g.drawString ("Jail", 110, 60);
			}
			else if (i == 30)
			{
				Image jailman = loadImage ("Resources/jailman.gif");
				g.drawImage (jailman, 40, 60, null);
				g.setFont (new Font ("Dialog", Font.BOLD, 35));
				g.drawString ("Go To Jail", 60, 60);
			}

			else if (i == 38)
			{
				Image tax = loadImage ("Resources/tax.gif");
				g.drawImage (tax, 60, 60, null);
				g.setFont (new Font ("Dialog", Font.BOLD, 30));
				g.drawString ("Luxury Tax", 65, 60);
				g.drawString ("Pay $120!", 75, 235);
			}
			else if (i == 0)
			{
				Image go = loadImage ("Resources/go.gif");
				Image arrow = loadImage ("Resources/arrow.gif");
				g.setFont (new Font ("Dialog", Font.BOLD, 22));
				g.drawString ("Collect $200 salary", 40, 50);
				g.drawString ("as you pass!", 80, 80);
				g.drawImage (go, 67, 80, null);
				g.drawImage (arrow, 65, 200, null);

			}
			else if (i == 4)
			{
				g.setFont (new Font ("Dialog", Font.BOLD, 30));
				g.drawString ("Income Tax", 60, 50);
				g.setFont (new Font ("Dialog", Font.BOLD, 25));
				g.drawString ("pay $100", 95, 130);
			}
			else if (i == 5 || i == 15 || i == 25 || i == 35)
			{
				// g.drawRect (40, 25, 205, 240);
				Image train = loadImage ("Resources/train.gif");
				g.drawImage (train, 120, 37, null);
				g.drawLine (50, 100, 227, 100);
				String t = s.getName ();
				t.toUpperCase ();
				g.setFont (new Font ("Dialog", Font.BOLD, 20));
				int x_co = getCo (t, s);
				g.drawString (t, x_co, 122);
				g.drawLine (50, 130, 227, 130);

				g.setFont (new Font ("Dialog", Font.PLAIN, 15));
				g.drawString ("Rent                               $25", 50, 160);
				g.drawString ("If 2 R.R.'s are owned       50", 50, 180);
				g.drawString ("If 3 R.R.'s are owned     100", 50, 200);
				g.drawString ("If 4 R.R.'s are owned     200", 50, 220);
				g.drawString ("Mortgage Value            $100", 50, 255);

			}

			else
			{
				g.setColor (new Color (204, 204, 204));
				g.fillRect (30, 15, 226, 261);

			}
		}
		display.setIcon (new ImageIcon (img));
	}


	public int getCo (String s, Square s1)
	{
		int x1 = 115;
		int x = s1.getName ().length ();
		if (x > 5)
		{
			x1 = x1 - ((x - 5) * 5);
		}
		else if (x < 5)
		{
			x1 = x1 + ((5 - x) * 5);
		}
		return x1;
	}


	public Color determineColor (int x)
	{
		Color c;
		if (x == 1)
			c = new Color (100, 44, 131);
		else if (x == 2)
			c = new Color (178, 194, 228);
		else if (x == 3)
			c = new Color (177, 50, 95);
		else if (x == 4)
			c = new Color (255, 128, 0);
		else if (x == 5)
			c = Color.red;
		else if (x == 6)
			c = Color.yellow;
		else if (x == 7)
			c = new Color (0, 164, 0);
		else
			c = Color.blue;
		return c;
	}


	public ImageIcon createImageIcon (String path)  ///create imageIcon

	{
		java.net.URL imgURL = getClass ().getResource (path);
		if (imgURL != null)
		{
			return new ImageIcon (imgURL);
		}
		else
		{
			System.err.println ("Couldn't find file: " + path);
			return null;
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


	public void Drag (int x, int y)
	{

		int num = -1;
		ArrayList games = tiles.getBoard ();

		for (int x1 = 0 ; x1 < squares.length ; x1++)
		{
			Square temp = (Square) games.get (x1);
			if (x > temp.getLeft () && x < temp.getRight () && y > temp.getDown () && y < temp.getUp ())
				num = temp.getIndex ();
		}
		if (num == -1)
		{

		}
		else
			Current_Pos = new Square ((Square) tiles.get (num));
		drawCard (Current_Pos, g);
	}


	public void mouseDragged (MouseEvent e)  //mouse methods
	{
	}


	public void mouseMoved (MouseEvent e)
	{

		Drag (e.getX (), e.getY ());
		// System.out.println ("(" + e.getX () + "," + e.getY () + ")");  //calls open method sends in coordinates of the point in which the mouse is released
	}


	public void mousePressed (MouseEvent e)  //mouse methods
	{
		// System.out.println ("(" + e.getX () + "," + e.getY () + ")");  //calls open method sends in coordinates of the point in which the mouse is released

	}


	public void mouseReleased (MouseEvent e)
	{
	}


	public void mouseEntered (MouseEvent e)
	{
	}


	public void mouseExited (MouseEvent e)
	{
	}


	public void mouseClicked (MouseEvent e)
	{
		try
		{
			if (frame.isVisible ())
			{
				frame.setVisible (false);
				move (roll_num);

			}
			if (stats.isVisible ())
			{
				stats.setVisible (false);
			}
		}
		catch (Exception e1)
		{
		}


	}


	public Image return_roll (int num)
	{
		Image image;
		if (num == 1)
			image = loadImage ("Resources/Dice/1.png");
		else if (num == 2)
			image = loadImage ("Resources/2.png");
		else if (num == 3)
			image = loadImage ("Resources/3.png");
		else if (num == 4)
			image = loadImage ("Resources/Dice/4.png");
		else if (num == 5)
			image = loadImage ("Resources/5.png");
		else
			image = loadImage ("Resources/6.png");
		return image;

	}


	public void displayDice ()
	{
		frame = new JFrame ();
		JPanel diceDisplay = new JPanel ();
		diceDisplay.setLayout (new GridBagLayout ());
		GridBagConstraints c = new GridBagConstraints ();
		//randomly generate 2 numbers
		Image dice1 = return_roll (num);
		Image dice2 = return_roll (num2);
		JButton btn = new JButton ("Ok");
		btn.addActionListener (new Listener ());
		BufferedImage dice = new BufferedImage (150, 68, BufferedImage.TYPE_INT_ARGB);
		Graphics g = dice.getGraphics ();
		g.drawImage (dice1, 0, 0, null);
		g.drawImage (dice2, 79, 0, null);
		JLabel label = new JLabel (new ImageIcon (dice));
		//add labels with icons of dice
		JLabel fonts = new JLabel ("You rolled a " + roll_num + "!");
		fonts.setFont (new Font ("Dialog", Font.PLAIN, 18));
		JLabel doubles;
		if (num == num2)
			doubles = new JLabel ("You have rolled the same number! " + "Roll again.");
		else
			doubles = new JLabel (" ");
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		diceDisplay.add (fonts, c);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 1;
		diceDisplay.add (new JLabel (" "), c);
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridy = 2;
		diceDisplay.add (label, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 4;
		diceDisplay.add (doubles, c);
		c.gridy = 5;
		diceDisplay.add (btn, c);

		frame.setContentPane (diceDisplay);
		frame.setSize (280, 200);
		frame.setLocationRelativeTo (null);
		frame.setTitle ("Roll!");
		frame.setVisible (true);
	}


	public void buyHouse (Square s)
	{
		JOptionPane buy = new JOptionPane ();
		Object[] list = {"1", "2", "3", "4"};
		if (s.getHousesOwned () < 4)
		{
			String t = (String) (buy.showInputDialog (this, s.getName () + "\nPlease select the number of houses to build ($" + s.getHousePrice () + "):", "Build", JOptionPane.QUESTION_MESSAGE, null, list, list [0]));
			int r = Integer.parseInt (t);
			if (s.getHousesOwned () + r > 4)
			{
				buy.showMessageDialog (this, "You can only buy 4 houses per country");
			}

			int pay = Current.getMoney () - (s.getHousePrice () * r);
			if ((s.getHousePrice () * r) > Current.getMoney ())
			{
				buy.showMessageDialog (this, "Sorry you do not have enough money to make the purchase.");

			}
			else
			{
				ArrayList purc = Current.getPurchase ();
				s.setHousesOwned (r);
				s.setRent ();
				for (int x = 0 ; x < purc.size () ; x++)
				{
					Square temp = (Square) purc.get (x);
					if (temp.getName ().equals (s.getName ()))
					{
						temp = new Square (s);
						purc.set (x, temp);
					}
				}
				tiles.set (s.getIndex (), s);
				Current.setMoney (pay);
				Current.setPurchases (purc);
				money.setText (" You have $" + Current.getMoney ());
				Current.setOwnOne (true);
				sell.setEnabled (true);
				String k = s.getName ();
				k = k.toUpperCase ();
				history.append ("\nPlayer " + (Current.getNum () + 1) + " has built " + r + "\n     houses(s) on " + k + ".");
				buy.showMessageDialog (this, "You bought " + r + " houses for $" + (s.getHousePrice () * r) + "." + "\nYou have $" + Current.getMoney () + "!");
			}

		}
		else if (s.getHotelOwned () == false)
		{
			int t = buy.showOptionDialog (this, s.getName () + "\nWould you like to build a hotel for $" + ((4 * s.getHousePrice ()) + s.getHotel ()), "Build", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (t == JOptionPane.YES_OPTION && Current.getMoney () >= (4 * s.getHousePrice ()) + s.getHotel ())
			{
				s.setHotelOwned ();
				s.setRent ();
				ArrayList purc = Current.getPurchase ();
				for (int x = 0 ; x < purc.size () ; x++)
				{
					Square temp = (Square) purc.get (x);
					if (temp.getName ().equals (s.getName ()))
					{
						temp = new Square (s);
						purc.set (x, temp);
					}
				}
				tiles.set (s.getIndex (), s);
				Current.setMoney (Current.getMoney () - (4 * s.getHousePrice ()) + s.getHotel ());
				Current.setOwnOne (true);
				sell.setEnabled (true);
				String k = s.getName ();
				k = k.toUpperCase ();
				history.append ("\nPlayer " + (Current.getNum () + 1) + " has built a hotel" + "\n     " + k + ".");


			}
			else if (t == JOptionPane.NO_OPTION)
			{

			}
			else
			{
				buy.showMessageDialog (this, "You do not have enough money");
			}
		}
		else
		{
			buy.showMessageDialog (this, "You already have a hotel.");
		}


	}


	class Listener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			String s = e.getActionCommand ();
			if (s.equals ("Roll!"))
			{
				num = (int) (Math.random () * 6) + 1;
				num2 = (int) (Math.random () * 6) + 1;
				roll_num = num + num2;
				// System.out.println ("NUM1 " + num);
				// System.out.println ("NUM2 " + num2);
				if (num == num2)
				{
					roll.setEnabled (true);
					endTurn.setEnabled (false);
					Current.setDoubles (Current.getDoubles () + 1);
					if (Current.getDoubles () == 3)
					{
						Current.setDoubles (0);
						Current.setCurrentPos (10);
						Square spot = (Square) tiles.get (Current.getCurrentPos ());
						location.setText (" You are on square " + Current.getCurrentPos ());
						thing.setText (" You are in " + spot.getName ());
						list [playerTurn] = new Player (Current);
						Current.setIsInJail (true);
						determineIfHaveCard ();
					}
				}
				else
				{
					Current.setDoubles (0);
					roll.setEnabled (false);
					endTurn.setEnabled (true);
				}

				// move (roll2);
				displayDice ();
			}

			else if (s.equals ("Info"))
			{
				stats = new JFrame ("Property");
				BufferedImage img3 = new BufferedImage (300, 325, BufferedImage.TYPE_INT_ARGB);
				BufferedImage img2 = new BufferedImage (4200, 325, BufferedImage.TYPE_INT_ARGB);
				BufferedImage img4 = new BufferedImage (344, 213, BufferedImage.TYPE_INT_ARGB);
				BufferedImage img5 = new BufferedImage (344, 213, BufferedImage.TYPE_INT_ARGB);
				Image jail2 = loadImage ("Resources/CPICS/C3.png");
				Image jail = loadImage ("Resources/CCPICS/CC3.png");
				Graphics k = img4.getGraphics ();
				Graphics h = img2.getGraphics ();
				Graphics j = img3.getGraphics ();
				Graphics q = img5.getGraphics ();
				k.drawImage (jail, 0, 0, null);
				q.drawImage (jail2, 0, 0, null);
				ArrayList purchases = Current.getPurchase ();
				for (int x = 0 ; x < purchases.size () ; x++)
				{
					drawCard ((Square) purchases.get (x), j);
					h.drawImage (img3, x * 270, 0, null);
				}

				if (Current.getJailCard1 () == true && Current.getJailCard2 () == true)
				{
					h.drawImage (img4, purchases.size () * 270, 30, null);
					h.drawImage (img5, (purchases.size () * 270) + 370, 30, null);
				}
				else if (Current.getJailCard1 () == true && Current.getJailCard2 () == false)
				{
					h.drawImage (img4, purchases.size () * 270, 30, null);
				}
				else if (Current.getJailCard1 () == false && Current.getJailCard2 () == true)
				{
					h.drawImage (img5, purchases.size () * 270, 30, null);
				}

				JLabel area = new JLabel ();
				area.setIcon (new ImageIcon (img2));
				JScrollPane stats_content = new JScrollPane (area, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				stats.setSize (900, 350);
				stats.setContentPane (stats_content);
				stats.setLocationRelativeTo (null);
				stats.setVisible (true);

			}
			else if (s.equals ("Build"))
			{
				buildWindow = new JFrame ();
				FlowLayout layout = new FlowLayout (FlowLayout.LEFT);
				JPanel content = new JPanel (layout);
				places = new JRadioButton [22];
				int y = 0;
				JLabel label = new JLabel ("Please Select a Location(s)");
				JLabel label2 = new JLabel ("to build a house");
				content.add (label);
				content.add (label2);
				group = new ButtonGroup ();
				for (int x = 0 ; x < tiles.size () ; x++)
				{
					Square temp = (Square) tiles.get (x);
					if (temp.getGroup () != 0 && temp.getGroup () != 11 && temp.getGroup () != 21)
					{
						places [y] = new JRadioButton (temp.getName () + "   ($" + temp.getHousePrice () + ")");
						places [y].setActionCommand (temp.getName ());
						group.add (places [y]);
						places [y].addActionListener (this);
						int i = temp.getGroup ();
						if (Current.check (i))
							places [y].setEnabled (true);
						else
							places [y].setEnabled (false);

						y++;
					}
				}
				for (int x = 0 ; x < places.length ; x++)
				{
					content.add (places [x]);
				}
				JButton button = new JButton ("Build!");
				content.add (button);
				button.addActionListener (new Listener ());
				buildWindow.setContentPane (content);
				buildWindow.setTitle ("Build");
				buildWindow.setSize (200, 750);
				buildWindow.setVisible (true);

			}
			else if (s.equals ("End Turn"))
			{
				newTurn ();
				endTurn.setEnabled (false);
			}

			else if (s.equals ("Ok"))
			{
				frame.setVisible (false);
				move (roll_num);
			}
			else if (s.equals ("Build!"))
			{
				ButtonModel b = group.getSelection ();
				for (int x = 0 ; x < tiles.size () ; x++)
				{
					Square temp = (Square) tiles.get (x);
					if (temp.getName ().equals (b.getActionCommand ()))
					{
						buyHouse (temp);

					}
				}
				repaint ();

			}
			else if (s.equals ("Trade"))
			{

				JOptionPane msg2 = new JOptionPane ();
				int x = 0;
				String string = (String) msg2.showInputDialog (game, "Which player do you wish to trade with?", "Trade", JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (string != null)
				{
					try
					{
						x = Integer.parseInt (string);
						if (x == Current.getNum () + 1 || x < 1 || x > playerNumber + 1)
						{
							msg2.showMessageDialog (game, "Invalid Input.");
						}
						else
						{
							Player trader = list [x - 1];
							ArrayList tradelist = trader.getPurchase ();
							if (tradelist.size () < 1)
							{
								msg2.showMessageDialog (game, "This player has nothing to trade.");
							}
							else
							{
								Object[] listz = new Object [tradelist.size ()];
								int nd = 0, n = 0;
								for (int xz = 0 ; xz < listz.length ; xz++)
								{
									Square temp = (Square) tradelist.get (xz);
									listz [xz] = temp.getName ();
								}
								String ss = (String) msg2.showInputDialog (game, "What would you like from player " + x + "?", "Trade", JOptionPane.QUESTION_MESSAGE, null, listz, listz [0]);
								if (ss == null)
								{
								}
								else
								{
									Square tempt = (Square) tradelist.get (0);
									for (int xw = 0 ; xw < tradelist.size () ; xw++)
									{
										tempt = (Square) (tradelist.get (xw));
										if (tempt.getName ().equals (ss))

										{
											nd = xw;
											xw = 80;
										}
									}
									if (tempt.isMortgaged () == true)
									{
										msg2.showMessageDialog (game, "Sorry this property is currently mortgaged.");
									}
									else if (tempt.getHousesOwned () > 0 || tempt.getHotelOwned () == true)
									{
										msg2.showMessageDialog (game, "Sorry there are buildings built on this property.");

									}
									else
									{
										ArrayList currentlist = Current.getPurchase ();
										Object[] listr = new Object [currentlist.size ()];
										for (int xc = 0 ; xc < listr.length ; xc++)
										{
											Square as = (Square) (currentlist.get (xc));
											listr [xc] = as.getName ();
										}
										String sq = (String) msg2.showInputDialog (game, "What do you offer?", "Offer", JOptionPane.QUESTION_MESSAGE, null, listr, listr [0]);
										if (sq == null)
										{
										}
										else
										{
											Square ac = (Square) currentlist.get (0);

											for (int xc = 0 ; xc < currentlist.size () ; xc++)
											{
												ac = (Square) (currentlist.get (xc));
												if (ac.getName ().equals (sq))
												{
													n = xc;
													xc = 80;
												}
											}
											if (ac.isMortgaged () == true)
											{
												msg2.showMessageDialog (game, "Sorry this property is currently mortgaged.");
											}
											else if (ac.getHousesOwned () > 0 || ac.getHotelOwned () == true)
											{
												msg2.showMessageDialog (game, "Sorry there are houses built on this property.");

											}
											else
											{
												String s1 = (String) msg2.showInputDialog (game, "Would you like to also offer cash?" + "\nif so enter in an amount or press cancel", "Offer", JOptionPane.QUESTION_MESSAGE, null, null, null);
												int j = 0;
												if (s1 == null)
												{
												}
												try
												{
													j = Integer.parseInt (s1);
												}
												catch (Exception e1)
												{

												}
												if (j < 0)
												{
													msg2.showInputDialog (game, "Invalid Input.");
													j = 0;
												}
												else if (j > Current.getMoney ())
												{
													msg2.showMessageDialog (game, "You do not have enough money.");
													j = 0;

												}
												int x0 = msg2.showOptionDialog (game, "Player " + x + ", Player " + (Current.getNum () + 1) + " wants to trade your" + "\n" + tempt.getName () + " for their " + ac.getName () + " and $" + j + "." + "\nDo you accept their offer?", "Trade", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
												if (x0 == JOptionPane.YES_OPTION)
												{
													if (ac.getGroup () == 11)
													{
														list [x - 1].addRailRoad (1);
													}
													else if (ac.getGroup () == 21)
													{
														list [x - 1].addUtilities (1);
													}
													if (ac.getGroup () == 11)
													{
														Current.addRailRoad (-1);
													}
													else if (ac.getGroup () == 21)
													{
														Current.addUtilities (-1);
													}
													if (tempt.getGroup () == 11)
													{
														Current.addRailRoad (1);
													}
													else if (tempt.getGroup () == 21)
													{
														Current.addUtilities (1);
													}
													if (tempt.getGroup () == 11)
													{
														list [x - 1].addRailRoad (-1);
													}
													else if (tempt.getGroup () == 21)
													{
														list [x - 1].addUtilities (-1);
													}
													squares [ac.getIndex ()] = x - 1;
													squares[tempt.getIndex ()] = Current.getNum();
													tradelist.remove (nd);
													tradelist.add (ac);
													currentlist.remove (n);
													currentlist.add (tempt);
													Current.setPurchases (currentlist);
													list [x - 1].setPurchases (tradelist);
													Current.determine ();
													list [x - 1].determine ();
													Current.setMoney (Current.getMoney () - j);
													list [x - 1].setMoney (list [x - 1].getMoney () + j);
													money.setText (" You have $" + Current.getMoney ());
													msg2.showMessageDialog (game, "Trade successful.");
													if (Current.isOwnAll ())
													{
														build.setEnabled (true);
													}
													String zz = tempt.getName ();
													zz = zz.toUpperCase ();
													String dd = ac.getName ();
													dd = dd.toUpperCase ();
													history.append ("\nPlayer " + (Current.getNum () + 1) + " traded " + zz + " from" + "\n     Player " + x + " for " + dd);
												}
												else
												{
												}

											}
										}
									}
								}
							}

						}
					}
					catch (Exception eq)
					{
						msg2.showMessageDialog (game, "Invalid Input.");

					}
				}

			}
			else if (s.equals ("Sell"))
			{
				JOptionPane msg2 = new JOptionPane ();
				ArrayList purchases = Current.getPurchase ();
				ArrayList strings = new ArrayList ();
				for (int x = 0 ; x < purchases.size () ; x++)
				{
					Square temp = (Square) purchases.get (x);
					if (temp.getHousesOwned () > 0 || temp.getHotelOwned () == true)
					{
						strings.add (temp.getName ());
					}
				}

				Object[] i = new Object [strings.size ()];

				for (int x = 0 ; x < i.length ; x++)
				{
					i [x] = (String) strings.get (x);
				}

				String s1 = (String) msg2.showInputDialog (game, "Please select a property in which to sell a house/hotel:", "Sell", JOptionPane.QUESTION_MESSAGE, null, i, i [0]);
				if (s1 == null)
				{
				}
				else
				{
					Square as = (Square) (tiles.get (0));
					for (int x = 0 ; x < purchases.size () ; x++)
					{
						as = (Square) (purchases.get (x));
						if (as.getName ().equals (s1))
						{
							x = 80;
						}
					}
					if (as.getHousesOwned () > 0)
					{
						int j = msg2.showOptionDialog (game, "Each house you sell is worth half" + "\nits original price ($" + (as.getHousePrice () / 2) + ")" + "\nDo you wish to sell one house?", "Sell", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (j == JOptionPane.YES_OPTION)
						{
							as.setHousesOwned (-1);
							as.setRent ();
							int y;
							for (y = 0 ; y < purchases.size () ; y++)
							{
								Square temp = (Square) purchases.get (y);
								if (temp.getHousesOwned () > 0)
									y = 100;
							}
							if (y == 100)
							{
								Current.setOwnOne (true);
							}
							else
							{
								Current.setOwnOne (false);
							}
							Current.setMoney (Current.getMoney () + (as.getHousePrice () / 2));
							money.setText (" You have $" + Current.getMoney ());

							for (int x = 0 ; x < purchases.size () ; x++)
							{
								Square temp = (Square) purchases.get (x);
								if (temp.getName ().equals (as.getName ()))
								{
									temp = new Square (as);
									purchases.set (x, temp);
								}
							}
							Current.setPurchases (purchases);
							String k = as.getName ();
							k = k.toUpperCase ();
							history.append ("\nPlayer " + (Current.getNum () + 1) + " has sold a " + "\n     a house on " + k + ".");

							if (Current.isOwnOne ())
							{
								sell.setEnabled (true);
							}
							else
							{
								sell.setEnabled (false);
							}
							tiles.set (as.getIndex (), as);
							repaint ();
						}
					}
					else
					{
						int j = msg2.showOptionDialog (game, "Each hotel you sell is worth half" + "\nits original price ($" + (((as.getHousePrice () * 4) + as.getHotel ()) / 2) + ")" + "\nDo you wish to sell the hotel?", "Sell", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (j == JOptionPane.YES_OPTION)
						{
							as.setHotelOwned (false);
							as.setRent ();
							Current.setMoney (Current.getMoney () + (((as.getHousePrice () * 4) + as.getHotel ()) / 2));
							money.setText (" You have $" + Current.getMoney ());

							for (int x = 0 ; x < purchases.size () ; x++)
							{
								Square temp = (Square) purchases.get (x);
								if (temp.getName ().equals (as.getName ()))
								{
									temp = new Square (as);
									purchases.set (x, temp);
								}
							}
							Current.setPurchases (purchases);
							String k = as.getName ();
							k = k.toUpperCase ();
							history.append ("\nPlayer " + (Current.getNum () + 1) + " has sold a " + "\n     hotel on " + k + ".");
							tiles.set (as.getIndex (), as);
							repaint ();
						}
					}

				}


			}
			else if (s.equals ("Mortgage"))
			{
				JOptionPane msg2 = new JOptionPane ();
				ArrayList list = Current.getPurchase ();
				ArrayList listz = Current.getMortgages ();
				Object[] sts = new Object [list.size ()];
				Object[] labels = {"Apply", "Lift"};
				int w = 0;
				for (int x = 0 ; x < sts.length ; x++)
				{
					Square z = (Square) list.get (x);
					if (z.isMortgaged () == false)
					{
						sts [w] = z.getName ();
						w++;

					}
				}
				Object[] sts2 = new Object [w];
				for (int x = 0 ; x < sts2.length ; x++)
				{
					sts2 [x] = sts [x];
				}
				if (sts2.length > 0)
				{
					Current.setAnyMoreToMortgage (true);

				}

				int x5 = msg2.showOptionDialog (game, "Would you like to apply for or lift a mortgage?", "Mortgage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, labels, labels [0]);


				// for (int x  = 0;x<sts.length;x++)
				// {
				// System.out.println("Sts"+x+":"+sts[x]);
				// }
				// System.out.println("W"+w);
				if (x5 == JOptionPane.YES_OPTION && Current.isAnyMoreToMortgage ())
				{

					Square temp = (Square) list.get (0);
					String string = (String) msg2.showInputDialog (game, "Select a property to mortgage: ", "Mortgage", JOptionPane.QUESTION_MESSAGE, null, sts2, sts2 [0]);

					int a = 0;
					for (int x = 0 ; x < list.size () ; x++)
					{
						temp = (Square) list.get (x);
						if (temp.getName ().equals (string))
						{
							a = x;
							x = 80;
						}
					}

					Image deed = loadImage ("Resources/deed.gif");
					ImageIcon deeds = new ImageIcon (deed);
					if (string == null)
					{
					}

					else
					{
						int x = msg2.showOptionDialog (game, "Are you sure you want to mortgage" + "\n" + temp.getName () + " for $" + temp.getMortgage () + "? To lift the mortgage" + "\nyou must pay the mortgage value plus %10.", "Mortgage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, deeds, null, null);
						if (x == JOptionPane.YES_OPTION && temp.getHousesOwned () == 0)
						{
							Current.addMortgages (temp);
							String xs = temp.getName ();
							xs = xs.toUpperCase ();
							history.append ("\nPlayer " + (Current.getNum () + 1) + " has put " + "\n     " + xs + " on mortgage.");
							temp.setMortgaged (true);
							Current.setMoney (Current.getMoney () + temp.getMortgage ());
							money.setText (" You have $" + Current.getMoney ());
							if (sts2.length == 1 && listz.size () < 1)
							{
								mortgage.setEnabled (false);
							}
							if (sts2.length == 1)
							{
								Current.setAnyMoreToMortgage (false);

							}
							tiles.set (temp.getIndex (), temp);
							Current.set (temp);
						}
						else if (x == JOptionPane.YES_OPTION)
						{
							msg2.showMessageDialog (game, "You currently have " + temp.getHousesOwned () + " houses on " + temp.getName () + "\nplease sell all houses before apply for a mortgage.");
						}
					}
				}
				else if (x5 == JOptionPane.NO_OPTION && listz.size () > 0)
				{
					Object[] input = new Object [listz.size ()];
					for (int x = 0 ; x < input.length ; x++)
					{
						Square temp = (Square) (listz.get (x));
						input [x] = temp.getName ();
					}
					String string = (String) msg2.showInputDialog (game, "Please select a property to lift its mortgage:", "Lift Mortgage", JOptionPane.QUESTION_MESSAGE, null, input, input [0]);
					Square temp = (Square) (listz.get (0));
					if (string == null)
					{
					}
					else
					{
						for (int x = 0 ; x < listz.size () ; x++)
						{
							temp = (Square) listz.get (x);
							if (string.equals (temp.getName ()))
							{
								x = 80;
							}
						}
						double costs = (1.1 * temp.getMortgage ());
						int cost = (int) (costs);
						int x6 = msg2.showOptionDialog (game, "It costs $" + cost + " to lift the Mortgage" + "\noff " + temp.getName () + ". Do you wish to proceed?", "Lift a Mortgage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (x6 == JOptionPane.YES_OPTION && cost < Current.getMoney ())
						{
							String xs = temp.getName ();
							xs = xs.toUpperCase ();
							history.append ("\nPlayer " + (Current.getNum () + 1) + " has taken " + "\n     " + xs + " out of mortgage.");
							Current.removeMortgages (temp);
							temp.setMortgaged (false);
							tiles.set (temp.getIndex (), temp);
							Current.determine ();
							Current.setMoney (Current.getMoney () - cost);
							money.setText (" You have $" + Current.getMoney ());

						}
						else if (x6 == JOptionPane.YES_OPTION)
						{
							msg2.showMessageDialog (game, "You do not have enough money.");
						}

					}

				}
				else if (x5 == JOptionPane.YES_OPTION)
				{
					msg2.showMessageDialog (game, "You do not have any other properties to mortgage.");
				}
				else
				{
					msg2.showMessageDialog (game, "You have no mortgaged properties at the moment.");
				}


			}
		}
	}
}



