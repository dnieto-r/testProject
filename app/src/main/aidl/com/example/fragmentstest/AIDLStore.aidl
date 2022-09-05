package com.example.fragmentstest;

import com.example.fragmentstest.models.User;
import com.example.fragmentstest.models.CustomCallback;

interface AIDLStore {
    void getMobileContacts(in CustomCallback output);
}
