package org.karl.sh.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.karl.sh.core.templates.BaseException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

/**
 * HTTP client请求工具类
 *
 * @author ROSE
 */
@Slf4j
public class HttpUtils {

    private static final int HTTP_CONN_TIME_OUT = 3000;

    private static final String UNKNOWN = "unknown";


    /**
     * 请求配置
     **/
    public static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().
            setConnectionRequestTimeout(HTTP_CONN_TIME_OUT)
            .setConnectTimeout(HTTP_CONN_TIME_OUT)
            .setSocketTimeout(HTTP_CONN_TIME_OUT)
            .build();


    /**
     * 发送post请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  请求参数
     * @return 返回json串
     * @author KARL.ROSE
     * @date 2020/4/26 14:59
     **/
    public static String sendPost(String url, Map<String, String> headers, String params) throws BaseException {
        String result = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            post.setConfig(REQUEST_CONFIG);
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    post.setHeader(header.getKey(), header.getValue());
                }
            }
            StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
            log.debug("request params : " + EntityUtils.toString(entity));
            post.setEntity(entity);
            CloseableHttpResponse httpResponse = client.execute(post);
            log.debug("sendPost execute ...");
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                log.info("get post result : " + result);
            }
            httpResponse.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        }
        return result;
    }



    /**
     * formData post请求
     *
     * @author KARL.ROSE
     * @date 2019/10/18 3:38 下午
     **/
    public static String sendPostByForm(String url, Map<String, String> headers, Map<String, String> params) throws BaseException {
        String result = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            post.setConfig(REQUEST_CONFIG);
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    post.setHeader(header.getKey(), header.getValue());
                }
            }

            List<BasicNameValuePair> pairs = new ArrayList<>();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
            CloseableHttpResponse httpResponse = client.execute(post);
            log.debug("sendPost execute ...");
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            }
            httpResponse.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        }
        return result;
    }


    /**
     * 发送delete请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return 返回json串
     * @author KARL.ROSE
     * @date 2020/4/26 14:59
     **/
    public static String sendDelete(String url, Map<String, String> headers) throws BaseException {
        String result = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setConfig(REQUEST_CONFIG);

            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpDelete.setHeader(header.getKey(), header.getValue());
            }
            CloseableHttpResponse httpResponse = client.execute(httpDelete);
            log.info("sendDelete execute ...");
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                log.info("get del result :" + result);
            }
            httpResponse.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        }
        return result;
    }

    /**
     * 发送put请求
     *
     * @param url     请求地址
     * @param headers 请求头
     * @param params  请求参数
     * @return 返回json串
     * @author KARL.ROSE
     * @date 2020/4/26 14:59
     **/
    public static String sendPut(String url, Map<String, String> headers, String params) throws BaseException {
        String result = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(url);
            httpPut.setConfig(REQUEST_CONFIG);

            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPut.setHeader(header.getKey(), header.getValue());
            }
            StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
            log.debug("request params : " + EntityUtils.toString(entity));
            httpPut.setEntity(entity);
            CloseableHttpResponse httpResponse = client.execute(httpPut);
            log.info("sendPut execute ...");
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                log.info("get put result :" + result);
            }
            httpResponse.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        }
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param param   请求参数
     * @param isProxy 是否使用代理模式
     *                proxyHost 代理主机
     *                proxyPort 代理端口
     * @return 所代表远程资源的响应结果
     * @throws Exception 异常
     */
    public static String sendUrlConnPost(String url, String param, boolean isProxy, String proxyHost,
                                         Integer proxyPort) throws Exception {
        DataOutputStream out = null;
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        String result;
        try {
            URL realUrl = new URL(url);
            if (isProxy) {
                //使用代理模式
                Proxy proxy = new Proxy(HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            conn.setConnectTimeout(1000 * 5);
            conn.setReadTimeout(1000 * 10);
            // 打开和URL之间的连接
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //数据以纯文本形式(text/json/xml/html)进行编码，其中不含任何控件或格式字符
            conn.setRequestProperty("Content-Type", "text/json/xml;charset=UTF-8");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.write(param.getBytes());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                log.debug("返回参数:" + line);
                line = new String(line.getBytes(), StandardCharsets.UTF_8);
                sb.append(line);
            }
            result = new String(sb);
        } catch (SocketTimeoutException se) {
            log.error("接口请求超时:" + url);
            throw new Exception("接口请求超时:" + url);
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
            throw new Exception(e);
        }
        //使用finally块来关闭连接、输出流、输入流
        finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * HttpServletRequest获取Ip地址
     *
     * @param request 请求信息
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String flag = ",";
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(flag)) {
                ip = ip.split(flag)[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 发送get请求
     *
     */
    public static String sendGet(URI uri) throws Exception {
        String content = "";
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 定义请求的参数
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(REQUEST_CONFIG);
        //response 对象
        CloseableHttpResponse response = null;
        try {
            // 执行http get请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                content = EntityUtils
                        .toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }

    /**
     * 获取消息体
     *
     * @param request 请求
     * @author KARL.ROSE
     * @date 2020/6/1 4:00 下午
     **/
    public static String readBody(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
