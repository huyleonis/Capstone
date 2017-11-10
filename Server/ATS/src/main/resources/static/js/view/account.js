var active_to_button = function (data, type, full, meta) {
    if (data == true) {
        return "<button class='label label-success' style='padding: 10px; padding-left: 16px; padding-right: 16px;' onclick = 'changeRole(this)'>Active</button>";
    } else {
        return "<button class='label label-danger' style='padding: 10px;' onclick = 'changeRole(this)'>Deactive</button>";
    }
}
function changeRole(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var skill = {
        "skillId": data.skillId
    };
    if (data.approved) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/skills/deactive-skill",
            data: JSON.stringify(skill),
            success: function (result) {
                if (result == "fail") {
                    setStatus("This skill does not exist! Please check again!", "#ff0000");
                } else {
                    setStatus("Update success!", "#00cc00");
                }
            },
            error: function (result) {
                setStatus("This skill does not exist! Please check again!", "#ff0000");
            }
        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/skills/active-skill",
            data: JSON.stringify(skill),
            success: function (result) {
                if (result == "fail") {
                    setStatus("This skill does not exist! Please check again!", "#ff0000");
                } else {
                    setStatus("Update success!", "#00cc00");
                }
            },
            error: function (result) {
                setStatus("This skill does not exist! Please check again!", "#ff0000");
            }
        });
    }
    $("#alert").show();
    clearStatus();
    reloadTable();
}
$(document)
        .ready(
                function ($) {
                    /*
					 * define dataTables
					 */
                    var index = 1;
                    var table = $('#table')
                            .DataTable(
                                    {
                                        "lengthMenu": [[5, 10, 20, -1],
                                            [5, 10, 20, "All"]], // change
                                        // the
                                        // lengthMenu to
                                        // 5, 10 ,20,
                                        // All record
                                        // per page
                                        // "language": { "search": "",
                                        // "lengthMenu": "Show _MENU_ per page"
                                        // }, //remove
                                        // the Search text and modified the
                                        // select box
                                        // "info": false, //remove the show x to
                                        // x result
                                        // "dom": '<"top"lf>rt<"bottom"p>',
                                        // f - filter input, r - processing, t -
                                        // table, l - lengthMenu, p -
                                        // pagination
                                        // top - top of the table, bottom -
                                        // bottom of the table
                                        "ajax": {
                                            "url":
                                                    "../account/getListAccount", // get
                                            // the
                                            // list
                                            "dataSrc": "" // returned list
                                                    // doesn't have a
                                                    // header so dataSrc
                                                    // =
                                                    // ""
                                        },
                                        "columns": [// define columns for
                                            // the table
                                            // data for the cell from the
                                            // returned list
                                            {
                                                "data": null
                                            },
                                            {
                                                "data": "id",
                                                "visible": false
                                                        // hide the column
														// processID
                                            },
                                            {
                                                "data": "username",
                                                className: "dt-body-left"
                                            },
                                            {
                                                "data": "password",
                                                "visible": false
                                            },
                                            {
                                                "data": "fullname"
                                            },
                                            {
                                            	"data": "role"
                                            },
                                            {
                                                "data": "email"
//                                                "visible": false
                                            },
                                            {
                                                "data": "phone",
                                                className: "dt-body-left"
//                                                "visible": false
                                            },
                                            {
                                                "data": "numberId",
                                                "visible": false
                                            },
                                            {
                                                "data": "vehicleId",
                                                "visible": false
                                            },
                                            {
                                                "data": "balance",
                                                "visible": false
                                            },
                                            {
                                                "data": "licensePlate"
//                                                "visible": false
                                            },
                                            {
                                                "data": "vehicletypeId",
                                                "visible": false
                                            },
                                            {
                                                "data": "active",
                                                "visible": false
                                            },
                                            {
                                                "data": "enable",
                                                "visible": false
                                            },
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success' onclick='openUpdateModal(this)'>Update</button>"
//                                                        + "<button class='btn btn-danger' onclick='openDeleteModal(this)'>Delete</button>",
                                            }],
                                        "columnDefs": [ {
                                            "searchable": false,
                                            "orderable": false,
                                            "targets": 0
                                        },{
                                            className: "dt-body-left",
                                            "targets": [2, 4, 5, 6, 10, 11]
                                        } ],
                                        "order": [[ 1, 'asc' ]]
                                    });
                    table.on( 'order.dt search.dt', function () {
                        table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                            cell.innerHTML = i+1;
                        } );
                    } ).draw();

                    // handle delete form submit
                    $("#delete-form").submit(function (event) {
                        event.preventDefault();
                        submitDeleteForm();
                    });
                    // handle update form submit
                    $("#update-form").submit(function (event) {
                        event.preventDefault();
                        submitUpdateForm();
                    })

                    $("#add-form").submit(function (event) {
                        event.preventDefault();
                        submitAddForm();
                        clearAddForm();
                    });
                });
