package com.sparta.newsfeed.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/auth/login"};

    /*
    isSignup을 사용한 이유는 HttpMethod를 확인하여 User에 CRUD 형식 변경 없이 UserController을 구현하면서도 로그인 필터를 정상작동하게 하기 위함이다.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, RuntimeException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        String requestURI = httprequest.getRequestURI();
        HttpServletResponse httpresponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        if (isWhiteList(requestURI) || isSignup(request, requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // TODO: UUID 리팩토링?
        HttpSession session = httprequest.getSession(false);
        if (session == null || session.getAttribute("sessionKey") == null) {
            httpresponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 반환
            httpresponse.getWriter().write("로그인 해주세요.");
            return;
        }

        log.info("로그인에 성공했습니다.");
        chain.doFilter(request, response);
    }

    private boolean isSignup(ServletRequest request, String requestURI) {
        boolean isPostMethod = ((HttpServletRequest) request).getMethod().equals(HttpMethod.POST.name());
        return isPostMethod && PatternMatchUtils.simpleMatch("/user", requestURI);
    }

    private boolean isWhiteList(String requsetURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requsetURI);
    }

}