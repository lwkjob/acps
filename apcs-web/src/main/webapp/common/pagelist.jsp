<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title></title>
    <script type="text/javascript">
     function goPage(page){
     	$("#currentPage").val(page);
     	$("#pagesForm").submit();
     }
  </script>
  </head>
  
  <body>
  	<table width="100%" class="table">
		<c:if test="${pagination.pageCount!=0}">
  		   <tr align="center" >
  		   		<td>
		    	 共&nbsp; ${pagination.pageCount }&nbsp;页 &nbsp;每页${pagination.pageSize}条 共&nbsp;${pagination.count }&nbsp;条记录
				 第&nbsp;${pagination.currentPage }&nbsp;页
		    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		     	<input class="btn" type="button" value="首页" onclick="goPage(${pagination.toppage})" <c:if test="${pagination.currentPage==1}">disabled</c:if> />
				<input class="btn" type="button" value="上一页" onclick="goPage(${pagination.previouspage})" <c:if test="${pagination.currentPage==1}">disabled</c:if>  />
				<input class="btn" type="button" value="下一页" onclick="goPage(${pagination.nextpage})" <c:if test="${pagination.currentPage==pagination.pageCount || pagination.pageCount==0}">disabled</c:if> />
				<input class="btn" type="button" value="尾页" onclick="goPage(${pagination.buttompage})" <c:if test="${pagination.currentPage==pagination.pageCount || pagination.pageCount==0}">disabled</c:if> />
   			</td>
  			</tr>
		</c:if>
		<c:if test="${pagination.pageCount==0}"><tr align="center" ><td>无数据</td></tr></c:if>
  		</table>
  </body>
</html>
