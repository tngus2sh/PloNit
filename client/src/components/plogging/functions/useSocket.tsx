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

interface Message {
  type: string;
  senderId: string;
  location: Location;
  roomId: string;
}

interface IuseSocket {
  roomId: string;
  senderId: string;
}

function useSocket({ roomId, senderId }: IuseSocket) {
  const [locations, setLocations] = useState<Locations>({});
  const [getLocation, setGetLocation] = useState<boolean>(false);
  const stompClient = useRef<Client | null>(null);

  function handleLocation() {
    setGetLocation(true);
  }

  function onMessageReceived(message: IMessage) {
    const newMessage: Message = JSON.parse(message.body);
    setLocations((current) => {
      // 기존 locations를 복사하여 새 객체에 기존 내용을 유지합니다.
      return { ...current, [newMessage.senderId]: newMessage.location };
    });
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

  async function sendMessage() {
    console.log("[roomId]:", roomId);
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

  useEffect(() => {
    connectToSocket();
    return () => {
      if (stompClient.current) {
        stompClient.current.deactivate();
      }
    };
  }, []);

  useEffect(() => {
    if (getLocation) {
      sendMessage();
      setGetLocation(false);
    }
  }, [getLocation]);

  return { locations, handleLocation };
}

export default useSocket;
