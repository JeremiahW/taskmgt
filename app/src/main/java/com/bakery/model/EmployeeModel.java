package com.bakery.model;

/**
 * Created by wangj on 10/23/15.
 */
public class EmployeeModel {
    private String _phone;

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_chinese() {
        return _chinese;
    }

    public void set_chinese(String _chinese) {
        this._chinese = _chinese;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    private String _id;
    private String _chinese;
    private String _username;

    @Override
    public String toString()
    {
        return this._chinese;
    }
}
