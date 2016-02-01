package ru.prog_matik.java.pregis.signet.connection;

import java.io.IOException;

/**
 * Created by andryha on 28.01.2016.
 */
public interface IConnection {
    void addConnectionHandler(RelayConnectionHandler relayConnection);

    public void connect();

    public void disconnect();

    public boolean isConnected();

    int read(byte[] bytes, int off, int len) throws IOException;

    void write(byte[] bytes);

    void notifyHandlersOfError(Exception e);

    void notifyHandlers(STATE s);

    enum STATE {
        CONNECTING,
        CONNECTED,
        AUTHENTICATED,
        AUTHENTICATION_FAILED,
        DISCONNECTED,
    }
}

