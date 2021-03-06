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

                        <a href="" data-toggle="modal" data-target="#add-modal"
                           class="btn btn-primary btn-lg pe-7s-add-user pe-5x pe-va" onclick="clearError()"></a>

                        <div id="add-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- add Form using ajax !-->--%>
                                <form id="add-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">New Price</h4>
                                        </div>
                                        <div class="modal-body">
                                        
                                            <div class="form-group">
                                                <label class="control-label">Price:</label> 
                                                <input type="text" class="form-control" id="add-form-price" required /> 
                                                <label id="nameError" class="error"></label>
                                          	</div>
                                                
                                          	<div class="form-group">
                                                <label class="control-label">Station:</label>
                                                <select id="add-form-stationId" class="form-control">
                                                	<option value="1">Cai Lậy</option>
                                                	<option value="2">Tây Ninh</option>
                                                	<option value="3">Bảo Lộc</option>
                                                </select>  
                                                <label id="nameError" class="error"></label>
                                          	</div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Type of Vehicle:</label>
                                                <select id="add-form-vehicletypeId" class="form-control">
                                                	<option value="1">Xe 4-5 chỗ</option>
                                                	<option value="2">Xe 7-8 chỗ</option>
                                                	<option value="3">Xe 13-16 chỗ</option>
                                                	<option value="4">Xe 18-21 chỗ</option>
                                                	<option value="5">Xe tải nhẹ</option>
                                                	<option value="6">Xe tải nặng</option>
                                                	<option value="7">Xe container</option>
                                                </select>  
                                                <label id="nameError" class="error"></label>
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">From Date:</label> 
                                                <input type="date" class="form-control" id="add-form-fromDate" required /> 
                                                <label id="nameError" class="error"></label>
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">Active:</label>
                                                <select id="add-form-active" class="form-control">
                                                	<option value="0">Inactive</option>
                                                	<option value="1">Active</option>
                                                </select>  
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
                                            <h4 class="modal-title">Update Price</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-id" readonly />
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Price:</label> 
                                                <input type="text" class="form-control" id="update-form-price" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
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
                                                <label class="control-label">Type of Vehicle:</label>
                                                <select id="update-form-vehicletypeId" class="form-control">
                                                	<option value="1">Xe 4-5 chỗ</option>
                                                	<option value="2">Xe 7-8 chỗ</option>
                                                	<option value="3">Xe 13-16 chỗ</option>
                                                	<option value="4">Xe 18-21 chỗ</option>
                                                	<option value="5">Xe tải nhẹ</option>
                                                	<option value="6">Xe tải nặng</option>
                                                	<option value="7">Xe container</option>
                                                </select>  
                                                <label id="nameErrorUpdate" class="error"></label>
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">From Date:</label> 
                                                <input type="date" class="form-control" id="update-form-fromDate" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                          	</div>
                                          	
                                          	<div class="form-group">
                                                <label class="control-label">Active:</label>
                                                <select id="update-form-active" class="form-control">
                                                	<option value="0">Inactive</option>
                                                	<option value="1">Active</option>
                                                </select>  
                                                <label id="nameErrorUpdate" class="error"></label>
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
                                            <b>Are you sure to delete this skill</b> <input type="hidden"
                                                                                            id="delete-form-skillId">
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
                                    <th>id</th>
                                    <th class="text-center">Station Name</th>
                                    <th class="text-center">Station Zone</th>
                                    <th class="text-center">Price</th>
                                    <th class="text-center">From Date</th>
                                    <th class="text-center">Station ID</th>
                                    <th class="text-center">VehicleType ID</th>
                                    <th class="text-center">Type of Vehicle</th>
                                    <th class="text-center">Active</th>
                                    <th class="text-center">Update/Delete</th>
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
    src="js/view/price.js"></script>
</html>
