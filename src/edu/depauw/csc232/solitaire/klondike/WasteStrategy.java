////////////////////////////////////////////////////////////////////////////////
// File:             WasteStrategy.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.klondike;

import java.util.List;

import edu.depauw.csc232.solitaire.model.Card;
import edu.depauw.csc232.solitaire.ui.Pile;
import edu.depauw.csc232.solitaire.ui.PileStrategy;

/**
 * This PileStrategy encapsulates the rules for the waste pile in Klondike. The
 * only interaction allowed is to drag the top card to a tableau or foundation
 * pile.
 * 
 * @author bhoward
 */
class WasteStrategy implements PileStrategy
{
   public WasteStrategy(KlondikeGame game)
   {
      this.game = game;
   }

   @Override
   public boolean checkCanDrag(Pile waste)
   {
      // Allow drag if not empty
      return !waste.isEmpty();
   }

   @Override
   public boolean checkCanDrop(Pile waste, List<Card> packet)
   {
      // Drops are not allowed
      return false;
   }

   @Override
   public boolean checkStartDrag(Pile waste, List<Card> packet)
   {
      // Nothing to check -- only one card may be dragged since pile is squared
      return true;
   }

   @Override
   public void finishDrag(Pile origin, List<Card> packet, Pile target)
   {
      // Check for winning the game after playing a card from the waste pile
      game.checkWin();
   }

   @Override
   public void handleClick(Pile waste, int n)
   {
      // Search for a place to move the card; check foundations, then tableaus
      if (!waste.isEmpty()) {
         for (Pile foundation : game.foundations) {
            if (foundation.tryDrag(waste, 1)) {
               return;
            }
         }

         for (Pile tableau : game.tableaus) {
            if (tableau.tryDrag(waste, 1)) {
               return;
            }
         }
      }
   }

   private final KlondikeGame game;
}
