package edu.depauw.csc232.solitaire.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;

import edu.depauw.csc232.solitaire.model.CardCollection;

/**
 * A Pile is a CardStack located on a game Table. It is able to respond to mouse
 * clicks and drag/drop events.
 * 
 * @author bhoward
 */
public class Pile extends CardStack
{
   private Pile(CardCollection cards, PileStrategy strategy, int horizontal,
            int vertical)
   {
      super(cards, horizontal, vertical);
      this.strategy = strategy;
   }

   /**
    * Construct a horizontal PacketTableItem for the given cards and strategy.
    * 
    * @param cards
    * @param strategy
    */
   public static Pile makeHorizontal(CardCollection cards,
            PileStrategy strategy)
   {
      return new Pile(cards, strategy, 1, 0);
   }

   /**
    * Construct a vertical PacketTableItem for the given cards and strategy.
    * 
    * @param cards
    * @param strategy
    */
   public static Pile makeVertical(CardCollection cards, PileStrategy strategy)
   {
      return new Pile(cards, strategy, 0, 1);
   }

   /**
    * Construct a squared-up PacketTableItem for the given cards and strategy.
    * 
    * @param cards
    * @param strategy
    */
   public static Pile makeSquared(CardCollection cards, PileStrategy strategy)
   {
      return new Pile(cards, strategy, 0, 0);
   }

   /**
    * Return whether this pile is under the location of the given mouse event.
    * 
    * @param event
    *           the mouse event
    * @return true if the event applies to this pile
    */
   public boolean underMouse(MouseEvent event)
   {
      // check whether the event is over this pile's image
      Image image = getCachedImage();
      int x = getX();
      int y = getY();
      int ex = event.getX();
      int ey = event.getY();
      return image != null && x <= ex && ex < x + image.getWidth(null)
         && y <= ey && ey < y + image.getHeight(null);
   }

   /**
    * Respond to a mouse click on this pile.
    * 
    * @param event
    *           the mouseClicked event
    */
   public void handleClick(MouseEvent event)
   {
      strategy.handleClick(this, event);
   }

   /**
    * Return whether the given packet may be dropped on this. To avoid lag, this
    * should do minimal computation and return quickly.
    * 
    * @param packet
    *           the candidate for dropping on this pile
    * @param event
    *           the mouseMoved event being checked
    * @return true if this pile will accept the drop
    */
   public boolean canDrop(Packet packet, MouseEvent event)
   {
      return strategy.checkCanDrop(this, packet);
   }

   /**
    * Return whether the given pile may be dragged. This could be used to change
    * the mouse appearance, for example. To avoid lag, this should do minimal
    * computation and return quickly.
    * 
    * @param event
    *           the mouseDragged event being checked
    * @return true if this pile will respond to being dragged
    */
   public boolean canDrag(MouseEvent event)
   {
      // true if there is at least one card to be dragged, and the strategy says
      // OK
      return !cards.isEmpty() && strategy.checkCanDrag(this);
   }

   /**
    * Return a Packet that will be dragged with the mouse for a potential drop.
    * Return null to abort the drag.
    * 
    * @param event
    *           the initial mouseDragged event recognized as a drag
    * @return the packet to be dragged; null to abort
    */
   public Packet startDrag(MouseEvent event)
   {
      int n = identifyCard(event);

      // Create a new collection with the selected cards
      CardCollection selectedCards = new CardCollection();
      for (int i = n; i < cards.size(); i++) {
         selectedCards.add(cards.get(i));
      }

      // Check that the collection is OK to drag
      if (!strategy.checkStartDrag(this, selectedCards)) {
         return null;
      }

      // Remove those cards from our packet
      for (int i = 0; i < selectedCards.size(); i++) {
         cards.deal();
      }
      invalidateImage();

      Packet packet = new Packet(this, selectedCards, xOFFSET / HOFFSET,
               yOFFSET / VOFFSET);
      packet.setX(getX() + n * xOFFSET);
      packet.setY(getY() + n * yOFFSET);
      return packet;
   }

   /**
    * Perform any necessary post-drag cleanup.
    */
   public void finishDrag(Packet packet, Pile target, MouseEvent event)
   {
      strategy.finishDrag(this, packet, target, event);
   }

   private int identifyCard(MouseEvent event)
   {
      // Determine the index of the selected card
      int dx = event.getX() - getX();
      int dy = event.getY() - getY();

      int top = cards.size() - 1;
      int xSelect = (xOFFSET == 0) ? top : Math.min(dx / xOFFSET, top);
      int ySelect = (yOFFSET == 0) ? top : Math.min(dy / yOFFSET, top);

      return Math.min(xSelect, ySelect);
   }

   private PileStrategy strategy;
}
