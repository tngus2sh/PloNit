import { initializeApp } from "firebase/app";
import { getMessaging, getToken } from "firebase/messaging";

const firebaseConfig = {
  apiKey: process.env.REACT_APP_API_KEY,
  authDomain: "plonit.firebaseapp.com",
  projectId: "plonit",
  storageBucket: "plonit.appspot.com",
  messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_APP_ID,
  measurementId: process.env.REACT_APP_MEASUREMENT_ID,
};

function requestPermission() {
  console.log("푸시 허가 받는 중 ...");

  void Notification.requestPermission().then((permission) => {
    if (permission === "granted") {
      console.log("푸시 알림이 허용되었습니다.");
    } else {
      console.log("푸시 알림이 허용되지 않았습니다");
    }
  });

  const app = initializeApp(firebaseConfig);
  const messaging = getMessaging(app);

  void getToken(messaging).then((token) => {
    if (token.length > 0) {
      console.log("푸시 토큰 : ", token);
    } else {
      console.log("푸시 토큰 실패");
    }
  });
}

requestPermission();
