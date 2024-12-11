package jtw.nice.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jtw.nice.entity.User;
import jtw.nice.exception.TokenAuthExpiredException;
import jtw.nice.exception.TokenMissingException;
import jtw.nice.utils.TokenUtils;
import jtw.nice.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    private final TokenUtils tokenUtil;
    private final UserHolder userHolder;

    @Autowired
    public AuthHandlerInterceptor(TokenUtils tokenUtil, UserHolder userHolder) {
        this.tokenUtil = tokenUtil;
        this.userHolder = userHolder;
    }

    @Value("${jwt.refreshTime}")
    private Long refreshTime;
    @Value("${jwt.expiration}")
    private Long expiresTime;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest httpServletRequest,
                             @NonNull HttpServletResponse httpServletResponse,
                             @NonNull Object handler) {
        log.info("============Enter the Interceptor============");

        // 如果不是映射到方法直接通过，可以访问资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String requestURI = httpServletRequest.getRequestURI();
        System.out.println(requestURI);
        if (requestURI.equals("/register") || requestURI.equals("/guest_login") || requestURI.equals("/employee_login")) {
            return true;
        }

        // 获取 token
        String token = httpServletRequest.getHeader("token");
        System.out.println("Token is empty: ");
        System.out.println(token == null);
        if (token == null || token.trim().isEmpty()) {
            throw new TokenMissingException("Token is missing or empty in the request header");
        }

        // 解码 token
        DecodedJWT decodedJWT = tokenUtil.decodeToken(token);
        int userId = decodedJWT.getClaim("user_id").asInt();
        String userRole = decodedJWT.getClaim("userRole").asString();
        if ("admin".equalsIgnoreCase(userRole)) {
            log.info("Admin user detected. Bypassing further checks.");
            return true;
        }
        int groupId = decodedJWT.getClaim("group_id").asInt();

        log.info("User Id: {}, Group ID: {}", userId, groupId);

        // Validate Token
        Date expirationDate = decodedJWT.getExpiresAt();
        long now = System.currentTimeMillis();
        long timeOfUse = now - decodedJWT.getClaim("timeStamp").asLong();
        if (expirationDate.before(new Date(now))) {
            throw new TokenAuthExpiredException("Token has expired.");
        }

        // Refresh Token
        if (timeOfUse >= refreshTime * 1000 && timeOfUse < expiresTime * 1000) {
            String newToken = tokenUtil.getToken(userId, userRole, groupId);
            httpServletResponse.setHeader("token", newToken);
            log.info("Token refreshed successfully");
        }

        User user = new User();
        user.setUserId(userId);
        user.setRole(userRole);
        user.setGroupId(groupId);

        userHolder.setUser(user);
        log.info("============Success============");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理 ThreadLocal 中的用户信息
        userHolder.remove();
    }
}

