package edu.nuist.aspect;

import com.alibaba.fastjson.JSON;
import edu.nuist.entity.Permission;
import edu.nuist.entity.Result;
import edu.nuist.service.SysPermissionService;
import edu.nuist.service.SysRoleService;
import edu.nuist.util.JWTUtils;
import edu.nuist.util.RedisUtil;
import edu.nuist.vo.UserAndRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private RedisUtil redisUtil;

    @Resource
    private SysPermissionService sysPermissionService;

    // 定义切点
    @Pointcut("@annotation(edu.nuist.annotation.PermissionAnnotation)")
    public void declarePointCut() {}

    // 环绕通知
    @Around("declarePointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        UserAndRoleVo userAndRoleVo = (UserAndRoleVo) redisUtil.get("user");
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

        log.info("permissions: {}", permissions);
        log.info("requestUrls: {}", requestURI);

        Result result = new Result();

        if (!urlList.contains(subRequestURI)) {
            result.setCode("500");
            result.setMsg("用户权限不足");
            return result;
        }

        return joinPoint.proceed();
    }

}
