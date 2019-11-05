package com.example.demo;

import com.example.demo.util.ConstantUtil;
import com.example.demo.util.DatabaseUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019-11-01 10:35
 **/
@Configuration
public class InitDatas {

    @Value("${spring.datasource.jdbc-url}")
    public String url;

    @Value("${spring.datasource.username}")
    public String username;

    @Value("${spring.datasource.password}")
    public String password;

    @Autowired
    private RepositoryService repositoryService;



    /**
     *初始化人员数据
     */
    @Bean(name = "initUser")
    public String initUser(){
        String sql = "select count(*) from user";
        String sql2 = "INSERT INTO user VALUES (1, 'admin', null, null, '123', 0, 'admin');\n" +
                "INSERT INTO user VALUES (2, '张三', 1, '开发组', '123', 0, '张三002');\n" +
                "INSERT INTO user VALUES (3, '李四', 1, '开发组', '123', 0, '李四003');\n" +
                "INSERT INTO user VALUES (4, '王五', 2, '测试组', '123', 0, '王五004');\n" +
                "INSERT INTO user VALUES (5, '赵六', 3, '人事部', '123', 0, '赵六005');\n" +
                "INSERT INTO user VALUES (6, '李白', 3, '人事部', '123', 0, '李白006');";
        String[] sqlArr = sql2.split(";");
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";

    }

