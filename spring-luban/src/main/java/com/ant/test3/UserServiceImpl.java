package com.ant.test3;

public class UserServiceImpl implements UserService{
    private OrgService orgService;
    @Override
    public void query() {

    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }
}
