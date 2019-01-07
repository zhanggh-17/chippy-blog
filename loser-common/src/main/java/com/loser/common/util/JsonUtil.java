package com.loser.common.util;

import com.google.gson.Gson;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.impl.JsonWriteContext;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.CharTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 
 * <strong>Title : JsonUtil </strong>. <br>
 * <strong>Description : Gson&Jackson类库的封装工具类，专门负责解析json数据.内部分别实现了Gson、Jackson对象的单例.</strong> <br>
 * <strong>Create on : Mar 13, 2015 4:57:23 PM </strong>. <br>
 * <p>
 * <strong>Copyright (C) AlexHo Co.,Ltd.</strong> <br>
 * </p>
 * @author k2 <br>
 * @version <strong>base-platform-0.1.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class JsonUtil {
	
	private static Logger logs = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper objMapper = null;
	private static Gson gson = null;

	static {
		if (null == objMapper) {
			objMapper = new ObjectMapper();
			objMapper.setSerializationInclusion(Inclusion.NON_NULL); 
			objMapper.setSerializationInclusion(Inclusion.NON_EMPTY); 
			
			/*// 使Jackson JSON支持Unicode编码非ASCII字符
			objMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			CustomSerializerFactory serializerFactory = new CustomSerializerFactory();
			serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());
			objMapper.setSerializerFactory(serializerFactory);*/
			
		}
		
		if (null == gson) {
			gson = new Gson();
		}
		
	}

	/**
	 * @author by k2 Jan 22, 2015
	 * 
	 * @desc 构造函数.
	 */
	private JsonUtil() {
	}

	/**
	 * @author by k2 May 28, 2015
	 * 
	 * @desc 将对象转换成json格式
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String toJson(Object obj) {
		if(Stringer.isNullOrEmpty(obj)){
			logs.error("==>JsonUtil.toJson() ->> Parameter is null! ='obj'!");
			return null;
		}
		String json = "";
		try {
			/*StringWriter writer = new StringWriter();
			JsonGenerator generator = new JsonFactory().createJsonGenerator(writer);
			objMapper.writeValue(generator, obj);
			if (generator != null) {
				generator.close();
			}
			json = writer.toString();
			writer.close();*/
			
			return objMapper.writeValueAsString(obj);
			
		}catch (JsonGenerationException e) {
			logs.error("==>JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logs.error("==>JsonMappingException: ", e);
		} catch (IOException e) {
			logs.error("==>JsonUtil convert to json failed!");
			e.printStackTrace();
		}
		
		return json;
		
	}

	/**
	 * @author by k2 May 28, 2015
	 * 
	 * @desc 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> List<T> toList(StringBuilder json, Class<T> clazz) {
		if(Stringer.isNullOrEmpty(json)){
			logs.error("==>JsonUtil.toList() ->> Parameter is null! ='json'!");
			return null;
		}
		List<T> objList = null;
		try {
			CollectionType type = objMapper.getTypeFactory().constructCollectionType(List.class, clazz);
			if (objMapper != null) {
				objList = objMapper.readValue(json.toString(), type);
			}

		} catch (IOException e) {
			logs.error("==>JsonUtil convert to List failed!");
			e.printStackTrace();
		}

		return objList;
	}

	/**
	 * @author by k2 May 28, 2015
	 * 
	 * @desc 将json格式转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json) {
		if(Stringer.isNullOrEmpty(json)){
			logs.error("==>JsonUtil.toMap() ->> Parameter is null! ='json'!");
			return null;
		}
		Map<String, Object> maps = null;
		try {
			//如果是map类型  
			maps = objMapper.readValue(json, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			logs.error("==>JsonUtil convert to Map failed!");
			e.printStackTrace();
		}
		return maps;
	}

	/***
	 * @author by k2 May 28, 2015
	 * 
	 * @desc 将json转换成bean对象-gson实现
	 * 
	 * @param json
	 * @return 
	 * @return
	 */
	public static <T> T toBean(String json, Class<T> clazz) {
		if(Stringer.isNullOrEmpty(json)){
			logs.error("==>JsonUtil.toBean() ->> Parameter is null! ='json'!");
			return null;
		}
		T obj = null;
		try {
			if (gson != null) {
				obj = gson.fromJson(json, clazz);
			}
		} catch (Exception e) {
			logs.error("==>JsonUtil convert to Bean failed!");
			e.printStackTrace();
		}

		return obj;
	}
	
	
	
	/**
	 * @author by k2 May 28, 2015
	 *
	 * @desc 将json转换成bean对象-jackson实现.
	 * @param json
	 * @param typeReference jackson自定义的类型
	 * @return
	 */
	public static <T> T toBean(String json, TypeReference<T> typeReference) {
		if(Stringer.isNullOrEmpty(json)){
			logs.error("==>JsonUtil.toBean() ->> Parameter is null! ='json'!");
			return null;
		}
		T t = null;
		try {
			if (objMapper != null) {
				t = objMapper.readValue(json, typeReference);
			}
		} catch (Exception e) {
			logs.error("==>JsonUtil convert to Bean failed!");
			e.printStackTrace();
		}

		return t;
	}
	
	
	
	/**
	 * @author by K2 Aug 12, 2015
	 *
	 * @desc 将json转换成JsonNode节点树.
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static JsonNode toJsonNode(String json){
		try {
			if(Stringer.isNullOrEmpty(json)){
				logs.error("==>JsonUtil.toJsonNode() ->> Parameter is null! 'json' !");
				return null;
			}
			return objMapper.readTree(json);
		} catch (JsonProcessingException e) {
			logs.error("==>JsonUtil convert to JsonNode failed!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * java对象(包含日期字段或属性)转换为json字符串
	 * 
	 * @param object
	 *            Java对象
	 * @return 返回字符串
	 */
	public static String fromObjectHasDateToJson(Object object) {
		objMapper.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logs.error("==>JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logs.error("==>JsonMappingException: ", e);
		} catch (IOException e) {
			logs.error("==>IOException: ", e);
		}
		return null;
	}

	/**
	 * java对象(包含日期字段或属性)转换为json字符串
	 * 
	 * @param object
	 *            Java对象
	 * @param dateTimeFormatString
	 *            自定义的日期/时间格式。该属性的值遵循java标准的date/time格式规范。如：yyyy-MM-dd
	 * @return 返回字符串
	 */
	public static String hasDateToJson(Object object, String dateTimeFormatString) {
		objMapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(dateTimeFormatString));
		try {
			return objMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logs.error("==>JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logs.error("==>JsonMappingException: ", e);
		} catch (IOException e) {
			logs.error("==>IOException: ", e);
		}
		return null;
	}

}

