<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>


        <form action="/updateBalance.shtml" method="post">
            <table>
                <tr>
                    <td>开始日期</td>
                    <td><input  type="text" value="201309" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/></td>
                </tr>
                <tr>
                    <td>结束日期</td>
                    <td> <input  type="text" value="201309" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/></td>
                </tr>
                <tr>
                    <td>用户编号</td>
                    <td><input  type="text" name="userId" value="342"/></td>
                </tr>
               <tr>
                   <td>选择账本</td>
                   <td>
                       <select name="accBook">
                           <option value="">全部</option>
                       <c:forEach items="${bookcodes}" var="bookcode">
                               <option value="${bookcode.platformrole}-${bookcode.entryuserrole}-${bookcode.accbooknumber}">
                                       ${bookcode.pname}-${bookcode.ename}-${bookcode.aname}
                               </option>
                       </c:forEach>
                       </select>
                   </td>

               </tr>
                <tr>
                    <td colspan="2"  > <input type="submit" value="刷新余额"/></td>
                </tr>

            </table>


        </form>
</body>
</html>
