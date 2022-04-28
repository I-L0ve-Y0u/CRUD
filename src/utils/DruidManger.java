package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

public class DruidManger {
    //定义DataSource对象 - java管理连接池
    private static DataSource dataSource = null ;
    // static静态代码块 再创建类时就会创建，所以执行的优先级会很高
    static {
        //创建Properties类的对象，用来操作.properties配置文件
        Properties properties = new Properties();
        String path = DruidManger.class.getClassLoader().getResource("druid.properties").getPath();
        try {
            properties.load(new FileInputStream(path));
        //通过druid的工厂类来创建DataSource对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //获取DataSource对象
    public static DataSource getDataSource(){
        return dataSource;
    }


}
