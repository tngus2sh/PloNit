import React, { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import { Client, IMessage } from "@stomp/stompjs";
import getGPS from "./getGPS";
import { Message } from "interface/ploggingInterface";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as Crewping from "store/crewping-slice";

interface IuseSocket {
  stompClient: React.MutableRefObject<Client | null>;
  roomId: string;
  senderId: string;
}

function useSocket({ stompClient, roomId, senderId }: IuseSocket) {
  const dispatch = useDispatch();
  const startRequest = useSelector<rootState, boolean>((state) => {
    return state.crewping.startRequest;
  });
  const endRequest = useSelector<rootState, boolean>((state) => {
    return state.crewping.endRequest;
  });
  const getLocation = useSelector<rootState, boolean>((state) => {
    return state.crewping.getLocation;
  });
  const userImage = useSelector<rootState, string>((state) => {
    return state.user.info.profileImage;
  });
  const profileImage = useSelector<rootState, string>((state) => {
    return state.crewping.profileImage;
  });
  const [toggleSocket, setToggleSocket] = useState<boolean>(false);

  function onMessageReceived(message: IMessage) {
    const newMessage: Message = JSON.parse(message.body);
    if (newMessage.type === "START") {
      dispatch(Crewping.setCrewpingStart(true));
    }
    if (newMessage.type === "END") {
      dispatch(Crewping.setCrewpingEnd(true));
    }
    if (newMessage.type === "LOCATION") {
      dispatch(Crewping.setLocations(newMessage));
    }
    if (newMessage.type === "WAIT") {
      dispatch(Crewping.setMembers(newMessage));
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

      setTimeout(() => {
        dispatch(Crewping.setProfileImage(userImage));
      }, 2000);
    };

    stompClient.current?.activate();
  }

  function sendStartRequest() {
    console.log(`[roomId]: ${roomId} - sendStartRequest`);
    if (stompClient.current?.connected) {
      const newMessage: Message = {
        type: "START",
        nickName: senderId,
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
        type: "END",
        nickName: senderId,
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
            type: "LOCATION",
            nickName: senderId,
            senderId: senderId,
            roomId: roomId,
            latitude: latitude,
            longitude: longitude,
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

  function sendWait() {
    console.log(`[roomId]: ${roomId} - sendWait`);
    if (stompClient.current?.connected) {
      const newMessage: Message = {
        type: "WAIT",
        nickName: senderId,
        senderId: senderId,
        roomId: roomId,
        profileImage: profileImage,
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
    if (toggleSocket) {
      connectToSocket();
    }
    return () => {
      if (stompClient.current) {
        stompClient.current.deactivate();
      }
    };
  }, [toggleSocket]);

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
      dispatch(Crewping.setGetLocation(false));
    }
  }, [getLocation]);

  useEffect(() => {
    if (profileImage) {
      sendWait();
    }
  }, [profileImage]);

  return { setToggleSocket };
}

export default useSocket;
