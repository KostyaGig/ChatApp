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
		var senderNickname = '';
		var senderId = clientMessage.senderId;

		users.forEach(function(item, index, array) {
  			if(item.id == senderId) {
  				senderNickname = item.nickname;
  				console.log('the same');
  			}
		});

		message.id = messageId;
		message.senderId = senderId;
		message.content = clientMessage.content;
		message.senderNickname = senderNickname;
		messages.push(message)

		io.emit('send_message',messages)

		messages.forEach(function(item, index, array) {
  			console.log(item, index);
		});

	})


})


