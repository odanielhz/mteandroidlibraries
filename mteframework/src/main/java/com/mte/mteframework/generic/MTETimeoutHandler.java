package com.mte.mteframework.generic;

import android.os.Handler;
import android.os.Looper;

public class MTETimeoutHandler
{


    private int targetms = 0;
    private boolean Expired = false;
    private boolean Running = false;
    private final Handler tmrhandler =  new Handler(Looper.getMainLooper());
    private MTETimeoutHandlerListener listener;

    //################################################################################
    //################################################################################
    public interface MTETimeoutHandlerListener
    {
        void onTimedOut();
    }

    //################################################################################
    //################################################################################
    public MTETimeoutHandler()
    {}

    //################################################################################
    //################################################################################
    public MTETimeoutHandler(int timeoutms)
    {
        Expired = false;
        targetms = timeoutms;
    }
    //################################################################################
    //################################################################################
    public void setListener(MTETimeoutHandlerListener listener)
    {
        this.listener = listener;
    }
    //################################################################################
    //################################################################################
    //################################################################################
    public void start()
    {
        if(Running)
        {
            //Stop Previous handler
            tmrhandler.removeCallbacksAndMessages(null);
        }
        tmrhandler.postDelayed(timedout, targetms);
        Running = true;
    }
    //################################################################################
    //################################################################################
    public void stop()
    {
        //Stop Previous handler
        tmrhandler.removeCallbacksAndMessages(null);
        Expired=false;
    }
//################################################################################
    //################################################################################
    public void set(int timeoutms)
    {
        if(Running)
        {
            //Stop Previous handler
            tmrhandler.removeCallbacksAndMessages(null);
        }
        targetms=timeoutms;
        tmrhandler.postDelayed(timedout, timeoutms);
        Running= true;
        Expired=false;
    }

    //################################################################################
    //################################################################################
    final Runnable timedout =  new Runnable() {
        @Override
        public void run()
        {
                //Timed has expired
            Expired = true;
            Running = false;

            //check if listener has been set
            if(listener !=null)
            {
                listener.onTimedOut();
            }

        }
    };
    //################################################################################
    //################################################################################
    public boolean isExpired()
    {
        return Expired;
    }
    //################################################################################
    //################################################################################
    public int getTargetms() {
        return targetms;
    }

    //################################################################################
    //################################################################################


    //################################################################################
    //################################################################################

}
