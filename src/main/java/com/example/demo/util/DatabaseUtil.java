package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Created by qwe on 2019/8/10.
 */
@Component
public class DatabaseUtil {


    public static Connection getConnection(String url, String username,String password){
        Connection conn = null;
        try {
            conn= DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement stat){
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement pstat){
        if(pstat!=null){
            try {
                pstat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
