package com.misskii.javatodolistapp;

import com.misskii.javatodolistapp.Util.SSLUtil;

public class Main {
    public static void main(String[] args) {
        SSLUtil.disableCertificateValidation();
        Application.main(args);
    }
}
