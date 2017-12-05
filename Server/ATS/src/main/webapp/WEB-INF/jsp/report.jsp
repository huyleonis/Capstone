<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <c:import url="header.jsp"/>

    <body>

        <div class="wrapper">
            <c:import url="menu.jsp"/>


            <div class="main-panel">
                <c:import url="navbar.jsp"/>
                <div class="content">
                    
                    <div class="container-fluid">

                        <button data-name="1510500714164"
                                class="btn btn-primary btn-lg pe-7s-add-user pe-5x pe-va hidden"></button>
                        <c:set value="${transactionId}" var="transactionId"></c:set>
                        <c:set value="${key}" scope="session" var="key"></c:set>
                        <c:if test="${not empty transactionId}">
                            <script>
                                var transactionIdRedirect = '${transactionId}';
                                var keyRedirect = '${key}';
                                console.log(transactionIdRedirect);
                            </script>
                        </c:if>

                        
                        <%-- End div add modal --%>
                        <div id="resolve-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- Update Form using ajax !-->--%>
                                <form id="update-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Resolve Report</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                            	<input class="form-control hidden" id="update-form-id" hidden="" readonly />
                                                <label class="control-label">Vehicle Photo:</label>
                                                <img id="update-form-photo" class="thumbnail" height="300" width="550" />
                                                <br/>
                                                <label class="control-label">License Plate Recognized: </label>
                                                <input type="text" class="form-control" id="update-form-licensePlate" readonly/>
                                                <label id="nameErrorUpdate" class="error"></label>
                                                <br/>
                                                <label class="control-label">Correct License Plate: </label>                    
                                                <input type="text" class="form-control" id="update-form-correct-licensePlate" required />
                                                <br/>
                                                <label class="control-label">Date tIme: </label>                    
                                                <input type="text" class="form-control" id="update-form-dateTime" readonly/>
                                                <br/>
                                                <button onclick="myFunction()" type="submit" class="btn btn-success">View Transaction</button>
                                                
                                                
                                                <table class="table table-striped" id="table1">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-center" style="font-weight: bolder">id </th>
                                                            <th class="text-center" style="font-weight: bolder">Created Time</th>
                                                            <th class="text-center" style="font-weight: bolder">Status</th>                                                    
                                                            <th class="text-center" style="font-weight: bolder">Photo</th>
                                                            <th class="text-center" style="font-weight: bolder"></th>
                                                                <%--<th class="pull-left">Delete/Update</th>--%>
                                                        </tr>
                                                    </thead>

                                                </table>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                        	<button class='btn btn-primary' type="submit" onclick='submitConfirm()'>Confirm</button>
                                            <button onclick = "clearUpdateForm()" type="button" class="btn btn-default"
                                                    data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%-- End div update modal --%>

                        

                        <table class="table cell-border table-responsive hover"
                               style="text-align: center" id="table">
                            <thead>
                                <tr>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">#</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">id </th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">License Plate</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Created Time</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Status</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Username</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Lane ID</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Station ID</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Photo</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Resolve</th>
                                        <%--<th class="pull-left">Delete/Update</th>--%>
                                </tr>
                            </thead>

                        </table>

                    </div>
                </div>



                <c:import url="footer.jsp"/>
            </div>

    </body>

    <script
    src="js/view/report.js"></script>
    <script
    src="js/view/transactionReport.js"></script>
</html>
