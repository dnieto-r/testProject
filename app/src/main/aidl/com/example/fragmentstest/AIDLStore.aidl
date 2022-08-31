// AIDLStore.aidl
package com.example.fragmentstest;

import com.example.fragmentstest.models.User;

interface AIDLStore {
    List<User> getInternalContacts();
}