<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<div class="header-container">
 <div style="text-align: center"; display:block; font-size: 500px>
    <div class="site-name" style="font-size:200%; font color:blue">Online iPhone Store</div>
 </div>
    <div class="header-bar">
    <div style="text-align: right">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        Hello
           <a href="${pageContext.request.contextPath}/accountInfo">
                ${pageContext.request.userPrincipal.name} </a>
         &nbsp;|&nbsp;
           <a href="${pageContext.request.contextPath}/logout">Logout</a>
 
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </c:if>
        </div>
    </div>
</div>