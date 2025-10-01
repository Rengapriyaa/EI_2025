Real-Time Chat App (Java Console)

This is a simple real-time chat application built in Java using socket programming.
It demonstrates the use of design patterns such as Singleton, Observer, and Adapter while following SOLID principles.

Features

* Create or join chat rooms with unique IDs
* Real-time messaging between multiple clients
* Display list of active users
* Logging support (file + console)
* Extensible design using design patterns

Project Structure

src.com.chatapp
 ├── adapter      # Adapters for console and socket
 ├── client       # Client-side logic
 ├── config       # Logging configuration
 ├── core         # Core logic (ChatRoom, Manager, Client entry point)
 ├── model        # User, Message, ChatRoom models
 ├── observer     # Observer pattern interfaces
 └── server       # Server-side logic and entry point


How to Run

1. Compile the project
   javac -d out src/com/chatapp/**/*.java
   
2. Start the Server
   java -cp out com.chatapp.server.ChatServerMain

3. Start one or more Clients
   java -cp out com.chatapp.core.ChatClientMain

Start chatting!
   Each client can send and receive messages in real time.

Tech Stack

* Java (Socket Programming)
* Observer, Singleton, and Adapter Patterns
* Java Logging API

Future Improvements

* Private messaging
* Chat history persistence
* WebSocket/HTTP adapters for web clients

Enjoy coding and chatting with this simple Java console app!
