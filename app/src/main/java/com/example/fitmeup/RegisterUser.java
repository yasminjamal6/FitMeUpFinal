package com.example.fitmeup;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "register_users")
public class RegisterUser {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    private String birthDate;
    private String gender;
    private String fitnessGoal;
    private String securityQuestion;
    private String securityAnswer;
    private String weight;
    private String height;


    public RegisterUser(String username, String email, String confirmEmail, String password, String confirmPassword,
                        String birthDate, String gender, String fitnessGoal, String securityQuestion,
                        String securityAnswer, String weight, String height) {
        this.username = username;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.birthDate = birthDate;
        this.gender = gender;
        this.fitnessGoal = fitnessGoal;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.weight = weight;
        this.height = height;

    }

    public RegisterUser() {
    }

    // Getters and setters for fitnessGoal
    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
