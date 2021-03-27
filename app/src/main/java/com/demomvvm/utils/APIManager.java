package com.demomvvm.utils;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.demomvvm.R;
import com.demomvvm.activity.BaseActivity;
import com.demomvvm.MainApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snehal on 1/15/2020.
 */

public class APIManager {
    public static void requestGetMethod(final Context context, String url, boolean showProgress, final VolleyCallback callback) {
        getData(context, Request.Method.GET, url, showProgress, callback);
    }

    public static void requestDeleteMethod(final Context context, String url, boolean showProgress, final VolleyCallback callback) {
        getData(context, Request.Method.DELETE, url, showProgress, callback);
    }

    public static void requestPostMethod(final Context context, final String url, final JSONObject postJson, boolean showProgress, final VolleyCallback callback) {
        postData(context, url, Request.Method.POST, postJson, showProgress, callback);
    }

    public static void requestPutMethod(final Context context, final String url, final JSONObject postJson, boolean showProgress, final VolleyCallback callback) {
        postData(context, url, Request.Method.PUT, postJson, showProgress, callback);
    }

    /**
     *  Get / Delete request
     * @param context :
     * @param method :
     * @param url :
     * @param callback :
     */
    private static void getData(final Context context, int method, final String url, boolean showProgress, final VolleyCallback callback) {
        System.out.println(url);
        if (NetworkUtils.isNetworkAvailable(context)) {
            if (showProgress) {
                BaseActivity.showProgressDialog(context);
            }
            final StringRequest stringRequest = new StringRequest(method, context.getResources().getString(R.string.BASE_URL)+url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response != null) {
                                try {
                                    Object json = new JSONTokener(response).nextValue();
                                    JSONObject jsonObject = null;
                                    if (json instanceof JSONObject) {
                                        jsonObject = new JSONObject(response);
                                    } else if (json instanceof JSONArray) {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("data", new JSONArray(response));
                                    }
                                    callback.onSuccess(jsonObject);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    BaseActivity.dismissProgressDialog();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    parseError(context, url, error, callback);
                }
            }) {
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
//                    if (SessionKeyController.getSessionKey() != null) {
//                        headers.put("session-key", SessionKeyController.getSessionKey());
//                    }
                    return headers;
                }
            };

            // Adding JsonObject request to request queue
            MainApplication.getInstance().addToRequestQueue(stringRequest, url);
        } else {
            BaseActivity.dismissProgressDialog();
            BaseActivity.showToast(context, context.getResources().getString(R.string.no_connection));
            getData(context, method, url, showProgress, callback);
        }
    }

    /**
     * Put / Post request
     * @param context :
     * @param url :
     * @param method :
     * @param postJson :
     * @param callback :
     */
    private static void postData(final Context context, final String url, int method, final JSONObject postJson, boolean showProgress, final VolleyCallback callback) {
        // Check if no view has focus:
        KeyboardUtils.closeKeyboard(context);

        if (NetworkUtils.isNetworkAvailable(context)) {
            if (showProgress) {
                BaseActivity.showProgressDialog(context);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, context.getResources().getString(R.string.BASE_URL) + url, postJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response != null) {
                                try {
                                    Object json = new JSONTokener(response.toString()).nextValue();
                                    JSONObject jsonObject = null;
                                    if (json instanceof JSONObject) {
                                        jsonObject = new JSONObject(response.toString());
                                    } else if (json instanceof JSONArray) {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("data", new JSONArray(response.toString()));
                                    }
                                    callback.onSuccess(jsonObject);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    BaseActivity.dismissProgressDialog();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    parseError(context, url, error, callback);
                }
            }
            ) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json");
//                    if (SessionKeyController.getSessionKey() != null) {
//                        headers.put("session-key", SessionKeyController.getSessionKey());
//                    }
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding JsonObject request to request queue
            MainApplication.getInstance().addToRequestQueue(jsonObjectRequest, url);

        } else {
            BaseActivity.dismissProgressDialog();
            BaseActivity.showToast(context, context.getResources().getString(R.string.no_connection));
        }
    }

    private static void parseError(Context context, String url, VolleyError error, VolleyCallback callback) {
        if (error instanceof TimeoutError) {
            BaseActivity.showToast(context, context.getString(R.string.timeout_error));
        } else if (error instanceof NoConnectionError) {
            BaseActivity.showToast(context, context.getString(R.string.connection_error));
        } else if (error instanceof ServerError) {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String json = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    callback.onError(new JSONObject(json));
                } catch (Exception e) {
                    BaseActivity.showToast(context, context.getString(R.string.server_error));
                    e.printStackTrace();
                }
            }
        } else if (error instanceof NetworkError) {
            BaseActivity.showToast(context, context.getString(R.string.no_connection));
        } else if (error instanceof ParseError) {
            System.out.println("Parse Error: "+ url +" "+error);
        }
        BaseActivity.dismissProgressDialog();
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject jsonObject);
        void onError(JSONObject jsonObject);
    }
}