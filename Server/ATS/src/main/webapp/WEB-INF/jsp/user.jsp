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

                        <a href="" data-toggle="modal" data-target="#add-modal"
                           class="btn btn-primary glyphicon glyphicon-plus"></a>

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
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Password:</label> 
                                                <input type="password" class="form-control" id="add-form-password" /> 
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Role:</label>
                                                <select id="add-form-role" class="form-control">
                                                    <option value="1">Admin</option>
                                                    <option value="2">Staff</option>
                                                </select>  
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Full Name:</label> 
                                                <input type="text" class="form-control" id="add-form-fullname" /> 
                                                <label id="nameError" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Email:</label> 
                                                <input type="email" class="form-control" id="add-form-email" /> 
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Phone:</label> 
                                                <input type="text" class="form-control" id="add-form-phone" /> 
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Number ID:</label> 
                                                <input type="text" class="form-control" id="add-form-numberId" /> 
                                                
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Enable:</label>
                                                <select id="add-form-enable" class="form-control">
                                                    <option value="false">Disable</option>
                                                    <option value="true">Enable</option>
                                                </select>  
                                                                                         
                                            </div>
                                            
                                             <div class="form-group hidden">
                                                <label class="control-label">Active</label> 
                                                <input type="text" class="form-control" id="add-form-active" value="true"/> 
                                                
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
                                                </select>  
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Full Name:</label> 
                                                <input type="text" class="form-control" id="update-form-fullname" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Email:</label> 
                                                <input type="email" class="form-control" id="update-form-email" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Phone:</label> 
                                                <input type="text" class="form-control" id="update-form-phone" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Number ID:</label> 
                                                <input type="text" class="form-control" id="update-form-numberId" /> 
                                                <label id="nameErrorUpdate" class="error"></label>
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-vehicleId" readonly />
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
                                    <th class="text-center">Vehicle ID</th>
                                    <th class="text-center">License Plate</th>
                                    <th class="text-center">Vehicle Type ID</th>
                                    <th class="text-center">Active</th>
                                    <th class="text-center">Enable</th>
                                    <th class="text-center">Update</th>
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
