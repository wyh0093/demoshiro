package com.example.demo.service.impl;


import com.example.demo.entity.Department;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entityModel.RoleModel;
import com.example.demo.entityModel.UserModel;
import com.example.demo.enums.UserStatus;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.CopyDtoUtil;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qwe on 2019/6/27.
 */
@Service
public class UserServiceImpl implements UserService {


    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }


    @Override
    public User findUserByUserNamePassword(String userName, String password){
        return userRepository.findUserByUserNamePassword(userName,password);
    }

    @Override
    public List<User> findAllByPage(Pageable pageable, String keyword) {

//        List<User> all = userRepository.findAll(findCriteria(keyword),pageable).getContent();



        return null;

    }

    @Override
    public PageInfo<UserModel> findAll(Pageable pageable,String keyword,Integer id,boolean flag) {

        List<User> list = new ArrayList<>();
        List<User> noPageList = userRepository.findAll(findCriteria(keyword,id),Sort.by(Sort.Direction.ASC, "id"));
        int count = noPageList.size();
        int totalPage = 0;
        if(flag){
            Page<User> pages = userRepository.findAll(findCriteria(keyword,id),pageable);
            totalPage =  pages.getTotalPages();
            list = pages.getContent();
        }else {
            list = noPageList;
        }
        List<UserModel> userModels = new ArrayList<>();
        userModels = CopyDtoUtil.copyList(list,UserModel.class);

        if(null!=userModels && !userModels.isEmpty()){
            for (UserModel user :userModels) {
                if(user.getDepartmentId()!=null)
                    user.setDepartmentName(departmentRepository.getOne(user.getDepartmentId()).getName());
                if(user.getStatus()!=null)
                    for(UserStatus userStatus :UserStatus.values()){
                        if(userStatus.ordinal()==user.getStatus()){
                            user.setStatusCName(userStatus.getStr());
                        }
                    }
            }
        }
        PageInfo<UserModel> pageInfo = new PageInfo<UserModel>(userModels,count,totalPage);
        return pageInfo;
    }

    /**
     * 获取部门经理
     * @param id
     * @return
     */
    @Override
    public User findManagerByDepart(Integer id) {

        User user = new User();
        String sql="SELECT id,c_name from user u2 WHERE u2.id=(\n" +
                "SELECT r.user_id from role r WHERE r.user_id in(\n" +
                "select id from user u WHERE u.department=? )\n" +
                "and r.ename='manager') ";
        Connection conn = DatabaseUtil.getConnection(url,username,password);
        PreparedStatement pstat = null;
        ResultSet result = null;
        if(conn!=null){
            try {
                pstat = conn.prepareStatement(sql);
                pstat.setInt(1,id);
                result = pstat.executeQuery();
                while (result.next()){
                    user.setId(result.getInt(1));
                    user.setcName(result.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.closePreparedStatement(pstat);
                DatabaseUtil.closeConnection(conn);
            }
        }
        return user;
    }

    @Override
    public User findBoss() {
        User user = new User();
        String sql = "SELECT * from user WHERE id = ( \n" +
                "SELECT  r.user_id from role r WHERE ename='boss' )";

        Connection conn = DatabaseUtil.getConnection(url,username,password);
        Statement stat = null;
        ResultSet result = null;
        if(conn!=null){
            try {
                stat = conn.createStatement();
                result = stat.executeQuery(sql);
                while (result.next()){
                    user.setId(result.getInt(1));
                    user.setcName(result.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.closeStatement(stat);
                DatabaseUtil.closeConnection(conn);
            }
        }
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void del(Integer id) {

        userRepository.delete(userRepository.findUserById(id));
    }

    /**
     * 根据executionId获取申请人信息
     * @param executionId
     * @return
     */
    @Override
    public User getApplyPerson(String executionId) {

        return null;
    }


    public Specification<User> findCriteria(String keyword,Integer statusId){

        return new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
                List<Predicate> list = new ArrayList<Predicate>();
                Predicate cName = null;
                Predicate userName = null;
                Predicate status = null;
                Predicate departmentName = null;
                Predicate PredicateAll = null;
                if(!StringUtils.isEmpty(statusId)){
                    status = cr.equal(root.get("status").as(Integer.class),statusId );
                    list.add(cr.and(status));
                }
                if(!StringUtils.isEmpty(keyword)){
                    cName = cr.like(root.get("cName").as(String.class),"%"+keyword+"%" );
//                    userName = cr.like(root.get("userName").as(String.class),"%"+keyword+"%" );
                    departmentName = cr.like(root.get("departmentName").as(String.class),"%"+keyword+"%" );
                    list.add(cr.or(cName,departmentName));
                }
                Predicate[] p = new Predicate[list.size()];
                return cr.and(list.toArray(p));
            }
        };

    }
}
