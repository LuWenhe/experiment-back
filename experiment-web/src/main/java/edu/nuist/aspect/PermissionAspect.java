package edu.nuist.aspect;

import edu.nuist.dto.UserPermissionDto;
import edu.nuist.enums.StatusEnum;
import edu.nuist.util.RedisUtil;
import edu.nuist.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@Component
@Aspect
public class PermissionAspect {

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisUtil redisUtil;

    // 定义切点
    @Pointcut("@annotation(edu.nuist.annotation.PermissionAnnotation) " +
            "|| @within(edu.nuist.annotation.PermissionAnnotation)")
    public void declarePointCut() {}

    // 环绕通知
    @Around("declarePointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        UserPermissionDto userPermission = (UserPermissionDto) redisUtil.get("userPermission");
        String username = userPermission.getUsername();
        Set<String> urlList = userPermission.getRequestUrlList();

        // 获取请求的Url
        String requestURI = request.getRequestURI();
        String subRequestURI = requestURI.substring(0, requestURI.lastIndexOf("/"));

        log.info("urList: {}", urlList);
        log.info("subRequestURI: {}", subRequestURI);

        // 如果用户拥有的权限地址不包含请求的接口, 则不执行接下来的代码
        if (!urlList.contains(subRequestURI)) {
            log.info("用户{}接口权限不足", username);
            return BasicResultVO.fail(StatusEnum.ERROR_401,"用户权限不足");
        }

        // 使方法继续执行
        return joinPoint.proceed();
    }

}
