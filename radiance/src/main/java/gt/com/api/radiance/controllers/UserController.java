/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.Subscription;
import gt.com.api.radiance.dtos.SubscriptionModel;
import gt.com.api.radiance.dtos.SubscriptionTypeModel;
import gt.com.api.radiance.dtos.UserModel;
import gt.com.api.radiance.entities.Payment;
import gt.com.api.radiance.entities.User;
import gt.com.api.radiance.helper.FormatDate;
import gt.com.api.radiance.helper.Roles;
import gt.com.api.radiance.queries.UserQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    public UserController() {
    }

    public List<UserModel> getUsers(String filter) {
//        UserPage users = new UserPage();
        List<UserModel> users = new ArrayList();
//        List<UserModel> list = new ArrayList();
        List<User> userList = UserQuery.getUserList();
        if (userList == null) {
            return null;
        }
        userList.stream().map(user -> {
            UserModel userModel = new UserModel();
            userModel.setUserId(user.getId().toString());
            userModel.setName(user.getName());
            userModel.setMail(user.getMail());
            userModel.setPhoneNumber(user.getPhoneNumber());
            userModel.setRole(user.getRole());
            userModel.setImage(user.getImage());
            userModel.setUser(user.getUser());
            userModel.setIsVerified(user.getIsVerified());
            //Fill Subscription model
            SubscriptionModel subscription = new SubscriptionModel();
            subscription.setFinalizationDate(FormatDate.convertTime(
                    user.getSubscription().getFinalizationDate()));
            subscription.setStatus(user.getSubscription().getStatus());
            //Fill SubscriptionType model
            SubscriptionTypeModel type = new SubscriptionTypeModel();
            type.setSubscriptionTypeId(user.getSubscriptionType().getId().toString());
            type.setName(user.getSubscriptionType().getName());
            type.setPrice(user.getSubscriptionType().getPrice());
            type.setDescription(user.getSubscriptionType().getDescription());
            subscription.setSubscriptionType(type);
            userModel.setSubscription(subscription);
            return userModel;
        }).map(userModel -> {
            userModel.setPassword("");
            return userModel;
        }).forEachOrdered(userModel -> {
            users.add(userModel);
        });
//        users.setUsers(usersModel);
//        users.setIsFirstPage(page == 1);
//        BigDecimal pages = BigDecimal.valueOf(usersModel.size())
//                .divide(BigDecimal.valueOf(size), 0, RoundingMode.UP);
//        Long totalPages = Long.valueOf(String.valueOf(pages));
//        int skip = (int) ((page - 1) * size);
//        int limit = size.intValue();
//        for (int i = skip; i < usersModel.size(); i++) {
//            list.add(usersModel.get(i));
//            if (list.size() == limit) {
//                break;
//            }
//        }
//        users.setIsLastPage(page.longValue() == totalPages.longValue());
//        users.setNumber(page);
//        users.setNumberOfElements(Long.valueOf(list.size()));
//        users.setIsPreviousPageAvailable(page > 1);
//        users.setSize(size);
//        users.setTotalElements(Long.valueOf(usersModel.size()));
//        users.setTotalPages(totalPages);
        return users;
    }

    public UserModel getUser(String username) {
//        ObjectId userId = new ObjectId(id);
        User user = UserQuery.findUser(username);
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getId().toString());
        userModel.setName(user.getName());
        userModel.setMail(user.getMail());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setRole(user.getRole());
        userModel.setImage(user.getImage());
        userModel.setUser(user.getUser());
        userModel.setPassword("");
        userModel.setIsVerified(user.getIsVerified());
        if (user.getSubscription() != null) {
            SubscriptionModel subscription = new SubscriptionModel();
            subscription.setFinalizationDate(FormatDate.convertTime(
                    user.getSubscription().getFinalizationDate()));
            subscription.setStatus(user.getSubscription().getStatus());
            //Get subscriptionType
            SubscriptionTypeModel type = SubscriptionTypeController.getSubscriptionType(
                    user.getSubscription().getSubscriptionTypeId().toString());
            subscription.setSubscriptionType(type);
            userModel.setSubscription(subscription);
        }
        return userModel;
    }

    public UserModel saveUser(UserModel userModel) {
        User user = new User();
        user.setName(userModel.getName());
        user.setMail(userModel.getMail());
        user.setPhoneNumber(userModel.getPhoneNumber());
        if (userModel.getSubscription().getSubscriptionType().getName().equals("Base")) {
            user.setRole(Roles.Role.Lector.toString());
            userModel.setRole(Roles.Role.Lector.toString());
        } else if (userModel.getSubscription().getSubscriptionType().getName().equals("Advanced")) {
            user.setRole(Roles.Role.Editor.toString());
            userModel.setRole(Roles.Role.Editor.toString());
        }
        user.setImage(userModel.getImage());
        user.setUser(userModel.getUser());
        user.setPassword(userModel.getPassword());
        user.setIsVerified(Boolean.TRUE);
        user.setIsActive(Boolean.TRUE);
        user.setIsDelete(Boolean.FALSE);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date date = c.getTime();
        Long l = date.getTime();
        Subscription subscription = new Subscription();
        subscription.setFinalizationDate(l.toString());
        subscription.setStatus(Boolean.TRUE);
        subscription.setSubscriptionTypeId(new ObjectId(
                userModel.getSubscription().getSubscriptionType().getSubscriptionTypeId()));
        user.setSubscription(subscription);
        ObjectId userId = UserQuery.saveUser(user);
        if (userId == null) {
            LOGGER.error("Failed to save user");
            return null;
        }
        Payment payment = new Payment();
        payment.setDate(String.valueOf(System.currentTimeMillis()));
        payment.setAmount(userModel.getSubscription().getSubscriptionType().getPrice());
        payment.setUserId(userId);
        if (!PaymentController.savePayment(payment)) {
            UserQuery.deleteUser(userId);
            LOGGER.error("Failed to save payment, deleting user");
            return null;
        }
        userModel.setIsVerified(Boolean.TRUE);
        userModel.getSubscription().setFinalizationDate(l.toString());
        userModel.getSubscription().setStatus(Boolean.TRUE);
        userModel.setUserId(userId.toString());
        return userModel;
    }

    public boolean verifyUserExists(String id) {
        ObjectId userId = new ObjectId(id);
        return UserQuery.verifyUserExists(userId);
    }

    public UserModel updateUser(String id, UserModel userModel) {
        ObjectId userId = new ObjectId(id);
        User user = new User();
        user.setName(userModel.getName());
        user.setMail(userModel.getMail());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setImage(userModel.getImage());
        user = UserQuery.updateUser(user, userId);
        if (user == null) {
            LOGGER.error("Unable to update the user");
            return null;
        }
        return userModel;
    }

    public Boolean deleteUser(String id) {
        ObjectId userId = new ObjectId(id);
        return UserQuery.deleteUser(userId);
    }

    public UserModel findUsername(String username) {
        User user = UserQuery.findUser(username);
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getId().toString());
        userModel.setName(user.getName());
        userModel.setMail(user.getMail());
        userModel.setImage(user.getImage());
        userModel.setUser(user.getUser());
        return userModel;
    }

    public boolean verifyUsername(String username) {
        return UserQuery.verifyUsername(username);
    }

    public boolean cancelSubscription(String id, UserModel userModel) {
        ObjectId userId = new ObjectId(id);
        if (!UserQuery.cancelSubscription(userId)) {
            return false;
        }
        userModel.getSubscription().setStatus(Boolean.FALSE);
        return true;
    }

}
