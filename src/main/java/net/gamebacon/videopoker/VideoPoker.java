package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;

class VideoPoker extends JFrame implements KeyListener {
	private final JButton actionButton;
	private final JButton increaseButton;
	private final JButton decreaseButton;


	private final JLabel infoText;
	
	private final JTextField betText;
	private final JTextField winAmountText;
	static JTextField balanceText;

	volatile boolean homo = true;

	int betLevel = 1;
	int winAmount = 0;
	static int balance;
	
	static CardContainer[] cardContainer = new CardContainer[5];

	private Font font;
	private Board board;
	private Game game;

	private Save save;
		
	public VideoPoker()	{
		//image-icons not working with macOS?
		/*
		try {
		    Image image = ImageIO.read(new File("src/main/resources/images/icon.jpg"));
		    setIconImage(image);
		} catch (IOException e) {
			System.out.println("no wok");
			e.printStackTrace();
		}
		 */

		save = new Save();
		balance = 100;//save.load();

		setSize(965,655);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("VideoPoker");
		addKeyListener(this);	
		setFocusable(true);

		try {
			GraphicsEnvironment homo = GraphicsEnvironment.getLocalGraphicsEnvironment();
			homo.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/pixelfont.ttf")));
			//System.out.println(Arrays.toString(homo.getAvailableFontFamilyNames()).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error with font");
		}

		font = new Font("VCR OSD Mono", Font.BOLD, 15);
		Font biggerFont = new Font("VCR OSD Mono", Font.BOLD, 35);

		Color blueColor = new Color(80,80,255);
		Color greenColor = new Color(0, 175, 110);
		Color redColor = new Color(190, 0, 0);
		Color panelBackgroundColor = new Color(0, 50, 105);

		Border numberBorder = BorderFactory.createLineBorder(Color.black, 3);
		
		actionButton = new JButton("Deal");
		actionButton.addActionListener(new ButtonActionListener());	
		actionButton.setPreferredSize(new Dimension(100, 50));
		actionButton.setFont(font);	;
		actionButton.addKeyListener(this);	;

		increaseButton = new JButton("^");
		increaseButton.addActionListener(new UpButtonActionListener());	
		increaseButton.setPreferredSize(new Dimension(30, 30));
		increaseButton.addKeyListener(this);	;
		increaseButton.setOpaque(true);
		//increaseButton.setIcon(new ImageIcon("images/arrow.png"));
		
		decreaseButton = new JButton("v");
		decreaseButton.addActionListener(new DownButtonActionListener());	
		decreaseButton.setPreferredSize(new Dimension(30, 30));
		decreaseButton.addKeyListener(this);	;
		
		board = new Board();


		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.blue);
		boardPanel.add(board);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, 1));
		topPanel.setBackground(blueColor);
		topPanel.add(boardPanel);

		JPanel cardPanel = new JPanel(); 
		cardPanel.setBackground(Color.yellow);
		cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		for(int i = 0; i < 5; i++) {
			cardContainer[i] = new CardContainer(i+1);
			cardPanel.add(cardContainer[i]);
		}

		infoText = new JLabel("VideoPoker", SwingConstants.CENTER);
		infoText.setFont(biggerFont);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout(10,10));
		middlePanel.setBackground(greenColor);
		middlePanel.add(infoText, BorderLayout.NORTH);
		middlePanel.add(cardPanel, BorderLayout.CENTER);


		JPanel betButtonPanel = new JPanel();
		betButtonPanel.setLayout(new BoxLayout(betButtonPanel, 1));
		betButtonPanel.add(increaseButton);
		betButtonPanel.add(decreaseButton);

		betText = new JTextField("1", 4);
		betText.setEditable(false);
		betText.setFont(font);
		betText.setForeground(Color.black);
		betText.setBackground(Color.white);
		//betText.setBorder(numberBorder);

		winAmountText = new JTextField("0", 4);
		winAmountText.setEditable(false);
		winAmountText.setFont(font);
		winAmountText.setForeground(Color.black);
		winAmountText.setBackground(Color.white);
		//winAmountText.setBorder(numberBorder);
		
		balanceText = new JTextField(Integer.toString(balance), 4);
		balanceText.setEditable(false);
		balanceText.setFont(font);
		balanceText.setForeground(Color.black);
		balanceText.setBackground(Color.white);
		//balanceText.setBorder(numberBorder);

		Object[][] table = {
			{new JLabel("Win"), winAmountText },
			{new JLabel("Bet"), betText },
			{new JLabel("Credit"), balanceText }
		};

		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(3, 2, 5, 5));
		numberPanel.setBackground(greenColor);
		numberPanel.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		
		for(int i = 0; i < 3; i++) 
			for(int j = 0; j < 2; j++) 
				numberPanel.add((JComponent) table[i][j]);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(redColor);
		bottomPanel.add(actionButton);
		bottomPanel.add(betButtonPanel);
		bottomPanel.add(numberPanel);
	
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, 1));
		mainPanel.add(topPanel);
		mainPanel.add(middlePanel);
		mainPanel.add(bottomPanel);
		mainPanel.addKeyListener(this);	
			
		add(mainPanel);	



		setVisible(true);
	}

	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == 32)
			actionButton.doClick();
		if(keyEvent.getKeyCode() == 38 && betLevel < 5)
			increaseButton.doClick();
		if(keyEvent.getKeyCode() == 40 && betLevel > 1)
			decreaseButton.doClick();
		if(keyEvent.getKeyCode() > 48 && keyEvent.getKeyCode() < 54) {
			int num = Integer.parseInt(keyEvent.getKeyText(keyEvent.getKeyCode()));
			if(game.gameover)
				updateBet(num);
			else
				cardContainer[num-1].toggleSelect(true);
		}
	}

	public void keyPressed(KeyEvent keyEvent) {
	}
	public void keyTyped(KeyEvent keyEvent) {}
	

	class UpButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(game.gameover && betLevel < 5) {
				updateBet(betLevel+1);
			}
		}
	}
	class DownButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(game.gameover && betLevel > 1) {
				updateBet(betLevel-1);
			}
		}
	}

	void updateBet(int level) {
		if(level < 1 || level > 5 || (level > betLevel && getBet(level) > balance))
			return;

		betLevel = level;
		betText.setText(Integer.toString(getBet(betLevel)));
		board.switchBettingLevel(betLevel);
		Sound.playSound("click", 1);
		if(getBet(betLevel) <= balance)
			balanceText.setForeground(Color.black);
	}	
	
	int getBet(int betlvl) {
		return (betlvl > 4 ? 10 : betlvl > 3 ? 5 : betlvl);
	}

	synchronized void updateBalance(int amount, boolean add) {
		if(add)
			balance += amount;
		else
			balance -= amount;
		balanceText.setText(Integer.toString(balance));
	}

	void startGame() {
		updateBalance(getBet(betLevel), false);
		infoText.setText("");
		winAmountText.setText("0");
		balanceText.setForeground(Color.black);
		game = new Game();
		infoText.setText(game.currentHand);
		actionButton.setText("Finsih");
	}

	synchronized void finishGame() {
		game.finish();	
		infoText.setText(game.currentHand);
		if(game.handValue <= 8) {
			board.displayWin(game.handValue, betLevel); 
			winAmount = Integer.parseInt(board.rowData[game.handValue][betLevel]);
			winAmountText.setText(Integer.toString(winAmount));
			updateBalance(winAmount, true);
		}
		else
			infoText.setText(infoText.getText() + " (no win)");
		actionButton.setText("New Game");
		game = null;
	}

	private void resetBet() {
		if(betLevel > 1 && getBet(betLevel) > balance)
			for (int i = betLevel; i > 0; i--) {
				if (getBet(i) <= balance) {
					updateBet(i);
					break;
				}
			}
	}
	
	private void action() {
		homo = false;
		new Thread((new Runnable(){
			public void run() {
				if(game != null)
					finishGame();
				else if(balance >= getBet(betLevel))
					startGame();
				else
					balanceText.setForeground(Color.red);
				homo = true;
			}
		})).start();
	}
	
	
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(homo) {
				action();
				Sound.playSound("click", 1);
			}
		}
	}	

	void setImage(String path, JButton destination, Dimension size) {
		try {                
        	//image = ImageIO.read(new File(path));
		} catch (Exception ex) {System.out.println("no wok loading " + path);}
		destination.setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(size.height, size.width, Image.SCALE_DEFAULT)));
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				VideoPoker videopoker = new VideoPoker();
			}
		});
	}
}

