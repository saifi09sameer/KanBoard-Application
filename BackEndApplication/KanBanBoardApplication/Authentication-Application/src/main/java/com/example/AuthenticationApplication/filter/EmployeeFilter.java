package com.example.AuthenticationApplication.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class EmployeeFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest =(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader ==null || !authHeader.startsWith("Bearer")){
            throw new ServletException("Token is missing");
        }else{
            String token=authHeader.substring(7);
            Claims claims= Jwts.parser().setSigningKey("secretKeyData").parseClaimsJws(token).getBody();
            httpServletRequest.setAttribute("employeeEmail", claims.get("employeeEmail"));
            httpServletRequest.setAttribute("employeeName", claims.get("employeeName"));
            httpServletRequest.setAttribute("employeePhoneNumber",claims.get("employeePhoneNumber"));
            httpServletRequest.setAttribute("employeeCity",claims.get("employeeCity"));
            httpServletRequest.setAttribute("employeeRole",claims.get("employeeRole"));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
