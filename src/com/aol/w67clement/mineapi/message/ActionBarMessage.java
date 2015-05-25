package com.aol.w67clement.mineapi.message;

import com.aol.w67clement.mineapi.nms.PacketSender;

/**
 *  The interface of com.aol.w67clement.mineapi.nms.ActionBarTitle
 * @author 67clement
 *
 */
public interface ActionBarMessage extends PacketSender {
	
	public ActionBarMessage setMessage(String actionBarMessage);
	
	public String getMessage();
	
	/**
	 *  To the JSON
	 * @return JSON String.
	 */
	public String toJSONString();

}
