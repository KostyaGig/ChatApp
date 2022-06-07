# ChatApp

- [Description](#description)
- [Technology](#technology)
- [Project statistic](#statistic)
- [Backend](#backend)

# Description

The chat app can be used for communication with people online!

## Features
- [State of the application](#state)
- [Join to the chat](#join)
- [Users](#users)
- [Send messages](#chat)
- [Edit messages](#chat)
- [Read messages](#chat)
- [Offline sending and editing messages](#offline)
- [Realtime update last sent message](#users)

### State
Handle state of the server and device's network connection <br />
Show the current state of the app in the toolbar
If the application aren't connected to the server we show an error with text "Waiting for server..." <br />
If the application lost connection with the Internet we show an error with text "Waiting for network"<br />
For handling internet connection I used [Broadcast receiever](https://developer.android.com/guide/components/broadcasts)<br /><br />

<img src="https://media.giphy.com/media/W4Je5J6tuBazsZqEEw/giphy.gif" width="400" height="400">

### Join

For join to the chat user needs to be entered his name. He also can add image for his profile (Optional)<br />
If user doesn't choose an image for profile then we'll send the default image to the server <br />
To test save state of the app I turned on "Do not keep activities" in the developer options <br />
After any device's configuration changes our app save the last state of the screen<br /> <br />

<img src="https://media.giphy.com/media/RVhdMR8YoPt4hIHvO0/giphy.gif" width="600" height="600">

| Success join user to the chat     | Failure join user to the chat        |
| ------------- |:-------------:|
|  <img src="https://media.giphy.com/media/uTcM3lE0N40Rhfj5CD/giphy.gif" width="400" height="400"> |<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/fail_join_empty_name.png?alt=media&token=b15b69d2-ed29-4855-9393-3ab3fd079a31" width="200" height="400" /> <br /> <br />
 

### Users

Show users who has already joned to the chat <br />
Showing image profile of the user his nick name and also we show the last sender message's name and its content <br />
For uploading images I used [Glide](https://github.com/bumptech/glide), they have circle form with the help of [CircleImageView](https://github.com/hdodenhof/CircleImageView)<br /> <br />

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/users_screen.png?alt=media&token=7e1c8367-43c2-4ad0-bef1-86fb4f58ef70" width="400" height="600"> <br />

The last sent message updates on screen with users<br />
For updating recycler view I used Diffutils<br />
<img src="https://media.giphy.com/media/vnyMXONz2SvPku3HCQ/giphy.gif"> <br /> <br />


### Chat
In this screen I also save the state to restore it in the future <br />
We can send messages and edit them <br/>
Edited message are marked like "edited" to undertanding that this message's content was changed  <br />
Also I realized reading messages other users and mark them like "read" <br />
If user is typing some text we show this state in the toolbar it looks like "User name is typing..." <br />

<img src="https://media.giphy.com/media/dVWqu39nF4PSUhi27v/giphy.gif"> <br /><br />
Last sent messages are displayed under the following way<br />

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/first_last.png?alt=media&token=dfbf9328-644a-478f-856a-b7d3b14c4c2f">

<img src="https://firebasestorage.googleapis.com/v0/b/chatsocketapp-2d5bd.appspot.com/o/second_last.png?alt=media&token=21d5bfc8-f2fd-4dfa-9a8d-3ea1a441d460">


### Offline
When we do not have the Internet but we want to send or edit the message we can do it <br />
All the transactions with messages will be shown thankful to notifications <br />
When the internet is able we'll hide all the notifications and commit scheduled transactions <br />
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
Beckend was wrotten with the help of [node.js](https://nodejs.org/en/) <br/>
You can see the file with source code [here](https://github.com/KostyaGig/ChatApp/blob/master/back_end/chat_back_end.js)
