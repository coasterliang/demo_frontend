package org.example.hsbcbookfrontend.network.model;

import com.google.gson.annotations.SerializedName;

public class OperationModel {
    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private Object msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
