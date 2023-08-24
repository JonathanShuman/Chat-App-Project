import jwt from 'jsonwebtoken'
import chatModel from '../models/chatModel.js'
import userModel from '../models/userModel.js';
import firebaseModel from '../models/firebaseModel.js';



async function getChatsOfloggedInUser(req, res) {
    // step 1: get the user connected by his token

    try {

        const token = req.headers.authorization.split(' ')[1];
        const data = jwt.decode(token);
        //console.log(data.username);
        const username = data.username;

        // step 2: get all the chats that the logged in user is a participant in
        const allChats = await chatModel.getAllChats();
        const allUserChats = allChats.filter((chat) => {
            return chat.participants.includes(username)
        })
        //console.log(allUserChats);
        //console.log(allChats);

        const response = [];

        // for each chat:


        for (let i = 0; i < allUserChats.length; i++) {
            const currChat = allUserChats[i];
            let objectToPush = {};

            // step 3 : catch the other username and get the needed details of him
            const otherUsername = currChat.participants.find(particpant => {
                return particpant !== username;
            })

            //console.log(otherUsername);

            const otherUserDetails = await userModel.getUserForToken(otherUsername);


            // step 4: calculate which message is the last message (by comparing the created dates)
            const lastMessageDetails = await chatModel.getLastMessageDetails(currChat);
            objectToPush = {
                id: currChat.id,
                // the second user
                user: {
                    "username": otherUserDetails.username,
                    "displayName": otherUserDetails.displayName,
                    "profilePic": otherUserDetails.profilePic
                },
                lastMessage: {
                    "id": lastMessageDetails && lastMessageDetails.id ? lastMessageDetails.id : "",
                    "created": lastMessageDetails && lastMessageDetails.created ? lastMessageDetails.created : "",
                    "content": lastMessageDetails && lastMessageDetails.content ? lastMessageDetails.content : ""
                }
               

            }

            // add this message to the chat object

           
            response.push(objectToPush);

        }
        return res.status(200).json(response);
    } catch (error) {
        console.error('Error get logged in user chats:', error);
        return res.status(500).json({ message: 'Internal server error' });
    }

}

async function postChatContact(req, res) {
    try {
        // get the logged in username
        const token = req.headers.authorization.split(' ')[1];
        const data = jwt.decode(token);
        const loggedInUsername = data.username;
        //get the added contact username
        const addedContactUsername = req.body.username;

        // console.log("START");
        // console.log(token);
        // console.log(addedContactUsername);
        // console.log("END");


        // get the next id for chat
        let newChatId = await chatModel.findLastId();
        let newChatIdNumber = parseInt(newChatId, 10); // Convert string to number
        newChatIdNumber = newChatIdNumber + 1; // Perform addition
        newChatId = newChatIdNumber.toString();

        //console.log(newChatId);

        // generate chat to add to mongo
        const newChat = {
            id: newChatId,
            participants: [loggedInUsername, addedContactUsername],
            messages: []
        }

        //console.log(newChat);


        // add the generated chat to mongo
        await chatModel.addNewChat(newChat);

        // now we want to return to the user what the api of hemi wants

        const addedContactDetails = await userModel.getUserForToken(addedContactUsername);

        const response = {
            id: newChatId.toString(),
            user: {
                username: addedContactDetails.username,
                displayName: addedContactDetails.displayName,
                profilePic: addedContactDetails.profilePic
            }
        }
        
        global.newChatId = global.newChatId + 1;

        return res.status(200).json(response);


    } catch (error) {
        console.error('Error adding contact', error);
        return res.status(500).json({ message: 'Internal server error' });
    }


}


async function getChatMessages(req, res) {

    // get the curr chat id

    try {

        let currChatId = req.params.chatid;
        currChatId = currChatId.toString();

        //console.log('Current chat ID:', currChatId);

        // get chat messages from mongo with the chat that has the currChatId

        const currChat = await chatModel.getChatMessagesByChatId(currChatId);

        //console.log(currChat);

        // extract the messages array from chat

        const messages = currChat.messages;

        //generate the response

        const response = [];

        for (let i = messages.length - 1; i >= 0; i--) {
            const currMessage = messages[i];
            const objectToPush = {
                id: currMessage.id,
                created: currMessage.created,
                sender: {
                    username: currMessage.senderUsername
                },
                content: currMessage.content
            }

            response.push(objectToPush);

        }

        // console.log(response);

        return res.status(200).json(response);

    } catch (error) {
        console.error('Error get messages', error);
        return res.status(500).json({ message: 'Internal server error' });
    }



}

