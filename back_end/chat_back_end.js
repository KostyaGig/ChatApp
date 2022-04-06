const express = require('express'); //requires express module
const socket = require('socket.io'); //requires socket.io module
const fs = require('fs');
const app = express();

var PORT = process.env.PORT || 3000;
const server = app.listen(PORT);

app.use(express.static('public'));
console.log('Server is running');
const io = socket(server);


var id = 0 ;
var users = [];
io.on('connection', (socket) => {

	console.log("connection " + socket.id)

	socket.on('join_user', (clientUser) => {
		var user = Object();
		id++;
		user.id = id;
		user.nickname = clientUser.nickname;
		users.push(user)

		io.emit('join_user',id)
	})

	socket.on('test', (json_object) => {
		console.log("json object " + json_object['data1'] + ", " + json_object['data2']);
	})

	socket.on('push_message', (message) => {

	})


})

