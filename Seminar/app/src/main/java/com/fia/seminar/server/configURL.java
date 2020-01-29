package com.fia.seminar.server;

public class configURL {
    public static String baseUrl = "http://192.168.43.71:5000";
//    public static String baseUrl = "http:// 192.168.1.5:5000";

    public static String Login = baseUrl + "/login/login";
    public static String Register = baseUrl + "/login/daftar";

    public static String InputSeminar = baseUrl + "/buatseminar/inputseminar";
    public static String DataSeminar = baseUrl + "/buatseminar/getAllseminar";

    public static String Delete = baseUrl + "/buatseminar/delete/";

    public static String Update = baseUrl + "/buatseminar/edit/";

    public static String InputPeserta = baseUrl + "/pesertaseminar/insertpeserta";
}
