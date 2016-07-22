<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <title> Bootstrap 3.x Admin Theme</title>
    <!-- 共用css和js -->
    <%@include file="/common/head_css.jsp"%>
</head>
<body class="bootstrap-admin-with-small-navbar">
<!-- small navbar -->
<%@include file="/common/head.jsp"%>

<div class="container">
    <!-- left, vertical navbar & content -->
    <div class="row">
        <!-- left, vertical navbar -->
        <%@include file="/common/left.jsp"%>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-header">
                        <h1>只刷月结</h1>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="panel panel-default bootstrap-admin-no-table-panel">
                    <div class="panel-heading">
                        <div class="text-muted bootstrap-admin-box-title">加载月结余额</div>
                    </div>
                    <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                        <form class="form-horizontal"  action="${ctx}/onlymonth/loadCache.shtml" method="post">
                            <fieldset>
                                <legend>加载月结余额</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="loadDate">开始</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="loadDate" type="text" value="" name="loadDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>


                                <button type="submit" class="btn btn-primary">走</button>
                                <button type="reset" class="btn btn-default">Cancel</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="panel panel-default bootstrap-admin-no-table-panel">
                    <div class="panel-heading">
                        <div class="text-muted bootstrap-admin-box-title">刷新余额</div>
                    </div>
                    <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                        <form class="form-horizontal"  action="${ctx}/onlymonth/oneByOneUpdateBalance.shtml" method="post">
                            <fieldset>
                                <legend>刷新余额</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="startDate">开始日期</label>
                                    <div class="col-lg-10">
                                        <input value="" class="form-control" id="startDate" type="text" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="endDate">结束日期</label>
                                    <div class="col-lg-10">
                                        <input value="" class="form-control" id="endDate" type="text" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
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
                        <div class="text-muted bootstrap-admin-box-title">生成临时表</div>
                    </div>
                    <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                        <form class="form-horizontal"  action="${ctx}/onlymonth/createFundmonthtemp.shtml" method="post">
                            <fieldset>
                                <legend>生成临时表</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="dateStr">日期</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="dateStr" type="text" value="" name="dateStr" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">走</button>
                                <button type="reset" class="btn btn-default">Cancel</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="panel panel-default bootstrap-admin-no-table-panel">
                    <div class="panel-heading">
                        <div class="text-muted bootstrap-admin-box-title">刷月结</div>
                    </div>
                    <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                        <form class="form-horizontal"  action="${ctx}/onlymonth/insertFundBookMonth.shtml" method="post">
                            <fieldset>
                                <legend>刷月结</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="startDate1">开始</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="startDate1" type="text" value="" name="start" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="endDate1">结束</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="endDate1" type="text" value="" name="end" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary">走</button>
                                <button type="reset" class="btn btn-default">Cancel</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>


            <div class="col-lg-12">
                <div class="panel panel-default bootstrap-admin-no-table-panel">
                    <div class="panel-heading">
                        <div class="text-muted bootstrap-admin-box-title">只刷月结</div>
                    </div>
                    <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                        <form class="form-horizontal"  action="${ctx}/onlymonth/shceduleAll.shtml" method="post">
                            <fieldset>
                                <legend>只刷月结</legend>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="startDate4">开始</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="startDate4" type="text" value="" name="start" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 control-label" for="endDate4">结束</label>
                                    <div class="col-lg-10">
                                        <input class="form-control" id="endDate4" type="text" value="" name="end" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary">走</button>
                                <button type="reset" class="btn btn-default">Cancel</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>

            </div>
        </div>
    </div>
</div>
<!-- footer -->
<%@include file="/common/foot.jsp"%>
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
