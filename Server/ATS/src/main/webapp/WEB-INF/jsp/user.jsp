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
                            <p class="form-group">Create account: </p>
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
                                            <h4 class="modal-title" style="color: blue; font-weight: bold">New Account</h4>
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
                                            <!--                                            <div class="form-group hidden">
                                                                                            <label class="control-label">Enable</label> 
                                                                                            <input type="text" class="form-control" id="add-form-enable" value="true"/> 
                                                                                        </div>
                                            
                                                                                        <div class="form-group hidden">
                                                                                            <label class="control-label">Active</label> 
                                                                                            <input type="text" class="form-control" id="add-form-active" value="true"/> 
                                                                                        </div>-->

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
                                            <h4 class="modal-title" style="color: blue; font-weight: bold">Update Account</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-id" readonly />
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Username:</label> 
                                                <input type="text" class="form-control" id="update-form-username" required readonly /> 
                                            </div>

                                            <div class="form-group hidden">
                                                <label class="control-label">Password:</label> 
                                                <input type="password" class="form-control" id="update-form-password" /> 
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Role:</label>
                                                <select id="update-form-role" class="form-control">
                                                    <option value="1">Admin</option>
                                                    <option value="2">Staff</option>
                                                </select>  
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Full Name:</label> 
                                                <input type="text" class="form-control" id="update-form-fullname" /> 

                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Email:</label> 
                                                <input type="email" class="form-control" id="update-form-email" /> 
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Phone:</label> 
                                                <input type="text" class="form-control" id="update-form-phone" /> 
                                            </div>

                                            <div class="form-group">
                                                <label class="control-label">Number ID:</label> 
                                                <input type="text" class="form-control" id="update-form-numberId" />           
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-vehicleId" readonly />
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-licensePlate" readonly />
                                            </div>

                                            <div class="form-group">
                                                <input class="form-control hidden" id="update-form-active" readonly />
                                            </div>

                                            <div class="form-group hidden">
                                                <label class="control-label">Enable</label> 
                                                <input type="text" class="form-control" id="update-form-enable" value="true"/> 
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
                                    <th style="font-size: 15px; color: blue; font-weight: bold">#</th>
                                    <th>id</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Username</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Full Name</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Role</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Email</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Phone</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Vehicle ID</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">License Plate</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Vehicle Type ID</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Number Id</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Active</th>
                                    <th class="text-center" style="font-size: 15px; color: blue; font-weight: bold">Enable</th>
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
    src="js/view/account.js"></script>
</html>