    /**
     * 初始化角色数据
     * @return
     */
    @Bean(name = "initRole")
    public String initRole(){
        String sql = "select count(*) from role ";
        String sql2 = "INSERT INTO role VALUES (1, '管理员', '管理员', 'admin');\n" +
                "INSERT INTO role VALUES (2, '员工', '员工', 'employee');\n" +
                "INSERT INTO role VALUES (3, '项目经理', '项目经理', 'manager');\n" +
                "INSERT INTO role VALUES (4, '总经理', '总经理', 'boss');\n" +
                "INSERT INTO role VALUES (5, '人事部经理', '人事部经理', 'manager');";
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        String[] sqlArr = sql2.split(";");
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    /**
     * 初始化人员-角色中间表
     */
    @Bean(name = "initUserRole")
    public String initUserRole(){
        String sql = "select count(*) from user_role ";
        String sql2 = "INSERT INTO user_role VALUES (6, 5);\n" +
                "INSERT INTO user_role VALUES (2, 2);\n" +
                "INSERT INTO user_role VALUES (3, 3);\n" +
                "INSERT INTO user_role VALUES (4, 4);\n" +
                "INSERT INTO user_role VALUES (5, 2);\n" +
                "INSERT INTO user_role VALUES (1, 1);";
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        String[] sqlArr = sql2.split(";");
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }



    /**
     * 初始化权限数据
     * @return
     */
    @Bean(name = "initPermission")
    public String initPermission(){
        String sql = "select count(*) from permission ";
        String sql2 = "INSERT INTO permission VALUES (1, '1', '权限管理', null, 'permission', null);\n" +
                "INSERT INTO permission VALUES (2, '2', '系统管理', 1, 'system', null);\n" +
                "INSERT INTO permission VALUES (3, '3', '用户管理', 2, 'user:management', null);\n" +
                "INSERT INTO permission VALUES (5, null, '用户查询', 3, 'user:query', null);\n" +
                "INSERT INTO permission VALUES (6, null, '用户添加', 3, 'user:add', null);\n" +
                "INSERT INTO permission VALUES (7, null, '用户修改', 3, 'user:update', null);\n" +
                "INSERT INTO permission VALUES (8, null, '用户删除', 3, 'user:delete', null);\n" +
                "INSERT INTO permission VALUES (9, null, '角色管理', 2, 'role:management', null);\n" +
                "INSERT INTO permission VALUES (10, null, '角色查询', 9, 'role:query', null);\n" +
                "INSERT INTO permission VALUES (11, null, '角色添加', 9, 'role:add', null);\n" +
                "INSERT INTO permission VALUES (12, null, '角色修改', 9, 'role:update', null);\n" +
                "INSERT INTO permission VALUES (13, null, '角色删除', 9, 'role:delete', null);\n" +
                "INSERT INTO permission VALUES (14, null, '部门管理', 2, 'department:management', null);\n" +
                "INSERT INTO permission VALUES (15, null, '部门添加', 14, 'department:add', null);\n" +
                "INSERT INTO permission VALUES (16, null, '部门修改', 14, 'department:update', null);\n" +
                "INSERT INTO permission VALUES (17, null, '部门删除', 14, 'department:delete', null);\n" +
                "INSERT INTO permission VALUES (18, null, '部门查询', 14, 'department:query', null);\n" +
                "INSERT INTO permission VALUES (19, null, '角色导出', 9, 'role:export', null);\n" +
                "INSERT INTO permission VALUES (20, null, '分配权限', 9, 'role:allotPermission', null);\n" +
                "INSERT INTO permission VALUES (21, null, '用户分配角色', 3, 'user:allotRole', null);\n" +
                "INSERT INTO permission VALUES (22, null, '部门导出', 14, 'department:export', null);\n" +
                "INSERT INTO permission VALUES (23, null, '用户导出', 3, 'user:export', null);";
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        String[] sqlArr = sql2.split(";");
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    /**
     * 初始化角色--权限数据
     * @return
     */
    @Bean(name = "initRolePermission")
    public String initRolePermission(){
        String sql = "select count(*) from role_permission ";
        String sql2 = "INSERT INTO role_permission VALUES (197, 18, 1);\n" +
                "INSERT INTO role_permission VALUES (196, 17, 1);\n" +
                "INSERT INTO role_permission VALUES (195, 16, 1);\n" +
                "INSERT INTO role_permission VALUES (194, 15, 1);\n" +
                "INSERT INTO role_permission VALUES (193, 14, 1);\n" +
                "INSERT INTO role_permission VALUES (192, 20, 1);\n" +
                "INSERT INTO role_permission VALUES (191, 19, 1);\n" +
                "INSERT INTO role_permission VALUES (190, 13, 1);\n" +
                "INSERT INTO role_permission VALUES (189, 12, 1);\n" +
                "INSERT INTO role_permission VALUES (188, 11, 1);\n" +
                "INSERT INTO role_permission VALUES (187, 10, 1);\n" +
                "INSERT INTO role_permission VALUES (186, 9, 1);\n" +
                "INSERT INTO role_permission VALUES (185, 23, 1);\n" +
                "INSERT INTO role_permission VALUES (184, 21, 1);\n" +
                "INSERT INTO role_permission VALUES (183, 8, 1);\n" +
                "INSERT INTO role_permission VALUES (182, 7, 1);\n" +
                "INSERT INTO role_permission VALUES (153, 10, 5);\n" +
                "INSERT INTO role_permission VALUES (152, 9, 5);\n" +
                "INSERT INTO role_permission VALUES (151, 5, 5);\n" +
                "INSERT INTO role_permission VALUES (150, 3, 5);\n" +
                "INSERT INTO role_permission VALUES (149, 2, 5);\n" +
                "INSERT INTO role_permission VALUES (148, 1, 5);\n" +
                "INSERT INTO role_permission VALUES (154, 14, 5);\n" +
                "INSERT INTO role_permission VALUES (155, 18, 5);\n" +
                "INSERT INTO role_permission VALUES (181, 6, 1);\n" +
                "INSERT INTO role_permission VALUES (180, 5, 1);\n" +
                "INSERT INTO role_permission VALUES (179, 3, 1);\n" +
                "INSERT INTO role_permission VALUES (178, 2, 1);\n" +
                "INSERT INTO role_permission VALUES (177, 1, 1);\n" +
                "INSERT INTO role_permission VALUES (198, 22, 1);";
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        String[] sqlArr = sql2.split(";");
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    /**
     * 初始化部门数据
     * @return
     */
    @Bean(name = "initDepartment")
    public String initDepartment(){
        String sql = "select count(*) from department ";
        String sql2 = "INSERT INTO department VALUES (1, '开发组', '开发组');\n" +
                "INSERT INTO department VALUES (2, '测试组', '测试组');\n" +
                "INSERT INTO department VALUES (3, '人事部', '人事部');";
        Connection conn = null;
        Statement state = null;
        ResultSet rest = null;
        String[] sqlArr = sql2.split(";");
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            state = conn.createStatement();
            rest = state.executeQuery(sql);
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                for (int i = 0; i < sqlArr.length; i++) {
                    state.addBatch(sqlArr[i]);
                }
                state.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closeStatement(state);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    /**
     * 初始化流程定义数据
     * @return
     */
//    @Bean(name = "initActProcdef")
    public String initActProcdef(){
        String sql = "select count(*) from act_re_procdef where KEY_ =? ";
        Connection conn = null;
        PreparedStatement prestate = null;
        ResultSet rest = null;
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            prestate = conn.prepareStatement(sql);
            prestate.setString(1,"leave");
            rest = prestate.executeQuery();
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
//            if(count==0){
//                for (int i = 0; i < sqlArr.length; i++) {
//                    state.addBatch(sqlArr[i]);
//                }
//                state.executeBatch();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closePreparedStatement(prestate);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    /**
     * 部署请假流程
     */
    @Bean(name = "activityLeaveDeploy")
    public String activityLeaveDeploy(){
        String sql = "select count(*) from act_re_procdef where KEY_ =? ";
        Connection conn = null;
        PreparedStatement prestate = null;
        ResultSet rest = null;
        try {
            conn = DatabaseUtil.getConnection(url,username,password);
            prestate = conn.prepareStatement(sql);
            prestate.setString(1, ConstantUtil.activityKey.get("leave_key"));
            rest = prestate.executeQuery();
            int count = 0;
            while (rest.next()){
                count = rest.getInt(1);
            }
            if(count==0){
                Deployment deployment = repositoryService.createDeployment()

                        .name(ConstantUtil.activityKey.get("leave_name"))

                        .addClasspathResource("processes/"+ConstantUtil.activityKey.get("leave_bpmn"))

                        .addClasspathResource("processes/"+ConstantUtil.activityKey.get("leave_png"))

                        .deploy();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseUtil.closePreparedStatement(prestate);
            DatabaseUtil.closeConnection(conn);
        }
        return "";
    }

    //获取所有url并追加到文件


}
