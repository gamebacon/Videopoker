package net.gamebacon.videopoker;

import net.gamebacon.videopoker.builder.JBuilder;
import net.gamebacon.videopoker.util.Sound;
import net.gamebacon.videopoker.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class VideoPoker extends JFrame implements KeyListener {

	private final JButton aboutButton;
	private final JButton actionButton;
	private final JButton increaseButton;
	private final JButton decreaseButton;

	private final Font font = new Font("VCR OSD Mono", Font.BOLD, 15);
	private final Font biggerFont = new Font("VCR OSD Mono", Font.BOLD, 35);

	private CardLayout cardLayout = new CardLayout();

	private final JLabel infoText;

	private final JTextField betText;
	private final JTextField winAmountText;
	static JTextField balanceText;

	volatile boolean yolo = true;

	int betLevel = 1;
	int winAmount = 0;
	static int balance;
	
	static CardContainer[] cardContainer = new CardContainer[5];


	private Board board;
	private Game game;

	private final Save save;

	public VideoPoker()	{


		save = new Save();
		try {
			balance = save.getDBMoney();//save.load();
			if(balance == -1)
				balance = Game.STARTING_BALANCE;
		} catch (SQLException throwables) {
			System.out.println("error retrieving money from DB");
			throwables.printStackTrace();
			balance = Game.STARTING_BALANCE;
		}

		setLayout(cardLayout);
		setSize(800,700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("VideoPoker");
		addKeyListener(this);	
		setFocusable(true);


		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/pixelfont.ttf")));
			//System.out.println(Arrays.toString(homo.getAvailableFontFamilyNames()).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error with font");
		}

		aboutButton = (JButton) JBuilder.init(JButton.class)
				.text("Info")
				.actionListener(e -> System.out.println("info"))
				.preferredSize(new Dimension(250, 50))
				.font(biggerFont)
				.keyListener(this)
				.bulid();

		actionButton = (JButton) JBuilder.init(JButton.class)
				.text("Deal")
				.actionListener(new ButtonActionListener())
				.preferredSize(new Dimension(250, 50))
				.font(biggerFont)
				.keyListener(this)
				.bulid();

		increaseButton = (JButton) JBuilder.init(JButton.class)
				.text("^")
				.actionListener(new UpButtonActionListener())
				//.preferredSize(new Dimension(50, 50))
				.keyListener(this)
                .opaque(true)
				.bulid();

		decreaseButton = (JButton) JBuilder.init(JButton.class)
				.text("v")
				.actionListener(new DownButtonActionListener())
				//.preferredSize(new Dimension(50, 50))
				.keyListener(this)
				.opaque(true)
				.bulid();

		board = new Board(this);

		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Util.mainColor);
		boardPanel.add(board);

		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(Util.mainColor);
		cardPanel.setLayout(new BoxLayout(cardPanel, 2));

		for(int i = 0; i < 5; i++) {
			cardContainer[i] = new CardContainer(this);
			cardPanel.add(cardContainer[i]);
		}

		infoText = new JLabel("VideoPoker", SwingConstants.CENTER);
		infoText.setFont(biggerFont);


		JPanel betButtonPanel = new JPanel();
		betButtonPanel.setLayout(new BoxLayout(betButtonPanel, 1));
		betButtonPanel.add(increaseButton);
		betButtonPanel.add(decreaseButton);

		betText = (JTextField) JBuilder.init(JTextField.class)
                .text("1")
				.columns(4)
				.editable(false)
				.foreground(Color.black)
				.background(Color.white)
                .font(font)
				.bulid();

		winAmountText = (JTextField) JBuilder.init(JTextField.class)
				.text("0")
				.columns(4)
				.editable(false)
				.foreground(Color.black)
				.background(Color.white)
				.font(font)
				.bulid();

		balanceText = (JTextField) JBuilder.init(JTextField.class)
				.text(Integer.toString(balance))
				.columns(4)
				.editable(false)
				.foreground(Color.black)
				.background(Color.white)
				.font(font)
				.bulid();



		Object[][] table = {
			{new JLabel("Win"), winAmountText },
			{new JLabel("Bet"), betText },
			{new JLabel("Credit"), balanceText }
		};

		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(3, 2, 5, 5));
		numberPanel.setBackground(Util.mainColor);

		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				numberPanel.add((JComponent) table[i][j]);
			}
		}

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Util.mainColor);
		// bottomPanel.add(aboutButton);
		bottomPanel.add(actionButton);
		bottomPanel.add(betButtonPanel);
		bottomPanel.add(numberPanel);

		JPanel mainPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(3, 0,0,30);
		mainPanel.setLayout(gridLayout);
		mainPanel.setLayout(new BoxLayout(mainPanel, 1));
		mainPanel.add(boardPanel);//topPanel);
		mainPanel.add(cardPanel);//)middlePanel);
		mainPanel.add(bottomPanel);
		mainPanel.addKeyListener(this);
		mainPanel.setBackground(Util.mainFrameColor);
		mainPanel.setBorder(BorderFactory.createLineBorder(Util.mainFrameColor, 9, true));


		add(mainPanel);
		add(new AboutPanel());



		trySetIconImage();


		addWindowListener(new WindowAdapter() {
		    @Override
            public void windowClosing(WindowEvent event) {
				try {
					save.saveMoneyToDB(balance);
				} catch (SQLException throwables) {
					System.out.println("error saving balance to DB");
					throwables.printStackTrace();
				}
			}
		});


		setVisible(true);
	}

	private void trySetIconImage() {
		//get the icon image
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/icon.png"));

		try {
			Class.forName("java.awt.Taskbar");
		} catch (ClassNotFoundException e) {
			setIconImage(image); //if not working, set with default approach
			return;
        }

		// only supported for version >= 9 ?
        try {
			Taskbar.getTaskbar().setIconImage(image); //set the image for unsupported OS
			Taskbar.getTaskbar().setMenu(new PopupMenu("VideoPoker"));
		} catch (UnsupportedOperationException ex) {
			setIconImage(image); //if not working, set with default approach
		}
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
			if(isGameOver())
				updateBet(num);
			else
				cardContainer[num-1].toggleSelect(true);
		}
	}

	public void keyPressed(KeyEvent keyEvent) {
	}
	public void keyTyped(KeyEvent keyEvent) {}

	public boolean isGameOver() {
		return game == null || game.gameover;
	}


	class UpButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(isGameOver() && betLevel < 5) {
				updateBet(betLevel+1);
			}
		}
	}
	class DownButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(isGameOver() && betLevel > 1) {
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
		infoText.setText("...");
		winAmountText.setText("0");
		balanceText.setForeground(Color.black);
		game = new Game();
		infoText.setText(game.currentHand);
		actionButton.setText("Deal");
	}

	synchronized void finishGame() {
		game.finish();	
		infoText.setText(game.currentHand);
		if(game.handValue <= 8) {
			winAmount = board.getWin(game.handValue, betLevel);//Integer.parseInt(board.rowData[game.handValue][betLevel]);
			winAmountText.setText(Integer.toString(winAmount));
			updateBalance(winAmount, true);
			board.displayWin();
		}
		else
			infoText.setText(infoText.getText() + " (no win)");
		actionButton.setText("New Game");
		game = null;
	}


	private void action() {
		yolo = false;
		new Thread((() -> {
			if(game != null) {
				finishGame();
			} else if(balance >= getBet(betLevel)) {
				board.reset();
				startGame();
			} else {
				balanceText.setForeground(Color.red);
			}

			yolo = true;
		})).start();
	}


	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(yolo) {
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

	public int getCurrentHandValue() {
		return game.handValue;
	}

	public int getBetLevel() {
		return betLevel;
	}



}

