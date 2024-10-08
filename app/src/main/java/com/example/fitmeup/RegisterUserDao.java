package com.example.fitmeup;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RegisterUserDao {

    @Insert
    void insert(RegisterUser registerUser);

    @Query("SELECT * FROM register_users WHERE username = :username AND password = :password LIMIT 1")
    RegisterUser getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM register_users WHERE email = :email AND password = :password LIMIT 1")
    RegisterUser getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM register_users WHERE email = :email LIMIT 1")
    RegisterUser getUserByEmail(String email);

    @Query("SELECT * FROM register_users WHERE securityQuestion = :securityQuestion AND securityAnswer = :securityAnswer LIMIT 1")
    RegisterUser getUserBySecurityQuestionAndAnswer(String securityQuestion, String securityAnswer);

    @Update
    void update(RegisterUser registerUser);

    @Query("SELECT * FROM register_users WHERE id = :id")
    RegisterUser getUserById(String id);


    @Query("SELECT * FROM register_users")
    List<RegisterUser> getAllUsers();

    @Query("UPDATE register_users SET weight = :newWeight WHERE id = :userId")
    void updateUserWeight(int userId, String newWeight);

    @Query("UPDATE register_users SET height = :newHeight WHERE id = :userId")
    void updateUserHeight(int userId, String newHeight);



}