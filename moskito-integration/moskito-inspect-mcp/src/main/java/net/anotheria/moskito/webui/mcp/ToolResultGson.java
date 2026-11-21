package net.anotheria.moskito.webui.mcp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanOperationInfo;

/**
 * Creates the gson instance used to serialize api results. The api objects are plain beans, but some of them contain
 * jdk types gson can't reflect into on java 17+ (stack traces, mbean infos), these get dedicated serializers.
 *
 * @author lrosenberg
 */
final class ToolResultGson {

	private ToolResultGson() {
	}

	static Gson create() {
		return new GsonBuilder()
				//stats contain NaN and infinite values, e.g. averages without requests.
				.serializeSpecialFloatingPointValues()
				.registerTypeAdapter(StackTraceElement.class,
						(JsonSerializer<StackTraceElement>) (element, type, context) -> new JsonPrimitive(element.toString()))
				.registerTypeHierarchyAdapter(MBeanFeatureInfo.class,
						(JsonSerializer<MBeanFeatureInfo>) (info, type, context) -> serializeMBeanFeature(info))
				.create();
	}

	private static JsonObject serializeMBeanFeature(MBeanFeatureInfo info) {
		JsonObject json = new JsonObject();
		json.addProperty("name", info.getName());
		json.addProperty("description", info.getDescription());
		if (info instanceof MBeanAttributeInfo attribute) {
			json.addProperty("type", attribute.getType());
			json.addProperty("readable", attribute.isReadable());
			json.addProperty("writable", attribute.isWritable());
		} else if (info instanceof MBeanOperationInfo operation) {
			json.addProperty("returnType", operation.getReturnType());
		}
		return json;
	}
}
