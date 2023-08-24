import mongoose from 'mongoose';

const firebasetokenScheme = new mongoose.Schema({
  username: String,
  firebaseToken: String,
});

const fireBaseUser = mongoose.model('firebase', firebasetokenScheme);

async function addOrUpdateFirebaseToken(username, firebaseToken) {
  try {
    const existingUser = await fireBaseUser.findOne({ username });

    if (existingUser) {
      // User already exists, update the firebaseToken
      existingUser.firebaseToken = firebaseToken;
      await existingUser.save();
      //console.log('Successfully updated firebase token:', existingUser);
    } else {
      // User doesn't exist, create a new document
      const newFirebase = new fireBaseUser({
        username: username,
        firebaseToken: firebaseToken,
      });
      await newFirebase.save();
      //console.log('Successfully created new firebase user:', newFirebase);
    }
  } catch (error) {
    console.error('Error updating firebase token:', error);
  }
}



async function getOtherParticipentFB(otherParticipant) {
  try {
    const user = await fireBaseUser.findOne({ username: otherParticipant });
    if (user) {
      //console.log('User found:', user);
      const firebaseToken = user.firebaseToken;
      //console.log('Firebase Token:', firebaseToken);
      return firebaseToken;
    } else {
      console.log('User not found.');
      return null; // Return null if the user is not found
    }
  } catch (error) {
    console.error('Error retrieving firebase token:', error);
    return null; // Return null in case of an error
  }
}






export default { addOrUpdateFirebaseToken, getOtherParticipentFB};
