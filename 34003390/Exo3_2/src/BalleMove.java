import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;

 
public class BalleMove extends JFrame {
   private JButton Demarre = new JButton("Start");
   private JButton Quitter = new JButton("Quitter");
   private JButton Stop = new JButton("Stop");
   private JButton Moins = new JButton("-");
   private JButton Plus = new JButton("+");
   private JPanel boutons = new JPanel();
   private Panneau pannel = new Panneau();
 
   public BalleMove() {
      super("Jeux de balle");
      pannel.setBackground(Color.GREEN);
      add(pannel);
      add(boutons, BorderLayout.SOUTH);
      boutons.add(Demarre);
      boutons.add(Quitter);
      boutons.add(Moins);
      boutons.add(Plus);

      /* Bouton Start */
      Demarre.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String texte;
				texte=Demarre.getText();
				if(texte.compareTo("Start")==0)
				{
					Demarre.setText("Stop ");
					ajoutBalle();
				}
				else if(texte.compareTo("Stop ")==0)
				{
					Demarre.setText("Start");
					pauseBalle();;
				}
			}
		});
		/* Bouton Stop */
		Stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String texte;
				texte=Demarre.getText();
				if(texte.compareTo("Start")==0)
				{
					ajoutBalle();

				}
			}
		});
		/* Bouton Quitter */
      Quitter.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      /* Bouton + */
      Plus.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	ajoutBalle();
          }
       });
      /* Bouton - */
      Moins.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	retireBalle();
          }
       });

      /*Fenetre*/
      setSize(400, 400);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
   }
 
   private void ajoutBalle() {
      Balle balle = new Balle();      
      pannel.ajout(balle);
      new Thread(new BalleSeparee(balle)).start();
   }
   	
   private void pauseBalle() {  
	   pannel.pause();

	   }
   private void retireBalle() {  
	   pannel.retire();

	   }

 
   
   
   private class BalleSeparee implements Runnable {
      private Balle balle;
 
      public BalleSeparee(Balle balle) {
          this.balle = balle;
      }
 
      public void run() {
         try {
            while(true){
               balle.deplace(pannel.getBounds());
               pannel.repaint();
               Thread.sleep(10);
            }
         }
         catch (InterruptedException ex) { }
      }
   }
 
   private class Panneau extends JPanel {

      private ArrayList<Balle> balles = new ArrayList<Balle>();
	  
	  private void pause() {
		    balles.clear();
		    }
		
	
      public void ajout(Balle balle) {
         balles.add(balle);
 
      }
      public void retire(){
    	  balles.remove(0);
      }
 
      protected void paintComponent(Graphics g) {
    	 int R = (int)(Math.random()*256);
    	 Random rand = new Random();
    	 Color randomColor = new Color(R);
         super.paintComponent(g);
         Graphics2D surface = (Graphics2D) g;
         g.setColor(randomColor);
         for (Balle balle : balles) surface.fill(balle.getForme());
 
      }
   }
 
   private class Balle {
      private double x, y, dx=5, dy=5;
 
 
      public void deplace(Rectangle2D zone) {
         x+=dx;
         y+=dy;
         if (x < zone.getMinX()) { x = zone.getMinX();  dx = -dx; }
         if (x+15 >= zone.getMaxX()) { x = zone.getMaxX() - 15;  dx = -dx; }
         if (y < zone.getMinY()) { y = zone.getMinY();  dy = -dy; }
         if (y+15 >= zone.getMaxY()) { y = zone.getMaxY() - 15;  dy = -dy; }
      }
 
      public Ellipse2D getForme() {
         return new Ellipse2D.Double(x, y, 30, 30);
      }
   }
 
   public static void main(String[] args) { 
	   new BalleMove(); }
}
