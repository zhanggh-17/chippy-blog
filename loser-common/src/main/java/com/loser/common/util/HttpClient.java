package com.loser.common.util;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.*;

/**
 * <strong>Title : HttpClient </strong>. <br>
 * <strong>Description : http请求处理工具类</strong> <br>
 * <strong>Create on : Mar 13, 2015 5:13:22 PM </strong>. <br>
 * <p>
 * <strong>Copyright (C) AlexHo Co.,Ltd.</strong> <br>
 * </p>
 *
 * @author k2 <br>
 * @version <strong>base-platform-0.1.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
@SuppressWarnings("deprecation")
public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static PoolingHttpClientConnectionManager poolConnManager = null;
    private static HttpMessageParserFactory<HttpResponse> responseParserFactory;
    private static HttpMessageWriterFactory<HttpRequest> requestWriterFactory;
    private static SSLContext sslcontext;
    private static Registry<ConnectionSocketFactory> socketFactoryRegistry;
    private static SocketConfig socketConfig;
    @SuppressWarnings("unused")
    private static DnsResolver dnsResolver;
    private static MessageConstraints messageConstraints;
    private static ConnectionConfig connectionConfig;
    private static CookieStore cookieStore;
    private static CredentialsProvider credentialsProvider;
    private static RequestConfig defaultRequestConfig;
    private static SSLConnectionSocketFactory sslsf;
    private static KeyStore keyStore;

    /**
     * 连接超时,默认10秒钟.
     */
    private static int SOCKET_TIMEOUT = 10 * 1000;

    /**
     * 传输超时，默认30秒
     */
    private static int CONNECT_TIMEOUT = 30 * 1000;

    /**
     * 该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大.
     */
    private static int CONNECTION_REQUEST_TIMEOUT = 20 * 1000;

    /**
     * 最大连接数 .
     */
    private final static int MAX_TOTAL_CONNECTIONS = 800;

    /**
     * 默认编码
     */
    private static String CONTENT_ENCODING = "UTF-8";

    private static int MAX_HEADER_COUNT = 200;

    private static int MAX_LINE_LENGTH = 2000;

    private static int VALIDATE_INACTIVITY = 1000;

    //private static long CONN_IDLE_TIMEOUT = 500L;

    public static CloseableHttpClient httpClient;

    static {

        init();

        httpClient = getHttpClient();
    }

    /**
     * @param url
     * @param data 默认Stirng类型，e.g key=val&key2=val2
     * @return
     * @author by k2 May 12, 2015
     * @desc http post类型请求.默认编码UTF-8
     */
    public synchronized static StringBuilder post(String url, Object data) {
        return post(url, data, CONTENT_ENCODING);
    }

    /**
     * @param url  http url
     * @param data Object type = String|Map
     * @return
     * @author by k2 May 12, 2015
     * @desc http post类型请求.
     */
    public synchronized static StringBuilder post(String url, Object data, String encoding) {

        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        HttpHost httpHost = null;
        StringBuilder responseEntity = null;
        String strings = "";
        try {

            URL _url = new URL(url);

            httpPost = new HttpPost(url);

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            if (!Stringer.isNullOrEmpty(data)) {

                log.debug("HttpClient post url => " + url + ", data:" + JsonUtil.toJson(data));

                if (data instanceof Map) {
                    // Map方式传参处理
                    @SuppressWarnings("unchecked")
                    Map<String, String> params = (Map<String, String>) data;

                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                    if (!Stringer.isNullOrEmpty(data)) {
                        Set<String> keySet = params.keySet();
                        for (String key : keySet) {
                            nvps.add(new BasicNameValuePair(key, params.get(key)));
                        }
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

                } else if (data instanceof String) {
                    // String方式传参处理
                    strings = (String) data;
                    strings = Stringer.nullToEmpty(strings);
                    ByteArrayEntity reqEntity = new ByteArrayEntity(strings.getBytes(encoding));
                    reqEntity.setContentEncoding(encoding);
                    httpPost.setEntity(reqEntity);
                }
            }

            httpPost.setConfig(defaultRequestConfig);

            httpHost = new HttpHost(_url.getHost(), _url.getPort());
            response = httpClient.execute(httpHost, httpPost);

            log.debug("httpClient.execute(httpHost,httpPost)返回结果==============》》" + response);

            if (!Stringer.isNullOrEmpty(response)) {
                String resp = EntityUtils.toString(response.getEntity(), encoding);
                log.debug("response返回结果状态和结果信息:" + response.getStatusLine().getStatusCode() + "内容==" + resp);
                if (response.getStatusLine().getStatusCode() != 200) {
                    return new StringBuilder(response.getStatusLine().getStatusCode());
                }

                responseEntity = new StringBuilder();
                responseEntity.append(resp);
                return responseEntity;
            } else {
                return new StringBuilder("调用接口返回是空值===失败");
            }

        } catch (Exception e) {
            log.error(e.getClass() + ":" + e.getMessage() + ", -> Reference: data=" + data + ", url=" + url);
            e.printStackTrace();
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (poolConnManager != null) {
                poolConnManager.closeExpiredConnections();
                //poolConnManager.closeIdleConnections(CONN_MANAGER_TIMEOUT, TimeUnit.MILLISECONDS);
            }
            try {
                if (!Stringer.isNullOrEmpty(response)) {
                    response.close();
                }
            } catch (IOException e) {
                log.error(e.getClass() + ":" + e.getMessage() + ", -> Reference: data=" + data + ", url=" + url);
            }
        }
        return null;
    }

    /**
     * @param url
     * @return
     * @author by k2 May 12, 2015
     * @desc https get类型请求.默认编码UTF-8
     */
    public synchronized static StringBuilder get(String url) {
        return get(url, CONTENT_ENCODING);
    }

    /**
     * @param url
     * @return
     * @author by k2 May 12, 2015
     * @desc https get类型请求.
     */
    public synchronized static StringBuilder get(String url, String encoding) {
        CloseableHttpResponse response = null;
        HttpGet httpGet = null;
        HttpHost httpHost = null;
        StringBuilder sb = null;
        try {
            URL _url = new URL(url);
            URI uri = new URI(_url.getProtocol(), _url.getHost(), _url.getPath(), _url.getQuery(), null);
            httpGet = new HttpGet(uri);

            httpGet.setHeader("Content-type", "text/html; charset=" + encoding);

            httpGet.setConfig(defaultRequestConfig);

            httpHost = new HttpHost(_url.getHost(), _url.getPort());
            response = httpClient.execute(httpHost, httpGet);

            if (!Stringer.isNullOrEmpty(response)) {

                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }

                sb = new StringBuilder();
                sb.append(EntityUtils.toString(response.getEntity(), encoding));
                return sb;
            }

        } catch (Exception e) {
            log.error(e.getClass() + ":" + e.getMessage() + ", -> Reference: url=" + url);
            e.printStackTrace();
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
            if (poolConnManager != null) {
                //该方法关闭超过连接保持时间的空闲连接
                poolConnManager.closeExpiredConnections();
                //该方法关闭空闲时间超过timeout的连接，空闲时间从交还给连接管理器时开始
                //不管是否已过期超过空闲时间则关闭。所以Idle时间应该设置的尽量长一点
                //poolConnManager.closeIdleConnections(CONN_IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
            }
            try {
                if (!Stringer.isNullOrEmpty(response)) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return
     * @author by k2 May 12, 2015
     * @desc 适合多线程的HttpClient, 用httpClient4.5实现.
     */
    public synchronized static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(poolConnManager)
                .setDefaultCookieStore(cookieStore)
                .setDefaultCredentialsProvider(credentialsProvider)
                //.setProxy(new HttpHost("myproxy", 8080))
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        return httpclient;
    }

    /**
     * @return
     * @author by k2 May 12, 2015
     * @desc 获取ssl证书 HttpClient,用httpClient4.5实现.
     */
    public synchronized static CloseableHttpClient getHttpClientOfSSL(String certPath, String certPasswd) {
        CloseableHttpClient client = null;
        FileInputStream instream = null;
        try {

            // 加载本地的证书进行https加密传输
            instream = new FileInputStream(new File(certPath));

            // 设置证书密码
            keyStore.load(instream, certPasswd.toCharArray());

            // Trust own CA and all self-signed certs
            sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, certPasswd.toCharArray())
                    .build();

            // Allow TLSv1 protocol only
            sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            client = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setConnectionManager(poolConnManager)
                    .setDefaultCookieStore(cookieStore)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

        } catch (Exception e) {
            log.error(e.getClass() + ":" + e.getMessage() + ", -> Reference: certPath=" + certPath);
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return client;
    }

    // 初始化参数
    private static void init() {
        log.debug("##>>> ----------- HttpClient init Start! -----------");
        try {
            // Use custom message parser / writer to customize the way HTTP
            // messages are parsed from and written out to the data stream.
            responseParserFactory = new DefaultHttpResponseParserFactory() {

                @Override
                public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
                    LineParser lineParser = new BasicLineParser() {

                        @Override
                        public Header parseHeader(final CharArrayBuffer buffer) {
                            try {
                                return super.parseHeader(buffer);
                            } catch (ParseException ex) {
                                return new BasicHeader(buffer.toString(), null);
                            }
                        }

                    };
                    return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE,
                            constraints) {

                        @Override
                        protected boolean reject(final CharArrayBuffer line, int count) {
                            // try to ignore all garbage preceding a status line
                            // infinitely
                            return false;
                        }

                    };
                }

            };

            requestWriterFactory = new DefaultHttpRequestWriterFactory();

            // Use a custom connection factory to customize the process of
            // initialization of outgoing HTTP connections. Beside standard
            // connection
            // configuration parameters HTTP connection factory can define message
            // parser / writer routines to be employed by individual connections.
            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
                    requestWriterFactory, responseParserFactory);

            // Client HTTP connection objects when fully initialized can be bound to
            // an arbitrary network socket. The process of network socket
            // initialization,
            // its connection to a remote address and binding to a local one is
            // controlled
            // by a connection socket factory.

            // SSL context for secure connections can be created either based on
            // system or application specific properties.
            sslcontext = SSLContexts.createSystemDefault();

            // Create a registry of custom connection socket factories for supported
            // protocol schemes.
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext)).build();

            // Use custom DNS resolver to override the system DNS resolution.
            dnsResolver = new SystemDefaultDnsResolver() {

                @Override
                public InetAddress[] resolve(final String host) throws UnknownHostException {
                    if (host.equalsIgnoreCase("myhost")) {
                        return new InetAddress[]{InetAddress.getByAddress(new byte[]{127, 0, 0, 1})};
                    } else {
                        return super.resolve(host);
                    }
                }

            };

            // Create socket configuration
            socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

            // Create a connection manager with custom configuration.
            //##>>>poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory);

            // Create message constraints
            messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(MAX_HEADER_COUNT)
                    .setMaxLineLength(MAX_LINE_LENGTH)
                    .build();

            // Create connection configuration
            connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();

            // Use custom cookie store if necessary.
            cookieStore = new BasicCookieStore();

            // Use custom credentials provider if necessary.
            credentialsProvider = new BasicCredentialsProvider();

            // Create global request configuration
            defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                    //##>>>.setProxy(new HttpHost("myotherproxy", 8080))
                    .build();

            keyStore = KeyStore.getInstance("PKCS12");

            // Configure the connection manager to use socket configuration either
            // by default or for a specific host.
            poolConnManager.setDefaultSocketConfig(socketConfig);
            //##>>>poolConnManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);

            // Validate connections after 1 sec of inactivity
            poolConnManager.setValidateAfterInactivity(VALIDATE_INACTIVITY);

            // Configure the connection manager to use connection configuration either
            // by default or for a specific host.
            poolConnManager.setDefaultConnectionConfig(connectionConfig);
            //##>>>poolConnManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

            // Configure total max or per route limits for persistent connections
            // that can be kept in the pool or leased by the connection manager.
            poolConnManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            poolConnManager.setDefaultMaxPerRoute(poolConnManager.getMaxTotal());

            //poolConnManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);
            log.debug("##>>> ----------- HttpClient init complete! -----------");
        } catch (KeyStoreException e) {
            log.error(e.getClass() + ":" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getClass() + ":" + e.getMessage());
            e.printStackTrace();
        }

    }

}
