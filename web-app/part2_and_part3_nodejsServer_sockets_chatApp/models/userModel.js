
import mongoose from 'mongoose';

const UserSchema = new mongoose.Schema({
    username: String,
    password: String,
    displayName: String,
    profilePic: String,
  });

  const User = mongoose.model('User', UserSchema);

async function addNewUser(username, password, displayName, profilePic) {
    try {
        const newUser = new User({
          username: username,
          password: password,
          displayName: displayName,
          profilePic: profilePic,
        });
    
        await newUser.save();
        console.log('User created successfully:', newUser);
      } catch (error) {
        console.error('Error creating user:', error);
      }


}

async function checkUserExist(username, password) {

     return await User.findOne({ username: username, password: password });
}

async function getUserForToken(username) {

    return await User.findOne({ username: username});
}

export default {addNewUser, checkUserExist, getUserForToken }

  
  
  
  
  