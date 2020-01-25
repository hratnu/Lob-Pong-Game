//Manages import libraries 
import javax.swing.*;
import javax.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Math;

//Main frame
public class game extends JFrame{

	//Variables
	int posX=0;
	int posY=0;
	int points=0;
	double ang=360;
	int Angle=360;
	double xi=0;
	double yi=0;
	double velocityX=50.0,velocityY=100.0;
	double t=0, t1=0, t2=0;
	int level=1;
	int height=800, width=1200;
	double x=0; double y=0;
	int paddleX, paddleY;
	int paddleW=200, paddleH=10;
	int paddleMove=0;
	protected int lives = 3;

	//Timer
	Timer timer;

	//Importing audio clips
	static Clip clip;
	static File s1= new File("song.wav");
	static File s2= new File("paddle.wav");

	//GUI elements
	JLabel score;


	//constructor
	public game() {
		this.setResizable(false);//the game window does not resize but it doesn't need to
		try {
			clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(s1));
			clip.start();
		}
		catch(Exception e) {}
		this.getContentPane().setBackground(Color.black);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
		canvas canvas = new canvas();
		canvas.setPreferredSize(new Dimension(1200,800));
		panel progress= new panel();
		this.add(progress, BorderLayout.NORTH);
		this.add(canvas, BorderLayout.CENTER);
		pack();

	}

	public class panel extends JPanel{

		public panel() {
			score= new JLabel("Score: 0");
			this.add(score);
			score.setFont(new Font("Courier New", Font.BOLD, 20));

		}			
	}

	public class canvas extends JComponent implements KeyListener{
		public canvas() {
			System.out.println(" height is" +getHeight()+" h  "+  height );
			System.out.println(" width is "+ getWidth()+ "w is"+  width);
			paddleX =width/2 - paddleW/2;
			paddleY =height- paddleH/2;
			System.out.println(" paddleX  "+ paddleX);
			System.out.println(" paddleY  "+ paddleY);
			timer = new Timer(10, new callBack());
			timer.start();
			addKeyListener(this);
			setFocusable(true);//indicates whether this Component is focusable
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.cyan);
			g.setFont(new Font("Courier New", Font.BOLD, 20));
			int w=getWidth();
			g.fillRect(0, 400, 15, 200);
			g.fillRect(w-15,400,15,200);
			g.setFont(new Font("Courier New", Font.BOLD, 40));

			if(posX<20 && posX>0 && posY>400 && posY<600) {
				points+=500;

				g.drawString("BONUS+500",w/2-100, 200);
				g.setColor(Color.MAGENTA);
				g.fillRect(0, 400, 15, 200);
			}

			if(posX>w-20 && posY>400 && posY<600) {
				g.drawString("BONUS+500",w/2-100, 200);
				points+=500;
				g.setColor(Color.MAGENTA);
				g.fillRect(w-15,400,15,200);


			}

			height=getHeight();
			width=getWidth();
			//System.out.println("muskaan"+height);
			//System.out.println("raffay" + width);
			g.setColor(Color.WHITE);
			g.fillOval(posX, posY, 20, 20);	
			g.setColor(Color.green);
			g.fillArc(1000,100,100,100,0,Angle);
			g.setColor(Color.RED);
			g.fillRect(paddleX, paddleY, paddleW, paddleH);

			if(t2<50) {
				g.setColor(Color.PINK);
				g.setFont(new Font("Courier New", Font.BOLD, 40));
				g.drawString("level: "+ level, getWidth()/2-100, getHeight()/2);
			}
			if(lives<=0) {
				g.setColor(Color.RED);
				g.setFont(new Font("Courier New", Font.BOLD, 60));
				g.drawString("GAME OVER!", getWidth()/2-150, getHeight()/2-50);
				g.setFont(new Font("Courier New", Font.BOLD, 40));
				g.setColor(Color.PINK);
				g.drawString("Total Score: "+ points, getWidth()/2-170, getHeight()/2+80);
			}
			g.setColor(Color.YELLOW);
			if(lives==3) {
				g.fillOval(100, 140, 20, 20);
				g.fillOval(140, 140, 20, 20);
				g.fillOval(180, 140, 20, 20);
			}
			if(lives==2) {
				g.fillOval(100, 140, 20, 20);
				g.fillOval(140, 140, 20, 20);
			}
			if(lives==1) {
				g.fillOval(100, 140, 20, 20);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed");
			System.out.println(e);
			int directionKey = e.getKeyCode();
			System.out.println(directionKey);
			//If-else statement determines which key was pressed.
			if (directionKey == 39 || directionKey == 68) { //Right arrow key
				System.out.println("Move right.");
				paddleMove = 15;
				repaint();
			} else if (directionKey == 37 || directionKey == 65) { //Left arrow key
				System.out.println("Move left.");
				paddleMove = -15;
				repaint();
			}
		}



		@Override
		public void keyReleased(KeyEvent arg0) {
			System.out.println("Stop moving.");
			paddleMove = 0;
			repaint();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			//nonthing
		}	
	}


	public class callBack implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
			points+=1;
			score.setText("score: " +  points);
			t=t+0.1;
			t2=t2+0.1;
			ang-=0.5;
			Angle= (int) ang;
			if(Angle<=0) {
				timer.stop();
				level++;
				paddleW-=paddleW/10;
				reset();
				repaint();
			}
	
			t1=t1+0.1;
			position();
		}
	}



	public void position() {
		//Prevents paddle from leaving the right side of the window.
		if (paddleX > getWidth() - paddleW) {
			paddleX = getWidth() - paddleW;
			paddleMove = 0;
		}

		//Prevents paddle from leaving the left side of the window.
		if (paddleX < 0) {
			paddleX = 0;
			paddleMove = 0;
		}

		paddleX += paddleMove;

		//case when ball touches the bottom edge
		if(posY>height) {
			//when ball meets the left half of the paddle it bounces towards left
			if (x >= paddleX-20 && x <= paddleX + paddleW/2) {
				velocityY=Math.abs(velocityY);
				velocityX=-1*Math.abs(velocityX);
				xi=x;
				yi=0;
				y=0;
				timer.stop();
				t1=0;t=0;
				timer.start();
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
				try {
					clip= AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(s2));
					clip.start();
				}
				catch(Exception e) {}
			}
			//when ball meets the right half of the paddle it bounces towards the right
			else if(x>paddleX+paddleW/2 && x<=paddleX+paddleW+20) {
				velocityY=Math.abs(velocityY);
				velocityX=Math.abs(velocityX);
				xi=x;
				yi=0;
				y=0;
				timer.stop();
				t1=0;t=0;
				timer.start();
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
				try {
					clip= AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(s2));
					clip.start();
				}
				catch(Exception e) {}
			}
			//when ball does not meet the paddle
			else {
				timer.stop();
				System.out.println("Round Over.");
				lives -= 1;
				System.out.println("Lives remaining: " + lives);
				if(lives>0) {
					reset();
				}
				else {
					System.out.println(" game over");
				}
			}
		}


		if(posX>width-20){
			xi=width-20;
			velocityX=-1*Math.abs(velocityX);
			timer.stop();
			t=0;
			timer.start();
			System.out.println(" t1"+ t1 + " t " + t);
			System.out.println("##########");
		}

		if(posX<0){
			xi=0;
			velocityX=Math.abs(velocityX);
			timer.stop();
			t=0;
			timer.start();
			System.out.println("#x#x#x#x#x#x");
		}


		x= xi+ velocityX*(t);
		y= yi+ velocityY*t1- (4.9)*t1*t1;
		posX= (int) x;
		int posY1= (int) y;
		posY= height-posY1;
	}

	//this methods resets all varibales for next level
	public void reset(){
		posX=0;
		posY=0;
		t=0; 
		t1=0;
		t2=0;
		xi=0;
		yi=0;
		ang=360;
		Angle=360;

		//changes balls velocity at different levels just for some randomness
		if(level==2) {
			velocityX=80; velocityY=90;
		}
		if(level==3) {
			velocityX=50; velocityY=60;
		}
		if(level==4) {
			velocityX=70; velocityY=80;
		}
		if(level==5) {
			velocityX=20; velocityY=110;
		}
		if(level==6) {
			velocityX=60; velocityY=60;
		}

		timer.start();
	}

}
