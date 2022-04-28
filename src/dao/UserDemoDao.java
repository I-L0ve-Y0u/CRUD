package dao;

import entity.Condition;
import entity.UserDemo;

import java.util.List;

public interface UserDemoDao {

    public int insertUser(UserDemo userDemo) throws Exception;

    public UserDemo selectUser(String username,String pwd) throws Exception;

    public List<UserDemo> selectAllUser() throws Exception;

    public int deleteUser(Integer id) throws Exception;

    UserDemo selectUserById(Integer id);

    public int updateUser(UserDemo userDemo) ;

    List<UserDemo> selectUserBySome(Condition condition);

    int selectCountUserDemo(Condition condition);


    List<UserDemo> selectUserDemoByPageBean(Condition condition, Integer p, Integer pageSize);

    UserDemo selectUserByUserName(String userName);
}
