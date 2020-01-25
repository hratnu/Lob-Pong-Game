//basic GUI to launch the game
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class gameGUI extends JFrame implements ActionListener {
		
		public gameGUI() {			
		setLayout(new BorderLayout());
		setSize(200,200);
		setTitle("Lob Pong Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		JButton play = new JButton("PLAY");//creates a button
		play.addActionListener(this);
		this.add(play);
		
		}	
		@Override
		public void actionPerformed(ActionEvent arg0) {
		new game();//Launches the game window when button is presses 
		} 
	
		public static void main(String[] args) {
		new gameGUI().setVisible(true);	
		
		}
}
