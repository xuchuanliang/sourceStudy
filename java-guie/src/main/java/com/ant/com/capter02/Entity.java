package com.test1.ant.com.capter02;

public class Entity implements Cloneable{
    private String name;
    private String gender;
    private Friend friend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", friend=" + friend +
                '}';
    }

    /**
     * 浅拷贝
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Entity clone() throws CloneNotSupportedException {
        Entity entity = (Entity) super.clone();
        entity.setFriend(friend.clone());
        return entity;
    }
}
