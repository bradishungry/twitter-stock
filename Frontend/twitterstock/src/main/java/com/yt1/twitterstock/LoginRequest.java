package com.yt1.twitterstock;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 2/20/2017.
 */

public class LoginRequest extends StringRequest
{

    private static final String LOGIN_REQUEST_URL = "http://proj-309-yt-1.cs.iastate.edu/Dlogin.php";
    private Map<String,String> params;
    public LoginRequest( String username,  String password, Response.Listener<String> listener)
    {
        super(Method.POST , LOGIN_REQUEST_URL, listener, null);
        params= new HashMap<>();

        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
