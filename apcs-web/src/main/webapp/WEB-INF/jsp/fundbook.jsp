<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <title> Bootstrap 3.x Admin Theme</title>
    <!-- 共用css和js -->
    <jsp:include page="/common/head_css.jsp"/>
</head>
<body class="bootstrap-admin-with-small-navbar">
<!-- small navbar -->
<jsp:include page="/common/head.jsp"/>

<div class="container">
    <!-- left, vertical navbar & content -->
    <div class="row">
        <!-- left, vertical navbar -->
        <jsp:include page="/common/left.jsp"/>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-header">
                        <h1>跑批任务</h1>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">刷新余额</div>
                        </div>
                        <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal"  action="${ctx}/updateBalance.shtml" method="post">
                                <fieldset>
                                    <legend>刷新余额</legend>

                                    <%--<div class="form-group">
                                        <label class="col-lg-2 control-label" for="date01">开始日期</label>
                                        <div class="col-lg-10">
                                            <input type="text" readonly class="form-control datepicker"  name="date01" id="date01" value="" data-date-format="yyyy-mm-dd">
                                            <p class="help-block">In addition to freeform text, any HTML5 text-based input appears like so.</p>
                                        </div>
                                    </div>--%>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate">开始日期</label>
                                        <div class="col-lg-10">
                                            <input value="201309" class="form-control" id="startDate" type="text" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="endDate">结束日期</label>
                                        <div class="col-lg-10">
                                            <input value="201309" class="form-control" id="endDate" type="text" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="userId">用户编号</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="userId" type="text" name="userid"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="accBook">选择账本</label>
                                        <div class="col-lg-10">
                                            <select id="accBook" class="form-control"  name="accBook">
                                                <option value="">全部账本</option>
                                                <c:forEach items="${bookcodes}" var="bookcode">
                                                    <option value="${bookcode.fundtype}&&${bookcode.bookcode}">
                                                            ${bookcode.bookcodedesc}(${bookcode.bookcode})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <span class="help-block">选择一个账本统计</span>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">刷新余额</button>
                                    <button type="reset" class="btn btn-default">Cancel</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">日结统计</div>
                        </div>
                        <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal"  action="${ctx}/updateBalance.shtml?monthFund=1" method="post">
                                <fieldset>
                                    <legend>Form Horizontal</legend>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate2">开始日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="startDate2" type="text" value="20130903" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMMdd'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="endDate2">结束日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="endDate2" type="text" name="endDate" value="20130903"  readonly onClick="WdatePicker({dateFmt:'yyyyMMdd'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="userId2">用户编号</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="userId2" type="text" name="userid"/>
                                        </div>
                                    </div>

                                    <div class="form-group ">
                                        <label class="col-lg-2 control-label" for="accBook2">选择账本</label>
                                        <div class="col-lg-10">
                                            <select id="accBook2" class="form-control"  name="accBook">
                                                <option value="">全部</option>
                                                <c:forEach items="${bookcodes}" var="bookcode">
                                                    <option value="${bookcode.bookcode}">
                                                            ${bookcode.bookcodedesc}-(${bookcode.bookcode})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <span class="help-block">选择一个账本统计</span>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">日结</button>
                                    <button type="reset" class="btn btn-default">Cancel</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">月结统计</div>
                        </div>
                        <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal"  action="${ctx}/updateBalance.shtml?monthFund=2" method="post">
                                <fieldset>
                                    <legend>Form Horizontal</legend>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate3">开始日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="startDate3" type="text" value="201309" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="endDate3">结束日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="endDate3" type="text" value="201312" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="userId3">用户编号</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="userId3" type="text" name="userid"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="accBook3">选择账本</label>
                                        <div class="col-lg-10">
                                            <select id="accBook3" class="form-control "  name="accBook">
                                                <option value="">全部</option>
                                                <c:forEach items="${bookcodes}" var="bookcode">
                                                    <option value="${bookcode.bookcode}">
                                                            ${bookcode.bookcodedesc}-(${bookcode.bookcode})
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <span class="help-block">选择一个账本统计</span>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">月结统计</button>
                                    <button type="reset" class="btn btn-default">Cancel</button>
                                </fieldset>
                            </form>
                        </div>   </div>
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
    });
</script>
</body>
</html>
