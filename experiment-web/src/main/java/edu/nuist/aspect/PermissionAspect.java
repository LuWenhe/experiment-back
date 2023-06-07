package edu.nuist.aspect;

import edu.nuist.entity.Permission;
import edu.nuist.entity.Result;
import edu.nuist.service.SysPermissionService;
import edu.nuist.util.JWTUtils;
import edu.nuist.vo.UserAndRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Aspect
public class PermissionAspect {

    @Resource
    private HttpServletRequest request;

    @Resource
    private SysPermissionService sysPermissionService;

    // 定义切点
    @Pointcut("@annotation(edu.nuist.annotation.PermissionAnnotation)")
    public void declarePointCut() {}

    // 环绕通知
    @Around("declarePointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader("token");
        UserAndRoleVo userAndRoleVo = JWTUtils.getUserAndRoleFromToken(token);
        Integer roleId = userAndRoleVo.getRoleId();

        // 拿到用户所属角色拥有的权限
        List<Permission> permissions = sysPermissionService.getPermissionsByRoleId(roleId);
        List<String> urlList = new ArrayList<>();

        for (Permission permission : permissions) {
            urlList.add(permission.getUrl());
        }

        // 获取请求的Url
        String requestURI = request.getRequestURI();
        String subRequestURI = requestURI.substring(0, requestURI.lastIndexOf("/"));

        Result result = new Result();

        // 如果改角色拥有的权限地址不包含请求的接口, 则不执行接下来的代码
        if (!urlList.contains(subRequestURI)) {
            result.setCode("500");
            result.setMsg("用户权限不足");
            return result;
        }

        // 使方法继续执行
        return joinPoint.proceed();
    }

}
