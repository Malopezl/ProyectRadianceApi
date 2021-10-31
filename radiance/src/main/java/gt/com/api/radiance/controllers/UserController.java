/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.UserModel;
import gt.com.api.radiance.dtos.UserPage;
import gt.com.api.radiance.entities.User;
import gt.com.api.radiance.helper.Roles;
import gt.com.api.radiance.queries.UserQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
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

    public UserPage getUserPage(Long size, Long page, String filter) {
        UserPage users = new UserPage();
        List<UserModel> usersModel = new ArrayList();
        List<UserModel> list = new ArrayList();
        Pattern regexp = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        List<User> userList = UserQuery.getUserList(regexp);
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
            userModel.getSubscription().setFinalizationDate(new Date(Long.valueOf(
                    user.getSubscription().getFinalizationDate())).toString());
            userModel.getSubscription().setSubscriptionType(user.getSubscriptionType());
            return userModel;
        }).map(userModel -> {
            userModel.setPassword("");
            return userModel;
        }).forEachOrdered(userModel -> {
            usersModel.add(userModel);
        });
        users.setUsers(usersModel);
        users.setIsFirstPage(page == 1);
        BigDecimal pages = BigDecimal.valueOf(usersModel.size())
                .divide(BigDecimal.valueOf(size), 0, RoundingMode.UP);
        Long totalPages = Long.valueOf(String.valueOf(pages));
        int skip = (int) ((page - 1) * size);
        int limit = size.intValue();
        for (int i = skip; i < usersModel.size(); i++) {
            list.add(usersModel.get(i));
            if (list.size() == limit) {
                break;
            }
        }
        users.setIsLastPage(page.longValue() == totalPages.longValue());
        users.setNumber(page);
        users.setNumberOfElements(Long.valueOf(list.size()));
        users.setIsPreviousPageAvailable(page > 1);
        users.setSize(size);
        users.setTotalElements(Long.valueOf(usersModel.size()));
        users.setTotalPages(totalPages);
        return users;
    }

    public UserModel getUser(String id) {
        ObjectId userId = new ObjectId(id);
        User user = UserQuery.findUser(userId);
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setUserId(userId.toString());
        userModel.setName(user.getName());
        userModel.setMail(user.getMail());
        userModel.setPhoneNumber(user.getPhoneNumber());
        userModel.setRole(user.getRole());
        userModel.setImage(user.getImage());
        userModel.setUser(user.getUser());
        userModel.setPassword("");
        userModel.setIsVerified(user.getIsVerified());
        if (user.getSubscription() != null) {
            userModel.getSubscription().setFinalizationDate(new Date(Long.valueOf(
                    user.getSubscription().getFinalizationDate())).toString());
            userModel.getSubscription().setStatus(user.getSubscription().getStatus());
            userModel.getSubscription().setSubscriptionType(SubscriptionTypeController.getSubscriptionType(
                    user.getSubscription().getSubscriptionTypeId().toString()));
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
        } else if (userModel.getSubscription().getSubscriptionType().getName().equals("Base")) {
            user.setRole(Roles.Role.Editor.toString());
        }
        user.setImage(userModel.getImage());
        user.setUser(userModel.getUser());
        user.setPassword(userModel.getPassword());
        user.setIsVerified(Boolean.FALSE);
        user.setIsDelete(Boolean.FALSE);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date date = c.getTime();
        Long l = date.getTime();
        user.getSubscription().setFinalizationDate(l.toString());
        user.getSubscription().setStatus(Boolean.TRUE);
        user.getSubscription().setSubscriptionTypeId(userModel.getSubscription().getSubscriptionType().getId());
        ObjectId userId = UserQuery.saveUser(user);
        if (userId == null) {
            LOGGER.error("Failed to save user");
            return null;
        }
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
        user.setUser(userModel.getUser());
        user.setPassword(userModel.getPassword());
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

}
