package com.cadastramento.radiador.service.user;

public interface UserService {
    void changePassword(String username, String currentPassword, String newPassword);
}