/**
 * <strong>Title : StringUnicodeSerializer </strong>. <br>
 * <strong>Description : 使Jackson JSON支持Unicode编码非ASCII字符.</strong> <br>
 * <strong>Create on : Mar 24, 2016 5:33:04 PM </strong>. <br>
 * <p>
 * <strong>Copyright (C) YOX Internet Technology Co.,Ltd.</strong> <br>
 * </p>
 * @author k2 <br>
 * @version <strong>base-platform-0.1.0</strong> <br>
 * <br>
 * @see
 * <br>
 */
class StringUnicodeSerializer extends JsonSerializer<String> {

	private final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	private final int[] ESCAPE_CODES = CharTypes.get7BitOutputEscapes();

	private void writeUnicodeEscape(JsonGenerator gen, char c) throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw('u');
		gen.writeRaw(HEX_CHARS[(c >> 12) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 8) & 0xF]);
		gen.writeRaw(HEX_CHARS[(c >> 4) & 0xF]);
		gen.writeRaw(HEX_CHARS[c & 0xF]);
	}

	private void writeShortEscape(JsonGenerator gen, char c) throws IOException {
		gen.writeRaw('\\');
		gen.writeRaw(c);
	}

	@Override
	public void serialize(String str, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		int status = ((JsonWriteContext) gen.getOutputContext()).writeValue();
		switch (status) {
		case JsonWriteContext.STATUS_OK_AFTER_COLON:
			gen.writeRaw(':');
			break;
		case JsonWriteContext.STATUS_OK_AFTER_COMMA:
			gen.writeRaw(',');
			break;
		case JsonWriteContext.STATUS_EXPECT_NAME:
			throw new JsonGenerationException("Can not write string value here");
		}
		gen.writeRaw('"');// 写入JSON中字符串的开头引号
		for (char c : str.toCharArray()) {
			if (c >= 0x80) {
				writeUnicodeEscape(gen, c); // 为所有非ASCII字符生成转义的unicode字符
			} else {
				// 为ASCII字符中前128个字符使用转义的unicode字符
				int code = (c < ESCAPE_CODES.length ? ESCAPE_CODES[c] : 0);
				if (code == 0) {
					gen.writeRaw(c); // 此处不用转义
				} else if (code < 0) {
					writeUnicodeEscape(gen, (char) (-code - 1)); // 通用转义字符
				} else {
					writeShortEscape(gen, (char) code); // 短转义字符 (\n \t ...)
				}
			}
		}
		gen.writeRaw('"');// 写入JSON中字符串的结束引号
	}

}
