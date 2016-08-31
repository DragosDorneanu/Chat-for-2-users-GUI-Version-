# Chat-for-2-users-GUI-Version-
Simple chat for 2 users.
- Application launches an instance of QueryUsernameFrame class
- After you enter an username, an instance of ChatClient class will be created with the specific username
- Each client will communicate with a server
     - client sends a message to server and the server sends it to the other client
     - the "signal" to send messages are the ENTER key and a "Send" button
     - the recieve messages problem is managed by an instance of RecieveMessageThread class ( if a message exists for reading then it will be readed while the app/thread is on )

Possible updates may come up ...
