# CLI Chatroom

CLI Chatroom is a multithreaded chatroom written in Java that runs on the command console.

## Installation

The server needs to be running first before clients

To run the server navigates to project directory

```
java server.ChatroomServer <port number>
```

Once the server is running, open another command console terminal and navigate to the project directory

```
java client.ChatroomClient <port number>
```

## Usage

Once the client is launched it will display a list of users in the room and prompt to enter a user name

```
----------------------------------
Chat room is empty
----------------------------------
Enter your username
```

Next the console will prompt the user to enter a room or to create a room

```
Enter the name of the room you wish to join, or enter a name to create a room   
----------------------------------
No open rooms
----------------------------------
```

Room wide broadcast can be done by typing into the console

John's side

```
[Joe]: hello
hi
```

Joe's side

```
hello
[John]: hi
```

Direct message can be sent using /username

```
/Joe hello
```

Users can also request information from the server such as list of active users in the room

```
/list
```

User can access the help function by typing in /help

```
/help
```
