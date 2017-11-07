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
                                                    "../beacon/get", // get
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
                                                "data": "id",
                                                "visible": false
                                                        // hide the column
														// processID
                                            },
                                            {
                                                "data": "uuid"
                                            },
                                            {
                                                "data": "major"
                                            },
                                            {
                                                "data": "minor"
                                            },
                                            {
                                                "data": "type"
                                            },
                                            {
                                                "data": "laneId",
                                                "visible": false
                                            },
                                            {
                                                "data": "laneName"
                                            },
                                            {
                                                "data": "stationId",
                                                "visible": false
                                            },
                                            {
                                                "data": "stationName"
                                            },
                                            {
                                                "data": "active",
                                                "visible": false
                                            },
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>"
                                                        + "<button class='btn btn-danger glyphicon glyphicon-trash' onclick='openDeleteModal(this)'></button>",
                                            }]
                                    });
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
//                        clearAddForm();
                    });
                });
/*
 * Contain ajax call to perfom update or delete a report
 */
// perform ajax call to save report
function submitAddForm() {
	var beacon;
	var lane = $("#add-form-laneId").val();
	
	var type = $("#add-form-type").val();
	if (type == "BEACON_PAYMENT") {
		type = "0";
	} else {
		type = "1";
	}
	
	var active = $("#add-form-active").val();
    if (active == "Active") {
		active = "1";
	} else {
		active = "0";
	}
	
	if (lane == "0") {
		beacon = {
	            "uuid": $("#add-form-uuid").val(),
	            "major": $("#add-form-major").val(),
	            "minor": $("#add-form-minor").val(),
	            "type": type,
	            "lane": null,
	            "station": {
	            	"id": $("#add-form-stationId").val()
	            },
	            "active": active
	    };
	} else {
		beacon = {
	            "uuid": $("#add-form-uuid").val(),
	            "major": $("#add-form-major").val(),
	            "minor": $("#add-form-minor").val(),
	            "type": type,
	            "lane": {
	            	"id": $("#add-form-laneId").val()
	            },
	            "station": {
	            	"id": $("#add-form-stationId").val()
	            },
	            "active": active
	    };
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../beacon/create",
        data: JSON.stringify(beacon),
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
	var beacon;
	var lane = $("#update-form-laneId").val();
	
	var type = $("#update-form-type").val();
	if (type == "BEACON_PAYMENT") {
		type = "0";
	} else {
		type = "1";
	}
	
	var active = $("#update-form-active").val();
    if (active == "Active") {
		active = "1";
	} else {
		active = "0";
	}
    
	if (lane == "0") {
		beacon = {
				"id": $("#update-form-id").val(),
	            "uuid": $("#update-form-uuid").val(),
	            "major": $("#update-form-major").val(),
	            "minor": $("#update-form-minor").val(),
	            "type": type,
	            "lane": null,
	            "station": {
	            	"id": $("#update-form-stationId").val()
	            },
	            "active": active
	    };
	} else {
		beacon = {
				"id": $("#update-form-id").val(),
	            "uuid": $("#update-form-uuid").val(),
	            "major": $("#update-form-major").val(),
	            "minor": $("#update-form-minor").val(),
	            "type": type,
	            "lane": {
	            	"id": $("#update-form-laneId").val()
	            },
	            "station": {
	            	"id": $("#update-form-stationId").val()
	            },
	            "active": active
	    };
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../beacon/update",
        data: JSON.stringify(beacon),
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
	        "isActive": $("#update-form-isActive").val(),
	        "isEnable": $("#update-form-isEnable").val()
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
    $("#update-form-uuid").val(data.uuid);
    $("#update-form-major").val(data.major);
    $("#update-form-minor").val(data.minor);
    $("#update-form-type").val(data.type);
    $("#update-form-laneId").val(data.laneId);
    $("#update-form-stationId").val(data.stationId);
    $("#update-form-active").val(data.active);
    curr = {
    	"id": data.id,
    	"uuid": data.uuid,
        "major": data.major,
        "minor": data.minor,
        "type": data.type,
        "lane": {
            "id": data.laneId
        },
        "station": {
            "id": data.stationId
        },
        "active": data.active
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

//set data to select tag station from database
$(document).ready(function(){
	$.ajax({
	    type: "GET",
	    contentType: "application/json",
	    url: "../station/get",
	    success: function (result) {
	    	$.each(JSON.parse(result), function (i, item) {
	    		$('#add-form-stationId').append($('<option>', { 
	    			value: item.id,
	    			text : item.name 
	    		}));
	    	});
	    	$.each(JSON.parse(result), function (i, item) {
	    		$('#update-form-stationId').append($('<option>', { 
	    			value: item.id,
	    			text : item.name 
	    		}));
	    	});
	    }
	});
});

//set data to select tag lane from database
$(document).ready(function(){
		  $.ajax({
			    type: "GET",
			    contentType: "application/json",
			    url: "../lane/getByStation",
			    data: {
			    	"stationId": 1
			    },
			    success: function (result) {
			    	$('#add-form-laneId').append($('<option>', { 
		    			value: 0,
		    			text : "-- Select an option --"
		    		}));
			    	$.each(result, function (i, item) {
			    		$('#add-form-laneId').append($('<option>', { 
			    			value: item.id,
			    			text : item.name
			    		}));
			    	});
			    	$('#update-form-laneId').append($('<option>', { 
		    			value: 0,
		    			text : "-- Select an option --"
		    		}));
			    	$.each(result, function (i, item) {
			    		$('#update-form-laneId').append($('<option>', { 
			    			value: item.id,
			    			text : item.name 
			    		}));
			    	});
			    }
		  });
});

// set data to select tag lane from database depend on station
$(document).ready(function(){
	$('#add-form-stationId').on('change', function() {
		  $.ajax({
			    type: "GET",
			    contentType: "application/json",
			    url: "../lane/getByStation",
			    data: {
			    	"stationId": this.value
			    },
			    success: function (result) {
			    	$('#add-form-laneId').html("");
			    	$('#add-form-laneId').append($('<option>', { 
		    			value: 0,
		    			text : "-- Select an option --"
		    		}));
			    	$.each(result, function (i, item) {
			    		$('#add-form-laneId').append($('<option>', { 
			    			value: item.id,
			    			text : item.name
			    		}));
			    	});
			    }
		  });
	});
});

$(document).ready(function(){
	$('#update-form-stationId').on('change', function() {
		  $.ajax({
			    type: "GET",
			    contentType: "application/json",
			    url: "../lane/getByStation",
			    data: {
			    	"stationId": this.value
			    },
			    success: function (result) {
			    	$('#update-form-laneId').html("");
			    	$('#update-form-laneId').append($('<option>', { 
		    			value: 0,
		    			text : "-- Select an option --"
		    		}));
			    	$.each(result, function (i, item) {
			    		$('#update-form-laneId').append($('<option>', { 
			    			value: item.id,
			    			text : item.name 
			    		}));
			    	});
			    }
		  });
	});
});