<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<nav class="navbar navbar-default navbar-fixed">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">${currTitle}</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-left">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-globe"></i>
                        <b class="caret"></b>
                        <span class="notification" id="test01"></span>
                    </a>
                    <ul class="dropdown-menu" id="list">

                    </ul>
                </li>

            </ul>

            <ul class="nav navbar-nav navbar-right">               
                <li>
                    <a href="#">
                        Log out
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="js/app.js"></script>