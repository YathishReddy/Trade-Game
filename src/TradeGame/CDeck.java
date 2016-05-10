/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGame;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Class containing a deck of Chance cards
 */
class CDeck
{
    ArrayList Deck = new ArrayList ();
    CDeck (int players)
    {
	for (int x = 0 ; x < 18 ; x++)
	    Deck.add (x, new Ccard (x, players));
    }

    public int getSize ()
    {
	return Deck.size ();
    }


    public Ccard deal (int p)
    {
	Ccard A = (Ccard) Deck.get (p);
	if (Deck.size () > 0 || p >= 0) //checks if it is within range
	    Deck.remove (p); //removes it
	return A;
    }


    public Ccard deal ()
    {
	Ccard A = this.get (0);
	if (Deck.size () > 0)
	    Deck.remove (0); //removes top card

	return A;
    }


    public Ccard get(int x)
    {
	Ccard card = (Ccard) Deck.get (x);
	return card;
    }


    public void shuffle ()
    {
	Collections.shuffle (Deck);
    }


    public void Sort ()
    {
    }
}

