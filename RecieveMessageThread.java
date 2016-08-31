import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JTextArea;

public class RecieveMessageThread extends Thread 
{
	private DataInputStream from;
	private String message;
	private JTextArea conversation;
	
	public RecieveMessageThread(DataInputStream from, JTextArea conversation) 
	{
		this.from = from;
		this.conversation = conversation;
	}
	
	private void recieveMessage(DataInputStream from) throws IOException
	{
		if(from.available() > 0)
		{
			message = from.readUTF();
			conversation.setText(conversation.getText() + message + '\n');
			conversation.setCaretPosition(conversation.getText().length());
		}
	}
	
	@Override
	public void run() 
	{
		while(from != null)
		{
			try {
				recieveMessage(from);
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}	