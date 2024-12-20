package com.sparta.newsfeed.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    /*
    회원 가입 기능 구현을 위해 일단 화이트리스트에 /user/signup만 적용해 둔 상태, 추후에 로그인과 로그아웃에 대한 url 추가 필요합니다.
     */
    private static final String[] WHITE_LIST = {"/","/user/signup","/auth/login","/auth/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, RuntimeException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        String requestURI = httprequest.getRequestURI();
        HttpServletResponse httpresponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        //WHITE_LIST에 포함된 경우 true 이므로 false 처리를 위해 !를 붙임
        if(!isWhiteList(requestURI)) {
            HttpSession session = httprequest.getSession(false);

            if (session == null || session.getAttribute("sessionKey") == null) {
                httpresponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 반환
                httpresponse.getWriter().write("로그인 해주세요."); // 응답 메시지 설정
                return; //401 처리 후 중단.
            }

            log.info("로그인에 성공했습니다.");
        }
        //1번 경우 : WHITE_LIST에 등록된 URL의 요청이라면 chain.doFilter 실행
        //2번 경우 : WHITE_LIST가 아닌 경우 위의 필터 로직 통과 후 chain.doFilter 다음 필터나 Servlet을 호출한다.
        //다음 필터가 없으면 Servlet -> Controller, 다음필터가 있으면 Filter를 호출
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requsetURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requsetURI);
    }

}