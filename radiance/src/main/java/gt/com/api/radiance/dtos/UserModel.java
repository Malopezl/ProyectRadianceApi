/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

/**
 *
 * @author malopez
 */
public class UserModel {

    private String userId;
    private String name;
    private String mail;
    private String phoneNumber;
    private String role;
    private String image;
    private String user;
    private SubscriptionModel subscription;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public SubscriptionModel getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionModel subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "UserModel{" + "userId=" + userId + ", name=" + name + ", mail=" + mail + ", phoneNumber=" + phoneNumber
                + ", role=" + role + ", image=" + image + ", user=" + user + ", subscription=" + subscription + '}';
    }

}
