package org.acme.entities;

import java.util.Set;

public class Role {

    private static int n=0;
    private int id;

    private String roleName;

    private Set<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role(){
        id=n;
        n++;
    }
}
