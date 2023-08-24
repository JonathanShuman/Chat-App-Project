import React, { useState, useRef, useEffect } from 'react';
import ContactTemplate from "../contactTemplate/ContactTemplate";


function AddContactButton(field) {
    const [contacts, setContacts] = useState({});
    const modalRef = useRef();
    const curr_date = new Date();
    const formatDate = curr_date.toLocaleDateString();
    const formatTime = curr_date.toLocaleTimeString();
    const fullDate = `${formatDate} ${formatTime}`;


    useEffect(() => {
        getChats();
    }, []); 

    async function getChats() {
        
        const token = sessionStorage.getItem("myToken");
        const res = await fetch(`http://localhost:5000/api/Chats`, {
            'method': 'get',
            'headers': {
                "authorization": `Bearer ${token}`
            },

        })
        const chatsDetails = await res.json();
        
        await chatsDetails.forEach((chat) => {
            if (chat.lastMessage === null){
                chat.lastMessage= "";
            }
            const newContact = {
                id: chat.id,
                username:chat.user.username,
                name: chat.user.displayName,
                date: fullDate,
                lastMess: chat.lastMessage,
                img: chat.user.profilePic,
            };

            setContacts(prevContacts => ({ ...prevContacts, [newContact.id]: newContact }));
            
        });
        

    }

    async function postChatContact(new_user) {
        const contactName = modalRef.current.value;
        const token = sessionStorage.getItem("myToken");
        const res = await fetch('http://localhost:5000/api/Chats', {
            'method': 'post',
            'headers': {
                'Content-Type': 'application/json',
                "authorization": `Bearer ${token}`,

            },
            body: JSON.stringify({
                username: `${contactName}`
            })
        })
       
        if (res.status === 200) {
            
            const contactValues = await res.json();
            
            const newContact = {
                id: contactValues.id,
                username:contactValues.user.username,
                name: contactValues.user.displayName,
                date: fullDate,
                lastMess: "",
                img: contactValues.user.profilePic,
            };
           
            setContacts(prevContacts => ({ ...prevContacts, [newContact.id]: newContact }));
            
            modalRef.current.value = "";
        }
        else {
            
            alert("No such user");
        }
        
    }

    const handleAddContact = async () => {

        await postChatContact();
       
    };

    return (
        <>
            <span>
                <button id="my-icon-btn" className="btn" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                    <i className="bi bi-person-plus-fill" id="my-add-icon"></i>
                </button>
            </span>
            <div className="modal fade" id="staticBackdrop" tabIndex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="staticBackdropLabel">Add new contact</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <input type="text" className="form-control" placeholder="Contact's identifier" ref={modalRef} />
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" className="btn btn-primary" onClick={handleAddContact} data-bs-dismiss="modal">Add</button>
                        </div>
                    </div>
                </div>
            </div>


            <div id = "contects-list" >
            <div id="contact-template-list">
                {Object.values(contacts).map((contact) => (
                    <ContactTemplate
                        key={contact.id}
                        username={contact.username}
                        name={contact.name}
                        date={contact.date}
                        lastMess={contact.lastMess.content}
                        img={contact.img}
                        contactID={contact.id}
                        onClick={field.onContactClick}
                        onClick2={field.onContactClick2}
                        onClick3={field.onContactClick3}
                        onClick4={field.onContactClick4}
                        onClick5 = {getChats}
                    />
                ))}
            </div>
            </div>
           

        </>

    );

}

export default AddContactButton;