package com.luminia.discord.bot.utils.rcon.packet;

import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.Random;

@Getter
@Setter
public class RconPacket {

    private final int packetType;
    private final int requestId;
    private byte[] payload = new byte[1];

    public RconPacket(int packetType) {
        this(packetType, new Random().nextInt());
    }

    public RconPacket(int packetType, int requestId) {
        this.packetType = packetType;
        this.requestId = requestId;
    }

    public RconPacket send(SocketChannel socketChannel) throws IOException {
        try {
            this.write(socketChannel);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return read(socketChannel);
    }

    protected void write(SocketChannel socketChannel) throws IOException {
        int length = payload.length + 14;

        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(length - 4);
        buffer.putInt(requestId);
        buffer.putInt(packetType);
        buffer.put(payload);

        buffer.put((byte) 0);
        buffer.put((byte) 0);

        socketChannel.write(ByteBuffer.wrap(buffer.array()));
    }

    protected RconPacket read(SocketChannel socketChannel) throws IOException {
        InputStream stream = socketChannel.socket().getInputStream();

        ByteBuffer buffer = ByteBuffer.allocate(4 * 3);
        stream.read(buffer.array());
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        int length = buffer.getInt();
        int requestId = buffer.getInt();
        int type = buffer.getInt();

        byte[] payload = new byte[length - 10];
        DataInputStream dataStream = new DataInputStream(stream);
        dataStream.readFully(payload);
        dataStream.read(new byte[2]);

        RconPacket packet = new RconPacket(type, requestId);
        packet.setPayload(payload);
        return packet;
    }
}