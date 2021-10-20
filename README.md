# CLI Chatroom

CLI Chatroom is a multithreaded chatroom written in Java that runs on the command console.

## Installation 

The server needs to be running first before clients

To run the server navigates to project directory
```shell
java server.ChatroomServer <port number>
```
Once the server is running, open another command console terminal and navigate to the project directory
```shell
java client.ChatroomClient <port number>
```

## Usage
Once the client is launched it will display a list of users in the room and prompt to enter a user name
```shell
Chat room is empty
Enter your username
```
Room wide broadcast can be done by typing into the console

John's side
```shell
[Joe]: hello
hi
```

Joe's side
```shell
hello
[John]: hi
```

Direct message can be sent using /<username>
```shell
/Joe hello
```

Users can also request information from the server such as list of active users in the room
```shell
/list
```