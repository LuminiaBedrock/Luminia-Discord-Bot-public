package com.luminia.discord.bot.utils.rcon;

import com.luminia.discord.bot.utils.rcon.exception.AuthenticationException;
import com.luminia.discord.bot.utils.rcon.packet.RconPacket;
import com.luminia.discord.bot.utils.rcon.packet.RconPacketType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class RconClient {

    private final InetSocketAddress address;
    private final Charset charset;
    private SocketChannel socketChannel;
    private String password;

    public RconClient(String host, int port) {
        this(host, port, StandardCharsets.UTF_8);
    }

    public RconClient(String host, int port, Charset charset) {
        this.address = new InetSocketAddress(host, port);
        this.charset = charset;
    }

    /**
     * Connect to the server using the provided password
     * @param password Password for authentication
     */
    public void connect(String password) {
        this.setPassword(password);
        this.connect();
    }

    /**
     * Connect to the server using the stored password
     * @throws AuthenticationException If authentication fails
     */
    public void connect() throws AuthenticationException {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.socket().setSoTimeout(1500);
            socketChannel.connect(address);
        } catch (IOException e) {
            throw new AuthenticationException("Authorization error", e);
        }

        RconPacket packet = new RconPacket(RconPacketType.AUTH_REQUEST);
        packet.setPayload(password.getBytes());

        RconPacket response = this.sendPacket(packet);

        if (response == null) {
            throw new AuthenticationException("Server is offline");
        }

        if (response.getRequestId() == -1) {
            throw new AuthenticationException("Wrong password");
        }
    }

    /**
     * Send a command to the server
     * @param command Command to send
     * @return Server response
     */
    public String sendCommand(String command) {
        RconPacket packet = new RconPacket(RconPacketType.COMMAND_EXECUTE);
        packet.setPayload(command.getBytes(charset));

        RconPacket response = sendPacket(packet);
        return new String(response.getPayload(), charset);
    }

    /**
     * Send the packet to the server
     * @param packet RconPacket to send
     * @param socketChannel SocketChannel to use for sending
     * @return A packet sent by the server in response
     */
    private RconPacket sendPacket(RconPacket packet, SocketChannel socketChannel) {
        try {
            return packet.send(socketChannel);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Send the packet to the socketChannel
     * @param packet RconPacket to send
     * @return A packet sent by the server in response
     */
    public RconPacket sendPacket(RconPacket packet) {
        return this.sendPacket(packet, socketChannel);
    }
}

