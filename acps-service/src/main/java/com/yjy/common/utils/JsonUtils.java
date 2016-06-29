package com.yjy.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import com.yjy.entity.Fundbookcode;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json处理类
 *
 */
public class JsonUtils {

    private static JsonFactory jsonFactory = new JsonFactory();


    public static String toJson(Object obj) {
        return toJson(obj, true);
    }

    @SuppressWarnings("deprecation")
    public static String toJson(Object obj, boolean usePretty) {
        String json = "";
        Writer w = new StringWriter();
        try {
            JsonGenerator jg = jsonFactory.createJsonGenerator(w);
            if (usePretty) {
                jg.useDefaultPrettyPrinter();
            }
            ObjectMapper mapper = new ObjectMapper();
            SerializationConfig c = mapper.getSerializationConfig();
            //过期的忽略Null值的属性方法
            //c.set(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
            //最新忽略Null值的属性方法
            c.setSerializationInclusion(Inclusion.NON_NULL);
            mapper.setSerializationConfig(c);
            mapper.writeValue(jg, obj);
            json = w.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                w.close();
                w = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static <T> T readToT(String jsonText, Class<T> clazz) {
        ObjectMapper o = new ObjectMapper();
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        o.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        o.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        o.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        try {
            T t = o.readValue(jsonText, clazz);
            return t;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回JSON数据构造成的Map
     *
     * @param jsonText
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map readToMap(String jsonText) {
        ObjectMapper o = new ObjectMapper();
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        o.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        o.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        o.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        Map maps = null;
        try {
            maps = o.readValue(jsonText, Map.class);
            return maps;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * 返回JSON数据构造成的Map
     *
     * @param jsonText
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static   Map<Integer,List<Fundbookcode>> readToMapList(String jsonText) {
        ObjectMapper o = new ObjectMapper();
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        o.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        o.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        o.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        Map maps = null;
        try {
            maps = o.readValue(jsonText, new TypeReference<Map<Integer, List<Fundbookcode>>>()  {});
            return maps;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_MAP;
    }



    public static <T> List<T> readToList(String jsonText, Class<T> clazz) {
        ObjectMapper o = new ObjectMapper();
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        o.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        o.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        o.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        o.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        JavaType javaType = o.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            List<T> list = o.readValue(jsonText, javaType);
            return list;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    class NullSerializer extends JsonSerializer<Object> {
        public void serialize(Object value, JsonGenerator jgen,
                              SerializerProvider provider) throws IOException,
                JsonProcessingException {
            //jgen.writeString("");
        }

    }
}