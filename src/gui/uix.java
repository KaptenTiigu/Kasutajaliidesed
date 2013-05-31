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

import exceptions.UnsuitableCardException;

import Game.Card;
import Game.Player;
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
	  JLabel MainContent = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("images/bg2.jpg")));
	  JPanel myCards = new JPanel();
	  JPanel pile = new JPanel();
	  JPanel opponent1 = new JPanel();
	  JPanel opponent2 = new JPanel();
	  JScrollPane scrollPane;
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
	public void changekillCard(String cardName, List<Player> players){
		//System.out.println("Esimene: "+ players.get(0).getName() +", Teine "+ players.get(1).getName());
		/**
		 * updateWEST(players.get(0));
		 */
		/**
		 * updateEAST(players.get(1));
		 */
		pile.removeAll();		
		ImageIcon icon2 = new ImageIcon((this.getClass().getClassLoader().getResource("images/"+cardName+".jpg")));
        JLabel copyLabel2 = new JLabel(icon2);
        pile.setBackground(new Color(0,0,0,0));
        pile.setLayout(new BoxLayout(pile, BoxLayout.PAGE_AXIS));
        JLabel label2;
        JLabel varv =null;
        JLabel labelpickup;// = new JLabel("KORJA KAARTE!");
        if(client.checkPermission()) {
        	label2 = new JLabel("SINU KÄIK!");
        	if (client.getKillColor() != Card.Color.NONE) {
        		switch(client.getKillColor()) {
        		case RED:
        			varv =  new JLabel("Käi PUNANE kaart!");
        			break;
        		case YELLOW:
        			varv =  new JLabel("Käi KOLLANE kaart!");
        			break;
        		case BLUE:
        			varv =  new JLabel("Käi SININE kaart!");
        			break;
        		case GREEN:
        			varv =  new JLabel("Käi ROHELINE kaart!");
        			break;
				default:
					break;
        		}
        		varv =  new JLabel("Käi " + client.getKillColor() +" kaart!");
        		varv.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
                varv.setForeground(Color.orange);
                varv.setAlignmentX(Component.CENTER_ALIGNMENT);
        	}
        	labelpickup = new JLabel("KORJA KAARTE (VAJUTA SIIA)!");
        	labelpickup.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {	    		
		            		try {
		            			if(client.checkPermission() && !client.getSending()) {
		            				//new PopUp();
		            				client.setPermission(false);
		            				client.setSending(true);
		            				client.pickUpInput();
		            			}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
							}	
		        }
		    });
        }
        else {
        	label2 = new JLabel("Vastase kord!");
        	labelpickup = new JLabel("");
        }
        label2.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        label2.setForeground(Color.white);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelpickup.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        labelpickup.setForeground(Color.white);
        labelpickup.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pile.add(label2);
        pile.add(copyLabel2);
        if(varv != null)pile.add(varv);
        pile.add(labelpickup);
        validate();
        repaint();		
	  }
	  
	  /** 
	   * Käes olevate kaartide uuendamine (SOUTH)
	   * @param cards
	   */
	public void changeHand(List<Card> cards) {
		System.out.println(cards.toString());
		MainContent.remove(scrollPane);
		scrollPane.removeAll();
		myCards.removeAll();
		 //MainContent.add(scrollPane, BorderLayout.SOUTH);
		myCards.setLayout(new WrapLayout());
		myCards.setBackground(new Color(0,0,0,64));
		for(final Card card : cards) {
			final Card fcard = card;
			ImageIcon icon = new ImageIcon((this.getClass().getClassLoader().getResource("images/"+card.getName()+".jpg")));
		    final JLabel copyLabel = new JLabel(icon);	   
		    copyLabel.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {	    		
		            try {
		            	if(client.checkPermission() && !client.getSending()) {
		            		client.setPermission(false);
				            System.out.println(fcard.getName());
				            client.checkCardSuitability(card);
				            client.setSending(true);
		            		if(card.chooseColor()) {
		            			client.setPermission(true);
		            			chooseColor(fcard);
		            		} else {
		            			client.playCard(fcard);
								myCards.remove(copyLabel);
		            		}
							/*validate();
				            repaint();*/
		            	}		
					} catch (UnsuitableCardException e1) {
						// SIIA MIDAGI KUI VALEL AJAL KÄIS.
						client.setPermission(true);
					}
		        }
		    });
		    myCards.add(copyLabel);    
		}
		  scrollPane = new JScrollPane(myCards);
		  scrollPane.setBackground(new Color(0,0,0,0));
		  scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		  scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		  scrollPane.setViewportView(myCards);//.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		  scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		  scrollPane.setPreferredSize(new Dimension(1000,200));
		  scrollPane.revalidate();
		  scrollPane.repaint();
		  MainContent.add(scrollPane, BorderLayout.SOUTH);
		  this.revalidate();
		  this.repaint();
		  
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
	    waitOthers.setFont(new Font("Bookman Old Style", Font.BOLD, 48));
	    MainContent.add(waitOthers, gbc);
	  }
	
	public void startGame(List<String> players, List<Card> cards, int count) {
		this.getContentPane().removeAll();
		MainContent.setLayout(new BorderLayout());
		//this.repaint();
		setSOUTH(cards);
		setCENTER();
		//setWEST(players.get(0), count);
		//setEAST(players.get(1), count);
		setContentPane(MainContent);
		this.validate();
		this.repaint();
	}
	/**
	 * Mängija kaartide ala tegemine (SOUTH)
	 */
	public void setSOUTH(List<Card> cards){
		myCards.setLayout(new WrapLayout());
		myCards.setBackground(new Color(0,0,0,64));
		//myCards.setLayout(new FlowLayout(FlowLayout.LEFT));
		/*for(final Card card : cards) {
			final Card fcard = card;
			ImageIcon icon = new ImageIcon(card.getName()+".jpg");
		    final JLabel copyLabel = new JLabel(icon);	   
		    copyLabel.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {	    		
		            try {
		            	if(client.checkPermission()) {
		            		client.playCard(fcard);
				            System.out.println(fcard.getName());
				            client.checkCardSuitability(card);
							myCards.remove(copyLabel);
							validate();
				            repaint();
				    		//validate();
		            	}		
					} catch (UnsuitableCardException e1) {
						// SIIA MIDAGI KUI VALEL AJAL KÄIS.
						//e1.printStackTrace();
					}
		        }
		    });
		    myCards.add(copyLabel);
		    
		    
		}*/
		
		  scrollPane = new JScrollPane(myCards);
		  /*scrollPane.setBackground(new Color(0,0,0,70));
		  scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		  scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		  scrollPane.setViewportView(myCards);//.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		  scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		  scrollPane.setPreferredSize(new Dimension(1000,200));
		  scrollPane.revalidate();
		  scrollPane.repaint();*/
		  MainContent.add(scrollPane, BorderLayout.SOUTH);
		
	}
	
	/**
	 * KillCard ala uuendamine (CENTER)
	 */
	public void setCENTER(){
		ImageIcon icon2 = new ImageIcon((this.getClass().getClassLoader().getResource("images/UNO CARD.jpg")));
        JLabel copyLabel2 = new JLabel(icon2);
        pile.setBackground(new Color(0,0,0,0));
        pile.setLayout(new BoxLayout(pile, BoxLayout.PAGE_AXIS));
        JLabel label2;
        JLabel labelpickup;// = new JLabel("KORJA KAARTE!");
        if(client.checkPermission()) {
        	label2 = new JLabel("SINU KÄIK!");
        	labelpickup = new JLabel("KORJA KAARTE (VAJUTA SIIA)!");
        	labelpickup.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {	    		
		            		try {
								client.pickUpInput();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}	
		        }
		    });
        }
        else {
        	label2 = new JLabel("Vastase kord!");
        	labelpickup = new JLabel("");
        }
        label2.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        label2.setForeground(Color.white);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelpickup.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        labelpickup.setForeground(Color.white);
        labelpickup.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pile.add(label2);
        pile.add(copyLabel2); 
        pile.add(labelpickup);
        MainContent.add(pile, BorderLayout.CENTER);
	  }
	/**
	 * Teise vastase ala uuendmaine (WEST)
	 */
	public void setWEST(String player, int count){
		JLabel copyLabel4 = new JLabel(player);
		JLabel copyLabel5 = new JLabel("Kaarte: "+count);
		copyLabel4.setForeground(Color.white);
		copyLabel5.setForeground(Color.white);
		copyLabel4.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		copyLabel5.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		//JPanel player2 = new JPanel();
		opponent1.setBackground(new Color(0,0,0,50));
		opponent1.setLayout(new BoxLayout(opponent1, BoxLayout.PAGE_AXIS));
		copyLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		copyLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		opponent1.add(copyLabel4);
		opponent1.add(copyLabel5);
		MainContent.add(opponent1, BorderLayout.WEST);
	}
	/**
	 * Teise vastase ala uuendmaine (WEST)
	 */
	public void setEAST(String player, int count){
		JLabel copyLabel4 = new JLabel(player);
		JLabel copyLabel5 = new JLabel("Kaarte: "+count);
		copyLabel4.setForeground(Color.white);
		copyLabel5.setForeground(Color.white);
		copyLabel4.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		copyLabel5.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		//JPanel player2 = new JPanel();
		opponent2.setBackground(new Color(0,0,0,50));
		opponent2.setLayout(new BoxLayout(opponent2, BoxLayout.PAGE_AXIS));
		copyLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		copyLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		opponent2.add(copyLabel4);
		opponent2.add(copyLabel5);
		MainContent.add(opponent2, BorderLayout.EAST);
	}
	
	public void updateEAST(Player player) {
		opponent2.removeAll();
		JLabel copyLabel4 = new JLabel(player.getName());
		JLabel copyLabel5 = new JLabel("Kaarte: "+ player.getCards().size());
		copyLabel4.setForeground(Color.white);
		copyLabel5.setForeground(Color.white);
		copyLabel4.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		copyLabel5.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		//JPanel player2 = new JPanel();
		opponent2.setBackground(new Color(0,0,0,50));
		opponent2.setLayout(new BoxLayout(opponent2, BoxLayout.PAGE_AXIS));
		copyLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		copyLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		opponent2.add(copyLabel4);
		opponent2.add(copyLabel5);
	}
	public void updateWEST(Player player) {
		opponent1.removeAll();
		JLabel copyLabel4 = new JLabel(player.getName());
		JLabel copyLabel5 = new JLabel("Kaarte: "+ player.getCards().size());
		copyLabel4.setForeground(Color.white);
		copyLabel5.setForeground(Color.white);
		copyLabel4.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
		copyLabel5.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		//JPanel player2 = new JPanel();
		opponent1.setBackground(new Color(0,0,0,50));
		opponent1.setLayout(new BoxLayout(opponent1, BoxLayout.PAGE_AXIS));
		copyLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		copyLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		opponent1.add(copyLabel4);
		opponent1.add(copyLabel5);
		//MainContent.add(opponent1, BorderLayout.WEST);
	}
	
	/**
	 * Värvi valimine
	 */
	public void chooseColor(final Card card) {
		pile.removeAll();		
        pile.setBackground(new Color(0,0,0,0));
        pile.setLayout(new BoxLayout(pile, BoxLayout.PAGE_AXIS));
        JLabel text = new JLabel("VALI VÄRV!");
        text.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        text.setForeground(Color.white);
        JButton red = new JButton("PUNANE");
        red.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	if(client.checkPermission()) {
            		client.setPermission(false);
            		 client.sendGIUwildCard(card, Card.Color.RED);
            	}
                
            }
        }); 
		JButton yellow = new JButton("KOLLANE");
        yellow.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	if(client.checkPermission()) {
            		client.setPermission(false);
            		 System.out.println("VAJUTASID YELLOW NUPPU!");
            		 client.sendGIUwildCard(card, Card.Color.YELLOW);
            	}
                
            }
        }); 
		JButton blue = new JButton("SININE");
        blue.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	if(client.checkPermission()) {
            		client.setPermission(false);
            		System.out.println("VAJUTASID SINIST NUPPU!");
            		client.sendGIUwildCard(card, Card.Color.BLUE);
            	}
                 
            }
        }); 
		JButton green = new JButton("ROHELINE");
        green.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	if(client.checkPermission()) {
            		client.setPermission(false);
            		System.out.println("VAJUTASID ROHELIST NUPPU!");
            		client.sendGIUwildCard(card, Card.Color.GREEN);
            	} 
            }
        }); 
		red.setBackground(Color.RED);
		red.setSize(new Dimension(500, 200));
		yellow.setBackground(Color.YELLOW);
		yellow.setSize(500, 200);
		blue.setBackground(Color.BLUE);
		blue.setSize(500, 200);
		green.setBackground(Color.GREEN);
		green.setSize(500, 200);
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		red.setAlignmentX(Component.CENTER_ALIGNMENT);
		yellow.setAlignmentX(Component.CENTER_ALIGNMENT);
		blue.setAlignmentX(Component.CENTER_ALIGNMENT);
		green.setAlignmentX(Component.CENTER_ALIGNMENT);
		pile.add(text);
        pile.add(red);
        pile.add(yellow);
        pile.add(blue);
        pile.add(green);
        validate();
        repaint();	
	}
	public void win(String winner) {
		this.getContentPane().removeAll();
	    MainContent.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
	    JLabel waitOthers = new JLabel("VÕITIS:     " + winner.toUpperCase());
	    waitOthers.setForeground(Color.white);
	    waitOthers.setFont(new Font("Bookman Old Style", Font.BOLD, 40));
	    MainContent.add(waitOthers, gbc);
	    //waitOthers = new JLabel("WINNER IS:     " + winner.toUpperCase());
		this.validate();
		this.repaint();
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
