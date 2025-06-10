import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
  apiKey: "AIzaSyC2FcKkb6DP8URpNrEGwh-zIEdCROYUJFQ",
  authDomain: "didong-foodapp-1465e.firebaseapp.com",
  databaseURL: "https://didong-foodapp-1465e-default-rtdb.firebaseio.com",
  projectId: "didong-foodapp-1465e",
  storageBucket: "didong-foodapp-1465e.appspot.com",
  messagingSenderId: "267636866368",
  appId: "1:267636866368:web:2b3ff782174d63d6274ea5",
  measurementId: "G-ZLTY0P0CHX"
};
const app = initializeApp(firebaseConfig);
export const storage = getStorage(app);