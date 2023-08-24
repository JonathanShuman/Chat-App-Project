
import mongoose from 'mongoose';

const ChatSchema = new mongoose.Schema({
    id: String,
    participants: Array,
    messages: Array,
});

const Chat = mongoose.model('Chat', ChatSchema);


async function getAllChats() {
    return await Chat.find({})
}

async function getLastMessageDetails(chat) {
    const array = chat.messages;
    if (array.length === 0) {
        return;
    }

    let maxObject = array[0];
    for (let i = 1; i < array.length; i++) {
        const currentId = parseInt(array[i].id);
        const maxId = parseInt(maxObject.id);
        if (currentId > maxId) {
            maxObject = array[i];
        }
    }

    return maxObject;

}

async function addNewChat(chat) {

    const newChat = new Chat({
        id: chat.id,
        participants: chat.participants,
        messages: chat.messages
    });
    await newChat.save();
}

async function getChatMessagesByChatId(currChatId) {
    return await Chat.findOne({ id: currChatId });
}

async function addMessageTochat(currchatId, message) {

    try {
        const updatedChat = await Chat.findOneAndUpdate(
            { id: currchatId },
            { $push: { messages: message } },
            { new: true }
        );

        if (updatedChat) {
            //console.log('Message added to the chat successfully.');
        } else {
            console.log('Failed to add message to the chat.');
        }
    } catch (error) {
        console.error('Error occurred while adding message to the chat:', error);
    }
}

async function findLastId(){
    try {
        const lastChat = await Chat.findOne({}, { id: 1 }).sort({ id: -1 }).limit(1);
        return lastChat ? lastChat.id : 0;
      } catch (error) {
        console.error('Error occurred while finding last chat ID:', error);
        return null;
      }

}

async function chatDelete(chatid){
    try {
        const deletedChat = await Chat.deleteOne({ id: chatid });
    
        if (deletedChat.deletedCount > 0) {
          console.log('Chat deleted successfully.');
        } else {
          console.log('Chat not found or failed to delete.');
        }
      } catch (error) {
        console.error('Error occurred while deleting chat:', error);
      }

}

async function getChatById(chatId){

    try {
        const chat = await Chat.findOne({ id: chatId });
        return chat;
    } catch (error) {
        console.error('Error occurred while retrieving chat by ID:', error);
        return null;
    }

}

async function getOtherParticipent(chatId, messageSenderUsernameToadd){
    try {
        const chat = await Chat.findOne({ id: chatId });
        if (chat) {
          const [participant1, participant2] = chat.participants;
          if (participant1 === messageSenderUsernameToadd) {
            return participant2;
          } else {
            return participant1;
          }
        } else {
          console.log("Chat not found.");
          return null;
        }
      } catch (error) {
        console.error("Error occurred while retrieving chat by ID:", error);
        return null;
      }
    }





export default { getAllChats, getLastMessageDetails, addNewChat, getChatMessagesByChatId, addMessageTochat, findLastId, chatDelete, getChatById , getOtherParticipent }





