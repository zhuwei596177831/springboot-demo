package com.zhuweiwei.springbootlearning0405.controller;

import com.zhuweiwei.springbootlearning0405.bean.LoginUser;
import com.zhuweiwei.springbootlearning0405.bean.Result;
import com.zhuweiwei.springbootlearning0405.mapper.TestMapper;
import com.zhuweiwei.springbootlearning0405.service.LoginUserService;
import com.zhuweiwei.springbootlearning0405.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author zww
 * @date 2020-04-06 14:57
 * @description
 **/
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TestMapper testMapper;
    @Resource
    TestService testService;
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    DefaultWebSessionManager defaultWebSessionManager;

    @GetMapping(value = {"/login", "/"})
    public String login(Model model, HttpServletRequest httpServletRequest) {
//        PageHelper.startPage(1, 2);
//        List<Test> pageList = testMapper.getPageList(1, 2);
//        List<Test> testList = testMapper.getXmlList();
//        List<Test> xmlList = testService.getXmlList();
//        List<Test> pageList = testMapper.getPageList(3, 2, new Test(), null);
//        PageInfo pageInfo = new PageInfo(pageList);
//        System.out.println("pageNum：" + pageInfo.getPageNum());
//        System.out.println("pageSize：" + pageInfo.getPageSize());
//        System.out.println("startRow：" + pageInfo.getStartRow());
//        System.out.println("endRow：" + pageInfo.getEndRow());
//        System.out.println("total：" + pageInfo.getTotal());
//        System.out.println("pages：" + pageInfo.getPages());
//        System.out.println("hasNextPage：" + pageInfo.isHasNextPage());
//        System.out.println("hasPreviousPage：" + pageInfo.isHasPreviousPage());
//        System.out.println(JSON.toJSONString(pageList));
//        System.out.println(JSON.toJSONString(testMapper.selectById(6)));
//        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", "香");
//        List list = testMapper.selectList(queryWrapper);
//        System.out.println(JSON.toJSONString(list));
        System.out.println(httpServletRequest.getContextPath());
        System.out.println(httpServletRequest.getRequestURL());
        System.out.println(httpServletRequest.getRequestURI());
        System.out.println(httpServletRequest.getServletPath());
        System.out.println(httpServletRequest.getContentType());
        System.out.println(httpServletRequest.getCharacterEncoding());
        return "login";
    }

//    @PostMapping("/login")
//    @ResponseBody
//    public String login(String email, String password, MultipartFile[] loginFile) {
//        return "成功";
//    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(LoginUser loginUser, Boolean rememberMe) {
        String loginId = loginUser.getLoginId();
        String password = loginUser.getPassword();
        if (StringUtils.isEmpty(loginId)) {
            return new Result(1, "用户名不可为空!", null);
        }
        if (StringUtils.isEmpty(password)) {
            return new Result(1, "用户名不可为空!", null);
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginId, password);
        if (rememberMe != null && rememberMe) {
            usernamePasswordToken.setRememberMe(true);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return Result.fail("用户名不存在!");
        } catch (IncorrectCredentialsException e) {
            return Result.fail("密码不正确!");
        }
        System.out.println("认证通过后：" + subject.getSession(false).getId());
        return Result.ok();
    }

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
//        RowBounds rowBounds = new RowBounds(3, 2);
//        List<Test> forRowBounds = testMapper.getForRowBounds(new Test(), rowBounds);
//        System.out.println(JSON.toJSONString(forRowBounds));
        Subject subject = SecurityUtils.getSubject();
        System.out.println("认证状态：" + subject.isAuthenticated());
        System.out.println("记住我状态：" + subject.isRemembered());
//        model.addAttribute("activeSessions", sessionDAO.getActiveSessions().size());
        model.addAttribute("activeSessions", defaultWebSessionManager.getSessionDAO().getActiveSessions().size());
        return "index";
    }

    @GetMapping("/addLoginUser")
    @ResponseBody
    public Result addLoginUser(LoginUser loginUser) {
        loginUser.setInputTime(LocalDateTime.now());
        loginUser.setPassword(DigestUtils.md5DigestAsHex(loginUser.getPassword().getBytes()));
        loginUserService.addLoginUser(loginUser);
        return Result.ok();
    }

    @PostMapping("/check")
    @ResponseBody
    public Result check() {
        return Result.ok();
    }

    @GetMapping("/order/myOrder")
    public String myOrder() {
        return "order/order";
    }

    @GetMapping("/permissionTest")
    public String permissionTest() {
        return "permissionTest";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(Model model) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("userName", loginUser.getUserName());
        return "unauthorized/unauthorized";
    }

}
