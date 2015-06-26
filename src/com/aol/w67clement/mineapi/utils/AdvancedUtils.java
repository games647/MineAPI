package com.aol.w67clement.mineapi.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AdvancedUtils {

	public static String getServerStatus(String ip, int port) {
		String c = null;
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), 5);
			DataOutputStream out = new DataOutputStream(
					socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());

			out.write(0xFE);

			StringBuilder sb = new StringBuilder();
			int b;
			while ((b = in.read()) != -1) {
				if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
					sb.append((char) b);
				}
			}

			String[] data = sb.toString().split("§");

			c = data[1] + "/" + data[2];
			socket.close();
		} catch (Exception e) {
			c = "down";
		}
		return c;
	}
}
