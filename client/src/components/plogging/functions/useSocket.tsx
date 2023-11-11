import React, { useState, useEffect, useRef } from "react";
import SockJS from "sockjs-client";
import { Client, IMessage } from "@stomp/stompjs";
import getGPS from "./getGPS";

interface Location {
  latitude: number;
  longitude: number;
}

interface Locations {
  [key: string]: Location;
}

interface UserImages {
  [key: string]: string;
}

interface Message {
  type: string;
  senderId: string;
  location?: Location;
  userImage?: string;
  roomId: string;
}

interface IuseSocket {
  roomId: string;
  senderId: string;
}

function useSocket({ roomId, senderId }: IuseSocket) {
  const [startRequest, setStartRequest] = useState<boolean>(false);
  const [crewpingStart, setCrewpingStart] = useState<boolean>(false);
  const [endRequest, setEndRequest] = useState<boolean>(false);
  const [crewpingEnd, setCrewpingEnd] = useState<boolean>(false);
  const [locations, setLocations] = useState<Locations>({});
  const [userImage, setUserImage] = useState<string>("");
  const [userImages, setUserImages] = useState<UserImages>({});
  const [getLocation, setGetLocation] = useState<boolean>(false);
  const stompClient = useRef<Client | null>(null);

  function handleStartRequest() {
    setStartRequest(true);
  }

  function handleEndRequest() {
    setEndRequest(true);
  }

  function handleLocation() {
    setGetLocation(true);
  }

  function onMessageReceived(message: IMessage) {
    const newMessage: Message = JSON.parse(message.body);
    if (newMessage.type === "start") {
      setCrewpingStart(true);
    }
    if (newMessage.type === "end") {
      setCrewpingEnd(true);
    }
    if (newMessage.type === "location") {
      setLocations((current) => {
        if (newMessage.location) {
          return { ...current, [newMessage.senderId]: newMessage.location };
        }
        return current;
      });
    }
    if (newMessage.type === "image") {
      setUserImages((current) => {
        if (newMessage.userImage) {
          return { ...current, [newMessage.senderId]: newMessage.userImage };
        }
        return current;
      });
    }
  }

  function connectToSocket() {
    stompClient.current = new Client({
      webSocketFactory: () => new SockJS(`${process.env.REACT_APP_SOCKET}`),
      debug: (msg) => console.log(msg),
    });

    stompClient.current.onConnect = () => {
      stompClient.current?.subscribe(
        `/topic/chat/room/${roomId}`,
        onMessageReceived,
      );
    };

    stompClient.current?.activate();
  }

  function sendStartRequest() {
    console.log(`[roomId]: ${roomId} - sendStartRequest`);
    if (stompClient.current?.connected) {
      const newMessage: Message = {
        type: "start",
        senderId: senderId,
        roomId: roomId,
      };
      console.log("[SEND]", newMessage);

      stompClient.current?.publish({
        destination: `/app/chat/message`,
        body: JSON.stringify(newMessage),
      });
    } else {
      console.error(
        "STOMP client is not connected or room is not selected. Cannot send message.",
      );
    }
  }

  function sendEndRequest() {
    console.log(`[roomId]: ${roomId} - sendEndRequest`);
    if (stompClient.current?.connected) {
      const newMessage: Message = {
        type: "end",
        senderId: senderId,
        roomId: roomId,
      };
      console.log("[SEND]", newMessage);

      stompClient.current?.publish({
        destination: `/app/chat/message`,
        body: JSON.stringify(newMessage),
      });
    } else {
      console.error(
        "STOMP client is not connected or room is not selected. Cannot send message.",
      );
    }
  }

  async function sendLocation() {
    console.log(`[roomId]: ${roomId} - sendLocation`);
    if (stompClient.current?.connected) {
      getGPS()
        .then((response) => {
          const { latitude, longitude } = response.coords;
          const newMessage: Message = {
            type: "location",
            senderId: senderId,
            location: { latitude, longitude },
            roomId: roomId,
          };
          console.log("[SEND]", newMessage);

          stompClient.current?.publish({
            destination: `/app/chat/message`,
            body: JSON.stringify(newMessage),
          });
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      console.error(
        "STOMP client is not connected or room is not selected. Cannot send message.",
      );
    }
  }

  function sendImage() {
    console.log(`[roomId]: ${roomId} - sendImage`);
    if (stompClient.current?.connected) {
      const newMessage: Message = {
        type: "image",
        senderId: senderId,
        userImage: userImage,
        roomId: roomId,
      };
      console.log("[SEND]", newMessage);

      stompClient.current?.publish({
        destination: `/app/chat/message`,
        body: JSON.stringify(newMessage),
      });
    } else {
      console.error(
        "STOMP client is not connected or room is not selected. Cannot send message.",
      );
    }
  }

  useEffect(() => {
    connectToSocket();
    return () => {
      if (stompClient.current) {
        stompClient.current.deactivate();
      }
    };
  }, []);

  useEffect(() => {
    if (startRequest) {
      sendStartRequest();
    }
  }, [startRequest]);

  useEffect(() => {
    if (endRequest) {
      sendEndRequest();
    }
  }, [endRequest]);

  useEffect(() => {
    if (getLocation) {
      sendLocation();
      setGetLocation(false);
    }
  }, [getLocation]);

  useEffect(() => {
    if (userImage) {
      sendImage();
    }
  }, [userImage]);

  return {
    crewpingStart, // true 시 크루핑 시작
    handleStartRequest, // 크루핑장이 크루핑을 시작하자는 요청 보내는 함수
    crewpingEnd, // true 시 크루핑 끝
    setCrewpingEnd, // 개인이 크루핑을 끝내는 함수
    handleEndRequest, // 크루핑장이 크루핑을 끝내자는 요청을 보내는 함수
    locations, // 자신을 포함한 다른 유저들의 위치를 가지는 obj
    handleLocation, // 현재 유저의 위치를 소켓으로 보내는 함수
    userImages, // 자신을 포함한 다른 유저들의 프로필 이미지는 가지는 obj
    setUserImage, // 현재 유저의 프로필 이미지를 보내는 함수
  };
}

export default useSocket;
