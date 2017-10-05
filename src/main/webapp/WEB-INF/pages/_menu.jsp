<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>   
 
 
<div class="menu-container">
  <div style="text-align: right">
   <a href="${pageContext.request.contextPath}/shop">
      Shop
   </a>
   |
   <a href="${pageContext.request.contextPath}/cart">
      My Cart
   </a>
   <security:authorize  access="hasRole('ROLE_ADMIN')">
   |
     <a href="${pageContext.request.contextPath}/orderList">
         Orders
     </a>
   </security:authorize>
   <security:authorize  access="hasRole('ROLE_ADMIN')">
   |
         <a href="${pageContext.request.contextPath}/product">
                        Add Product
         </a>
   </security:authorize>
  </div>
</div>