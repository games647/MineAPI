package com.w67clement.mineapi.system;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.glowstone.entity.meta.profile.PlayerProfile;
import net.glowstone.entity.meta.profile.PlayerProperty;
import org.apache.commons.codec.binary.Base64;

public class MC_GameProfile
{

    private static final JsonParser parser = new JsonParser();
    private final UUID uuid;
    private final String name;
    private List<Property> properties;

    public MC_GameProfile(UUID uuid, String name)
    {
        this(uuid, name, new ArrayList<>());
    }

    public MC_GameProfile(UUID uuid, String name, List<Property> properties)
    {
        this.uuid = uuid;
        this.name = name;
        this.properties = properties;
    }

    /**
     * Serialize the properties in Json.
     *
     * @param properties Properties to serialize.
     *
     * @return Serialized properties.
     */
    public static JsonElement serializeProperties(List<Property> properties)
    {
        JsonArray result = new JsonArray();

        properties.forEach(property -> {
            JsonObject object = new JsonObject();

            object.addProperty("name", property.getName());
            object.addProperty("value", property.getValue());

            if (property.hasSignature())
            {
                object.addProperty("signature", property.getSignature());
            }

            result.add(object);
        });

        return result;
    }

    /**
     * Deserialize the properties in Json.
     *
     * @param json Properties to deserialize.
     *
     * @return Deserialized properties.
     */
    public static List<Property> deserializeProperties(JsonElement json)
    {
        List<Property> properties = new ArrayList<>();
        if (json instanceof JsonObject)
        {
            JsonObject object = (JsonObject) json;
            object.entrySet().forEach(entry -> {
                if (entry.getValue() instanceof JsonArray)
                {
                    ((JsonArray) entry.getValue()).forEach(element -> properties.add(new Property(entry.getKey(), element.getAsString())));
                }
            });
        }
        else if (json instanceof JsonArray)
        {
            ((JsonArray) json).forEach(element -> {
                if (element instanceof JsonObject)
                {
                    JsonObject object = (JsonObject) element;

                    String name = object.getAsJsonPrimitive("name").getAsString();
                    String value = object.getAsJsonPrimitive("value").getAsString();
                    String signature = null;

                    if (object.has("signature"))
                    {
                        signature = object.getAsJsonPrimitive("signature").getAsString();
                    }

                    properties.add(new Property(name, value, signature));
                }
            });
        }
        return properties;
    }

    /**
     * Deserialize the properties in Json.
     *
     * @param json Properties to deserialize.
     *
     * @return Deserialized properties.
     */
    public static List<Property> deserializeProperties(String json)
    {
        return deserializeProperties(new JsonParser().parse(json));
    }

