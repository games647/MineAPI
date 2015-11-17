package com.w67clement.mineapi.message;

import com.w67clement.mineapi.nms.PacketSender;

/**
 * Create title with this class! <br />
 * <br />
 * <a href="https://github.com/67clement/MineAPI/wiki/Using-Title/">Page of the
 * wiki for Titles</a>
 * 
 * @author w67clement
 *
 */
public interface Title extends PacketSender
{

	/*
	 * Time
	 */

	/**
	 * That method is equivalent to the method getFadeln, but this one gives you
	 * a value in seconds.
	 * 
	 * @see#getFadeIn
	 * @return Title's FadeIn time in seconds.
	 */
	public int getFadeIn_InSeconds();

	/**
	 * Gets the title's FadeIn time.
	 * 
	 * @return FadeIn time in ticks.
	 */
	public int getFadeIn();

	/**
	 * That method is equivalent to the method getFadeOut, but this one gives
	 * you a value in seconds.
	 * 
	 * @see#getFadeOut
	 * @return Title's FadeOut time in seconds.
	 */
	public int getFadeOut_InSeconds();

	/**
	 * Gets the title's FadeOut time.
	 * 
	 * @return FadeOut time in ticks.
	 */
	public int getFadeOut();

	/**
	 * That method is equivalent to the method Stay, but this one gives you a
	 * value in seconds.
	 * 
	 * @see#getStay
	 * @return Title's stay time in seconds.
	 */
	public int getStay_InSeconds();

	/**
	 * Gets the title's stay time.
	 * 
	 * @return Stay time in ticks.
	 */
	public int getStay();

	/**
	 * Redefine the fadein time of the title.
	 * 
	 * @param fadeIn
	 *            FadeIn time in ticks.
	 */
	public Title setFadeIn(int fadeIn);

	/**
	 * Redefine the fadein time of the title.
	 * 
	 * @param fadeIn
	 *            FadeIn time in ticks.
	 */
	public Title setStay(int stay);

	/**
	 * Redefine the fadein time of the title.
	 * 
	 * @param fadeIn
	 *            FadeIn time in ticks.
	 */
	public Title setFadeOut(int fadeOut);

	/*
	 * Title SubTitle
	 */

	/**
	 * Gets the title text in the title.
	 * 
	 * @return Title text in a String value.
	 */
	public String getTitle();

	/**
	 * Gets the subtitle text in the title.
	 * 
	 * @return Subtitle text in a String value.
	 */
	public String getSubTitle();

	/**
	 * Redefine the title text of the Title.
	 * 
	 * @param title
	 *            An sample title.
	 */
	public Title setTitle(String title);

	/**
	 * Redefine the subtitle text of the Title.
	 * 
	 * @param subTitle
	 *            An sample subtitle.
	 */
	public Title setSubTitle(String subTitle);

}
