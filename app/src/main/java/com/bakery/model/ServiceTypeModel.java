package com.bakery.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangj on 10/24/15.
 */
public class ServiceTypeModel {

    public ServiceTypeModel()
    {}

    public ServiceTypeModel(String id, String subject)
    {
        this._id = id;
        this._subject = subject;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    }

    private String _id;
    private String _subject;

    @Override
    public String toString() {
        return _subject.toString();
    }

    public void set_serviceTypes(ArrayList<ServiceTypeModel> _serviceTypes) {
        this._serviceTypes = _serviceTypes;
    }

    public ArrayList<ServiceTypeModel> get_serviceTypes() {
        if(this._serviceTypes == null)
        {
            this._serviceTypes = new ArrayList<>();
            this._serviceTypes.add(new ServiceTypeModel("2", "安装"));
            this._serviceTypes.add(new ServiceTypeModel("3", "维修"));
            this._serviceTypes.add(new ServiceTypeModel("4", "送货"));
            this._serviceTypes.add(new ServiceTypeModel("5", "业务"));
            this._serviceTypes.add(new ServiceTypeModel("6", "加粉"));
        }
        return _serviceTypes;
    }
    private ArrayList<ServiceTypeModel> _serviceTypes;
}
