import express from 'express'
import mongoose from 'mongoose';
import bodyParser from 'body-parser';
import session from 'express-session';
import jwt from 'jsonwebtoken'
import http from 'http';
import { Server as SocketIOServer } from 'socket.io';
import usersController from './controller/usersController.js'
import tokenController from './controller/tokenController.js'
import chatController from './controller/chatController.js'

global.newChatId = 1;
global.newMessageId = 1;

const server = express();
const httpServer = http.createServer(server);

const io = new SocketIOServer(httpServer);


await mongoose.connect('mongodb://127.0.0.1:27017/mydatabase', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})
    .then(() => {
        console.log('Connected to MongoDB');
    })
    .catch((error) => {
        console.error('Error connecting to MongoDB:', error);
        process.exit(1); // Exit the process if unable to connect to the database
    });

server.use(
    session({
        secret: 'secretKey',
        resave: false,
        saveUninitialized: true,
    })
);

server.use(express.json());

server.use(bodyParser.json({ limit: '10mb' }));

server.use(express.static('public'));

io.on('connection', (socket) => {
    socket.on('message sended', (addComment) => {
      console.log("Received new message:", addComment);
      io.emit('some one sended message', addComment);
    });
  });

server.post('/api/Users', usersController.createUser);
server.post('/api/Tokens', tokenController.generateToken);
server.get(`/api/Users/:username`, usersController.getUser);
server.get('/api/Chats', chatController.getChatsOfloggedInUser);
server.post('/api/Chats', chatController.postChatContact);
server.get('/api/Chats/:chatid/Messages', chatController.getChatMessages);
server.post('/api/Chats/:chatid/Messages', chatController.postNewMessage);
server.delete('/api/Chats/:chatid', chatController.deleteChatById);
server.get('/api/Chats/:chatid', chatController.getAllChatDetails);



httpServer.listen(5000);