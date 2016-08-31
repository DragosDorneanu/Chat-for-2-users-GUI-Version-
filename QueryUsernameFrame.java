import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class QueryUsernameFrame extends JFrame implements ActionListener
{
	public static final long serialVersionUID = 0L;
	private TextField username;
	private Button signIn;
	private Panel queryBox;
	
	class EnterKeyListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			char key = e.getKeyChar();
			if(key == KeyEvent.VK_ENTER)
			{
				dispose();
				new ChatClient(username.getText());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) { }

		@Override
		public void keyTyped(KeyEvent e) { }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
			if(e.getSource() == signIn)
			{
				dispose();
				new ChatClient(username.getText());
			}
	}
	
	private void setFrameCharacteristics()
	{
		this.setVisible(true);
		this.setTitle("Enter Username");
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 600, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initUsernameTextField()
	{
		username = new TextField(40);
		username.setSize(500, 100);
		Font textFont = new Font("Dornilian", Font.ROMAN_BASELINE, 20);
		username.setFont(textFont);
		username.addKeyListener(new EnterKeyListener());
	}
	
	private void initSignInButton()
	{
		signIn = new Button("Sign In");
		signIn.addActionListener(this);
		signIn.setSize(100, 100);
	}
	
	private void initQueryBox()
	{
		queryBox = new Panel();
		queryBox.add(username);
		queryBox.add(signIn);
	}
	
	public QueryUsernameFrame()
	{
		setFrameCharacteristics();
		initUsernameTextField();
		initSignInButton();
		initQueryBox();
		this.add(queryBox);
		username.requestFocusInWindow();
	}
	
	public static void main(String[] args) {
		new QueryUsernameFrame();
	}
}