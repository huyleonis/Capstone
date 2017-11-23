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
                    <div class="alert alert-success" id="alert" style="display: none">
                        <p id="text"></p>
                    </div>
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

                        <div id="add-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- add Form using ajax !-->--%>
                                <form id="add-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">New Transaction</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <label class="control-label">Username:</label>
                                                <input type="text" class="form-control" id="add-form-username" required />
                                                <label id="nameError" class="error"></label>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" id="save" class="btn btn-success">Save</button>
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%-- End div add modal --%>
                        <div id="update-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- Update Form using ajax !-->--%>
                                <form id="update-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Update Report</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-id" readonly />
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Vehicle Photo:</label>
                                                <img id="update-form-photo" class="thumbnail" height="300" width="550" />
                                                <br/>
                                                <label class="control-label">License Plate Recognized: </label>
                                                <input type="text" class="form-control" id="update-form-licensePlate" required readonly/>
                                                <label id="nameErrorUpdate" class="error"></label>
                                                <br/>
                                                <label class="control-label">Correct License Plate: </label>                    
                                                <input type="text" class="form-control" id="update-form-correct-licensePlate" required />
                                                <button onclick="myFunction()">View Transaction</button>
                                                
                                                
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
                                            <button type="submit" id="update" class="btn btn-success">Save</button>
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%-- End div update modal --%>

                        <div id="delete-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">
                                <%--<!-- AJAX delete form-->--%>
                                <form id="delete-form" method="POST" class="form-horizontal">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Delete</h4>
                                        </div>
                                        <div class="modal-body">
                                            <b>Are you sure to delete this report</b> <input type="hidden"
                                                                                             id="delete-form-id">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit"  class="btn btn-success">Yes</button>
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">Cancel</button>
                                        </div>
                                    </div>
                                    <%-- End div modal-content --%>
                                </form>
                            </div>
                        </div>
                        <%-- End div modal delete --%>

                        <table class="table cell-border table-responsive hover"
                               style="text-align: center" id="table">
                            <thead>
                                <tr>
                                    <th class="text-center" style="font-weight: bolder">#</th>
                                    <th class="text-center" style="font-weight: bolder">id </th>
                                    <th class="text-center" style="font-weight: bolder">License Plate</th>
                                    <th class="text-center" style="font-weight: bolder">Created Time</th>
                                    <th class="text-center" style="font-weight: bolder">Status</th>
                                    <th class="text-center" style="font-weight: bolder">Username</th>
                                    <th class="text-center" style="font-weight: bolder">Lane ID</th>
                                    <th class="text-center" style="font-weight: bolder">Station ID</th>
                                    <th class="text-center" style="font-weight: bolder">Photo</th>
                                    <th class="text-center" style="font-weight: bolder"></th>
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
