<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="sidebar" data-color="blue" data-image="img/sidebar-5.jpg">

    <!--

        Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
        Tip 2: you can also add an image using data-image tag

    -->

    <div class="sidebar-wrapper">
        <div class="logo">
            <a href="/home" class="simple-text">
                ATS Management
            </a>
        </div>

        <ul class="nav">
            <li <c:if test="${currSelected == 1}">class="active"</c:if>>
                <a href="/home">
                    <i class="pe-7s-graph"></i>
                    <p>Dashboard</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 2}">class="active"</c:if>>
                <a href="/account">
                    <i class="pe-7s-user"></i>
                    <p>Account</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 3}">class="active"</c:if>>
                <a href="/beacon">
                    <i class="pe-7s-plugin"></i>
                    <p>Beacon</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 4}">class="active"</c:if>>
                <a href="/station">
                    <i class="pe-7s-map-2"></i>
                    <p>Station</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 5}">class="active"</c:if>>
                <a href="/lane">
                    <i class="pe-7s-way"></i>
                    <p>Lane</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 6}">class="active"</c:if>>
                <a href="/price">
                    <i class="pe-7s-cash"></i>
                    <p>Price</p>
                </a>
            </li>            
            <li <c:if test="${currSelected == 7}">class="active"</c:if>>
                <a href="/vehicletype">
                    <i class="pe-7s-ticket"></i>
                    <p>Vehicle Type</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 8}">class="active"</c:if>>
                <a href="/transaction">
                    <i class="pe-7s-news-paper"></i>
                    <p>Transaction</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 9}">class="active"</c:if>>
                <a href="/report">
                    <i class="pe-7s-note"></i>
                    <p>Customer Report</p>
                </a>
            </li>
            <li <c:if test="${currSelected == 10}">class="active"</c:if>>
                <a href="/photoDump">
                    <i class="pe-7s-photo-gallery"></i>
                    <p>Unknown Photo</p>
                </a>
            </li>
            
        </ul>
    </div>
</div>