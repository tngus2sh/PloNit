import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

export default function test() {
  const config = {
    apiKey: "AIzaSyDkOZCvicVeBUgXozaBKA44Rfa0nVmKoc0",
    authDomain: "fcm-test-e5c56.firebaseapp.com",
    projectId: "fcm-test-e5c56",
    storageBucket: "fcm-test-e5c56.appspot.com",
    messagingSenderId: "911328456436",
    appId: "1:911328456436:web:c13bb2ce79f12e827ef8a1",
    measurementId: "G-87G9C7CL47",
  };

  navigator.serviceWorker
    .register("./firebase-messaging-sw.js")
    .then(function () {
      void Notification.requestPermission().then((permission) => {
        if (permission === "granted") {
          console.log("푸시 알림이 허용되었습니다.");
        } else {
          console.log("푸시 알림이 허용되지 않았습니다");
        }
      });

      const app = initializeApp(config);
      const messaging = getMessaging(app);

      void getToken(messaging, {
        vapidKey:
          "BI22DGeYupjm6S_19aO8XMQnZD_8o22SfACFvaGUz7pLuxVZ5nlmce4XDXgNoCTsYe18-HER_Y0vyyftyHXvjvE",
      }).then((token) => {
        if (token.length > 0) {
          console.log("푸시 토큰 : ", token);
          const tokenElement = document.getElementById("token");
          tokenElement.innerHTML = token;
        } else {
          console.log("푸시 토큰 실패 !");
        }
      });

      // //포그라운드 메시지 수신
      onMessage(messaging, (payload) => {
        console.log("Message received. ", payload);
        // ...
      });
    });
}

test();
// const config = {
//     apiKey: "AIzaSyANwszWUtIFPZdPNawZ5eNWEO1B-TF9wHM",
//     authDomain: "fcm-test-e5c56.firebaseapp.com",
//     projectId: "fcm-test-e5c56",
//     storageBucket: "fcm-test-e5c56.appspot.com",
//     messagingSenderId: "765168199061",
//     appId: "1:765168199061:web:84b52be1f1ae9072d3829c",
//     measurementId: "G-0EKY8M6CEC",
//   };

// export function requestPermission() {
//   console.log("푸시 허가 받는 중 ...");

//   void Notification.requestPermission().then((permission) => {
//     if (permission === "granted") {
//       console.log("푸시 알림이 허용되었습니다.");
//     } else {
//       console.log("푸시 알림이 허용되지 않았습니다");
//     }
//   });

//   const app = initializeApp(firebaseConfig);
//   const messaging = getMessaging(app);

//   // 토큰을 반환하는 Promise를 생성
//   return getToken(messaging).then((token) => {
//     if (token.length > 0) {
//       console.log("푸시 토큰 : ", token);
//       return token; // 토큰 반환
//     } else {
//       console.log("푸시 토큰 실패");
//       throw new Error("푸시 토큰 실패"); // 실패시 에러 반환
//     }
//   });
// }
// requestPermission();
