package edu.nuist.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import edu.nuist.dto.RequestLogDTO;
import edu.nuist.entity.Permission;
import edu.nuist.entity.Result;
import edu.nuist.service.SysPermissionService;
import edu.nuist.util.JWTUtils;
import edu.nuist.vo.UserAndRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Aspect
public class PermissionAspect {

    @Resource
    private HttpServletRequest request;

    private final String REQUEST_ID_KEY = "request_unique_id";

    @Resource
    private SysPermissionService sysPermissionService;

    // 定义切点
    @Pointcut("@annotation(edu.nuist.annotation.PermissionAnnotation)")
    public void declarePointCut() {}

    // 异常通知
    @AfterThrowing(value = "declarePointCut()", throwing = "ex")
    public void doAfterThrowingAdvice(Throwable ex) {
        printExceptionLog(ex);
    }

    // 环绕通知
    @Around("declarePointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] objArgs = joinPoint.getArgs();

        RequestLogDTO logVo = new RequestLogDTO();

        // 设置请求唯一ID
        logVo.setId(IdUtil.fastUUID());
        request.setAttribute(REQUEST_ID_KEY, logVo.getId());

        logVo.setUri(request.getRequestURI());
        logVo.setName(request.getMethod());

        String token = request.getHeader("token");
        UserAndRoleVo userAndRoleVo = JWTUtils.getUserAndRoleFromToken(token);
        Integer roleId = userAndRoleVo.getRoleId();

        List<Object> args = Lists.newArrayList();

        // 过滤掉一些不能转为json字符串的参数
        Arrays.stream(objArgs).forEach(e -> {
            if (e instanceof MultipartFile || e instanceof HttpServletRequest
                    || e instanceof HttpServletResponse || e instanceof BindingResult) {
                return;
            }

            args.add(e);
        });

        logVo.setArgs(args.toArray());
        logVo.setPath(methodSignature.getDeclaringTypeName() + "." + method.getName());
        logVo.setReferer(request.getHeader("referer"));
        logVo.setRemoteAddr(request.getRemoteAddr());
        logVo.setUserAgent(request.getHeader("user-agent"));

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
            logVo.setAuth(false);
            log.info(JSON.toJSONString(logVo));
            return result;
        }

        log.info(JSON.toJSONString(logVo));
        // 使方法继续执行
        return joinPoint.proceed();
    }

    // 打印异常日志
    public void printExceptionLog(Throwable ex) {
        JSONObject logVo = new JSONObject();
        logVo.put("id", request.getAttribute(REQUEST_ID_KEY));
        log.error(JSON.toJSONString(logVo), ex);
    }

}
