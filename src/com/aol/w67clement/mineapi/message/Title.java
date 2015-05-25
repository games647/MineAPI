package com.aol.w67clement.mineapi.message;

import com.aol.w67clement.mineapi.nms.PacketSender;

/**
 * 
 * @author 67clement
 *
 */
public interface Title extends PacketSender {

	/**
	 *  Time
	 */
	
	public int getFadeIn_InSeconds();
	
	public int getFadeIn();
	
	public int getFadeOut_InSeconds();
	
	public int getFadeOut();
	
	public int getStay_InSeconds();
	
	public int getStay();
	
	public Title setFadeIn(int fadeIn);
	
	public Title setStay(int stay);
	
	public Title setFadeOut(int fadeOut);
	
	/**
	 *  Title
	 *  SubTitle
	 */
	
	public String getTitle();
	
	public String getSubTitle();
	
	public Title setTitle(String title);
	
	public Title setSubTitle(String subTitle);
	
}
