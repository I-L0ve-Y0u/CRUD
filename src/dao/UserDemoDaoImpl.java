package dao;

import entity.Condition;
import entity.UserDemo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DruidManger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDemoDaoImpl implements UserDemoDao {
    QueryRunner queryRunner = new QueryRunner(DruidManger.getDataSource());

    @Override
    public int insertUser(UserDemo userDemo) {

        String sql = " insert into userDemo values(null,?,?,?,?,?,?,?) ";
        Object[] parms = {userDemo.getUserName(), userDemo.getUserPwd(), userDemo.getSex(), userDemo.getChk()
                , userDemo.getSel(), userDemo.getAddress(), userDemo.getBirthday()};

        int i = 0;
        try {
            i = queryRunner.update(sql, parms);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;
    }

    @Override
    public UserDemo selectUser(String username, String pwd) throws Exception {
        String sql = " select * from userdemo where userName = ? and userPwd = ? ";
        Object[] parms = {username, pwd};
        UserDemo userDemo = queryRunner.query(sql, new BeanHandler<UserDemo>(UserDemo.class), parms);
        return userDemo;
    }

    @Override
    public List<UserDemo> selectAllUser() throws Exception {
        String sql = " select u.*,e.ename from userdemo u inner join  education e on u.sel = e.eid ";
        List<UserDemo> query = queryRunner.query(sql, new BeanListHandler<UserDemo>(UserDemo.class));
        return query;
    }

    @Override
    public int deleteUser(Integer id) {
        String sql = " delete from userdemo where id = ? ";


        try {
            return queryRunner.update(sql, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public UserDemo selectUserById(Integer id) {
        String sql = " select * from userdemo where id = ? ";
        try {
            return queryRunner.query(sql, new BeanHandler<UserDemo>(UserDemo.class), id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateUser(UserDemo userDemo) {
        String sql = " update userdemo set userName =? , userPwd = ? , sex = ? ,chk = ? , " +
                "sel = ? , address = ? ,birthday = ? where id = ? ";
        Object[] parms = {userDemo.getUserName(), userDemo.getUserPwd(), userDemo.getSex(), userDemo.getChk()
                , userDemo.getSel(), userDemo.getAddress(), userDemo.getBirthday(), userDemo.getId()};
        try {
            return queryRunner.update(sql, parms);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<UserDemo> selectUserBySome(Condition condition) {
        String searchUserName = condition.getSearchUserName();
        String eid = condition.getEid();
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        List<Object> params = new ArrayList<>();
        String sql = " select u.*,e.ename from userdemo u inner join  education e on u.sel = e.eid " +
                "where 1=1 ";
        if (searchUserName != null && !searchUserName.isEmpty()) {
            sql += " and u.userName like ? ";
            params.add("%" + searchUserName + "%");
        }
        if (eid != null && !eid.isEmpty()) {
            sql += " and e.eid = ? ";
            params.add(eid);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql += " and u.birthday >= ? ";
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " and u.birthday <= ? ";
            params.add(endDate);
        }
        sql += " order by id desc ";

        try {
            return queryRunner.query(sql, new BeanListHandler<UserDemo>(UserDemo.class), params.toArray());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int selectCountUserDemo(Condition condition ) {
        String searchUserName = condition.getSearchUserName();
        String eid = condition.getEid();
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        List<Object> params = new ArrayList<>();
        String sql = " select count(1) from userdemo u inner join  education e on u.sel = e.eid " +
                "where 1=1 ";
        if (searchUserName != null && !searchUserName.isEmpty()) {
            sql += " and u.userName like ? ";
            params.add("%" + searchUserName + "%");
        }
        if (eid != null && !eid.isEmpty()) {
            sql += " and e.eid = ? ";
            params.add(eid);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql += " and u.birthday >= ? ";
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " and u.birthday <= ? ";
            params.add(endDate);
        }
        sql += " order by id desc ";

        try {
            // 注意 这里类型转换的是 包装类long类型 需要转换
            Long query = (Long) queryRunner.query(sql, new ScalarHandler<>(), params.toArray());
            //下面返回需要掉方法返回int类型 intValue()
            return query.intValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<UserDemo> selectUserDemoByPageBean(Condition condition, Integer p, Integer pageSize) {
        String searchUserName = condition.getSearchUserName();
        String eid = condition.getEid();
        String startDate = condition.getStartDate();
        String endDate = condition.getEndDate();
        List<Object> params = new ArrayList<>();
        String sql = " select u.*,e.ename from userdemo u inner join  education e on u.sel = e.eid " +
                "where 1=1 ";
        if (searchUserName != null && !searchUserName.isEmpty()) {
            sql += " and u.userName like ? ";
            params.add("%" + searchUserName + "%");
        }
        if (eid != null && !eid.isEmpty()) {
            sql += " and e.eid = ? ";
            params.add(eid);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql += " and u.birthday >= ? ";
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " and u.birthday <= ? ";
            params.add(endDate);
        }
        sql += " order by id desc limit ?,? ";
        params.add((p-1)*pageSize);
        params.add(pageSize);

        try {

            return queryRunner.query(sql, new BeanListHandler<UserDemo>(UserDemo.class), params.toArray());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public UserDemo selectUserByUserName(String userName) {
        String sql = " select * from userdemo where userName = ? ";
        try {
            return queryRunner.query(sql,new BeanHandler<UserDemo>(UserDemo.class),userName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