/*
 * Contain ajax call to perfom update or delete a report
 */
// perform ajax call to save report
function submitAddForm() {
	var account;
	
    var role = $("#add-form-role").val();
    if (role == "Admin") {
		role = "1";
	} else if (role == "Staff") {
		role = "2";
	} else {
		role = "3";
	}
    
    var active = $("#add-form-active").val();
    if (active == "Active") {
		active = "1";
	} else {
		active = "0";
	}
    
    var enable = $("#add-form-enable").val();
    if (enable == "Enable") {
    	enable = "1";
	} else {
		enable = "0";
	}
    
    var licensePlate = $("#add-form-licensePlate").val();
    
    if (role != "3" || licensePlate == "") {
    	account = {
                "username": $("#add-form-username").val(),
                "password": $("#add-form-password").val(),
                "role": role,
                "fullname": $("#add-form-fullname").val(),
                "email": $("#add-form-email").val(),
                "phone": $("#add-form-phone").val(),
                "numberId": $("#add-form-numberId").val(),
                "vehicle": null,
                "balance": "0",
                "active": active,
                "enable": enable
        };
	} else {
		account = {
		        "username": $("#add-form-username").val(),
		        "password": $("#add-form-password").val(),
		        "role": role,
		        "fullname": $("#add-form-fullname").val(),
		        "email": $("#add-form-email").val(),
		        "phone": $("#add-form-phone").val(),
		        "numberId": $("#add-form-numberId").val(),
		        "vehicle": {
		        		"licensePlate": $("#add-form-licensePlate").val(),
		        		"vehicletype": {
		        			"id": $("#add-form-typeId").val()
		        		}
		        },
		        "balance": $("#add-form-balance").val(),
		        "active": active,
		        "enable": enable
		    };
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../account/create",
        data: JSON.stringify(account),
        success: function (result) {
            if (result == "fail") {
                setStatus("Something was wrong! Please check again!", "#ff0000");
            } else {
                setStatus("Add success!", "#00cc00");
            }
        },
        error: function (result) {
            setStatus("Something was wrong! Please check again!", "#ff0000");
        }
    });
    $("#add-modal").modal("hide");
    $("#alert").show();
    reloadTable();
    clearStatus();
}

