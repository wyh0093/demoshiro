package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.entityModel.RoleModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.util.DatabaseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    protected RoleRepository roleRepository;

    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public Page<Role> findAll(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        roleRepository.findAll();
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<RoleModel> findAllCriteria(Integer currentpage, Integer pageSize, Role role, String page) {
        Specification<Role> queryCondition = new Specification<Role>() {
            private static final long serialVersionUID = -594262632507712037L;
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> crite, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                if( role!= null && role.getCname() != null){
                    Predicate name = cb.like(root.get("cname").as(String.class), "%"+role.getCname()+"%");
                    Predicate description = cb.like(root.get("description").as(String.class), "%"+role.getCname()+"%");
                    pr.add(cb.or(name,description));
                }
                return cb.and(pr.toArray(new Predicate[pr.size()]));
            }
        };
        List<Role> roleList = new ArrayList<>();
        if("true".equals(page)){
            roleList = roleRepository.findAll(queryCondition, PageRequest.of(currentpage - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"))).getContent();
        }else {
            roleList = roleRepository.findAll(queryCondition, Sort.by(Sort.Direction.ASC, "id"));
        }
        List<RoleModel> roleModels = new ArrayList<>();
        for(Role role1 : roleList){
            RoleModel roleModel = new RoleModel();
            BeanUtils.copyProperties(role1,roleModel);
            roleModels.add(roleModel);
        }
        return roleModels;
    }

    @Override
    public List<RoleModel> findAllPage(Integer page, Integer size, String name) {
        List<Role> list = null;
        if(name!=null && !"".equals(name)){
            list = roleRepository.findAllPage(page,size,name);
        }else {
            list = this.findAll(page,size).getContent();
        }
        List<RoleModel> modelList = new ArrayList<>();
        BeanUtils.copyProperties(list,modelList);
        return modelList;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void del(Integer id) {
        roleRepository.delete(this.findRoleById(id));
    }

    @Override
    public int totalCount(Role role) {
       return this.findAllCriteria(0,0,role,"false").size();
    }

    @Override
    public Role findOne(Integer id) {
        if(id!=null && id>0){
            return roleRepository.findRoleById(id);
        }
        return null;
    }

    @Override
    public List<Role> findByUserId(Integer id) {
//        return roleRepository.findByUserId(id);
        return null;
    }

    @Override
    public List<Role> findAllNoCondition() {

        List<Role> roleList = new ArrayList<>();
        String sql = "SELECT id,cname from role  ";
        Connection conn = DatabaseUtil.getConnection(url,username,password);
        Statement stat = null;
        ResultSet result = null;
        if(conn!=null){
            try {
                stat = conn.createStatement();
                result = stat.executeQuery(sql);
                while (result.next()){
                    Role role = new Role();
                    role.setId(result.getInt(0));
                    role.setCname(result.getString(1));
                    roleList.add(role);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.closeStatement(stat);
                DatabaseUtil.closeConnection(conn);
            }
        }
        return roleList;
    }
}
