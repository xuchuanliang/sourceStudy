package com.test1.ant.test3;

public class OrgServiceImpl implements OrgService{
    private UserService userService;
    @Override
    public void query() {

    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
