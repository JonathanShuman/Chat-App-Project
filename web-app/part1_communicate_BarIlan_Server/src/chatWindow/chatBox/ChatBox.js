import React, { useState, useRef, useEffect } from 'react';
import io from 'socket.io-client';




function ChatBox(field) {

  const socket = io('http://localhost:5000');

  

  const messRef = useRef();
  const commentsListRef = useRef(null);

   socket.on('some one sended message', (addComment) => {

    field.updateArr([addComment, ...field.messagesArr]);

  })


  async function postChatMessage() {
    
    const newComment = messRef.current.value;
    const token = sessionStorage.getItem("myToken");
    const name = field.contact
    const cont_id = field.idContact
    
    const res = await fetch(`http://localhost:5000/api/Chats/${cont_id}/Messages`, {
      'method': 'post',
      'headers': {
        'Content-Type': 'application/json',
        "authorization": `Bearer ${token}`,

      },
      body: JSON.stringify({
        msg: `${newComment}`
      })

    })
    
    const addComment = await res.json();

    await field.updateArr([addComment, ...field.messagesArr]);

    socket.emit('message sended', addComment);

    
  }


  const handleAddComment = async () => {

    await postChatMessage();

    

    messRef.current.value = '';
  };

  return (
    <>
      <div id="chat-box-header">{field.contact}</div>

      <div id="comments-list" ref={commentsListRef}>

        {field.messagesArr &&
          field.messagesArr
            .slice()
            .reverse()
            .map((element, index) => {
              if (element.sender.username !== field.username) {
                return (
                  <div className="chat-bubble chat-bubble-right" key={index}>
                    <div className="mess-content">{element.content}</div>
                  </div>
                );
              } else {
                return (
                  <div className="chat-bubble chat-bubble-left" key={index}>
                    <div className="mess-content">{element.content}</div>
                  </div>
                );
              }
            })}

      </div>

      <div className="input-group" id="my-chat-input">
        <input type="text" className="form-control" placeholder="Type a message" ref={messRef}></input>
        <button className="btn btn-primary" type="button" onClick={handleAddComment}>Send</button>
      </div>
    </>

  );

}

export default ChatBox;