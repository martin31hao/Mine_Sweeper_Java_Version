package mineSweeper;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class CreateUI extends JFrame implements MouseListener, ActionListener{
	//private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
	private JLayeredPane jLayerPa;  
	private JPanel panelAbove;
	private JPanel panelUnder;
	private JButton[][] buttons; 
	private JLabel[][] labels;
	private GridLayout gLayout;
	private MenuBar menuBar;
	private Menu gameMenu, aboutMenu ;
	private MenuItem newGame, exitGame, aboutAuthor; 
	private boolean[][] isButton;
	private int dir[][] = {{1, 0}, {1, 1}, {1, -1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
	private int notBomb;
	private boolean lose;
	private boolean firstClick;
	private boolean isFlag[][];
	private CreateMine newMine;
	
	public CreateUI(){
		
		initializeMine();
		
		mainFrame = new JFrame("MineSweeper");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set up menus
		menuBar = new MenuBar();
		gameMenu = new Menu("Game");
		aboutMenu = new Menu("About");
		newGame = new MenuItem("New Game");
		exitGame = new MenuItem("Exit Game");
		aboutAuthor = new MenuItem("About the author");
		
		gameMenu.add(newGame);
		gameMenu.add(exitGame);
		aboutMenu.add(aboutAuthor);
		
		menuBar.add(gameMenu);
		menuBar.add(aboutMenu);
		
		mainFrame.setMenuBar(menuBar);
		
		newGame.addActionListener(this);
		exitGame.addActionListener(this);
		aboutAuthor.addActionListener(this);
		
		// set grid layout of mine
		setGridLayout();
		
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		jLayerPa = new JLayeredPane();
		jLayerPa.setPreferredSize(new Dimension(360, 360));
		//jLayerPa.setBorder(BorderFactory.createTitledBorder("Let's play MINESWEEPER!"));
		jLayerPa.addMouseListener(this);
		panelUnder = new JPanel();
		panelAbove = new JPanel();
		
		//set panel layout to grid layout
		panelUnder.setLayout(gLayout);
		panelAbove.setLayout(gLayout);
		panelUnder.setBounds(0, 0, 360, 360);
		panelAbove.setBounds(0, 0, 360, 360);
		panelAbove.setOpaque(false);

		setLabelsAndButtons();
		
		jLayerPa.add(panelUnder, new Integer(0));
		jLayerPa.add(panelAbove, new Integer(1));
		
        //add(Box.createRigidArea(new Dimension(0, 10)));
        //add(jLayerPa);
		
		mainFrame.getContentPane().add(jLayerPa, BorderLayout.CENTER);
        
        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
	}
	
	//set labels and buttons
	public void setLabelsAndButtons(){
		labels = new JLabel[9][9];
		buttons = new JButton[9][9];
		for(int i = 0; i < 9; i ++){
			for(int j = 0; j < 9; j ++){		
				labels[i][j] = new JLabel(String.valueOf(newMine.getNum(i, j)));
				//labels[i][j].setPreferredSize(new Dimension(40, 40));
				labels[i][j].setVerticalAlignment(JLabel.CENTER);
		        labels[i][j].setHorizontalAlignment(JLabel.CENTER);
				labels[i][j].setOpaque(true);
				/*labels[i][j].setBackground(Color.white);
		        labels[i][j].setForeground(Color.black);*/
				//labels[i][j].setBounds(j  * 40, i * 40, 40, 40);
				//labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				panelUnder.add(labels[i][j]);
				//panel.add(labels[i][j]);
				//jLayerPa.add(labels[i][j], new Integer(0));
				
				buttons[i][j] = new JButton("");
				//buttons[i][j].setPreferredSize(new Dimension(40, 40));
				buttons[i][j].setVerticalAlignment(JLabel.CENTER);
		        buttons[i][j].setHorizontalAlignment(JLabel.CENTER);
				buttons[i][j].setOpaque(true);
				/*buttons[i][j].setBackground(Color.gray);
		        buttons[i][j].setForeground(Color.black);*/
				//buttons[i][j].setBounds(j  * 40, i * 40, 40, 40);
				buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				//panel.add(buttons[i][j]);
				buttons[i][j].addMouseListener(this);
				panelAbove.add(buttons[i][j]);
				//jLayerPa.add(buttons[i][j], new Integer(1));
				//buttons[i][j].addMouseListener(this);
				isButton[i][j] = true;
			}
		}
	}
	
	// set performances of mouse-clicking actions
	public void mouseClicked(MouseEvent e) {
		Component tmp = e.getComponent();
		if(e.getButton() == MouseEvent.BUTTON1){
			for(int i = 0; i < 9; i ++){
				for(int j = 0; j < 9; j ++){
					if(isButton[i][j] == true && tmp == buttons[i][j]){
						if(isFlag[i][j] == true){
							return;
						}
						if(firstClick == true){
							if(labels[i][j].getText().equals("-1")){
								newMine = new CreateMine(i, j);
								setLabelsAgain();
							}
							firstClick = false;
							showLabel(i, j);
							return ;
						}
						if(labels[i][j].getText().equals("-1")){
							lose = true;
							return;
						}
						showLabel(i, j);
						break;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			for(int i = 0; i < 9; i ++){
				for(int j = 0; j < 9; j ++){
					if(isButton[i][j] == true && tmp == buttons[i][j]){
						setButtonFlag(i, j);
					}
				}
			}
		}
	}
	
	public void setButtonFlag(int rowNum, int colNum){
		if(isFlag[rowNum][colNum] == false){
			buttons[rowNum][colNum].setBackground(Color.red);
			isFlag[rowNum][colNum] = true;
		}
		else{
			buttons[rowNum][colNum].setBackground(null);
			isFlag[rowNum][colNum] = false;
		}
	}
	
	// reset all the numbers under the mine
	public void setLabelsAgain(){
		for(int i = 0; i < 9; i ++){
			for(int j = 0; j < 9; j ++){
				labels[i][j].setText(String.valueOf(newMine.getNum(i, j)));
			}
		}
	}
	
	public void setButtonsAgain(){
		for(int i = 0; i < 9; i ++){
			for(int j = 0; j < 9; j ++){
				buttons[i][j].setBackground(null);
				buttons[i][j].setOpaque(true);
				buttons[i][j].setContentAreaFilled(true); 
			}
		}
	}
	
	// check whether the point is legal
	public boolean boundCheck(int rowNum, int colNum){
		if(rowNum >= 0 && colNum >= 0 && rowNum < 9 && colNum < 9){
			return true;
		}
		return false;
	}
	
	// show grid recursively when its surrounding is not bomb
	public void showLabel(int rowNum, int colNum){
		setButtonClear(rowNum, colNum);
		String labelText = labels[rowNum][colNum].getText();
		labels[rowNum][colNum].setVisible(true);
		if(labelText.equals("0")){
			labels[rowNum][colNum].setVisible(false);
		}
		isButton[rowNum][colNum] = false;
		notBomb ++;
		if(notBomb == 71){
			return ;
		}
		if(labelText.equals("0")){
			for(int i = 0; i < 8; i ++){
				int newRow = dir[i][0] + rowNum;
				int newCol = dir[i][1] + colNum;				
				if(boundCheck(newRow, newCol) == true && isButton[newRow][newCol] == true){
					if(labels[newRow][newCol].getText().equals("-1") == false){
						showLabel(newRow, newCol);
					}
				}
			}
		}
	}
	
	// action of the menubars
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGame){			
			initializeMine();
			setButtonsAgain();
			setLabelsAgain();
		}
		if(e.getSource() == exitGame){
			resetGame();
		}
		if(e.getSource() == aboutAuthor){
			JOptionPane.showMessageDialog(null, "Author: Xinkai Wang @ Wuhan University\n\n" +
					"Contact me: martin31hao@gmail.com");
		}
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public void initializeMine(){
		int initRow = (int) (10 * Math.random()) % 9;
		int initCol = (int) (10 * Math.random()) % 9;
		newMine = new CreateMine(initRow, initCol);
		isFlag = new boolean[9][9];
		isButton = new boolean[9][9];
		for(int i = 0; i < 9; i ++){
			for(int j = 0; j < 9; j ++){
				isFlag[i][j] = false;
				isButton[i][j] = true;
			}
		}
		notBomb = 0;
		lose = false;
		firstClick = true;
	}
	
	// set lay out of grid
	public void setGridLayout(){
		gLayout = new GridLayout(9, 9);
		gLayout.setHgap(0);
		gLayout.setVgap(0);
	}
	
	// judge whether the user has won
	public boolean judgeWin(){
		if(notBomb == 71){
			JOptionPane.showMessageDialog(null, "You win!");
			return true;
		}
		return false;
	}
	
	// judge whether the user has lost
	public boolean judgeLose(){
		if(lose == true){
			JOptionPane.showMessageDialog(null, "You lose!");
			return true;
		}
		return false;
	}
	
	public void setButtonClear(int rowNum, int colNum){
		buttons[rowNum][colNum].setOpaque(false);
		buttons[rowNum][colNum].setContentAreaFilled(false); 
	}
	
	public void setButtonOpaque(int rowNum, int colNum){
		buttons[rowNum][colNum].setOpaque(true);
		buttons[rowNum][colNum].setContentAreaFilled(true); 
	}
	
	public void resetGame(){
		mainFrame.dispose();
	}
}
