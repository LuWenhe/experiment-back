package edu.nuist.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nuist.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义JWT拦截器类
 */
public class JWTInterceptor implements HandlerInterceptor {

    public static final Logger LOGGER = LoggerFactory.getLogger(JWTInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 将预请求给放过，处理真正的请求
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        Map<String, Object> map = new HashMap<>();
        //获取请求头中令牌
        String token = request.getHeader("token");

        try {
            //验证令牌
            JWTUtils.verify(token);
            return true;//放行请求
        } catch (SignatureVerificationException e) {
            LOGGER.error("无效签名!");
            //e.printStackTrace();
            map.put("msg","无效签名!");
        } catch (TokenExpiredException e){
            //e.printStackTrace();
            LOGGER.error("token过期");
            map.put("msg","token过期!");
        } catch (AlgorithmMismatchException e){
            // e.printStackTrace();
            LOGGER.error("token算法不一致");
            map.put("msg","token算法不一致!");
        } catch (Exception e){
            // e.printStackTrace();
            LOGGER.error("token无效");
            map.put("msg","token无效!!");
        }

        map.put("state", false);//设置状态
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println("json: " + json);

        return false;
    }

}
