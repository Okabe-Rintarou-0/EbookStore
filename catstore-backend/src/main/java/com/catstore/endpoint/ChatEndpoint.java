package com.catstore.endpoint;

import com.catstore.config.HttpSessionConfig;
import com.catstore.decoder.WebsocketMessageDecoder;
import com.catstore.encoder.WebsocketMessageEncoder;
import com.catstore.model.*;
import com.catstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(
        value = "/chat",
        encoders = {
                WebsocketMessageEncoder.class
        },
        decoders = {
                WebsocketMessageDecoder.class
        },
        configurator = HttpSessionConfig.class
)
@Component
public class ChatEndpoint {
    private static UserService userService;
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private HttpSession httpSession;

    private static final long MAX_DISCONNECT_THRESH = 6000;
    private static final HashMap<Session, Long> heartBeat = new HashMap<>();

    @Autowired
    public void setUserService(UserService userService) {
        ChatEndpoint.userService = userService;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get("session");
        System.out.println("User joined with http session id " + httpSession.getId());
        String response = "User | WebSocket session ID " + session.getId() + " | HTTP session ID " + httpSession.getId();
        System.out.println(response);

        sessions.add(session);
        heartBeat.put(session, System.currentTimeMillis());
        //Server pings, wait for pongs.
    }

    @OnMessage
    public void onMessage(final Session session, WebsocketMessage message) {
        System.out.println("Receive a message from server: " + message.toString());

        if (message instanceof ActionMessage) {
            ActionMessage actionMessage = (ActionMessage) message;
            String action = actionMessage.action;
            Integer userId = (Integer) httpSession.getAttribute("userId");
            switch (action) {
                case "Join":
                case "Leave":
                    sendAll(new InfoMessage(userService.getChatRoomMemberInfo(userId), actionMessage.action));
                    break;
                case "Chat":
                    sendAll(new ChatMessage(userService.getChatRoomMemberInfo(userId), actionMessage.message));
                    break;
            }
        } else if (message instanceof PingPongMessage) {
            PingPongMessage pingPongMessage = (PingPongMessage) message;
            System.out.println("Server received heart-beat " + pingPongMessage.timestamp);
            heartBeat.put(session, pingPongMessage.timestamp);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("User closed with http session id " + httpSession.getId());
        String response = "User | WebSocket session ID " + session.getId() + " | HTTP session ID " + httpSession.getId();
        System.out.println(response);

        sessions.remove(session);
        Integer userId = (Integer) httpSession.getAttribute("userId");
        sendAll(new InfoMessage(userService.getChatRoomMemberInfo(userId), "Leave"));
    }


    /* Forward a message to all connected clients
     * The endpoint figures what encoder to use based on the message type */
    public synchronized void sendAll(Object msg) {
        try {
            for (Session s : sessions) {//session.getOpenSessions()) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(msg);
                    System.out.println("Send: " + msg.toString());
                }
            }
        } catch (IOException | EncodeException e) {
            System.out.println("Error occurred: " + e.toString());
        }
    }

    @Scheduled(fixedRate = 2000L)
    void checkHeartBeat() {
        long current = System.currentTimeMillis();
        sessions.removeIf(session -> current - heartBeat.get(session) >= MAX_DISCONNECT_THRESH);
        sendAll(new PingPongMessage());
    }
}



