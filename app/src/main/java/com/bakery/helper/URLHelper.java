package com.bakery.helper;

/**
 * Created by wangj on 10/17/15.
 */
public class URLHelper {
    public static String host = "192.168.1.154:62508";
    public static String LoginUrl = "http://"+ host +"/api/UserApi/Login";
    public static String UsersUrl = "http://"+ host +"/api/UserApi/GetUsers";
    public static String ClientsUrl = "http://"+ host +"/api/ClientApi/GetClients";
    public static String AddClientUrl =  "http://" + host + "/api/ClientApi/Add";
    public static String TasksUrl = "http://"+ host +"/api/TaskApi/GetTasks";
    public static String AssignTaskUrl =  "http://" + host + "/api/TaskApi/SetTask";
    public static String AddTaskUrl =  "http://" + host + "/api/TaskApi/Add";
}
