package com.w67clement.mineapi.system.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.plugin.Plugin;

public abstract class JsonConfig extends Config
{

    protected JsonParser parser;
    protected JsonElement json;
    protected Gson gson;

    public JsonConfig(Plugin plugin, File file)
    {
        super(plugin, file);
        this.parser = new JsonParser();
        this.gson = new Gson();
    }

    @Override
    public void load()
    {
        super.load();
        try
        {
            FileReader reader = new FileReader(this.getFile());
            json = parser.parse(reader);
            reader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void save()
    {
        try
        {
            FileWriter writer = new FileWriter(this.getFile());
            writer.write(this.gson.toJson(this.json));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
