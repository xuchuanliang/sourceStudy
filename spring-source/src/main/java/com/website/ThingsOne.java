package com.website;

import org.springframework.beans.factory.annotation.Required;

public class ThingsOne {
    private ThingsTwo thingsTwo;
    private ThingsThree thingsThree;

    public ThingsOne() {
    }

    public ThingsOne(ThingsTwo thingsTwo, ThingsThree thingsThree) {
        this.thingsTwo = thingsTwo;
        this.thingsThree = thingsThree;
    }
    public void test(){
        System.out.println(thingsTwo);
        System.out.println(thingsThree);
    }

    public ThingsTwo getThingsTwo() {
        return thingsTwo;
    }

    public void setThingsTwo(ThingsTwo thingsTwo) {
        this.thingsTwo = thingsTwo;
    }

    public ThingsThree getThingsThree() {
        return thingsThree;
    }

    public void setThingsThree(ThingsThree thingsThree) {
        this.thingsThree = thingsThree;
    }
}
