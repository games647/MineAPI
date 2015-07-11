package com.aol.w67clement.mineapi.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AdvancedUtils {

	/**
	 * Method for getting a server data
	 * @author Master
	*/
	public static String[] getServerData(String ip, int port) {
		String[] data = null;
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

			data = sb.toString().split("§");
			socket.close();
		} catch (Exception e) {
			data = new String[] {"Server down.", "Down.", "Down."};
		}
		return data;
	}
	
	public static String getServerStatus(String ip, int port) {
		String c = null;
		String[] data = getServerData(ip, port);
		if (data[1].equals("Down.") && data[2].equals("Down.")) {
			c = "Down.";
		} else {
			c = data[1] + "/" + data[2];
		}
		return c;
	}
	
	/**
	 *  Gets the motd of an minecraft server.
	 * @param ip Ip of the server.
	 * @param port Port of the server.
	 * @return The motd! And if the server was down, return: "Server down."
	 */
	public static String getServerMotd(String ip, int port) {
		return getServerData(ip, port)[0];
	}
}
