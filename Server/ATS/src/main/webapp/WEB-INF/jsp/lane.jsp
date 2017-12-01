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

                        <div class="form-inline col-lg-8">
                            <p class="form-group">Create lane: </p>
                            <a href="" data-toggle="modal" data-target="#add-modal"
                               class="btn btn-primary glyphicon glyphicon-plus form-group"></a>

                        </div>
                        <div class="form-inline col-lg-4">
                        </div>

                        <div id="add-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- add Form using ajax !-->--%>
                                <form id="add-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title" style="color: blue; font-weight: bold">New Lane</h4>
                                        </div>
                                        <div class="modal-body">
                                        
                                            <div class="form-group">
                                                <label class="control-label">Name:</label> 
                                                <input type="text" class="form-control" id="add-form-name" required />
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">Station:</label>
                                                <select id="add-form-stationId" class="form-control">
                                                	<option value="1">Cai Lậy</option>
                                                	<option value="2">Tây Ninh</option>
                                                	<option value="3">Bảo Lộc</option>
                                                </select>
                                            </div>
                                            

                                            
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" id="save" class="btn btn-primary" style="font-weight: bold">Save</button>
                                            <button type="button" class="btn btn-warning" style="font-weight: bold"
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
                                            <h4 class="modal-title" style="color: blue; font-weight: bold">Update Lane</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-id" readonly />
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Name:</label> 
                                                <input type="text" class="form-control" id="update-form-name" required />
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">Station:</label>
                                                <select id="update-form-stationId" class="form-control">
                                                	<option value="1">Cai Lậy</option>
                                                	<option value="2">Tây Ninh</option>
                                                	<option value="3">Bảo Lộc</option>
                                                </select>  
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-active" readonly />
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" id="update" class="btn btn-primary" style="font-weight: bold">Save</button>
                                            <button type="button" class="btn btn-warning" style="font-weight: bold"
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
                                    <th>id</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Name</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Station ID</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Active</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Update</th>
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
    src="js/view/lane.js"></script>
</html>
