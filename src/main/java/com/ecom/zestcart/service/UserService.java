package com.ecom.zestcart.service;

import com.ecom.zestcart.model.User;

public interface UserService {
    User registerUser(User user);
    User findByUsername(String username);
}
