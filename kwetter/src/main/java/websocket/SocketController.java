package websocket;

import com.google.gson.Gson;
import domain.Kweet;
import domain.User;
import services.UserService;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

// http://www.baeldung.com/java-websockets
@ServerEndpoint("/socket/{username}")
public class SocketController {
    private static Gson gson = new Gson();
    private static Map<Long, Set<Session>> peers = new HashMap<>();
    @Inject
    private UserService userService;

    @OnClose
    public static void close(Session session) {
        for (long key : peers.keySet()) {
            for (Session s : peers.get(key)) {
                if (s == session) {
                    peers.get(key).remove(session);
                }
            }
        }
    }

    public static void sendKweetSocket(Kweet kweet, List<User> users) {
        try {
            String json = gson.toJson(kweet);

            for (Map.Entry<Long, Set<Session>> peer : peers.entrySet()) {
                for (User user : users) {
                    if (user.getId() == peer.getKey()) {
                        for (Session session : peer.getValue()) {
                            if (session.isOpen()) {
                                session.getBasicRemote().sendObject(json);
                            } else {
                                close(session);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(@PathParam("username") String username, Session session) {
        User user = userService.getUserByUsername(username);

        if (user != null) {
            long id = user.getId();
            addPeer(id, session);
        }
    }

    private void addPeer(Long id, Session session) {
        if (peers.containsKey(id)) {
            peers.get(id).add(session);
        } else {
            Set<Session> set = new HashSet<>();
            set.add(session);
            peers.put(id, set);
        }
    }
}
