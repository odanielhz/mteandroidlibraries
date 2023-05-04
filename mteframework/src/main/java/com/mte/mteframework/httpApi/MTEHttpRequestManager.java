package com.mte.mteframework.httpApi;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class MTEHttpRequestManager
{

    RequestQueue requestQueue;
    Context mContext;
    MTEHttpRequestEvent listener;
    //==========================================================================================================================
    //==========================================================================================================================
    public MTEHttpRequestManager(Context context)
    {
        //initialize a new volley request
        requestQueue = Volley.newRequestQueue(context);
        mContext = context;
    }
    //==========================================================================================================================
    //==========================================================================================================================
    public MTEHttpRequestManager(Context context, MTEHttpRequestEvent eventlistener)
    {
        //initialize a new volley request
        requestQueue = Volley.newRequestQueue(context);
        mContext = context;
        listener = eventlistener;
    }
    //==========================================================================================================================
    //==========================================================================================================================
    public void setEventListener(MTEHttpRequestEvent eventListener)
    {
        listener = eventListener;
    }
    //==========================================================================================================================
    //==========================================================================================================================
    public void makePostRequest(int RequestId,String api_url, JSONObject jsonbody)
    {
        try
        {
            final String requestBody = jsonbody.toString();
            StringRequest sr =  new StringRequest(Request.Method.POST, api_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if(listener!=null)
                            {
                                listener.onErrorResponse(RequestId,error);
                                listener.onRequestResponseFailed(RequestId, error.toString());
                            }

                        }
                    }){
                //===================================================================
                //===================================================================
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                //===================================================================
                //===================================================================
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (Exception ex1) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                //===================================================================
                //===================================================================

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response)
                {
                    String responseString = "";
                    if (response != null)
                    {
                        if(response.statusCode == 200)
                        {
                            //=========================================================================================
                            //Success Response
                            if(listener!=null)
                            {
                                try
                                {
                                    listener.onRequestResponseSuccess(RequestId, new JSONArray(new String(response.data, StandardCharsets.UTF_8)));
                                }
                                catch (JSONException e)
                                {
                                    return  Response.error(new VolleyError(e.toString()));
                                }
                            }
                            //=========================================================================================
                        }



                        //Return
                    }

                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }


                //===================================================================
                //===================================================================


                //===================================================================
                //===================================================================


            };

            //=============================================
            //Make the request
            requestQueue.add(sr);

        }
        catch (Exception ex)
        {
            if(listener!=null)
            {
                listener.onException(RequestId, "onException. "+ex.toString());
                listener.onRequestResponseFailed(RequestId, "onException. "+ex.toString());
            }
        }
    }




}
