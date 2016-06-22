
package com.github.apycazo.quinielator2.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component("http")
public class HttpWrapper {
    /**
     * Default timeout for http connections
     */
    public static final int defaultTimeout = 5000;

    /**
     * Default http content types known
     */
    public static enum HttpContent {

        Json("application/json"), XML("application/xml"), PlainText("text/plain"),
        HtmlText("text/html"), FormData("multipart/form-data"), UrlEncoded("application/x-www-form-urlencoded");
        private final String value;

        HttpContent(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }
    }

    /**
     * Http methods this class can use
     */
    public static enum HttpMethod {

        POST, GET, PUT, DELETE
    }

    /**
     * Clasifies http response types according to the http response code
     */
    public static enum HttpResponse {

        API_error, Informational, Success, Redirection, Client_Error, Server_Error;

        public static HttpResponse getResponseType(int responseCode) {
            int classValue = responseCode / 100;
            HttpResponse resp;
            switch (classValue) {
                case 0:
                    resp = HttpResponse.API_error;
                    break;
                case 1:
                    resp = HttpResponse.Informational;
                    break;
                case 2:
                    resp = HttpResponse.Success;
                    break;
                case 3:
                    resp = HttpResponse.Redirection;
                    break;
                case 4:
                    resp = HttpResponse.Client_Error;
                    break;
                case 5:
                    resp = HttpResponse.Server_Error;
                    break;
                default:
                    resp = HttpResponse.API_error;
            }
            return resp;
        }
    }
    private Map<String, String> requestProperties;
    private URL url;
    private int responseCode;
    private int timeout;
    private String response;
    private HttpMethod httpMethod;
    private HttpResponse httpResponse;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Empty builder, url not set (!), and default timeout
     */
    public HttpWrapper() {
        this(null, defaultTimeout, null);
    }

    /**
     * Default builder, requires url, and uses the default timeout
     *
     * @param url address to use as endpoint
     */
    public HttpWrapper(URL url) {
        this(url, defaultTimeout, null);
    }

    /**
     * Complete builder, specify the url, timeout and request properties map
     *
     * @param url address to use as endpoint
     * @param timeout timeout before discarding a message
     * @param requestProperties map <key,value>, to send as request properties
     */
    public HttpWrapper(URL url, int timeout, Map<String, String> requestProperties) {
        this.url = url;
        this.timeout = timeout;
        this.httpMethod = HttpMethod.GET;
        this.httpResponse = HttpResponse.API_error;
        this.response = "";
        this.responseCode = -1;
        if (requestProperties == null) {
            this.requestProperties = new LinkedHashMap<>();
        } else {
            this.requestProperties = requestProperties;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Forward method wrappers">
    /**
     * Http GET, using stored URL
     *
     * @return An http response type
     */
    public HttpResponse get() {
        return get(url);
    }

    /**
     * Http GET, using provided URL
     *
     * @param url Address to get
     * @return An http response type
     */
    public HttpResponse get(URL url) {
        this.url = url;
        this.httpMethod = HttpMethod.GET;
        return forward();
    }

    public HttpResponse post(String message) {
        return post(message, url, null);
    }

    public HttpResponse post(String message, URL url) {
        return post(message, url, null);
    }

    public HttpResponse post(String message, URL url, HttpContent contentType) {
        message = message == null ? "" : message;
        this.url = url;
        if (contentType != null) {
            this.setContentType(contentType.getValue());
        }
        this.httpMethod = HttpMethod.POST;
        return forward(message);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Forward message methods">
    public HttpResponse forward() {
        return forward(url, "", httpMethod, requestProperties);
    }

    public HttpResponse forward(String msg) {
        return forward(url, msg, httpMethod, requestProperties);
    }

    public HttpResponse forward(URL endpoint, String msg, HttpMethod type, Map<String, String> requestProperties) {
        responseCode = 0;
        HttpURLConnection conn;
        try {
            conn = openConnection(endpoint, type.name(), requestProperties);
        } catch (IOException ex) {
            response = ex.getMessage();
            return HttpResponse.API_error;
        }
        // If msg not empty, write it
        if (!msg.isEmpty() && !sendMsg(msg, conn)) {
            return HttpResponse.API_error;
        }
        try {
            responseCode = conn.getResponseCode();
        } catch (IOException ex) {
            response = ex.getMessage();
            return HttpResponse.API_error;
        }
        try {
            if (!getConnectionResponse(conn.getInputStream())) {
                getConnectionResponse(conn.getErrorStream());
            }
        } catch (IOException ex) {
            response = ex.getMessage();
            if (responseCode > 0) {
                return HttpResponse.getResponseType(responseCode);
            } else {
                return HttpResponse.API_error;
            }
        }
        httpResponse = HttpResponse.getResponseType(responseCode);
        return httpResponse;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Connection methods">
    private HttpURLConnection openConnection(URL endpoint, String type, Map<String, String> requestProperties) throws IOException {
        HttpURLConnection connection;
        URLConnection uc = endpoint.openConnection();
        connection = (HttpURLConnection) uc;
        Iterator<String> it = requestProperties.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = requestProperties.get(key);
            connection.setRequestProperty(key, value);
        }
        connection.setDoOutput(true); // triggers POST
        connection.setDoInput(true);
        connection.setConnectTimeout(timeout);
        connection.setRequestMethod(type);
        return connection;
    }

    private boolean sendMsg(String msg, HttpURLConnection connection) {
        try {
            Writer wout = new OutputStreamWriter(connection.getOutputStream());
            wout.write(msg);
            wout.flush();
            wout.close();
            return true;
        } catch (Exception e) {
            logError("Can't write msg. Cause:" + e.getMessage());
            return false;
        }
    }

    private boolean getConnectionResponse(InputStream answer) {
        try {
            response = "";
            int c;
            while ((c = answer.read()) != -1) {
                response += (char) c;
            }
            answer.close();
            return true;
        } catch (Exception e) {
            logError("Can't read response. Cause:" + e.getMessage());
            return false;
        }
    }

    public String logError(String message) {
        return logError(0, message);
    }

    public String logError(int level, String message) {
        return "";
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GET/SET">
    public HttpWrapper setProperties(Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
        return this;

    }

    public HttpWrapper setProperty(String key, String value) {
        this.requestProperties.put(key, value);
        return this;
    }

    public String getProperty(String key) {
        if (!this.requestProperties.containsKey(key)) {
            return "";
        } else {
            return this.requestProperties.get(key);
        }
    }

    public HttpWrapper setURL(URL url) {
        this.url = url;
        return this;
    }

    public URL getURL() {
        return this.url;
    }

    public Map<String, String> getProperties() {
        return this.requestProperties;
    }

    public HttpWrapper setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public HttpWrapper setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public HttpWrapper setContentType(String contentType) {
        this.requestProperties.put("content-type", contentType);
        return this;
    }

    public String getContentType() {
        if (this.requestProperties.containsKey("content-type")) {
            return this.requestProperties.get("content-type");
        } else {
            return "";
        }
    }

    public String getResponse() {
        return response == null ? "" : response;
    }
    // </editor-fold>
}
