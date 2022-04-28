package service;

import dao.UserDemoDao;
import dao.UserDemoDaoImpl;
import entity.Condition;
import entity.UserDemo;
import utils.PageBean;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDemoDao userDemoDao = new UserDemoDaoImpl();

    @Override
    public boolean addUser(UserDemo userDemo) throws Exception {

        return userDemoDao.insertUser(userDemo) > 0 ? true : false;
    }

    @Override
    public boolean inquireUser(String username, String pwd) throws Exception {
        return userDemoDao.selectUser(username, pwd) != null ? true : false;
    }

    @Override
    public List<UserDemo> findAllUser() throws Exception {

        return userDemoDao.selectAllUser();
    }

    @Override
    public boolean delUser(Integer id)  {
        try {
            return userDemoDao.deleteUser(id) > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDemo getUserByID(Integer id) {

        return userDemoDao.selectUserById(id);
    }

    @Override
    public boolean updateUserByID(UserDemo userDemo) {
        return userDemoDao.updateUser(userDemo) > 0 ?true :false;
    }

    @Override
    public List<UserDemo> findUserDemoBySome(Condition condition) {
        return userDemoDao.selectUserBySome(condition);
    }

    @Override
    public PageBean findUserDemoByPageBean(Condition condition, Integer p) {
        //设置 pageBean属性 对象的值
        PageBean pageBean = new PageBean();
        //设置总共多少条数据（要带查询条件的！）
        int totalCount = userDemoDao.selectCountUserDemo(condition);
        pageBean.setPageTotal(totalCount);
        //这是当前页面保存多少条数据
        pageBean.setPageSize(5);
        //设置当前页  注意 必须要按早设置完pageSize之后，才可以去设置页面数，否则会提前报空 空指针异常
        pageBean.setP(p);
        List<UserDemo> userDemos = userDemoDao.selectUserDemoByPageBean(condition,pageBean.getP(),pageBean.getPageSize());
        pageBean.setDataList(userDemos);



        return pageBean;
    }

    @Override
    public boolean existsUserName(String userName) {
        return userDemoDao.selectUserByUserName(userName) != null ? true : false;
    }
}
