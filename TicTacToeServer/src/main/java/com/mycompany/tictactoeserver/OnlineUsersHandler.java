/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

import com.iti.group3.tic_tac_toe_shared.GameData;
import com.iti.group3.tic_tac_toe_shared.GameMove;
import com.iti.group3.tic_tac_toe_shared.Request;
import com.iti.group3.tic_tac_toe_shared.RequestType;
import com.iti.group3.tic_tac_toe_shared.Response;
import com.iti.group3.tic_tac_toe_shared.ResponseType;
import com.iti.group3.tic_tac_toe_shared.UserData;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ehab
 */
public class OnlineUsersHandler {

    UserDAO userDAO = new UserDAO();

    public Response getOnlineUsers(UserSocket cs, Request request) {
        Response<List<UserData>> responseList;
        
        try {
            List<UserData> onlineUsers = userDAO.getOnlineUsers();
            if (onlineUsers.isEmpty()) {
                responseList = new Response<>(false, ResponseType.NO_ONLINEUSERS, null);
            } else {
                responseList = new Response<>(true, ResponseType.GET_ONLINE_USERS_SUCCESS, onlineUsers);
            }
        } catch (SQLException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            responseList = new Response(false, ResponseType.SERVER_FAILURE, null);
        }

        return responseList;
    }

    public Response getLeaderBoard(UserSocket cs, Request request) {
        UserDAO userDAO = new UserDAO();
        Response<List<UserData>> responseList;

        try {
            List<UserData> onlineUsers = userDAO.getLeaderBoard();
            if (onlineUsers.isEmpty()) {
                responseList = new Response<>(false, ResponseType.NO_ONLINEUSERS, null);
            } else {
                responseList = new Response<>(true, ResponseType.GET_ONLINE_USERS_SUCCESS, onlineUsers);
            }
        } catch (SQLException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            responseList = new Response(false, ResponseType.SERVER_FAILURE, null);
        }

        return responseList;
    }

    private UserStreamSocket getUserStreamSocket(UserData userData) {
        for (int i = 0; i < ServerMain.onlineStreamSockets.size(); i++) {
            if (userData.getUserName().equals(ServerMain.onlineStreamSockets.get(i).user.getUserName())) {
                return ServerMain.onlineStreamSockets.get(i);

            }
        }
        return null;
    }

    public Response sendGameInvite(UserStreamSocket userSocket1, Request request) {

        UserStreamSocket userSocket2 = getUserStreamSocket((UserData) request.getData());

        if (userSocket2 == null) {
            return new Response(false, ResponseType.INVITE_DROPPED, null);
        } else {
            try {
                userSocket2.write(new Response(true, ResponseType.REQUEST_GAME, userSocket1.user));
                return null;
            } catch (IOException ex) {
                System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return new Response(false, ResponseType.INVITE_DROPPED, null);

    }

    public Response acceptGameInvite(UserStreamSocket userSocket2, Request request) {
        try {
            UserStreamSocket userSocket1 = getUserStreamSocket((UserData) request.getData());
            GameSession session = new GameSession(userSocket1, userSocket2);
            ServerMain.activeGames.add(session);
            GameData game = new GameData("1", userSocket1.user, userSocket2.user);
            userSocket1.write(new Response(true, ResponseType.START_GAME, game));
            userSocket2.write(new Response(true, ResponseType.START_GAME, game));
            return null;
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return new Response(false, ResponseType.SERVER_FAILURE, null);
    }

    public Response rejectGameInvite(UserStreamSocket userSocket2, Request request) {
        try {
            UserStreamSocket userSocket1 = getUserStreamSocket((UserData) request.getData());
            userSocket1.write(new Response(true, ResponseType.INVITE_REJECTED, userSocket2.user));
            return null;
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return new Response(false, ResponseType.SERVER_FAILURE, null);

    }

    private GameSession getSessionByPlayer(UserStreamSocket player) {
        for (GameSession session : ServerMain.activeGames) {
            if (session.contains(player)) {
                return session;
            }
        }
        return null;
    }

    private void removeSessionByPlayer(UserStreamSocket player) {
        ServerMain.activeGames.removeIf(gameSession -> gameSession.contains(player));
    }

    public void sendMove(UserStreamSocket sender, Request request) {
        GameMove gameMove = (GameMove) request.getData();
        GameSession gameSession = getSessionByPlayer(sender);
        if (gameSession == null) {
            return;
        }
        UserStreamSocket recieveUserSocket = gameSession.getOpponent(sender);
        try {
            recieveUserSocket.write(new Response(true, ResponseType.Server_SENT_MOVE, gameMove));
            sender.write(new Response(true, ResponseType.Server_SENT_MOVE, gameMove));
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void restartGame(UserStreamSocket sender) {
        GameSession gameSession = getSessionByPlayer(sender);
        if (gameSession == null) {
            return;
        }
        UserStreamSocket recieveUserSocket = gameSession.getOpponent(sender);
        try {
            recieveUserSocket.write(new Response(true, ResponseType.SERVER_RESTART_GAME, null));
            sender.write(new Response(true, ResponseType.SERVER_RESTART_GAME, null));
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void endGame(UserStreamSocket sender) {
        GameSession gameSession = getSessionByPlayer(sender);
        UserStreamSocket recieveUserSocket = gameSession.getOpponent(sender);
        try {
            recieveUserSocket.write(new Response(true, ResponseType.SERVER_END_GAME, null));
            sender.write(new Response(true, ResponseType.SERVER_END_GAME, null));
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        System.out.println("active games list is: " + ServerMain.activeGames.size());
        removeSessionByPlayer(sender);
        System.out.println("active games list is: " + ServerMain.activeGames.size());
    }
    
    public void updateScore(UserStreamSocket sender, Request request) throws SQLException {
        UserData winner = (UserData) request.getData();
        int winnerscore = userDAO.updateUserOnlineScore(winner);
        GameSession gameSession = getSessionByPlayer(sender);
        UserStreamSocket recieveUserSocket = gameSession.getOpponent(sender);
        try {
            recieveUserSocket.write(new Response(true, ResponseType.SERVER_UPDATE_WINNER_SCORE, winnerscore));
            sender.write(new Response(true, ResponseType.SERVER_UPDATE_WINNER_SCORE, winnerscore));
        } catch (IOException ex) {
            System.getLogger(OnlineUsersHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
