package com.w67clement.mineapi.chat;

import org.apache.commons.lang3.Validate;

/**
 * Created by w67clement on 06/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class ChatComponentClickEvent
{
    private ClickEventAction action;
    private String value;

    public ChatComponentClickEvent(ClickEventAction action, String value)
    {
        this.action = action;
        this.value = value;
    }

    public ClickEventAction getAction()
    {
        return action;
    }

    public String getValue()
    {
        return value;
    }

    public enum ClickEventAction
    {

        EXECUTE_COMMAND("run_command"),
        OPEN_FILE("open_file"),
        OPEN_URL("open_url"),
        SUGGEST_COMMAND("suggest_command");

        private String jsonAction = null;

        ClickEventAction(String jsonAction)
        {
            this.jsonAction = jsonAction;
        }

        public static ClickEventAction getByJsonAction(String jsonAction) {
            Validate.notNull(jsonAction, "JsonAction String cannot be null.");
            for (ClickEventAction action : values()) {
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
