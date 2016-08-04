package com.wen.magi.baseframe.managers;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.wen.magi.baseframe.utils.IOUtils;
import com.wen.magi.baseframe.utils.LangUtils;
import com.wen.magi.baseframe.utils.LogUtils;
import com.wen.magi.baseframe.web.BitmapLruCache;

import java.util.Map;

public class RequestQueueManager {
    private static RequestQueue mQueue;

    private static ImageLoader mImageLoader;


    private RequestQueueManager() {
        // no instances
    }

    public static void initialize(Context context) {
        if (mQueue == null) {
            HttpStack stack = new HurlStack();

            Network network = new BasicNetwork(stack);
            mQueue =
                    new RequestQueue(new DiskBasedCache(AppCacheManager.getManager().getCacheDir()), network);
            mQueue.start();
        }
        if (mImageLoader == null) {
            int memClass =
                    ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            // Use 1/8th of the available memory for this memory cache.
            int cacheSize = 1024 * 1024 * memClass / 16;
            mImageLoader = new ImageLoader(mQueue, new BitmapLruCache(cacheSize));
        }
    }

    public static RequestQueue getRequestQueue() {
        if (mQueue != null) {
            return mQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }


    /**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache} which effectively means
     * that no memory caching is used. This is useful for images that you know that will be show only
     * once.
     *
     * @return
     */
    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    public synchronized static <T> void addRequest(Request<T> r) {
        addRequest(r, AppManager.getApplicationContext());
    }

    public synchronized static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        if (mQueue != null)
            mQueue.add(request);
    }

    public static void cancelAll() {
        if (mQueue != null) {
            mQueue.stop();
            mQueue = null;
        }
    }

    /**
     * 结束mQueue
     */
    public static void destroy() {
        cancelAll();
        token = null;
        mImageLoader = null;
    }

    public static void clearToken() {
        token = null;
    }

    /**
     * 根据tag，取消队列中的网络访问
     *
     * @param tag
     */
    public static void cancelTag(Object tag) {
        if (mQueue != null) {
            mQueue.cancelAll(tag);
        }
    }

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_TOKEN = "remember_token";

    /**
     * Checks the response headers for session cookie and saves it if it finds it.
     *
     * @param headers Response Headers.
     */
    public static void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_TOKEN)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                token = cookie;
                IOUtils.savePreference(SESSION_TOKEN, cookie);
            }
        }
    }

    private static String token;

    public static String getTokenPhone() {
        if (LangUtils.isNotEmpty(token)) {
            String[] splits = token.split("\\|");
            if (splits != null && splits.length > 0) {
                return splits[0];
            }
        }
        return token;
    }

    /**
     * Adds session cookie to headers if exists.
     *
     * @param headers
     */
    public static void addSessionCookie(Map<String, String> headers) {
        //TODO 使用小熊快跑接口测试用
        if (token == null) {
            token = IOUtils.getPreferenceValue(SESSION_TOKEN);
        }
        String sessionId = /*token*/"13521125097_jRlEtx|10dada62b8f329ecf15ddb5c4f2b4756f3e231e9";
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_TOKEN);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
}
