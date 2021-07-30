package com.android.okhttp.monitor.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MONITOR_DATA".
*/
public class MonitorDataDao extends AbstractDao<MonitorData, Long> {

    public static final String TABLENAME = "MONITOR_DATA";

    /**
     * Properties of entity MonitorData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Duration = new Property(1, Long.class, "duration", false, "DURATION");
        public final static Property Method = new Property(2, String.class, "method", false, "METHOD");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property Host = new Property(4, String.class, "host", false, "HOST");
        public final static Property Path = new Property(5, String.class, "path", false, "PATH");
        public final static Property Scheme = new Property(6, String.class, "scheme", false, "SCHEME");
        public final static Property Protocol = new Property(7, String.class, "protocol", false, "PROTOCOL");
        public final static Property RequestDate = new Property(8, java.util.Date.class, "requestDate", false, "REQUEST_DATE");
        public final static Property RequestHeaders = new Property(9, String.class, "requestHeaders", false, "REQUEST_HEADERS");
        public final static Property RequestBody = new Property(10, String.class, "requestBody", false, "REQUEST_BODY");
        public final static Property RequestContentType = new Property(11, String.class, "requestContentType", false, "REQUEST_CONTENT_TYPE");
        public final static Property IsRequestBodyIsPlainText = new Property(12, Boolean.class, "isRequestBodyIsPlainText", false, "IS_REQUEST_BODY_IS_PLAIN_TEXT");
        public final static Property ResponseCode = new Property(13, int.class, "responseCode", false, "RESPONSE_CODE");
        public final static Property ResponseDate = new Property(14, java.util.Date.class, "responseDate", false, "RESPONSE_DATE");
        public final static Property ResponseHeaders = new Property(15, String.class, "responseHeaders", false, "RESPONSE_HEADERS");
        public final static Property ResponseBody = new Property(16, String.class, "responseBody", false, "RESPONSE_BODY");
        public final static Property ResponseMessage = new Property(17, String.class, "responseMessage", false, "RESPONSE_MESSAGE");
        public final static Property ResponseContentType = new Property(18, String.class, "responseContentType", false, "RESPONSE_CONTENT_TYPE");
        public final static Property ResponseContentLength = new Property(19, Long.class, "responseContentLength", false, "RESPONSE_CONTENT_LENGTH");
        public final static Property IsResponseBodyIsPlainText = new Property(20, Boolean.class, "isResponseBodyIsPlainText", false, "IS_RESPONSE_BODY_IS_PLAIN_TEXT");
        public final static Property ErrorMsg = new Property(21, String.class, "errorMsg", false, "ERROR_MSG");
    }


    public MonitorDataDao(DaoConfig config) {
        super(config);
    }
    
    public MonitorDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MONITOR_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DURATION\" INTEGER," + // 1: duration
                "\"METHOD\" TEXT," + // 2: method
                "\"URL\" TEXT," + // 3: url
                "\"HOST\" TEXT," + // 4: host
                "\"PATH\" TEXT," + // 5: path
                "\"SCHEME\" TEXT," + // 6: scheme
                "\"PROTOCOL\" TEXT," + // 7: protocol
                "\"REQUEST_DATE\" INTEGER," + // 8: requestDate
                "\"REQUEST_HEADERS\" TEXT," + // 9: requestHeaders
                "\"REQUEST_BODY\" TEXT," + // 10: requestBody
                "\"REQUEST_CONTENT_TYPE\" TEXT," + // 11: requestContentType
                "\"IS_REQUEST_BODY_IS_PLAIN_TEXT\" INTEGER," + // 12: isRequestBodyIsPlainText
                "\"RESPONSE_CODE\" INTEGER NOT NULL ," + // 13: responseCode
                "\"RESPONSE_DATE\" INTEGER," + // 14: responseDate
                "\"RESPONSE_HEADERS\" TEXT," + // 15: responseHeaders
                "\"RESPONSE_BODY\" TEXT," + // 16: responseBody
                "\"RESPONSE_MESSAGE\" TEXT," + // 17: responseMessage
                "\"RESPONSE_CONTENT_TYPE\" TEXT," + // 18: responseContentType
                "\"RESPONSE_CONTENT_LENGTH\" INTEGER," + // 19: responseContentLength
                "\"IS_RESPONSE_BODY_IS_PLAIN_TEXT\" INTEGER," + // 20: isResponseBodyIsPlainText
                "\"ERROR_MSG\" TEXT);"); // 21: errorMsg
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MONITOR_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MonitorData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long duration = entity.getDuration();
        if (duration != null) {
            stmt.bindLong(2, duration);
        }
 
        String method = entity.getMethod();
        if (method != null) {
            stmt.bindString(3, method);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String host = entity.getHost();
        if (host != null) {
            stmt.bindString(5, host);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(6, path);
        }
 
        String scheme = entity.getScheme();
        if (scheme != null) {
            stmt.bindString(7, scheme);
        }
 
        String protocol = entity.getProtocol();
        if (protocol != null) {
            stmt.bindString(8, protocol);
        }
 
        java.util.Date requestDate = entity.getRequestDate();
        if (requestDate != null) {
            stmt.bindLong(9, requestDate.getTime());
        }
 
        String requestHeaders = entity.getRequestHeaders();
        if (requestHeaders != null) {
            stmt.bindString(10, requestHeaders);
        }
 
        String requestBody = entity.getRequestBody();
        if (requestBody != null) {
            stmt.bindString(11, requestBody);
        }
 
        String requestContentType = entity.getRequestContentType();
        if (requestContentType != null) {
            stmt.bindString(12, requestContentType);
        }
 
        Boolean isRequestBodyIsPlainText = entity.getIsRequestBodyIsPlainText();
        if (isRequestBodyIsPlainText != null) {
            stmt.bindLong(13, isRequestBodyIsPlainText ? 1L: 0L);
        }
        stmt.bindLong(14, entity.getResponseCode());
 
        java.util.Date responseDate = entity.getResponseDate();
        if (responseDate != null) {
            stmt.bindLong(15, responseDate.getTime());
        }
 
        String responseHeaders = entity.getResponseHeaders();
        if (responseHeaders != null) {
            stmt.bindString(16, responseHeaders);
        }
 
        String responseBody = entity.getResponseBody();
        if (responseBody != null) {
            stmt.bindString(17, responseBody);
        }
 
        String responseMessage = entity.getResponseMessage();
        if (responseMessage != null) {
            stmt.bindString(18, responseMessage);
        }
 
        String responseContentType = entity.getResponseContentType();
        if (responseContentType != null) {
            stmt.bindString(19, responseContentType);
        }
 
        Long responseContentLength = entity.getResponseContentLength();
        if (responseContentLength != null) {
            stmt.bindLong(20, responseContentLength);
        }
 
        Boolean isResponseBodyIsPlainText = entity.getIsResponseBodyIsPlainText();
        if (isResponseBodyIsPlainText != null) {
            stmt.bindLong(21, isResponseBodyIsPlainText ? 1L: 0L);
        }
 
        String errorMsg = entity.getErrorMsg();
        if (errorMsg != null) {
            stmt.bindString(22, errorMsg);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MonitorData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long duration = entity.getDuration();
        if (duration != null) {
            stmt.bindLong(2, duration);
        }
 
        String method = entity.getMethod();
        if (method != null) {
            stmt.bindString(3, method);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String host = entity.getHost();
        if (host != null) {
            stmt.bindString(5, host);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(6, path);
        }
 
        String scheme = entity.getScheme();
        if (scheme != null) {
            stmt.bindString(7, scheme);
        }
 
        String protocol = entity.getProtocol();
        if (protocol != null) {
            stmt.bindString(8, protocol);
        }
 
        java.util.Date requestDate = entity.getRequestDate();
        if (requestDate != null) {
            stmt.bindLong(9, requestDate.getTime());
        }
 
        String requestHeaders = entity.getRequestHeaders();
        if (requestHeaders != null) {
            stmt.bindString(10, requestHeaders);
        }
 
        String requestBody = entity.getRequestBody();
        if (requestBody != null) {
            stmt.bindString(11, requestBody);
        }
 
        String requestContentType = entity.getRequestContentType();
        if (requestContentType != null) {
            stmt.bindString(12, requestContentType);
        }
 
        Boolean isRequestBodyIsPlainText = entity.getIsRequestBodyIsPlainText();
        if (isRequestBodyIsPlainText != null) {
            stmt.bindLong(13, isRequestBodyIsPlainText ? 1L: 0L);
        }
        stmt.bindLong(14, entity.getResponseCode());
 
        java.util.Date responseDate = entity.getResponseDate();
        if (responseDate != null) {
            stmt.bindLong(15, responseDate.getTime());
        }
 
        String responseHeaders = entity.getResponseHeaders();
        if (responseHeaders != null) {
            stmt.bindString(16, responseHeaders);
        }
 
        String responseBody = entity.getResponseBody();
        if (responseBody != null) {
            stmt.bindString(17, responseBody);
        }
 
        String responseMessage = entity.getResponseMessage();
        if (responseMessage != null) {
            stmt.bindString(18, responseMessage);
        }
 
        String responseContentType = entity.getResponseContentType();
        if (responseContentType != null) {
            stmt.bindString(19, responseContentType);
        }
 
        Long responseContentLength = entity.getResponseContentLength();
        if (responseContentLength != null) {
            stmt.bindLong(20, responseContentLength);
        }
 
        Boolean isResponseBodyIsPlainText = entity.getIsResponseBodyIsPlainText();
        if (isResponseBodyIsPlainText != null) {
            stmt.bindLong(21, isResponseBodyIsPlainText ? 1L: 0L);
        }
 
        String errorMsg = entity.getErrorMsg();
        if (errorMsg != null) {
            stmt.bindString(22, errorMsg);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MonitorData readEntity(Cursor cursor, int offset) {
        MonitorData entity = new MonitorData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // duration
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // method
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // host
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // path
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // scheme
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // protocol
            cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)), // requestDate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // requestHeaders
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // requestBody
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // requestContentType
            cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0, // isRequestBodyIsPlainText
            cursor.getInt(offset + 13), // responseCode
            cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)), // responseDate
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // responseHeaders
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // responseBody
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // responseMessage
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // responseContentType
            cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19), // responseContentLength
            cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0, // isResponseBodyIsPlainText
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21) // errorMsg
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MonitorData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDuration(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setMethod(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHost(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPath(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setScheme(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setProtocol(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRequestDate(cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)));
        entity.setRequestHeaders(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRequestBody(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setRequestContentType(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIsRequestBodyIsPlainText(cursor.isNull(offset + 12) ? null : cursor.getShort(offset + 12) != 0);
        entity.setResponseCode(cursor.getInt(offset + 13));
        entity.setResponseDate(cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)));
        entity.setResponseHeaders(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setResponseBody(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setResponseMessage(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setResponseContentType(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setResponseContentLength(cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19));
        entity.setIsResponseBodyIsPlainText(cursor.isNull(offset + 20) ? null : cursor.getShort(offset + 20) != 0);
        entity.setErrorMsg(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MonitorData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MonitorData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MonitorData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}