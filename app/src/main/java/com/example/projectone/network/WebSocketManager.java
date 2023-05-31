package com.example.projectone.network;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketManager {

    private WebSocketClient webSocketClient;

    public void connect() {
        try {
            URI uri = new URI("ws://192.168.0.16:8080/chat"); // Reemplaza la URL con la dirección de tu servidor WebSocket
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    // Lógica cuando la conexión se abre correctamente
                }

                @Override
                public void onMessage(String message) {
                    // Lógica para manejar los mensajes recibidos
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    // Lógica cuando la conexión se cierra
                }

                @Override
                public void onError(Exception ex) {
                    // Lógica para manejar errores de conexión
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
        }
    }

    public void disconnect() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
