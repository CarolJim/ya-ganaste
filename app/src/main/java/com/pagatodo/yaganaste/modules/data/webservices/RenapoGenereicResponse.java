package com.pagatodo.yaganaste.modules.data.webservices;

import com.google.gson.annotations.SerializedName;

public class RenapoGenereicResponse
{
    @SerializedName("success")
    private String success;

    @SerializedName("reasonPhrase")
    private String reasonPhrase;

    @SerializedName("responseMessage")
    private String responseMessage;

    @SerializedName("body")
    private Body body;

    @SerializedName("statusCode")
    private String statusCode;

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public String getReasonPhrase ()
    {
        return reasonPhrase;
    }

    public void setReasonPhrase (String reasonPhrase)
    {
        this.reasonPhrase = reasonPhrase;
    }

    public String getResponseMessage ()
    {
        return responseMessage;
    }

    public void setResponseMessage (String responseMessage)
    {
        this.responseMessage = responseMessage;
    }

    public Body getBody ()
    {
        return body;
    }

    public void setBody (Body body)
    {
        this.body = body;
    }

    public String getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (String statusCode)
    {
        this.statusCode = statusCode;
    }

    @Override
    public String toString()
    {
        return "RenapoGenericResponse [success = "+success+", reasonPhrase = "+reasonPhrase+", responseMessage = "+responseMessage+", body = "+body+", statusCode = "+statusCode+"]";
    }
}
