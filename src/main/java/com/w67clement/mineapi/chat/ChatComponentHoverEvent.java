package com.w67clement.mineapi.chat;

import org.apache.commons.lang3.Validate;

/**
 * Created by w67clement on 06/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class ChatComponentHoverEvent
{
    private HoverEventAction action;
    private String value;

    public ChatComponentHoverEvent(HoverEventAction action, String value)
    {
        this.action = action;
        this.value = value;
    }

    public HoverEventAction getAction()
    {
        return action;
    }

    public String getValue()
    {
        return value;
    }

    public static enum HoverEventAction
    {

        SHOW_TEXT("show_text");

        private String jsonAction = null;

        HoverEventAction(String jsonAction)
        {
            this.jsonAction = jsonAction;
        }

        public static HoverEventAction getByJsonAction(String jsonAction)
        {
            Validate.notNull(jsonAction, "JsonAction String cannot be null.");
            for (HoverEventAction action : values())
            {
                if (action.jsonAction.equals(jsonAction))
                    return action;
            }
            return null;
        }

        public String getJsonAction()

        {
            return jsonAction;
        }
    }
}
