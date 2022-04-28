package dao;

import entity.Education;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidManger;

import java.sql.SQLException;
import java.util.List;

public class EducationDaoImpl implements EducationDao {
    QueryRunner queryRunner = new QueryRunner(DruidManger.getDataSource());
    @Override
    public List<Education> selectAllEducation() {
        String sql = " select * from education ";
        try {
            return queryRunner.query(sql,new BeanListHandler<Education>(Education.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
