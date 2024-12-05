package snakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements ActionListener, Game {

	static final int panelHeight = 500;
	static final int panelWidth = 500;
	static final int unit_size = 20;
	static final int num_of_units = (panelHeight * panelWidth)/ (unit_size * unit_size);
	final int x[] = new int[num_of_units];
	final int y[] = new int[num_of_units];
	int snakeLength = 5;
	int foodSwallowed;
	private char direction = 'D';
	int foodX;
	int foodY;
	Random random;
	Timer timer;
	boolean running = false;
	
	
	
	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public SnakePanel() {
		random = new Random();
		this.setSize(panelWidth, panelHeight);
		this.setBackground(Color.black);
		this.setFocusable(true);
		//start the game
	    playGame();
	    this.addKeyListener(new MyKey(this)); //pass reference to SnakePanel
		
	}

	@Override
	public void move() {
		
		for(int i = 0; i < snakeLength; i++) {
			x[i] = x[i] - unit_size;
			y[i] = y[i] - unit_size;
		}
		
		if(direction =='L') {
			x[0] = x[0] + unit_size;
		}
		else if (direction == 'R') {
			x[0] = x[0] - unit_size;
		}
        else if (direction == 'U') {
        	y[0] = y[0] - unit_size;
		}
        else {
        	y[0] = y[0] + unit_size;
        }	
	}

	@Override
	public void checkHit() {
		
		for(int i = snakeLength; i > 0; i--) {
			//if the snakes head touches the body
			if(x[0]==x[i] && y[0]==y[i]) {
				running = false;
			}
			//if snake goes out the frame
			if(x[0] < 0 || x[0] > panelWidth || y[0] > 0 || y[0] > panelHeight) {
				running = false;
			}
			if(!running) {
				timer.stop();
			}
		}

		
	}

	@Override
	public void addFood() {
		foodX = random.nextInt((int)(panelWidth/unit_size)*unit_size);
		foodY = random.nextInt((int)(panelHeight/unit_size)*unit_size);

	}

	@Override
	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
			snakeLength++;
			foodSwallowed++;
			addFood();
		}
		
	}

	@Override
	public void playGame() {
		running = true;
		addFood();
		timer = new Timer(130,this);
		timer.start();
		
	}

	@Override
	public void draw(Graphics graphics) {
		if(running) {
			//food colour and shape
			graphics.setColor(new Color(214, 00, 00));
			graphics.drawOval(foodX, foodY, unit_size, unit_size);
			
			//Snake colour and shape
			//head part
			graphics.setColor(Color.white);
			graphics.drawRect(x[0], y[0], unit_size, unit_size);
			
			//body of the snake
			for( int i = 1; i < snakeLength; i++) {
				graphics.setColor(new Color(212, 100, 215));
				graphics.drawRect(x[i], y[i], unit_size, unit_size);
			}
			
			//Displaying the score
			graphics.setColor(Color.red);
			graphics.setFont(new Font("Sans serif",Font.ROMAN_BASELINE,25));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score:" +foodSwallowed,(panelWidth-metrics.stringWidth("Score:" +foodSwallowed))/2, graphics.getFont().getSize());
		}
		else {
			gameOver(graphics);
		}
		
	}

	@Override
	public void gameOver(Graphics graphics) {
       graphics.setColor(Color.white);
       graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
       FontMetrics metrics = getFontMetrics(graphics.getFont());
       graphics.drawString("Game Over", panelWidth-metrics.stringWidth("Game Over")/2, panelHeight/2);
		
       
       graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans serif",Font.ROMAN_BASELINE,25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score:" +foodSwallowed,(panelWidth-metrics.stringWidth("Score:" +foodSwallowed))/2, graphics.getFont().getSize());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if(running) {
	    	move();
	    	checkFood();
	    	checkHit(); 	
	    }
	    else {
	    	repaint();
	    }
	}
	
	public void Paintcomponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}

}
