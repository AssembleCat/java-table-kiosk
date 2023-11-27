package common;

import common.master.User;

public class UserAccount {
    public int id;
    public String name;
    public String userEmail;
    public String userPassword;

    public UserAccount() {
    }

    public Boolean isUserSet() {
        return id != 0;
    }

    public void setUserAccountFromUser(User user) {
        id = user.id;
        name = user.name;
        userEmail = user.email;
        userPassword = user.password;
    }
}
