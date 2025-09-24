/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.json;

import it.italiandudes.idl.throwable.exceptions.IO.json.JSONMessageValidityException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.Objects;

@SuppressWarnings("unused")
public class JSONMessage {

    // Attributes
    @NotNull private final String action;
    @Nullable private final JSONObject content;

    // Constructors
    public JSONMessage(@NotNull final String base64message) throws IllegalArgumentException, JSONException, JSONMessageValidityException {
        this(new JSONObject(new String(Base64.getDecoder().decode(base64message))));
    }
    public JSONMessage(@NotNull final JSONObject json) throws JSONMessageValidityException {
        if (json.isNull("action")) throw new JSONMessageValidityException("Action is null");
        try {
            this.action = json.getString("action");
        } catch (JSONException e) {
            throw new JSONMessageValidityException("Missing action", e);
        }
        try {
            if (json.isNull("content")) this.content = null;
            else this.content = json.getJSONObject("content");
        } catch (JSONException e) {
            throw new JSONMessageValidityException("Content must be present it's value must be null or JSONObject");
        }
    }
    public JSONMessage(@NotNull final String action, @Nullable final JSONObject content) {
        this.action = action;
        this.content = content;
    }

    // Attributes
    public @NotNull String toBase64String() {
        return Base64.getEncoder().encodeToString(toString().getBytes());
    }
    public @NotNull String getAction() {
        return action;
    }
    public @Nullable JSONObject getContent() {
        return content;
    }
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof JSONMessage that)) return false;
        return getAction().equals(that.getAction()) && Objects.equals(getContent(), that.getContent());
    }
    @Override
    public int hashCode() {
        int result = getAction().hashCode();
        result = 31 * result + Objects.hashCode(getContent());
        return result;
    }
    @Override
    public @NotNull String toString() {
        JSONObject json = new JSONObject();
        json.put("action", action);
        json.put("content", content);
        return json.toString();
    }
}
