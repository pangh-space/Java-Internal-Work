package com.jiw.ph.http;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description HttpStreamDemo
 * @Author pangh
 * @Date 2020年07月03日
 * @Version v1.0.0
 * <p>
 * maven 引入
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * <version>4.4</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>cn.hutool</groupId>
 * <artifactId>hutool-all</artifactId>
 * <version>5.1.0</version>
 * </dependency>
 */
@Slf4j
public class HttpClientGetStream {

    public static void main(String[] args) throws IOException {


        String url = "Get请求的URL";

        // 模拟（创建）一个浏览器用户
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        // httpclient进行连接
        CloseableHttpResponse response = client.execute(httpGet);
        // 获取内容
        HttpEntity entity = response.getEntity();
        // 将内容转化成IO流
        InputStream content = entity.getContent();

        String encode = Base64.encode(IoUtil.readBytes(content));
        log.info("Base64文件：{}", encode);

        // 文件写入路径
        String filePath = "/Users/pangh/Documents/TempWord/audio/";

        // 通过输出流，写入文件
        FileOutputStream fileOutputStream = new FileOutputStream(FileUtil.file(filePath + "1.wav"));

        fileOutputStream.write(Base64.decode(encode));

        fileOutputStream.close();


    }

}