var curr;
function submitUpdateForm() {
	var account;
	var role = $("#update-form-role").val();
    if (role == "Admin") {
		role = "1";
	} else if (role == "Staff") {
		role = "2";
	} else {
		role = "3";
	}
    
    var active = $("#update-form-active").val();
    if (active == "Active") {
		active = "1";
	} else {
		active = "0";
	}
    
    var enable = $("#update-form-enable").val();
    if (enable == "Enable") {
    	enable = "1";
	} else {
		enable = "0";
	}
    
    var licensePlate = $("#update-form-licensePlate").val();
    
	if (role != "3" || licensePlate == "") {
		account = {
				"id": $("#update-form-id").val(),
		        "username": $("#update-form-username").val(),
		        "password": $("#update-form-password").val(),
		        "role": role,
		        "fullname": $("#update-form-fullname").val(),
		        "email": $("#update-form-email").val(),
		        "phone": $("#update-form-phone").val(),
		        "numberId": $("#update-form-numberId").val(),
		        "vehicle": null,
		        "balance": "0",
		        "active": active,
		        "enable": enable
		    };
	} else {
		account = {
				"id": $("#update-form-id").val(),
		        "username": $("#update-form-username").val(),
		        "password": $("#update-form-password").val(),
		        "role": role,
		        "fullname": $("#update-form-fullname").val(),
		        "email": $("#update-form-email").val(),
		        "phone": $("#update-form-phone").val(),
		        "numberId": $("#update-form-numberId").val(),
		        "vehicle": {
		        		"licensePlate": $("#update-form-licensePlate").val(),
		        		"vehicletype": {
		        			"id": $("#update-form-typeId").val()
		        		}
		        },
		        "balance": $("#update-form-balance").val(),
		        "active": active,
		        "enable": enable
		    };
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../account/update",
        data: JSON.stringify(account),
        success: function (result) {
            if (result == "fail") {
                setStatus("Update fail!", "#ff0000");
            } else {
                setStatus("Update success!", "#00cc00");
            }
        },
        error: function (result) {
            setStatus("Something was wrong! Please check again!", "#ff0000");
        }
    });
    $("#update-modal").modal("hide");
    $("#alert").show();
    reloadTable();
    clearStatus();
}
// handle delete form submit
function submitDeleteForm() {
	var account = {
			"id": $("#update-form-id").val(),
	        "username": $("#update-form-username").val(),
	        "password": $("#update-form-password").val(),
	        "role": $("#update-form-role").val(),
	        "fullname": $("#update-form-fullname").val(),
	        "email": $("#update-form-email").val(),
	        "phone": $("#update-form-phone").val(),
	        "numberId": $("#update-form-numberId").val(),
	        "vehicle": {
	        		"id": $("#update-form-vehicleId").val(),
	        		"licensePlate": $("#update-form-licensePlate").val(),
	        		"vehicletype": {
	        			"id": $("#update-form-typeId").val()
	        		}
	        },
	        "balance": $("#update-form-balance").val(),
	        "active": $("#update-form-active").val(),
	        "enable": $("#update-form-enable").val()
	    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../account/delete",
        data: JSON.stringify(account),
        success: function (result) {
            if (result == "fail") {
                setStatus("Delete fail!", "#ff0000");
            } else {
                setStatus("Delete success!", "#00cc00");
            }
        },
        error: function (result) {
            setStatus("This account does not exist! Please check again!", "#ff0000");
        }
    });
    $("#delete-modal").modal("hide");
    $("#alert").show();
    reloadTable();
    clearStatus();
}
// ajax jquery dataTables reload
function reloadTable() {
    setTimeout(function () {
        $('#table').DataTable().ajax.reload(null, false); // reload without
        // come back to the
        // first page
    }, 200); // reload the table after 0.2s
}

// report-home.jsp's script

/*
 * Modal process for report-home.jsp
 */

// open updateModal
function openUpdateModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    $("#update-form-id").val(data.id);
    $("#update-form-username").val(data.username);
    $("#update-form-password").val(data.password);
    $("#update-form-role").val(data.role);
    $("#update-form-fullname").val(data.fullname);
    $("#update-form-email").val(data.email);
    $("#update-form-phone").val(data.phone);
    $("#update-form-numberId").val(data.numberId);
    $("#update-form-vehicleId").val(data.vehicleId);
    $("#update-form-licensePlate").val(data.licensePlate);
    $("#update-form-typeId").val(data.vehicletypeId);
    $("#update-form-balance").val(data.balance);
    $("#update-form-active").val(data.active);
    $("#update-form-enable").val(data.enable);
    curr = {
        "id": data.id,
        "username": data.username,
        "password": data.password,
        "role": data.role,
        "fullname": data.fullname,
        "email": data.email,
        "phone": data.phone,
        "numberId": data.numberId,
        "vehicle": {
        		"id": data.vehicleId,
        		"licensePlate": data.licensePlate,
        		"vehicletype": {
        			"id": data.vehicletypeId
        		}
        },
        "balance": data.balance,
        "active": data.active,
        "enable": data.enable
    };
    clearErrorUpdate();
    $("#update-modal").modal('toggle');
}
// open delete confirm modal
function openDeleteModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    $("#delete-form-skillId").val(data.skillId);
    $("#delete-modal").modal('toggle');
}

