package com.yt1.twitterstock;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 2/16/2017.
 */

public class RegisterRequest extends StringRequest
{

      private static final String REGISTER_REQUEST_URL = "http://proj-309-yt-1.cs.iastate.edu/register.php";
        private Map<String,String> params;
    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params= new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("age", age + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
