import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendButtonAndMessageTextListener implements KeyListener, ActionListener
{
	private JTextArea conversation;
	private DataOutputStream destination;
	private String username;
	
	public SendButtonAndMessageTextListener(JTextArea conversation, DataOutputStream destination, String username)
	{
		this.conversation = conversation;
		this.destination = destination;
		this.username = username;
	}
	
	private void sendMessage(JTextArea messageArea)
	{
		String message = username + " : " + messageArea.getText();
		conversation.setText(conversation.getText() + message + '\n');
		messageArea.setText("");
		try 
		{
			destination.writeUTF(message);
			destination.flush();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			JTextArea messageArea = ChatClient.getMessageSpot();
			if(messageArea.getText().length() - 1 > 0)
			{
				sendMessage(messageArea);
				messageArea.setCaretPosition(0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(ChatClient.getMessageSpot().getText().length() > 0)
		{
			JTextArea messageArea = ChatClient.getMessageSpot();
			if(messageArea.getText().length() - 1 > 0)
			{
				sendMessage(messageArea);
				messageArea.setCaretPosition(0);
			}
		}
	}
}