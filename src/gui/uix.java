package gui;
import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import Game.Card;
import Klient.Client;
public class uix extends JFrame implements ActionListener {
	Client client;
	  JPanel pane = new JPanel();
	  JButton pressme = new JButton("Press Me");
	  BufferedImage myPicture;
	  JLabel l1;
	  JLabel picLabel;
	  String imageName;
	  ImageIcon icon;
	  JLabel copyLabel;
	  JLabel MainContent = new JLabel(new ImageIcon("bg.jpg"));
	  JPanel myCards = new JPanel();
	  JPanel pile = new JPanel();
	  JPanel opponent1 = new JPanel();
	  JPanel opponent2 = new JPanel();
	  //
	  public uix(Client client)        // the frame constructor
	  {
		  
	    //super("JPrompt Demo"); setBounds(100,100,300,200);
		super("UNO kaardi mäng");
		this.client = client;
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    makeDesign();
	    /*List<Card> miks = new ArrayList<Card>();
	    startGame(miks);*/
	   // MainContent.setLayout((new BoxLayout(MainContent, BoxLayout.PAGE_AXIS)));
	    /*MainContent.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        /*JLabel label9 = new JLabel("label1");
        JLabel label99 = new JLabel("label2");
        MainContent.add(label9, gbc);
        MainContent.add(label99, gbc);
	    //Container con = this.getContentPane(); // inherit main frame
	    //con.add(pane);    // JPanel containers default to FlowLayout
	    //pressme.setMnemonic('P'); // associate hotkey to button
	    //pane.add(pressme); pressme.requestFocus();
	    MainContent.setBackground(new Color(0,0,0,96));
	    JLabel waitOthers = new JLabel("OOTAN TEISI MÄNGIJAID...");
	    waitOthers.setForeground(Color.white);
	    //waitOthers.setVerticalAlignment(JLabel.CENTER);
	    waitOthers.setFont(new Font("Arial", Font.PLAIN, 36));
	    MainContent.add(waitOthers, gbc);*/
	    //BufferedImage myPicture;
	    setVisible(true); // make frame visible
	    //setOpacity(0.4f);
		//setLayout(new BorderLayout());
		//setContentPane(new JLabel(new ImageIcon("bg.jpg")));
		//setLayout(new FlowLayout());
	    //setSOUTH();
		l1=new JLabel("Here is a button");
		JButton b1=new JButton("SEE JÄÄB THJAKS");
		JButton b2=new JButton("SIIA ESIMESE VASTASE KAARDID");
		JButton b3=new JButton("SIIA TEISE VASTASE KAARDID");
		JButton b4=new JButton("SIIA PILE");
		JButton b5=new JButton("PILE");
		//JPanel maks = new JPanel();
		JPanel pile = new JPanel();
		JPanel pile2 = new JPanel();
		JPanel player1 = new JPanel();
		JPanel player2 = new JPanel();
		//maks.setBackground(new Color(0,0,0,64));
		pile.setBackground(new Color(0,0,0,0));
		player1.setBackground(new Color(0,0,0,88));
		player2.setBackground(new Color(0,0,0,88));
		ImageIcon icon2 = new ImageIcon("RED WILD.jpg");
        JLabel copyLabel2 = new JLabel(icon2);
        ImageIcon icon3 = new ImageIcon("RED WILD.jpg");
        JLabel copyLabel3 = new JLabel(icon3);
        ImageIcon icon4 = new ImageIcon("RED WILD.jpg");
        JLabel copyLabel4 = new JLabel(icon4);
        pile.setLayout(new BoxLayout(pile, BoxLayout.PAGE_AXIS));
        JLabel label2 = new JLabel("Viimati käidud kaart!");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pile.add(label2);
        pile.add(copyLabel2); 
        player1.setLayout(new BoxLayout(player1, BoxLayout.PAGE_AXIS));
        JLabel label1 = new JLabel("Esimese mängija nimi");
        label1.setVerticalTextPosition(JLabel.TOP);
        label1.setForeground(Color.white);
        copyLabel3.setVerticalTextPosition(JLabel.BOTTOM);
        player1.add(label1); 
        player1.add(copyLabel3); 
        player2.setLayout(new FlowLayout());
        player2.add(copyLabel4); 
		//icon = new ImageIcon("RED ZERO.jpg");
       // copyLabel = new JLabel(icon);
        //maks.setLayout(new FlowLayout(FlowLayout.LEFT));
       // maks.add(copyLabel);  
		b1.addActionListener(this); 
		//JLabel content = new JLabel(new ImageIcon("bg.jpg"));
        //MainContent.setLayout(new BorderLayout());
		//add(l1);
		//content.add(b1, BorderLayout.NORTH);
		/*content.add(maks, BorderLayout.SOUTH);
		content.add(player1, BorderLayout.WEST);
		content.add(player2, BorderLayout.EAST);
		content.add(pile, BorderLayout.CENTER);
		setContentPane(content);*/
		//setContentPane(new JLabel(new ImageIcon("bg.jpg")));
        setContentPane(MainContent);
        setTitle("UNO GAME");
		// Just for refresh :) Not optional!
		
	    
		/*try {
			myPicture = ImageIO.read(new File("GREEN SKIP.jpg"));
		    picLabel = new JLabel(new ImageIcon( myPicture ));
		    add( picLabel );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
        /*
         * Create an icon from an image using
         * ImageIcon(String imagePath, String description)
         * constructor.
         */
       
       /* icon = new ImageIcon("RED ZERO.jpg");
        copyLabel = new JLabel(icon);
        add(copyLabel);  */           
		setSize(1000,700);
		//setSize(600,800);
	   
	  }
	/**
	 * Killcardi uuendamine (CENTER)
	 * @param cardName
	 */
	public void changekillCard(String cardName){
		  String picture = cardName + ".jpg";
		  icon = new ImageIcon(picture);
		copyLabel.setIcon(icon);
		this.repaint();
		this.validate();
			
	  }
	  
