import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.Panel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JScrollPane;

public class ChatClient extends JFrame 
{	
	public static final long serialVersionUID = 0L;
	private Socket client;
	private DataInputStream clientInput;
	private DataOutputStream clientOutput;
	private String username;
	private JTextArea conversation;
	private static JTextArea messageSpot;
	private Panel messageWritingArea;
	private Button send;
	private JScrollPane conversationScroll, messageSpotScroll;
	
	public static JTextArea getMessageSpot() {
		return messageSpot;
	}
	
	private void sendClientNameToServer(DataOutputStream clientOutput, String clientName) throws IOException
	{
		clientOutput.writeUTF(clientName);
		clientOutput.flush();
	}
	
	private void initConversationArea()
	{
		conversation = new JTextArea(18, 50);
		Font conversationFont = new Font("Dornilian Conversation", Font.LAYOUT_LEFT_TO_RIGHT, 20);
		conversation.setFont(conversationFont);
		conversation.setEditable(false);
		conversationScroll = new JScrollPane(conversation);
		this.add(conversationScroll, BorderLayout.NORTH);
	}
	
	private void initMessageSpot()
	{
		messageSpot = new JTextArea(3, 60);
		Font messageFont = new Font("Dornilian Message", Font.LAYOUT_RIGHT_TO_LEFT, 15);
		messageSpot.setFont(messageFont);
		messageSpot.addKeyListener(new SendButtonAndMessageTextListener(conversation, clientOutput, username));
		messageSpotScroll = new JScrollPane(messageSpot);
	}
	
	private void initSendMessageButton()
	{
		send = new Button("Send");
		send.setSize(2, 2);
		send.addActionListener(new SendButtonAndMessageTextListener(conversation, clientOutput, username));
	}
	
	private void initMessageWritingArea()
	{
		messageWritingArea = new Panel();
		messageWritingArea.setLayout(new BorderLayout());
		messageWritingArea.add(messageSpotScroll, BorderLayout.WEST);
		messageWritingArea.add(send);
		this.add(messageWritingArea, BorderLayout.SOUTH);
		messageSpot.requestFocusInWindow();
	}
	
	private void initChatFrameCharacteristics(String username)
	{
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 600, 500);
		this.setTitle("Chat : user " + username);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initConversationArea();
		initMessageSpot();
		initSendMessageButton();
		initMessageWritingArea();
		this.pack();
	}
	
	public ChatClient(String clientName)
	{
		username = clientName;
		try
		{
			client = new Socket("localhost", 8888);
			clientInput = new DataInputStream(client.getInputStream());
			clientOutput = new DataOutputStream(client.getOutputStream());
			initChatFrameCharacteristics(username);
			sendClientNameToServer(clientOutput, clientName);
			new RecieveMessageThread(clientInput, conversation).start();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}