package com.zhuweiwei.springbootlearning0405.jackson;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.POJONode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2020-10-31 16:05:59
 * @description
 */
public class JacksonHelloWord {
    @Test
    public void testHelloWorld() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString("朱伟伟");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    /**
     * {"name"}
     */
    @Test
    public void testWriteJsonKey() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    @Test
    public void testJsonObject() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "朱伟伟");
        jsonGenerator.writeFieldName("person");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("age", 26);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    /**
     * {
     * "name" : "朱伟伟",
     * "stringArray" : [ "哈哈哈", "嘟嘟嘟" ],
     * "objectArray" : [ {
     * "name" : "美月莉亚"
     * }, {
     * "name" : "椎名由奈"
     * } ]
     */
    @Test
    public void testJsonArray() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.useDefaultPrettyPrinter();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "朱伟伟");
        jsonGenerator.writeFieldName("stringArray");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeString("哈哈哈");
        jsonGenerator.writeString("嘟嘟嘟");
        jsonGenerator.writeEndArray();
        jsonGenerator.writeFieldName("objectArray");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "美月莉亚");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "椎名由奈");
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    /**
     * {"success":false,"name":null}
     */
    @Test
    public void testBooleanAndNull() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeBooleanField("success", false);
        jsonGenerator.writeNullField("name");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    @Test
    public void testArrayFieldStart() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("testArray");
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

    @Test
    public void testUser() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.setCodec(new UserObjectCodec());
        jsonGenerator.writeObject(new User());
        jsonGenerator.close();
    }

    @Test
    public void testSameKeyName() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "朱伟伟");
        jsonObject.put("name", "是是是");
        System.out.println(jsonObject.toJSONString());
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", "朱伟伟");
        jsonGenerator.writeStringField("name", "是是是");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }

}

