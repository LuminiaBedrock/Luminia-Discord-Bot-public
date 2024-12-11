package com.luminia.discord.bot.utils.query;

import lombok.NonNull;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BedrockQuery {

    private static final byte UNCONNECTED_PING = 0x01;
    private static final byte[] UNCONNECTED_MESSAGE_SEQUENCE = {0x00, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, 0x12, 0x34, 0x56, 0x78};

    private static final Random random = new Random();
    private static long dialerId = random.nextLong();

    private final int timeout;

    public BedrockQuery() {
        this(2000);
    }

    public BedrockQuery(int timeout) {
        this.timeout = timeout;
    }

    public CompletableFuture<BedrockQueryResponse> query(String address, int port) {
        return query(new InetSocketAddress(address, port));
    }

    public CompletableFuture<BedrockQueryResponse> query(InetSocketAddress address) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeByte(UNCONNECTED_PING);
                dataOutputStream.writeLong(System.currentTimeMillis() / 1000);
                dataOutputStream.write(UNCONNECTED_MESSAGE_SEQUENCE);
                dataOutputStream.writeLong(dialerId++);

                byte[] requestData = outputStream.toByteArray();
                byte[] responseData = new byte[1024 * 1024 * 4];

                try (DatagramSocket socket = new DatagramSocket()) {
                    DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address.getAddress(), address.getPort());
                    socket.send(requestPacket);

                    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
                    socket.setSoTimeout(timeout);
                    socket.receive(responsePacket);

                    // MCPE;<motd>;<protocol>;<version>;<players>;<max players>;<id>;<sub motd>;<gamemode>;<not limited>;<port>;<port>
                    String[] splittedData = new String(responsePacket.getData(), 35, responsePacket.getLength(), StandardCharsets.UTF_8).split(";");

                    int protocol = Integer.parseInt(splittedData[2]);
                    int playerCount = Integer.parseInt(splittedData[4]);
                    int maxPlayers = Integer.parseInt(splittedData[5]);

                    return new BedrockQueryResponse(true, splittedData[1], protocol, splittedData[3], playerCount, maxPlayers, splittedData[7], splittedData[8]);
                }
            } catch (Exception e) {
                return BedrockQueryResponse.empty();
            }
        });
    }
}