import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSendMessageThread extends Thread 
{
	private DataInputStream from1, from2;
	private DataOutputStream destination1, destination2;
	private String message;
	
	public ServerSendMessageThread(DataInputStream from1, DataOutputStream destination1, DataInputStream from2, DataOutputStream destination2)
	{
		this.from1 = from1;
		this.destination1 = destination1;
		this.from2 = from2;
		this.destination2 = destination2;
	}
	
	private void sendMessageFromClientToClient() throws IOException
	{
		if(from1.available() > 0)
		{
			message = from1.readUTF();
			System.out.println(message);
			destination2.writeUTF(message);
			destination2.flush();
		}
		if(from2.available() > 0)
		{
			message = from2.readUTF();
			System.out.println(message);
			destination1.writeUTF(message);
			destination1.flush();
		}
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			try {
				sendMessageFromClientToClient();
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