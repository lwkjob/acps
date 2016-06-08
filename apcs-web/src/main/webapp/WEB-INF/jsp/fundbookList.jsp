<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>403 error page | Bootstrap 3.x Admin Theme</title>
    <!-- 共用css和js -->
    <jsp:include page="/common/head_css.jsp"/>
</head>
<body class="bootstrap-admin-with-small-navbar">
    <!-- small navbar -->
    <jsp:include page="/common/head.jsp"/>

<div class="container">
    <!-- left, vertical navbar & content -->
    <jsp:include page="/common/left.jsp"/>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-header">
                        <h1>账本列表</h1>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">账本列表</div>
                        </div>
                        <%--<div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">--%>
                        <div class="bootstrap-admin-panel-content">
                            <form id="pagesForm" action="/fundbooklist.shtml" method="post">
                                <input type="hidden"  id="currentPage" name="currentPage" value="${pagination.currentPage }"/>
                            </form>
                            <table class="table table-hover table-striped table-bordered">
                        <c:if test="${pagination.pageCount!=0}">
                                <thead>
                                <td>用户id</td>
                                <td>账单id</td>
                                <td>借</td>
                                <td>贷</td>
                                <td>余</td>
                                </thead>
                            </c:if>

                                <tbody>
                                <c:forEach items="${pagination.data}" var="fundbook">
                                    <tr>

                                            <td>${fundbook.userid}</td>
                                            <td>${fundbook.bookid}</td>
                                            <td>${fundbook.debit}</td>
                                            <td>${fundbook.credit}</td>
                                            <td>${fundbook.balance}</td>


                                    </tr>

                                </c:forEach>


                                </tbody>
                            </table>
                            <jsp:include page="/common/pagelist.jsp"/>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- footer -->
    <jsp:include page="/common/foot.jsp"/>

<script type="text/javascript">
    $(function() {
        $('.datepicker').datepicker();
        $('.uniform_on').uniform();
        $('.chzn-select').chosen();
        $('.selectize-select').selectize();
        $('.textarea-wysihtml5').wysihtml5({
            stylesheets: [
                '${ctx}/vendors/bootstrap-wysihtml5-rails-b3/vendor/assets/stylesheets/bootstrap-wysihtml5/wysiwyg-color.css'
            ]
        });
        $('#pageTable').dataTable( {
            "sDom": "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-6'i><'col-md-6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            }
        } );
    });
</script>
</body>
</html>
