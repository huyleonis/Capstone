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
                           class="btn btn-primary glyphicon glyphicon-plus" onclick="clearError()"></a>

                        <div id="add-modal" class="modal fade" role="dialog"
                             data-backdrop="false">
                            <div class="modal-dialog">

                                <%--<!-- Modal content-->--%>
                                <%--<!-- add Form using ajax !-->--%>
                                <form id="add-form">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">New Account</h4>
                                        </div>
                                        <div class="modal-body">
                                        
                                            <div class="form-group">
                                                <label class="control-label">Username:</label> 
                                                <input type="text" class="form-control" id="add-form-username" required /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group hidden">
                                                <label class="control-label">Password:</label> 
                                                <input type="password" class="form-control" id="add-form-password" /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group">
                                                <label class="control-label">Role:</label>
                                                <select id="add-form-role" class="form-control">
                                                	<option value="1">Admin</option>
                                                	<option value="2">Staff</option>
                                                </select>  
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group">
                                                <label class="control-label">Full Name:</label> 
                                                <input type="text" class="form-control" id="add-form-fullname" required /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group">
                                                <label class="control-label">Email:</label> 
                                                <input type="email" class="form-control" id="add-form-email" required /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group">
                                                <label class="control-label">Phone:</label> 
                                                <input type="text" class="form-control" id="add-form-phone" required /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                                
                                                <div class="form-group">
                                                <label class="control-label">Number ID:</label> 
                                                <input type="text" class="form-control" id="add-form-numberId" required /> 
                                                <label id="nameError" class="error"></label>
                                                </div>
                                               
                                                <div class="form-group">
                                                <label class="control-label">Enable:</label>
                                                <select id="add-form-enable" class="form-control">
                                                	<option value="0">Disable</option>
                                                	<option value="1">Enable</option>
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
                                            <h4 class="modal-title">Update Account</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-id" readonly />
                                            </div>
                                            <%-- <div class="form-group">
                                                <label class="control-label">Username:</label> <input
                                                    class="form-control" id="update-form-username"
                                                    onblur="checkValidateNameUpdate()" required /> <label
                                                    id="nameErrorUpdate" class="error"></label>
                                            </div> --%>
                                            <div class="form-group">
                                                <label class="control-label">Username:</label> 
                                                <input type="text" class="form-control" id="update-form-username" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group hidden">
                                                <label class="control-label">Password:</label> 
                                                <input type="password" class="form-control" id="update-form-password" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                            <label class="control-label">Role:</label>
                                                <select id="update-form-role" class="form-control">
                                                	<option value="1">Admin</option>
                                                	<option value="2">Staff</option>
                                                	<option value="3">Driver</option>
                                                </select>  
											<label id="nameErrorUpdate" class="error"></label>
											</div>
											
											<div class="form-group">
                                                <label class="control-label">Full Name:</label> 
                                                <input type="text" class="form-control" id="update-form-fullname" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Email:</label> 
                                                <input type="email" class="form-control" id="update-form-email" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Phone:</label> 
                                                <input type="text" class="form-control" id="update-form-phone" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Number ID:</label> 
                                                <input type="text" class="form-control" id="update-form-numberId" required /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-vehicleId" readonly />
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">License Plate:</label> 
                                                <input type="text" class="form-control" id="update-form-licensePlate" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Type of Vehicle:</label>
                                                <select id="update-form-typeId" class="form-control">
                                                	
                                                </select>  
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label class="control-label">Balance:</label> 
                                                <input type="text" class="form-control" id="update-form-balance" required /> 
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
                                            
                                            <div class="form-group">
                                                <label class="control-label">Enable:</label>
                                                <select id="update-form-enable" class="form-control">
                                                	<option value="0">Disable</option>
                                                	<option value="1">Enable</option>
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
                                    <th>#</th>
                                    <th>id</th>
                                    <th class="text-center">Username</th>
                                    <th class="text-center">Full Name</th>
                                    <th class="text-center">Role</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Phone</th>
                                    <th class="text-center">Number ID</th>
                                    <th class="text-center">Vehicle ID</th>
                                    <th class="text-center">Balance</th>
                                    <th class="text-center">License Plate</th>
                                    <th class="text-center">Vehicle Type ID</th>
                                    <th class="text-center">Active</th>
                                    <th class="text-center">Enable</th>
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
    src="js/view/account.js"></script>
</html>
