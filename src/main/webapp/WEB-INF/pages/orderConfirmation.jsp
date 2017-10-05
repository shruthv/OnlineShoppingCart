<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 
<title>Order Confirmation</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
   <jsp:include page="_header.jsp" />
 
   <jsp:include page="_menu.jsp" />
 
   <div class="page-title"> Order Confirmation</div>
  
   <div class="container">
       <h3>Thank you for the Order</h3>
       Your order number is: ${lastOrderedCart.orderNum}
   </div>
 
</body>
</html>