	  /** 
	   * Käes olevate kaartide uuendamine (SOUTH)
	   * @param cards
	   */
	public void changeHand(List<Card> cards) {
		  for(Card card : cards) {
			  //card.getName();
			  ImageIcon url = new ImageIcon(card.getName()+".jpg");
			  JLabel image;
			  //REPAINT
		  }
	  }
	  
	  public void addPlayers(List<String> players) {
		  //mängijate nimede lisamine
	  }
	/**
	 * Esialgne disain, kui oodatakse mängijate liitumist.
	 */
	public void makeDesign() {
	    MainContent.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
	    JLabel waitOthers = new JLabel("OOTAN TEISI MÄNGIJAID...");
	    waitOthers.setForeground(Color.white);
	    waitOthers.setFont(new Font("Arial", Font.PLAIN, 36));
	    MainContent.add(waitOthers, gbc);
	  }
	
	public void startGame(List<Card> cards) {
		this.getContentPane().removeAll();
		MainContent.setLayout(new BorderLayout());
		this.repaint();
		setSOUTH(cards);
		setCENTER();
		setContentPane(MainContent);
		this.repaint();
		this.validate();
	}
	/**
	 * Mängija kaartide ala uuendamine (SOUTH)
	 */
	public void setSOUTH(List<Card> cards){
		myCards.setLayout(new WrapLayout());
		myCards.setBackground(new Color(0,0,0,64));
		//myCards.setLayout(new FlowLayout(FlowLayout.LEFT));
		for(Card card : cards) {
			final Card fcard = card;
			ImageIcon icon = new ImageIcon(card.getName()+".jpg");
		    JLabel copyLabel = new JLabel(icon);	   
		    copyLabel.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {
		    		client.playCard(fcard);
		            System.out.println(fcard.getName());
		            myCards.removeAll(); ;
		        }
		    });
		    myCards.add(copyLabel);
		    
		    
		}
		JScrollPane scrollPane = new JScrollPane();
		  scrollPane = new JScrollPane(myCards);
		  scrollPane.setBackground(new Color(0,0,0,0));
		  scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		  scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		  scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		  scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		  scrollPane.setPreferredSize(new Dimension(1000,200));
		  scrollPane.revalidate();
		  MainContent.add(scrollPane, BorderLayout.SOUTH);
		//MainContent.add(myCards, BorderLayout.SOUTH);
        //maks.add(copyLabel);
	}
	
	/**
	 * KillCard ala uuendamine (CENTER)
	 */
	public void setCENTER(){
		ImageIcon icon2 = new ImageIcon("UNO CARD.jpg");
        JLabel copyLabel2 = new JLabel(icon2);
        pile.setBackground(new Color(0,0,0,0));
        pile.setLayout(new BoxLayout(pile, BoxLayout.PAGE_AXIS));
        JLabel label2 = new JLabel("Viimati käidud kaart!");
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
        label2.setForeground(Color.white);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pile.add(label2);
        pile.add(copyLabel2); 
        MainContent.add(pile, BorderLayout.CENTER);
	  }
	/**
	 * Teise vastase ala uuendmaine (WEST)
	 */
	public void setWEST(){
	  
	}
	/**
	 * Teise vastase ala uuendmaine (WEST)
	 */
	public void setEAST(){
	  
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//changekillCard();
		// TODO Auto-generated method stub
		//icon = new ImageIcon("RED WILD.jpg");
		//copyLabel.setIcon(icon);
		/*try {
			myPicture = ImageIO.read(new File("troll.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picLabel = new JLabel(new ImageIcon( myPicture ));
		//add( picLabel );
		this.getContentPane().remove(picLabel);
		//this.getContentPane().add(picLabel);
		add( picLabel );*/
		//this.repaint();
		//this.validate();
	}
	  
}