# ChatApp

- [Description](#description)
- [Technologies](#technology)
- [Project statistic](#statistic)
- [Backend](#backend)

# Description

The Chat App can be used for communication between people all over the world!

## Features
- [Saving state if the application](#state)
- [Joining to the chat](#join)
- [List of Users](#users)
- [Send messages](#chat)
- [Edit messages](#chat)
- [Read messages](#chat)
- [Offline sending and editing messages](#offline)
- [Realtime update last sent message](#users)

### State
The application are available to handle user's network connection and connection with the server in real time<br />
We can see the current state of the connection with server or user's network connection on the toolbar
If the application aren't connected to the server error occurs: "Waiting for server..." <br />
If the application lost connection with the Internet error occurs: "Waiting for network"<br />
To handle network connection of devices I used [Broadcast receiever](https://developer.android.com/guide/components/broadcasts)<br /><br />

<img src="https://media.giphy.com/media/W4Je5J6tuBazsZqEEw/giphy.gif" width="400" height="400">

### Join

To join to any chat user should enter his name. He's got an ability to add image profile (Optional)<br />
If profile image wasn't chosen a standard image profile will be shown<br />
To test my application's ability to save state I used "Don't keep activityies" option you can find in "Developer options"<br />
The application is able to save the last state of the screen if any configuration changes or something more like lack of memory occurs<br /> <br />

<img src="https://media.giphy.com/media/RVhdMR8YoPt4hIHvO0/giphy.gif" width="600" height="600">

| Success join user to the chat     | Failure join user to the chat        |
| ------------- |:-------------:|
|  <img src="https://media.giphy.com/media/uTcM3lE0N40Rhfj5CD/giphy.gif" width="400" height="400"> |<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/fail_join_empty_name.png?alt=media&token=b15b69d2-ed29-4855-9393-3ab3fd079a31" width="200" height="400" /> <br /> <br />
 

### Users

Show users who have already joned to the chat<br />
I display users in the list who have ever joined to the app<br />
For uploading images I used [Glide](https://github.com/bumptech/glide), they have circle form with the help of [CircleImageView](https://github.com/hdodenhof/CircleImageView)<br /> <br />

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/users_screen.png?alt=media&token=7e1c8367-43c2-4ad0-bef1-86fb4f58ef70" width="400" height="600"> <br />

The last sent message updates on screen with users<br />
For updating recycler view I used Diffutils<br />
<img src="https://media.giphy.com/media/vnyMXONz2SvPku3HCQ/giphy.gif"> <br /> <br />


### Chat
In this screen I also save the state to restore it in the future<br />
We can send messages and edit them<br/>
Edited message are marked like "edited" to undertanding that this message's content was changed<br />
Also I realized reading messages other users and mark them like "read" <br />
If user is typing some text we show this state in the toolbar it looks like "User name is typing..." <br />

<img src="https://media.giphy.com/media/dVWqu39nF4PSUhi27v/giphy.gif"> <br /><br />
Last sent messages are displayed by the following way<br />

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/first_last.png?alt=media&token=dfbf9328-644a-478f-856a-b7d3b14c4c2f">

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/second_last.png?alt=media&token=21d5bfc8-f2fd-4dfa-9a8d-3ea1a441d460">


### Offline
When we do not have the Internet but we want to send or edit the message we can do it <br />
All the transactions with messages will be shown via android notifications <br />
When the internet is available we hide all the notifications and commit scheduled transactions <br />
For automatically sending and editting messages I used [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)<br /> <br />

<img src="https://media.giphy.com/media/3ulV8yeCYIZn8f9DwX/giphy.gif">


# Technology
- Clean architecture 
- SOLID
- Junit tests
- Single activity approach
- Used [Koin](https://insert-koin.io) for dependency injection
- [View binding](https://developer.android.com/topic/libraries/view-binding) is used to interact with views within fragments and recyclerview adapters
- Kotlin coroutines are used for asynchronous operations
- [Socket io](https://socket.io/blog/native-socket-io-and-android/)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) and [Glide](https://github.com/bumptech/glide) libs are involved to obtain and display user profile image
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)

# Statistic
<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/Screenshot%202022-06-07%20at%208.53.04%20PM.png?alt=media&token=91a070c3-6175-499b-a762-0cef593c2293">

# Backend 
Beckend was written with the help of [node.js](https://nodejs.org/en/) <br/>
You can see the file with source code [here](https://github.com/KostyaGig/ChatApp/blob/master/back_end/chat_back_end.js)