async function postNewMessage(req, res) {
    try {
        const token = req.headers.authorization.split(' ')[1];
        const data = jwt.decode(token);

        // get chat id

        let currChatId = req.params.chatid;
        currChatId = currChatId.toString();

        // get message to add
        const messageIdtoAdd = global.newMessageId.toString();
        const messageCreatedToadd = new Date().toISOString();
        const messageSenderUsernameToadd = data.username;
        const messageContentToadd = req.body.msg;
        const localDate = new Date().toLocaleString();
       
        const message = {
            id: messageIdtoAdd,
            created: localDate,
            senderUsername: messageSenderUsernameToadd,
            content: messageContentToadd
        }

        await chatModel.addMessageTochat(currChatId, message);

        const response = {
            id: messageIdtoAdd,
            created: localDate,
            sender: {
                username: messageSenderUsernameToadd
            },
            content: messageContentToadd
        }
        global.newMessageId = global.newMessageId + 1;

        //the person that we send him a message and want to send notification.
        const otherParticipant = await chatModel.getOtherParticipent(req.params.chatid, data.username);
        //the person firebase token.
        const findFirebaseToken = await firebaseModel.getOtherParticipentFB(otherParticipant);    
        const serverKey= "AAAAsT64-yE:APA91bGAdRZgwFfPkNnBOjyfmnkdYep2trV0UcCOCI7logtX_ZJ2jFuVZ-ywW28ejIppwj0Y8CfHq5_yhUL6YIVUl9uRX7XcU9BFSr7P6fthDvJMYv8hhNXpI7wDVuIAC6IaE8QV3K6r"  
        
        let notificationReq = {
        to: findFirebaseToken,
        notification: {
          title: otherParticipant,
          body: messageContentToadd
        }
      }

      await fetch('https://fcm.googleapis.com/fcm/send', {
        method: 'POST',
        headers: {
          'Authorization': `key=${serverKey}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(notificationReq),
      });


    //   console.log("STARR- FB, PERSON, MESSAGE");
    //   console.log(findFirebaseToken);
    //   console.log(otherParticipant);
    //   console.log(messageContentToadd);
    //   console.log("END");


        return res.status(200).json(response);
    } catch (error) {
        console.error('Error post message', error);
        return res.status(500).json({ message: 'Internal server error' });
    }

    

}

async function deleteChatById(req, res) {
    try {
        let currChatId = req.params.chatid;
        const idChatToDelete = currChatId.toString();
        await chatModel.deleteChat(idChatToDelete);
        res.status(200).send('Chat deleted successfully.');
    } catch (error) {
        // Handle any errors that occurred during deletion
        console.error('Error occurred while deleting chat:', error);
        res.status(500).send('Failed to delete chat.');
    }

}

async function getAllChatDetails(req, res) {
    try {
        // id of chat
        const currChatId = req.params.chatid;
        const idOfChat = currChatId.toString();

        // logged in user name
        const token = req.headers.authorization.split(' ')[1];
        const data = jwt.decode(token);
        const loggedInUsername = data.username;

        // get selected chat
        const selectedChat = await chatModel.getChatById(idOfChat);

        // get contact details
        let contactDetails;
        if (selectedChat.participants[0] === loggedInUsername) {
            contactDetails = await userModel.getUserForToken(selectedChat.participants[1]);
        } else {
            contactDetails = await userModel.getUserForToken(selectedChat.participants[0]);
        }

        // messages array of this chat
        let chatMessages = [];

        for (let i = selectedChat.messages.length - 1; i >= 0; i--) {
            const currMessage = selectedChat.messages[i];

            const senderDetails = await userModel.getUserForToken(currMessage.senderUsername);
            const objectToPush = {
                id: currMessage.id,
                created: currMessage.created,
                sender: {
                    username: currMessage.senderUsername,
                    displayName: senderDetails.displayName,
                    profilePic: senderDetails.profilePic
                },
                content: currMessage.content
            };

            chatMessages.push(objectToPush);
        }

        const response = {
            id: selectedChat.id,
            users: [{
                username: contactDetails.username,
                displayName: contactDetails.displayName,
                profilePic: contactDetails.profilePic
            }],
            messages: chatMessages
        };

        // Send the response
        res.status(200).json(response);

    } catch (error) {
        console.error('Error occurred while fetching chat details:', error);
        res.status(500).json({ error: 'Failed to fetch chat details' });
    }
}


export default { getChatsOfloggedInUser, postChatContact, getChatMessages, postNewMessage, deleteChatById, getAllChatDetails }