// clear input of add modal
function clearAddForm() {
	$("#add-form-username").val("");
    $("#add-form-password").val("");
    $("#add-form-fullname").val("");
    $("#add-form-email").val("");
    $("#add-form-phone").val("");
    $("#add-form-numberId").val("");
    $("#add-form-licensePlate").val("");
    $("#add-form-balance").val(0);
}

// clear input of update modal
function clearUpdateForm() {
    $("#update-form-skillName").val("");
    $("#delete-modal").modal("hide");
}

function clearError() {
    document.getElementById("nameError").innerHTML = "";
    document.getElementById("codeError").innerHTML = "";
    document.getElementById("code").value = "";
    document.getElementById("name").value = "";
    $("#save").prop('disabled', false);
}

function clearErrorUpdate() {
    document.getElementById("nameErrorUpdate").innerHTML = "";
    document.getElementById("codeErrorUpdate").innerHTML = "";
    $("#update").prop('disabled', false);
}

// clear the div inform save delete status
function clearStatus() {
    setTimeout(function () {
        $("#alert").fadeOut(1000); // slowly faded div status
        $("#text").html();
    }, 3000);
}

// set status of save update form or delete-form
function setStatus(result, background) {
    $("#text").html(result);
    var a = document.getElementById("alert");
    a.style.backgroundColor = background;
}

// $("add-form").validate();
// open report details page
function checkValidateNameAdd() {
    var skill = {
        skillName: $("#add-form-skillName").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/JSON",
        url: contextPath + "/skills/check-duplicate-add-name",
        data: JSON.stringify(skill),
        success: function (result) {
            if (result == "duplicate") {
                $("#nameError").html("Skill already existed");
                $("#save").prop('disabled', true);
            } else if (result == "overLength") {
                $("#nameError").html("Skill name must consist of at least 1 and maximum 50 characters");
                $("#save").prop('disabled', true);
            } else {
                $("#nameError").html("");
            }
        }
    });
    $("#save").prop('disabled', false);
}

function checkValidateNameUpdate() {
    var skill = {
        skillName: $("#update-form-skillName").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/JSON",
        url: contextPath + "/skills/check-duplicate-update-name/" + curr.skillName.replace(/[/]/g, '_'),
        data: JSON.stringify(skill),
        success: function (result) {
            if (result == "duplicate") {
                $("#nameErrorUpdate").html("Skill already existed");
                $("#update").prop('disabled', true);
            } else if (result == "overLength") {
                $("#nameErrorUpdate").html("Skill name must consist of at least 1 and maximum 50 characters");
                $("#update").prop('disabled', true);
            } else {
                $("#nameErrorUpdate").html("");
            }
        }
    });
    $("#update").prop('disabled', false);
}

function clearError() {
    document.getElementById("nameError").innerHTML = "";
//    document.getElementById("add-form-skillName").value = "";
    $("#save").prop('disabled', false);
}

function clearErrorUpdate() {
    document.getElementById("nameErrorUpdate").innerHTML = "";
    $("#update").prop('disabled', false);
}

// set data to select tag type of vehicle from database
$(document).ready(function(){
	$.ajax({
	    type: "GET",
	    contentType: "application/json",
	    url: "../vehicletype/get",
	    success: function (result) {
	    	$.each(JSON.parse(result), function (i, item) {
	    		$('#add-form-typeId').append($('<option>', {
	    			value: item.id,
	    			text : item.name
	    		}));
	    	});
	    	$.each(JSON.parse(result), function (i, item) {
	    		$('#update-form-typeId').append($('<option>', {
	    			value: item.id,
	    			text : item.name
	    		}));
	    	});
	    }
	});
});
