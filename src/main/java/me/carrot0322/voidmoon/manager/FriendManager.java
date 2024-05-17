package me.carrot0322.voidmoon.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.util.client.Jsonable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class FriendManager extends Feature implements Jsonable {
    public FriendManager() {
        super("Friends");
    }

    private final List<String> friends = new ArrayList<>();

    public boolean isFriend(String name) {
        return this.friends.stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }

    public boolean isFriend(EntityPlayer player) {
        return this.isFriend(player.getGameProfile().getName());
    }

    public void addFriend(String name) {
        this.friends.add(name);
    }

    public void removeFriend(String name) {
        friends.removeIf(s -> s.equalsIgnoreCase(name));
    }

    public List<String> getFriends() {
        return this.friends;
    }

    public boolean shouldAttack(EntityPlayer player) {
        return !isFriend(player);
    }

    @Override public JsonElement toJson() {
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (String friend : friends) {
            array.add(new JsonPrimitive(friend));
        }
        object.add("friends", array);
        return object;
    }

    @Override public void fromJson(JsonElement element) {
        for (JsonElement e : element.getAsJsonObject().get("friends").getAsJsonArray()) {
            friends.add(e.getAsString());
        }
    }

    @Override public String getFileName() {
        return "friends.json";
    }
}