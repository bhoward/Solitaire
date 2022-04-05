////////////////////////////////////////////////////////////////////////////////
// File:             Packet.java
// Course:           CSC 232, Spring 2022
// Authors:          bhoward
//
// Acknowledgments:  None
//
// Online sources:   None
////////////////////////////////////////////////////////////////////////////////

package edu.depauw.csc232.solitaire.ui;

import java.awt.event.MouseEvent;
import java.util.List;

import edu.depauw.csc232.solitaire.model.Card;

/**
 * A Packet is a CardStack that contains a collection of cards currently being
 * dragged. The collection will not be empty.
 * 
 * @author bhoward
 */
public class Packet extends CardStack
{
   // Package-private constructor for a packet with a given number of cards
   // taken from the origin Pile. The cards are (perhaps temporarily) removed
   // from the origin.
   Packet(Pile origin, List<Card> selected, int horizontal, int vertical)
   {
      super(horizontal, vertical);
      this.origin = origin;

      addAll(selected);
      int n = selected.size();
      for (int i = 0; i < n; i++) {
         origin.deal();
      }
   }

   /**
    * Notify this packet that its current drag motion has been cancelled. The
    * cards are returned to the origin pile.
    * 
    * @param event
    *           the mouse event that cancelled the drag
    */
   public void cancelDrag(MouseEvent event)
   {
      origin.addAll(cards);
   }

   /**
    * Notify this packet that it has been dropped on the given pile. The cards
    * are added to the target pile, and the origin is notified of the drag
    * completion.
    * 
    * @param target
    *           the target of the drop
    * @param event
    *           the mouseReleased event for the drop
    */
   public void endDrag(Pile target, CardMover mover, MouseEvent event)
   {
      // allow the CardMover to do the official move
      origin.addAll(cards);
      mover.move(cards.size(), origin, target);

      origin.finishDrag(this, target, mover, event);
   }

   /**
    * @return the pile from which this packet was dragged
    */
   public Pile getOrigin()
   {
      return origin;
   }

   private final Pile origin;
}
