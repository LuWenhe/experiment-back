package edu.nuist.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nuist.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义JWT拦截器类
 */
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 将预请求给放过，处理真正的请求
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        Map<String, Object> hashMap = new HashMap<>();
        //获取请求头中令牌
        String token = request.getHeader("token");

        try {
            // 验证令牌
            JWTUtils.verify(token);
            // 放行请求
            return true;
        } catch (SignatureVerificationException e) {
            log.error("无效签名!");
            hashMap.put("msg","无效签名!");
        } catch (TokenExpiredException e){
            log.error("token过期");
            hashMap.put("msg","token过期!");
        } catch (AlgorithmMismatchException e){
            log.error("token算法不一致");
            hashMap.put("msg","token算法不一致!");
        } catch (Exception e){
            log.error("token无效");
            hashMap.put("msg","token无效!!");
        }

        hashMap.put("status", 500);
        String json = new ObjectMapper().writeValueAsString(hashMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
    }

}
