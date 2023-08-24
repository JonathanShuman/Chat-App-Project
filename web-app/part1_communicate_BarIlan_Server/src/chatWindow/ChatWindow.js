import './ChatWindow.css';
import React, { useState, useRef } from 'react';
import AddContactButton from './addContactButton/AddContactButton';
import MyUser from './myUser/MyUser';
import ChatBox from './chatBox/ChatBox';
import { Link } from 'react-router-dom';


function ChatWindow() {

    const [selectedMessage,setMessage]=useState("");

    const [selectedContact, setSelectedContact] = useState(null);
    const [selectedID, setSelectedID] = useState(null);
    const [contactMessages, setcontactMessages] = useState(null);
    const [contactUsername, setContactUsername] = useState(null);
    const handleContactClick = (contact) => {
        setSelectedContact(contact);
    }
    const handleContactClick2 = (id) => {
        setSelectedID(id);
    }
    const handleContactClick3 = (m) => {
        setcontactMessages(m);
        
    }
    const handleContactClick4 = (username) => {
        setContactUsername(username);
        console.log(username);
    }

    return (
        <>
            <Link to="/login">
                <button id="log-out-btn">
                    <i className="bi bi-box-arrow-right"></i>
                </button>
            </Link>
            <div className="container text-center" id="my-div2">
                <div className="row align-items-start no-gutters" id="my-row">
                    <div className="col-4 p-0" id="my-col-left">

                        <MyUser />
                        <AddContactButton onContactClick={handleContactClick} onContactClick2={handleContactClick2} onContactClick3={handleContactClick3} onContactClick4={handleContactClick4} selectedMessage={selectedMessage}/>


                    </div>

                    {/* end of left col */}
                    <div className="col-8 p-0" id="my-col-right">
                        {selectedContact ? (
                            <ChatBox contact={contactUsername} idContact={selectedID} messagesArr={contactMessages} updateArr={handleContactClick3} username={contactUsername} setMessage={setMessage}/>
                        ) : (
                            <div id='no-chat'>Click on a contact to start a chat.</div>
                        )}

                    </div>
                    {/* end of right col */}
                </div>
            </div>
        </>
    );
}
export default ChatWindow;
