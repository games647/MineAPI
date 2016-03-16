package com.w67clement.mineapi.message;

import com.w67clement.mineapi.nms.PacketSender;

/**
 * Create title with this class! <br>
 * <br>
 * <a href="https://github.com/67clement/MineAPI/wiki/Using-Title/">Page of the
 * wiki for Titles</a>
 *
 * @author w67clement
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

    public Title(int fadeIn, int stay, int fadeOut, String title, String subtitle)
    {
        super(null);
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
     * @return Title's FadeIn time in seconds.
     *
     * @see#getFadeIn
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
     * Redefine the FadeIn time of the title.
     *
     * @param fadeIn FadeIn time in ticks.
     *
     * @return Instance.
     */
    public Title setFadeIn(int fadeIn)
    {
        this.fadeIn = fadeIn;
        return this;
    }

    /**
     * That method is equivalent to the method getFadeOut, but this one gives
     * you a value in seconds.
     *
     * @return Title's FadeOut time in seconds.
     *
     * @see#getFadeOut
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
     * Redefine the FadeOut time of the title.
     *
     * @param fadeOut FadeOut time in ticks.
     *
     * @return Instance.
     */
    public Title setFadeOut(int fadeOut)
    {
        this.fadeOut = fadeOut;
        return this;
    }

    /**
     * That method is equivalent to the method Stay, but this one gives you a
     * value in seconds.
     *
     * @return Title's stay time in seconds.
     *
     * @see#getStay
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
     * Redefine the stay time of the title.
     *
     * @param stay Stay time in ticks.
     *
     * @return Instance.
     */
    public Title setStay(int stay)
    {
        this.stay = stay;
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
     * Redefine the title text of the Title.
     *
     * @param title An sample title.
     *
     * @return Instance.
     */
    public Title setTitle(String title)
    {
        this.title = title;
        return this;
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
     * Redefine the subtitle text of the Title.
     *
     * @param subTitle An sample subtitle.
     *
     * @return Instance.
     */
    public Title setSubTitle(String subTitle)
    {
        this.subtitle = subTitle;
        return this;
    }
}