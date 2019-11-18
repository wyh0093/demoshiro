package com.example.demo.conf;

import com.example.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/18 16:20
 **/
public class SpringSecurityAuditorAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        if (user != null){
            return Optional.of(user.getId());
        } else {
            return Optional.empty();
        }
    }
}
