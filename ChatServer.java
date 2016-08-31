import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatServer 
{
	private static String readUsername(DataInputStream userInput) throws IOException 
	{
		String username = userInput.readUTF();
		System.out.println("User " + username + " is now connected to the server");
		return username;
	}
	
	public static void main(String[] args) throws IOException
	{
		ServerSocket chatServer = new ServerSocket(8888);
		Socket user1, user2;
		String username1, username2;
		DataInputStream user1Input, user2Input;
		DataOutputStream user1Output, user2Output;
		
		System.out.println("Waiting for two users to connect to the server...");
		
		user1 = chatServer.accept();
		user1Input = new DataInputStream(user1.getInputStream());
		user1Output = new DataOutputStream(user1.getOutputStream());
		username1 = readUsername(user1Input);
		
		user2 = chatServer.accept();
		user2Input = new DataInputStream(user2.getInputStream());
		user2Output = new DataOutputStream(user2.getOutputStream());
		username2 = readUsername(user2Input);
		
		System.out.println("Users " + username1 + " and " + username2 + " are chating");
		new ServerSendMessageThread(user1Input, user1Output, user2Input, user2Output).start();
		chatServer.close();
	}
}