package services;

import dao.JPA;
import dao.impl.UserGroupJPA;
import domain.UserGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class UserGroupService implements Serializable {
    @Inject
    @JPA
    private UserGroupJPA dao;

    public UserGroup addUserGroup(UserGroup group) {
        return dao.create(group);
    }
}
