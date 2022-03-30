package edu.depauw.csc232.solitaire.klondike;

import java.awt.event.MouseEvent;

import edu.depauw.csc232.solitaire.ui.CardStack;
import edu.depauw.csc232.solitaire.ui.PileStrategy;

/**
 * This PileStrategy encapsulates the rules for the waste pile in Klondike. The
 * only interaction allowed is to drag the top card to a tableau or foundation
 * pile.
 * 
 * @author bhoward
 */
public class WasteStrategy implements PileStrategy
{
   @Override
   public boolean checkCanDrag(CardStack waste)
   {
      // Allow drag if not empty
      return !waste.isEmpty();
   }

   @Override
   public boolean checkCanDrop(CardStack waste, CardStack packet)
   {
      // Drops are not allowed
      return false;
   }

   @Override
   public boolean checkStartDrag(CardStack waste, CardStack packet)
   {
      // Nothing to check -- only one card may be dragged since pile is squared
      return true;
   }

   @Override
   public void handleClick(CardStack waste, MouseEvent event)
   {
      // Do nothing (future extension -- search for a place to move the card)
   }
}
