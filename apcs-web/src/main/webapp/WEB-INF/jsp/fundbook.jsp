<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>403 error page | Bootstrap 3.x Admin Theme</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-theme.min.css">

    <!-- Bootstrap Admin Theme -->
    <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme.css">
    <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-admin-theme-change-size.css">

    <!-- Vendors -->
    <link rel="stylesheet" media="screen" href="${ctx}/vendors/bootstrap-datepicker/css/datepicker.css">
    <link rel="stylesheet" media="screen" href="${ctx}/css/datepicker.fixes.css">
    <link rel="stylesheet" media="screen" href="${ctx}/vendors/uniform/themes/default/css/uniform.default.min.css">
    <link rel="stylesheet" media="screen" href="${ctx}/css/uniform.default.fixes.css">
    <link rel="stylesheet" media="screen" href="${ctx}/vendors/chosen.min.css">
    <link rel="stylesheet" media="screen" href="${ctx}/vendors/selectize/dist/css/selectize.bootstrap3.css">
    <link rel="stylesheet" media="screen" href="${ctx}/vendors/bootstrap-wysihtml5-rails-b3/vendor/assets/stylesheets/bootstrap-wysihtml5/core-b3.css">

    <!-- Bootstrap Error Page -->
    <link rel="stylesheet" media="screen" href="${ctx}/css/bootstrap-error-page.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/js/html5shiv.js"></script>
    <script type="text/javascript" src="${ctx}/js/respond.min.js"></script>
    <![endif]-->
</head>
<body class="bootstrap-admin-with-small-navbar">
<!-- small navbar -->
<nav class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar-sm" role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-left bootstrap-admin-theme-change-size">
                        <li class="text">Change size:</li>
                        <li><a class="size-changer small">Small</a></li>
                        <li><a class="size-changer large active">Large</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Link</a></li>
                        <li><a href="#">Link</a></li>
                        <li>
                            <a href="#">Reminders <i class="glyphicon glyphicon-bell"></i></a>
                        </li>
                        <li>
                            <a href="#">Settings <i class="glyphicon glyphicon-cog"></i></a>
                        </li>
                        <li>
                            <a href="#">Go to frontend <i class="glyphicon glyphicon-share-alt"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> <i class="glyphicon glyphicon-user"></i> Username <i class="caret"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="index.html">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- main / large navbar -->
<nav class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small" role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".main-navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="about.html">Admin Panel</a>
                </div>
                <div class="collapse navbar-collapse main-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Link</a></li>
                        <li><a href="#">Link</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-hover="dropdown">Dropdown <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li role="presentation" class="dropdown-header">Dropdown header</li>
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation" class="dropdown-header">Dropdown header</li>
                                <li><a href="#">Separated link</a></li>
                                <li><a href="#">One more separated link</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div>
        </div>
    </div><!-- /.container -->
</nav>

<div class="container">
    <!-- left, vertical navbar & content -->
    <div class="row">
        <!-- left, vertical navbar -->
        <div class="col-md-2 bootstrap-admin-col-left">
            <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                <li>
                    <a href="about.html"><i class="glyphicon glyphicon-chevron-right"></i> 跑批任务</a>
                </li>
                <li>
                    <a href="about.html"><i class="glyphicon glyphicon-chevron-right"></i> 报表</a>
                </li>
                <li>
                    <a href="about.html"><i class="glyphicon glyphicon-chevron-right"></i>汇总报表 </a>
                </li>

            </ul>
        </div>

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
                            <form class="form-horizontal"  action="/updateBalance.shtml" method="post">
                                <fieldset>
                                    <legend>Form Horizontal</legend>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="date01">开始日期</label>
                                        <div class="col-lg-10">
                                            <input type="text" readonly class="form-control datepicker"  name="date01" id="date01" value="" data-date-format="yyyy-mm-dd">
                                            <p class="help-block">In addition to freeform text, any HTML5 text-based input appears like so.</p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate">结束日期</label>
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
                                            <input class="form-control" id="userId" type="text" name="userId"/>
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
                            <form class="form-horizontal"  action="/updateBalance.shtml?monthFund=1" method="post">
                                <fieldset>
                                    <legend>Form Horizontal</legend>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate2">开始日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="startDate2" type="text" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMMdd'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="endDate2">结束日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="endDate2" type="text" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMMdd'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="userId2">用户编号</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="userId2" type="text" name="userId"/>
                                        </div>
                                    </div>

                                    <div class="form-group ">
                                        <label class="col-lg-2 control-label" for="accBook2">选择账本</label>
                                        <div class="col-lg-10">
                                            <select id="accBook2" class="form-control"  name="accBook">
                                                <option value="">全部</option>
                                                <c:forEach items="${bookcodes}" var="bookcode">
                                                    <option value="${bookcode.bookcode}">
                                                            ${bookcode.bookcodedesc}
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
                            <form class="form-horizontal"  action="/updateBalance.shtml?monthFund=2" method="post">
                                <fieldset>
                                    <legend>Form Horizontal</legend>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="startDate3">开始日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="startDate3" type="text" name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="endDate3">结束日期</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="endDate3" type="text" name="endDate" readonly onClick="WdatePicker({dateFmt:'yyyyMM'});"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="userId3">用户编号</label>
                                        <div class="col-lg-10">
                                            <input class="form-control" id="userId3" type="text" name="userId"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 control-label" for="accBook3">选择账本</label>
                                        <div class="col-lg-10">
                                            <select id="accBook3" class="form-control "  name="accBook">
                                                <option value="">全部</option>
                                                <c:forEach items="${bookcodes}" var="bookcode">
                                                    <option value="${bookcode.bookcode}">
                                                            ${bookcode.bookcodedesc}
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
<div class="navbar navbar-footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <footer role="contentinfo">
                    <p class="left">Bootstrap 3.x Admin Theme</p>
                    <p class="right">&copy; 2013 <a href="http://www.meritoo.pl" target="_blank">Meritoo.pl</a></p>
                </footer>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/twitter-bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="${ctx}/js/bootstrap-admin-theme-change-size.js"></script>
<script type="text/javascript" src="${ctx}/vendors/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="${ctx}/vendors/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/vendors/selectize/dist/js/standalone/selectize.min.js"></script>
<script type="text/javascript" src="${ctx}/vendors/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${ctx}/vendors/bootstrap-wysihtml5-rails-b3/vendor/assets/javascripts/bootstrap-wysihtml5/wysihtml5.js"></script>
<script type="text/javascript" src="${ctx}/vendors/bootstrap-wysihtml5-rails-b3/vendor/assets/javascripts/bootstrap-wysihtml5/core-b3.js"></script>
<script type="text/javascript" src="${ctx}/vendors/twitter-bootstrap-wizard/jquery.bootstrap.wizard-for.bootstrap3.js"></script>
<script type="text/javascript" src="${ctx}/vendors/boostrap3-typeahead/bootstrap3-typeahead.min.js"></script>

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
