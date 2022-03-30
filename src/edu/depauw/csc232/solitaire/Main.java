package edu.depauw.csc232.solitaire;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.depauw.csc232.solitaire.klondike.KlondikeGame;

/**
 * 
 * @author bhoward
 */
public class Main
{
   public static void main(String[] args)
   {
      Main main = new Main();
      main.start();
   }

   public void start()
   {
      JFrame frame = new JFrame("CSC232 Solitaire");

      Box buttons = Box.createVerticalBox();
      frame.add(buttons);

      JButton klondike = new JButton("Klondike");
      klondike.setAlignmentX(JButton.CENTER_ALIGNMENT);
      klondike.addActionListener(event -> {
         Game game = new KlondikeGame();
         game.start();
      });
      buttons.add(klondike);
      
      JButton quit = new JButton("Quit");
      quit.setAlignmentX(JButton.CENTER_ALIGNMENT);
      quit.addActionListener(event -> {
         frame.dispose();
      });
      buttons.add(quit);

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(300, 200);
      frame.setVisible(true);
   }
}
