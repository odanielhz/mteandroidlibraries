package com.mte.mteframework.httpApi;

import com.android.volley.VolleyError;
import org.json.JSONArray;

public interface MTEHttpRequestEvent
{


    public void onException(int requestid, String errorString);
    public void onErrorResponse(int requestid,VolleyError error);
    public void onRequestResponseSuccess(int requestid, JSONArray responsearray);
    public void onRequestResponseFailed(int requestid,String responsestring);
}
