package com.bohdan.JwtTokenGeneratorProj.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError
{
    private int status;
    private String msg;
    private Date timestamp;

    public AppError(int status, String msg)
    {
        this.status = status;
        this.msg = msg;
        this.timestamp = new Date();
    }
}
