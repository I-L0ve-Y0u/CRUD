package service;

import entity.Condition;
import entity.UserDemo;
import utils.PageBean;

import java.util.List;

public interface UserService {

    public boolean addUser(UserDemo userDemo) throws Exception;

    public boolean inquireUser(String username,String pwd) throws Exception;

    public List<UserDemo> findAllUser() throws Exception ;

    public boolean delUser(Integer id)  ;


    UserDemo getUserByID(Integer id);
    public boolean updateUserByID(UserDemo userDemo)  ;

    List<UserDemo> findUserDemoBySome(Condition condition);

    PageBean findUserDemoByPageBean(Condition condition,Integer p);

    boolean existsUserName(String userName);
}
