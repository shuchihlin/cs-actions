package org.score.content.httpclient.build;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.score.content.httpclient.HttpClientInputs;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: davidmih
 * Date: 8/28/14
 */
public class RequestBuilder {
    //ordered
    private static String [] methods = new String []{HttpDelete.METHOD_NAME, HttpGet.METHOD_NAME, HttpHead.METHOD_NAME,
            HttpOptions.METHOD_NAME, HttpPost.METHOD_NAME, HttpPut.METHOD_NAME,  HttpTrace.METHOD_NAME};

    private String method;
    private URI uri;
    private HttpEntity entity;

    public RequestBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public RequestBuilder setEntity(HttpEntity entity) {
        this.entity = entity;
        return this;
    }

    public HttpRequestBase build() {
        String method = this.method.toUpperCase().trim();
        if (Arrays.binarySearch(methods, method) < 0) {
            throw new IllegalArgumentException("invalid '"+ HttpClientInputs.METHOD+"' input '" + method + "'");
        }

        org.apache.http.client.methods.RequestBuilder requestBuilder
                = org.apache.http.client.methods.RequestBuilder.create(method);
        requestBuilder.setUri(uri);
        requestBuilder.setEntity(entity);

        return (HttpRequestBase) requestBuilder.build();
    }
}