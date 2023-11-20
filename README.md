# Chat-App-Project


## For cloning our project you can to do this following steps:<br />
1.Enter the terminal.<br />
2.Open a folder in your computer (In example call her "gitFolder').<br />
3.Open the folder.<br />
4.Right click "open in terminal".<br />
5.Write in the termninal "git clone https://github.com/JonathanShuman/Chat-App-Project.git".<br />
6.Open the relevant folder.<br />
7.Add the folder "node_modules".<br />
8.Open the cmd from the current folder.<br />
9. Go to the description of ex2 in order to choose which part you want to run and get instructions on what to write for it. <br />
10.Now you can see our App<br />

## Description-
### Ex1_partA
In this part of the project we were required to design 3 different screens - a register screen, a login screen and a login screen.
All screens are static, but you can switch between the login screen and the register screen.
We called our app "Span on" and it has a logo on the top left side of the screen.

Screenshots and explanation of each screen:

##### Register Screen-
On the register screen there is an option to open a new user by entering a username, password (including password verification), display name and photo.<br />
<img width="959" alt="register" src="https://user-images.githubusercontent.com/118110474/235076332-1933ec33-dc15-4d42-8aa7-419e44270185.PNG">


##### Login Screen-
On the login screen there is an option to enter a username and password or go to the registration page.<br />
<img width="959" alt="login" src="https://user-images.githubusercontent.com/118110474/235076355-d9aec62e-c2b8-44e4-bc8e-14ef89f07616.PNG">


##### Chat Screen-
The chat screen is divided into 2 sides-<br />
The left side: the upper part shows the username and the picture of our user and below for each chat (for each user we talk) : a picture of the user, his nickname, the last message sent and the date and time it arrived.<br />
The right side: where the correspondence will be displayed, which are the messages with the person whose conversation is currently open.
In addition, we have a Log out button that allows you to exit the chat screen.<br />
<img width="960" alt="chat" src="https://user-images.githubusercontent.com/118110474/235076389-0f4c1bd2-cc2b-477e-a467-d8e0d8ff16ed.PNG">

### Ex1_partB
In this part of the project we were required to take our code from the previous exercise and turn it into a web application by using react (with the same screens). <br />
On each page we had to implement a different logic, which we will present now: <br />

##### Register Screen-
In the registration screen we defined that the username must be in a certain format and in addition there is a message whether the username is taken or not.  <br />
If the user has not filled in all the details, he receives a notification. <br />
<img width="944" alt="reg1" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/ca902be9-d2f1-4298-9474-b4a372572837">  <br />
<img width="931" alt="reg2" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/c9e558c6-9ee3-4fb3-9d2d-487fba6b426b">  <br />
<img width="933" alt="reg3" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/10c24ba2-afeb-477d-a13d-9a18e95d624f">  <br />
<img width="931" alt="reg4" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/f6aa1486-5e9d-4f4d-9b48-cb537d95c7bf">  <br />
<img width="947" alt="‏‏reg5" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/a4e32902-b049-4fd4-8f59-2edb6c8588b0">  <br />


##### Login Screen-
In the login screen we make sure that the user enter existing username and that the password matches.  <br />
If the user has not filled all the fields correctly , he receives a notification. <br /> 

<img width="936" alt="‏‏log1" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/00cb131d-c436-4c81-ae01-9ca7c4fddd6a">  <br />
<img width="938" alt="‏‏log2" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/5a42194a-f05a-4227-9eea-c5f5616b9b92">  <br />


##### Chat Screen-
In the chats screen you can add new conversations, send messages (now only from our side), and switch between conversations when each contact we created <br />
has unique conversations for him.  On the left side there is the time of the last message sent and its content. <br />
Logging out takes the user to the login screen and disconnects them from the application. <br />
<img width="922" alt="‏‏chat" src="https://github.com/tzlilshkuri/ap2_ex1_pB/assets/118110474/6e565164-8dce-43fd-9ab8-421b46b1c3ee">  <br />

### ap2_ex2-
This assignment is divided into three parts- <br />

##### Part 1 -<br />
In this part we got a ready server and we had to adapt the React code from the previous exercise so that it would work fully against the server.<br />
All the logic had to be performed in front of and with the help of the server - and not locally as it was in the previous exercise.<br />
For example, registering, logging in, sending messages, etc. - all with the help of the server.<br />
In order to run this part, do the following steps- <br />
1.Enter the folder where the server is located, enter cmd and write "chat.exe". <br />
2. Now in order to run the application you have to enter the project folder, enter cmd and write "npm start"  (A more detailed description at the beginning of the readme).<br/> 

##### Parts 2+3 - <br />
First, we created a NodeJS server in the MVC architecture that exposes the same functionality as in the server from the first part and will save the data to MongoDB. <br />
When browsing to the server's home page, the React application will return, and all actions in the React application will work directly from the server,<br />
which will be implemented asynchronously. <br />
Second, we have created real-time communication, that is, when two users are connected to the application at the same time and send each other a message, the message will be displayed directly in the interface without the user needing to do anything else.<br />
We implement the above function with WebSockets.<br />
In order to run this part, do the following steps- <br />
1. Follow all the steps up to and including step 8 at the beginning of the readme and now: <br />
3. Comnnect to MongoDB.<br />
4. Enter the folder where the server is located, enter cmd and write "node server.js". <br />
5. Enter in chrome to the addres "http://localhost:5000/".  <br />
6. Now you can use our app.  <br />


##### Important notes regarding registration and login:
1. The username must be of the form user@gmail.com. <br />
2. When trying to register with a user name that is already in use, we will receive a message that the name is taken. <br />
3. Each password must contain at least 1 capital letter, a number and be at least 7 digits long. <br />
4. When at least one of the fields is empty/not filled in correctly, the user receives a message to check that all the fields are correct. <br />
5. If someone typed different password in the password and confirm password fields he will receive a message.<br />
Screenshot for demonstration: <br />
<img width="228" alt="match" src="https://github.com/tzlilshkuri/ap2_ex2/assets/118110474/63bec8a2-0179-4801-96e8-93db410be1c7">
<img width="221" alt="notmatch" src="https://github.com/tzlilshkuri/ap2_ex2/assets/118110474/c763d3d7-84e7-46b3-81f0-1f776aaed749">
<img width="229" alt="passwrod" src="https://github.com/tzlilshkuri/ap2_ex2/assets/118110474/34d4f6c6-9c82-4eba-84a2-49b9fa2dd378">
<img width="212" alt="username" src="https://github.com/tzlilshkuri/ap2_ex2/assets/118110474/5e3ad5e5-7c09-4c1f-9521-bf04777bb0e1">
<img width="217" alt="usernameexist" src="https://github.com/tzlilshkuri/ap2_ex2/assets/118110474/036c306d-4dcb-4354-8fb8-30302f8aa28d">


##### android part - <br />

## For cloning our project you can to do this following steps:<br />
1.Enter the terminal.<br />
2.Open a folder in your computer (In example call her "gitFolder').<br />
3.Open the folder.<br />
4.Right click "open in terminal".<br />
5.Write in the termninal "git clone https://github.com/tzlilshkuri/ap2_ex3.git".<br />
6.Open the relevant folder.<br />
7.Add the folder "node_modules".<br />
8. Enter the folder where the server is located, enter cmd and write "node server.js" (this step run the server side) <br />
9. Open the Android Studio and click on "run".
10. Enjoy our app!

##  Generel Description-
In this part we created an Android client for our chat system. <br />
The application contain all the screens as in the previous exercises: <br />
- Login screen  <br />
- Registration screen  <br />
- Contacts screen  <br />
- Chat screen .<br />
In addition, we have settings screen that allowed to edit the address of the server + port we are working with and a <br />
button that changes the theme of the application (In out case from bright theme to dark theme). <br />

##### Notifications- <br />
When sendong message for someone the server"push" messages to clients using Notifications Push using Firebase. <br />
That is, as soon as a user sends you a message you will get display an appropriate notification.<br />
(As you can see in the screenshots below).<br />
***A very important note: the notifications can only be seen on devices whose version is not Android 13. <br />

##### Important notes regarding registration and login:
1. The username must be of the form user@gmail.com. <br />
2. When trying to register with a user name that is already in use, we will receive a message that the name is taken. <br />
3. Each password must contain at least 1 capital letter, a number and be at least 7 digits long. <br />
4. When at least one of the fields is empty/not filled in correctly, the user receives a message to check that all the fields are correct. <br />
5. If someone typed different password in the password and confirm password fields he will receive a message.<br />
6. You can't add to your contacts a user who don't exist in the system. <br />


##### Screenshot for demonstration: <br />
Bright Theme: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/f2a238ce-6431-4ef7-a957-67c9f58a9834.png" width="250" height="400">
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/031aebe0-fe91-4e9d-80bb-612fde97d292.png" width="250" height="400">
 <br />


Dark Theme: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/83edaa88-f080-4c28-a120-b21835b2d070.png" width="250" height="400">
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/0a3aac3f-301f-4d0e-a33b-9f8f013857af.png" width="250" height="400">
 <br />


Settings: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/1250c67a-3c66-4faf-a8e7-62831365713f.png" width="250" height="400">
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/04dbd21b-cce7-4f0d-aeca-aa42442dff82.png" width="250" height="400">
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/cb5f431c-7d88-421f-a2db-b6aa18fe2d58.png" width="250" height="400">
 <br />

Contact screen: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/10f6aab3-7372-4a40-a2a7-bd914b8a8883.png" width="250" height="400">
 <br />

Chat Screen: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/15ac5b99-db58-4720-b93e-8e1ae1747df1.png" width="250" height="400">
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/db4779aa-2972-4bc3-8f9f-704d960fdf47.png" width="250" height="400">
 <br />


Push Notification: <br />
<img src="https://github.com/tzlilshkuri/ap2_ex3/assets/118110474/cb9c51d6-026c-4306-9847-1c61914b4645.png" width="250" height="400">
 <br />





