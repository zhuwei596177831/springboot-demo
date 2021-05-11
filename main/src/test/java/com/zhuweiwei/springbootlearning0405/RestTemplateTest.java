package com.zhuweiwei.springbootlearning0405;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zww
 * @date 2020-04-19 13:31
 * @description
 **/
@SpringBootTest
public class RestTemplateTest {

    private static final String baseUrl = "http://127.0.0.1:8081/main/restTemplate/";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    void testGetForObject() {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        String forObject = restTemplate.getForObject(baseUrl + "getForObject", String.class);

//        String forObject = restTemplate.getForObject(baseUrl + "getForObject?name={0}&accNo={1}", String.class, "朱伟伟", "783345");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "朱伟伟");
        map.put("accNo", "783345");
        map.put("id", "1251743508835545089");
//        String forObject = restTemplate.getForObject(baseUrl + "getForObject?name={name}&accNo={accNo}", String.class, map);

//        String forObject = restTemplate.getForObject(baseUrl + "getForObject/1251743295081230337?name={name}&accNo={accNo}", String.class, map);

//        System.out.println(forObject);

//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(baseUrl + "getForObject/{id}?name={0}&accNo={1}", String.class, "1251743508835545089", "香椎由奈", "520");
//        System.out.println(responseEntity.getStatusCode());
//        System.out.println(responseEntity.getStatusCodeValue());
//        System.out.println(responseEntity.getBody());
//        System.out.println("===========");
//        HttpHeaders headers = responseEntity.getHeaders();
//        System.out.println(headers.getContentType());
//        System.out.println(headers.getAcceptCharset());
//        System.out.println(headers.getAcceptLanguage());
    }

    @Test
    void testExchange() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "美月莉亚");
        map.put("accNo", "520");
        map.put("id", "1251743367936290817");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user-token", "596177831");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
//        ResponseEntity<String> exchangeResponseEntity = restTemplate.exchange(baseUrl + "getForObject/{id}?name={name}&accNo={accNo}", HttpMethod.GET, requestEntity, String.class, map);
        ResponseEntity<String> exchangeResponseEntity = restTemplate.exchange(baseUrl + "postForObject/{id}?name={name}&accNo={accNo}", HttpMethod.POST, requestEntity, String.class, map);
        System.out.println(exchangeResponseEntity.getStatusCodeValue());
        System.out.println(exchangeResponseEntity.getStatusCode());
        System.out.println(exchangeResponseEntity.getBody());
    }

    @Test
    void testPostForObject() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        Map<String, Object> map = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.add("user-token", "596177831");
/**
 * 方式一
 */
//        map.put("name", "美月莉亚");
//        map.put("accNo", "520");
//        map.put("testName", "则呃呃呃额");
//        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
//        String s = restTemplate.postForObject(baseUrl + "postForObject?name={name}&accNo={accNo}&testName={testName}",
//                httpEntity, String.class, map);
//        String s = restTemplate.postForObject(baseUrl + "postForObject?name={0}&accNo={1}&testName={2}",
//                httpEntity, String.class, "美月莉亚", "520", "哈哈哈哈");
//        System.out.println(s);

/**
 * 方式二
 */
//        MultiValueMap multiValueMap = new LinkedMultiValueMap();
//        multiValueMap.add("id", "1251743415248039937");
//        multiValueMap.add("name", "希岛爱理");
//        multiValueMap.add("accNo", "520");
//        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(multiValueMap, httpHeaders);
//        String s = restTemplate.postForObject(baseUrl + "postForObject", requestEntity, String.class);
//        System.out.println(s);

    }

    @Test
    void testPostForJsonBody() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user-token", "596177831");
        //Content type默认是 'text/plain;charset=UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        com.zhuweiwei.springbootlearning0405.bean.Test test = new com.zhuweiwei.springbootlearning0405.bean.Test();
        test.setName("香椎由奈");
        test.setAccNo("520");
        String jsonString = JSON.toJSONString(test);
        System.out.println(jsonString);
        String s = restTemplate.postForObject(baseUrl + "postForBody?testAccNo={0}", JSON.toJSON(test), String.class, "的点点滴滴");
//        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, httpHeaders);
//        String s = restTemplate.postForObject(baseUrl + "postForBody", httpEntity, String.class);
        System.out.println(s);
    }

}
