import React, { useState, useRef, useEffect } from 'react';


function ContactTemplate(field) {



    var ownMessages = [];
    const buttonRef = useRef(null);

    async function getMessages() {

        const token = sessionStorage.getItem("myToken");
        const res = await fetch(`http://localhost:5000/api/Chats/${field.contactID}/Messages`, {
            'method': 'get',
            'headers': {
                'Content-Type': 'application/json',
                "authorization": `Bearer ${token}`
            },

        })
        const curr_messages = await res.json();

        for (let i = 0; i < curr_messages.length; i++) {
            ownMessages.push(curr_messages[i]);

        }

    }
    const handleButtonClick = async () => {
        await getMessages();
        field.onClick(field.name);
        field.onClick2(field.contactID);
        field.onClick3(ownMessages);
        field.onClick4(field.username);
        field.onClick5();
        localStorage.setItem("curren_contact", field.name);
    }


    return (
        <>
            <div className="wrap-contact-button">
                <button className={`contact-button ${localStorage.getItem("curren_contact") === field.name ? 'active' : ''}`} onClick={handleButtonClick} ref={buttonRef}  >
                    <div className="contact-info">
                        <img src={field.img} id="contact-img" alt="image" className="round-image" />
                        <p className="name" id="contact-name">{field.name}</p>
                        <p id="contact-date">{field.date}</p>
                        <div className="row" id="contact-lass-mess-row">
                            <div id="contact-last-mess">
                                {field.lastMess && field.lastMess.length > 10
                                    ? field.lastMess.substring(0, 10) + '...'
                                    : ""}
                            </div>
                        </div>
                    </div>
                </button>
            </div>


        </>

    );

}

export default ContactTemplate;