    /**
     * Gets MC_GameProfile by Mojang's GameProfile Object.
     *
     * @param gameprofile Mojang's GameProfile.
     *
     * @return MC_GameProfile with Mojang's GameProfile values.
     */
    public static MC_GameProfile getByMojangObject(Object gameprofile)
    {
        if (MineAPI.getServerType() == ServerType.GLOWSTONE)
        {
            if (gameprofile.getClass().getSimpleName().equals("PlayerProfile") && gameprofile.getClass().getPackage().getName().equals("net.glowstone.entity.meta.profile"))
            {
                PlayerProfile glowProfile = (PlayerProfile) gameprofile;
                MC_GameProfile profile = new MC_GameProfile(glowProfile.getUniqueId(), glowProfile.getName());
                List<Property> properties = new ArrayList<>();
                List<PlayerProperty> glowProperties = glowProfile.getProperties();
                glowProperties.forEach(property -> properties.add(new Property(property.getName(), property.getValue(), property.getSignature())));
                profile.properties = properties;
                return profile;
            }
            else
                return null;
        }
        else
        {
            if (gameprofile.getClass().getSimpleName().equals("GameProfile") && gameprofile.getClass().getPackage().getName().equals("com.mojang.authlib"))
            {
                Method methodId = ReflectionAPI.getMethod(gameprofile, "getId");
                Method methodName = ReflectionAPI.getMethod(gameprofile, "getName");
                MC_GameProfile profile = new MC_GameProfile((UUID) ReflectionAPI.invokeMethod(gameprofile, methodId), (String) ReflectionAPI.invokeMethod(gameprofile, methodName));
                Object serializer = ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getClass("com.mojang.authlib.properties.PropertyMap$Serializer")));
                JsonElement json = (JsonElement) ReflectionAPI.invokeMethod(serializer, ReflectionAPI.getMethod(serializer, "serialize", ReflectionAPI.getClass("com.mojang.authlib.properties.PropertyMap"), Type.class, JsonSerializationContext.class), ReflectionAPI.getValue(gameprofile, ReflectionAPI.getField(gameprofile.getClass(), "properties", true)), null, null);
                profile.properties = deserializeProperties(json);
                return profile;
            }
            else
                return null;
        }
    }

    /**
     * Gets MC_GameProfile by Mojang's GameProfile Object serialized.
     *
     * @param json GameProfile serialized in Json
     *
     * @return MC_GameProfile with Mojang's GameProfile serialized values.
     */
    public static MC_GameProfile fromJson(String json)
    {
        return fromJson(parser.parse(json));
    }

    /**
     * Gets MC_GameProfile by Mojang's GameProfile Object serialized.
     *
     * @param json GameProfile serialized in Json
     *
     * @return MC_GameProfile with Mojang's GameProfile serialized values.
     */
    public static MC_GameProfile fromJson(JsonElement json)
    {
        if (json instanceof JsonObject)
        {
            JsonObject obj = new JsonObject();
            UUID id = UUID.fromString(obj.get("id").getAsString());
            String name = obj.get("name").getAsString();
            MC_GameProfile profile = new MC_GameProfile(id, name);
            List<Property> properties = deserializeProperties(obj.get("properties"));
            profile.setProperties(properties);
            return profile;
        }
        return null;
    }

    /**
     * @return The profile's UUID.
     */
    public final UUID getUuid()
    {
        return this.uuid;
    }

    /**
     * @return The profile's name.
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * @return The profile's properties.
     */
    public List<Property> getProperties()
    {
        return this.properties;
    }

    /**
     * Sets the profile's properties.
     *
     * @param properties Properties.
     */
    public void setProperties(List<Property> properties)
    {
        this.properties = properties;
    }

    /**
     * Convert this Object to the Nms Object (For the Reflection)
     *
     * @return Nms' Object.
     */
    public Object toNms()
    {
        if (MineAPI.getServerType() == ServerType.GLOWSTONE)
        {
            List<PlayerProperty> properties = new ArrayList<>();
            this.properties.forEach(property -> properties.add(new PlayerProperty(property.name, property.value, property.signature)));
            return new PlayerProfile(this.name, this.uuid, properties);
        }
        else
        {
            Constructor<?> constructor = ReflectionAPI.getConstructor(ReflectionAPI.getClass("com.mojang.authlib.GameProfile"), UUID.class, String.class);
            Object profile = ReflectionAPI.newInstance(constructor, this.uuid, this.name);
            Object serializer = ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getClass("com.mojang.authlib.properties.PropertyMap$Serializer")));
            Object properties = ReflectionAPI.invokeMethod(serializer, ReflectionAPI.getMethod(serializer, "deserialize", JsonElement.class, Type.class, JsonDeserializationContext.class), serializeProperties(this.properties), null, null);
            ReflectionAPI.setValue(profile, ReflectionAPI.getField(profile != null ? profile.getClass() : null, "properties", true), properties);
            return profile;
        }
    }

    /**
     * Gets the GameProfile in JsonObject.
     *
     * @return Gson's JsonObject.
     */
    public JsonObject toJson()
    {
        JsonObject gameprofile = new JsonObject();
        gameprofile.addProperty("id", this.uuid.toString());
        gameprofile.addProperty("name", this.name);
        gameprofile.add("properties", serializeProperties(this.properties));
        return gameprofile;
    }

    /**
     * Gets the GameProfile in Json.
     *
     * @return GameProfile in Json.
     */
    public String toJsonString()
    {
        return this.toJson().toString();
    }

    public static class Property
    {
        private String name;
        private String value;
        private String signature;

        public Property(String name, String value)
        {
            this(name, value, null);
        }

        public Property(String name, String value, String signature)
        {
            this.name = name;
            this.value = value;
            this.signature = signature;
        }

        public String getName()
        {
            return this.name;
        }

        public String getValue()
        {
            return this.value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public String getSignature()
        {
            return this.signature;
        }

        public boolean hasSignature()
        {
            return this.signature != null;
        }

        public boolean isSignatureValid(PublicKey publicKey)
        {
            try
            {
                Signature signature = Signature.getInstance("SHA1withRSA");
                signature.initVerify(publicKey);
                signature.update(this.value.getBytes());
                return signature.verify(Base64.decodeBase64(this.signature));
            }
            catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }

}
