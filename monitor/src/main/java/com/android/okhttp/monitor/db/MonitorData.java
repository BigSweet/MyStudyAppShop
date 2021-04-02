package com.android.okhttp.monitor.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity
public class MonitorData implements Parcelable {
    @Id
    private Long id;
    @Property
    private Long duration;
    @Property
    private String method;
    @Property
    private String url;
    @Property
    private String host;
    @Property
    private String path;
    @Property
    private String scheme;
    @Property
    private String protocol;
    @Property
    private Date requestDate;
    @Property
    private String requestHeaders;
    @Property
    private String requestBody;
    @Property
    private String requestContentType;
    @Property
    private Boolean isRequestBodyIsPlainText;
    @Property
    private int responseCode;
    @Property
    private Date responseDate;
    @Property
    private String responseHeaders;
    @Property
    private String responseBody;
    @Property
    private String responseMessage;
    @Property
    private String responseContentType;
    @Property
    private Long responseContentLength;
    @Property
    private Boolean isResponseBodyIsPlainText;
    @Property
    private String errorMsg;

    @Generated(hash = 19906161)
    public MonitorData(Long id, Long duration, String method, String url,
                       String host, String path, String scheme, String protocol,
                       Date requestDate, String requestHeaders, String requestBody,
                       String requestContentType, Boolean isRequestBodyIsPlainText,
                       int responseCode, Date responseDate, String responseHeaders,
                       String responseBody, String responseMessage, String responseContentType,
                       Long responseContentLength, Boolean isResponseBodyIsPlainText,
                       String errorMsg) {
        this.id = id;
        this.duration = duration;
        this.method = method;
        this.url = url;
        this.host = host;
        this.path = path;
        this.scheme = scheme;
        this.protocol = protocol;
        this.requestDate = requestDate;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.requestContentType = requestContentType;
        this.isRequestBodyIsPlainText = isRequestBodyIsPlainText;
        this.responseCode = responseCode;
        this.responseDate = responseDate;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.responseMessage = responseMessage;
        this.responseContentType = responseContentType;
        this.responseContentLength = responseContentLength;
        this.isResponseBodyIsPlainText = isResponseBodyIsPlainText;
        this.errorMsg = errorMsg;
    }

    @Generated(hash = 1979056246)
    public MonitorData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestHeaders() {
        return this.requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestContentType() {
        return this.requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public Boolean getIsRequestBodyIsPlainText() {
        return this.isRequestBodyIsPlainText;
    }

    public void setIsRequestBodyIsPlainText(Boolean isRequestBodyIsPlainText) {
        this.isRequestBodyIsPlainText = isRequestBodyIsPlainText;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Date getResponseDate() {
        return this.responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseContentType() {
        return this.responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public Long getResponseContentLength() {
        return this.responseContentLength;
    }

    public void setResponseContentLength(Long responseContentLength) {
        this.responseContentLength = responseContentLength;
    }

    public Boolean getIsResponseBodyIsPlainText() {
        return this.isResponseBodyIsPlainText;
    }

    public void setIsResponseBodyIsPlainText(Boolean isResponseBodyIsPlainText) {
        this.isResponseBodyIsPlainText = isResponseBodyIsPlainText;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.duration);
        dest.writeString(this.method);
        dest.writeString(this.url);
        dest.writeString(this.host);
        dest.writeString(this.path);
        dest.writeString(this.scheme);
        dest.writeString(this.protocol);
        dest.writeLong(this.requestDate != null ? this.requestDate.getTime() : -1);
        dest.writeString(this.requestHeaders);
        dest.writeString(this.requestBody);
        dest.writeString(this.requestContentType);
        dest.writeValue(this.isRequestBodyIsPlainText);
        dest.writeInt(this.responseCode);
        dest.writeLong(this.responseDate != null ? this.responseDate.getTime() : -1);
        dest.writeString(this.responseHeaders);
        dest.writeString(this.responseBody);
        dest.writeString(this.responseMessage);
        dest.writeString(this.responseContentType);
        dest.writeValue(this.responseContentLength);
        dest.writeValue(this.isResponseBodyIsPlainText);
        dest.writeString(this.errorMsg);
    }

    protected MonitorData(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.duration = (Long) in.readValue(Long.class.getClassLoader());
        this.method = in.readString();
        this.url = in.readString();
        this.host = in.readString();
        this.path = in.readString();
        this.scheme = in.readString();
        this.protocol = in.readString();
        long tmpRequestDate = in.readLong();
        this.requestDate = tmpRequestDate == -1 ? null : new Date(tmpRequestDate);
        this.requestHeaders = in.readString();
        this.requestBody = in.readString();
        this.requestContentType = in.readString();
        this.isRequestBodyIsPlainText = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.responseCode = in.readInt();
        long tmpResponseDate = in.readLong();
        this.responseDate = tmpResponseDate == -1 ? null : new Date(tmpResponseDate);
        this.responseHeaders = in.readString();
        this.responseBody = in.readString();
        this.responseMessage = in.readString();
        this.responseContentType = in.readString();
        this.responseContentLength = (Long) in.readValue(Long.class.getClassLoader());
        this.isResponseBodyIsPlainText = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.errorMsg = in.readString();
    }

    public static final Parcelable.Creator<MonitorData> CREATOR = new Parcelable.Creator<MonitorData>() {
        @Override
        public MonitorData createFromParcel(Parcel source) {
            return new MonitorData(source);
        }

        @Override
        public MonitorData[] newArray(int size) {
            return new MonitorData[size];
        }
    };
}
