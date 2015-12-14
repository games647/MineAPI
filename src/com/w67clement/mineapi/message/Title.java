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
public abstract class Title extends PacketSender
{

	/*
	 * Time.
	 */
	protected int fadeIn;
	protected int stay;
	protected int fadeOut;
	/*
	 * In seconds.
	 */
	protected int fadeIn_Seconds = fadeIn * 20;
	protected int stay_Seconds = stay * 20;
	protected int fadeOut_Seconds = fadeOut * 20;
	/*
	 * String.
	 */
	protected String title;
	protected String subtitle;

	public Title(int fadeIn, int stay, int fadeOut, String title,
			String subtitle) {
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
		this.subtitle = subtitle;
	}

	/**
	 * That method is equivalent to the method getFadeln, but this one gives you
	 * a value in seconds.
	 * 
	 * @see#getFadeIn
	 * @return Title's FadeIn time in seconds.
	 */
	public int getFadeIn_InSeconds()
	{
		return this.fadeIn_Seconds;
	}

	/**
	 * Gets the title's FadeIn time.
	 * 
	 * @return FadeIn time in ticks.
	 */
	public int getFadeIn()
	{
		return this.fadeIn;
	}

	/**
	 * That method is equivalent to the method getFadeOut, but this one gives
	 * you a value in seconds.
	 * 
	 * @see#getFadeOut
	 * @return Title's FadeOut time in seconds.
	 */
	public int getFadeOut_InSeconds()
	{
		return this.fadeOut_Seconds;
	}

	/**
	 * Gets the title's FadeOut time.
	 * 
	 * @return FadeOut time in ticks.
	 */
	public int getFadeOut()
	{
		return this.fadeOut;
	}

	/**
	 * That method is equivalent to the method Stay, but this one gives you a
	 * value in seconds.
	 * 
	 * @see#getStay
	 * @return Title's stay time in seconds.
	 */
	public int getStay_InSeconds()
	{
		return this.stay_Seconds;
	}

	/**
	 * Gets the title's stay time.
	 * 
	 * @return Stay time in ticks.
	 */
	public int getStay()
	{
		return this.stay;
	}

	/**
	 * Redefine the FadeIn time of the title.
	 * 
	 * @param fadeIn
	 *            FadeIn time in ticks.
	 */
	public Title setFadeIn(int fadeIn)
	{
		this.fadeIn = fadeIn;
		return this;
	}

	/**
	 * Redefine the stay time of the title.
	 * 
	 * @param stay
	 *            Stay time in ticks.
	 */
	public Title setStay(int stay)
	{
		this.stay = stay;
		return this;
	}

	/**
	 * Redefine the FadeOut time of the title.
	 * 
	 * @param fadeOut
	 *            FadeOut time in ticks.
	 */
	public Title setFadeOut(int fadeOut)
	{
		this.fadeOut = fadeOut;
		return this;
	}

	/**
	 * Gets the title text in the title.
	 * 
	 * @return Title text in a String value.
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Gets the subtitle text in the title.
	 * 
	 * @return Subtitle text in a String value.
	 */
	public String getSubTitle()
	{
		return this.subtitle;
	}

	/**
	 * Redefine the title text of the Title.
	 * 
	 * @param title
	 *            An sample title.
	 */
	public Title setTitle(String title)
	{
		this.title = title;
		return this;
	}

	/**
	 * Redefine the subtitle text of the Title.
	 * 
	 * @param subTitle
	 *            An sample subtitle.
	 */
	public Title setSubTitle(String subTitle)
	{
		this.subtitle = subTitle;
		return this;
	}
}