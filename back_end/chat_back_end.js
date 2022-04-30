const express = require('express'); //requires express module
const socket = require('socket.io'); //requires socket.io module
const crypto = require('crypto'); // for uuid

const fs = require('fs');
const app = express();

var PORT = process.env.PORT || 3000;
const server = app.listen(PORT);

app.use(express.static('public'));
console.log('Server is running');
const io = socket(server);


var userId = 0 ;
var users = [];
var messages = [];

var offlineUserNotificationTokens = [];

io.on('connection', (socket) => {

	console.log("connection " + socket.id)

	socket.on('join_user', (clientUser) => {
		var user = Object();
		userId++;
		user.id = userId;
		user.nickname = clientUser.nickname;
		users.push(user)

		io.emit('join_user',userId)
	})


	socket.on('send_message', (clientMessage) => {
		var message = Object();
		var messageId = crypto.randomUUID();
		var senderId = clientMessage.senderId;

		message.id = messageId;
		message.senderId = senderId;
		message.content = clientMessage.content;
		message.isRead = false;
		message.senderNickName = clientMessage.senderNickName;
		messages.push(message)

		io.emit('messages',messages)

		console.log("send message ",message)

		sendNotification(message.senderNickName,message.content)
	})


	socket.on('messages', () => {
		io.emit('messages',messages)
	})

	socket.on('edit_message',(clientMessage) => {
		var id = clientMessage.id;
		var content = clientMessage.content;
		var indexEditingContentMessage = -1;
		var newMessage = Object();

		messages.forEach(function(item, index, array) {
  			if (item.id == id) {

  				newMessage.id = item.id;
  				newMessage.senderId = item.senderId
  				newMessage.content = content;
  				newMessage.senderNickName = item.senderNickName;

  				newMessage.isRead = false

  				indexEditingContentMessage = index;
  			}
		});

		messages[indexEditingContentMessage] = newMessage;


		io.emit('messages',messages)

	})

	socket.on('read_message',(messageIds) => {
		var jsonIds = JSON.parse(messageIds)['ids'];
		if (typeof jsonIds[0] !== 'undefined') {
			var indexMessageForUpdate = -1;
			var newMessage = Object();
			jsonIds.forEach(function(messageId, index, array) {
				messages.forEach(function(message, messageIndex, array) {
					if (message.id == messageId) {

						newMessage.id = message.id;
						newMessage.senderId = message.senderId;
						newMessage.content = message.content;
						newMessage.senderNickName = message.senderNickName;
						newMessage.isRead = true;

						indexMessageForUpdate = messageIndex;
					}
				});
			});

			messages[indexMessageForUpdate] = newMessage;

			io.emit('messages',messages)
		}
	})

	const functions = require("firebase-functions");
	var admin = require("firebase-admin");
	var serviceAccount = require("/Users/kostazinovev/Desktop/chat_firebase_key.json");

	if (!admin.apps.length) {
    	admin.initializeApp({
  			credential: admin.credential.cert(serviceAccount)
		});
	}


	socket.on('disconnect_user',(notificationToken) => {

		var token = notificationToken['notification_token']
		offlineUserNotificationTokens.push(token)

		console.log('disconnectUser',offlineUserNotificationTokens)
	})

	socket.on('connect_user',(notificationToken) => {
		var token = notificationToken['notification_token']
		var indexOf = offlineUserNotificationTokens.indexOf(token)

		if (indexOf > -1) {
			offlineUserNotificationTokens.splice(indexOf,1)
		}

		console.log('connectUser',offlineUserNotificationTokens)
	})

	function sendNotification(senderNickName,content) {

		var payload = {
			notification: {
				title: senderNickName,
    			body: content
			}
		}

		var options = {
  			priority: "high",
  			timeToLive: 60 * 60 * 24
		};

		offlineUserNotificationTokens.forEach(function(item, index, array) {
			var notificationToken = item

			admin.messaging().sendToDevice(notificationToken, payload, options)
  				.then((response) => {
    				console.log('Successfully sent message:', response);
  				})
  				.catch((error) => {
    				console.log('Error sending message:', error);
  				});
		})

	}


})
