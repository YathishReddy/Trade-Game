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
import java.util.Collections;
import java.util.ArrayList;

/**
 * Community chance deck
 */
class CcDeck
{
    ArrayList Deck = new ArrayList ();
    CcDeck (int players)
    {
	for (int x = 0 ; x < 18 ; x++)
	    Deck.add (x, new CcCard (x, players));
    }

    public int getSize ()
    {
	return Deck.size ();
    }


    public CcCard deal (int p)
    {
	CcCard A = (CcCard) Deck.get (p);
	if (Deck.size () > 0 || p >= 0) //checks if it is within range
	    Deck.remove (p); //removes it
	return A;
    }


    public CcCard deal ()
    {
	CcCard A = this.get (0);
	if (Deck.size () > 0)
	    Deck.remove (0); //removes top card

	return A;
    }


    public CcCard get (int x)
    {
	CcCard card = (CcCard) Deck.get (x);
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

