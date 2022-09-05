package com.example.fragmentstest.models;

import com.example.fragmentstest.models.User;

interface CustomCallback {

    void onResult(inout List<User> result);
}