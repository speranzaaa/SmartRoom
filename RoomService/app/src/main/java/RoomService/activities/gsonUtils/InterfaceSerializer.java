package RoomService.activities.gsonUtils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public final class InterfaceSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	
	@Override
	public JsonElement serialize(final T src, final Type typeOfSrc, final JsonSerializationContext context) {
		final Type targetType = src != null
		        ? src.getClass() // `type` can be an interface so Gson would not even try to traverse the fields, just pick the implementation class 
		        : typeOfSrc;            // if not, then delegate further
		JsonObject serialized = context.serialize(src, targetType).getAsJsonObject();
		serialized.addProperty("deserializeAs", src.getClass().getName());
		return serialized;
	}
	
	@Override
	public T deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
		String stringType = json.getAsJsonObject().get("deserializeAs").getAsString();
		Class<?> clazz;
		try {
			clazz = Class.forName(stringType);
			TypeToken<?> typeToken = TypeToken.get(clazz);
			return context.deserialize(json, typeToken.getType());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
