var active_to_button = function (data, type, full, meta) {
    if (data == true) {
        return "<button class='label label-success' style='padding: 10px; padding-left: 16px; padding-right: 16px;' onclick = 'changeRole(this)'>Active</button>";
    } else {
        return "<button class='label label-danger' style='padding: 10px;' onclick = 'changeRole(this)'>Deactive</button>";
    }
};
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
var table;
$(document)
    .ready(
        function ($) {
            /*
             * define dataTables
             */
        	
            var index = 1;
            table = $('#table')
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
                                "../transaction/getReportDetail", // get
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
                                "data": "id"
//                                "visible": false
                                // hide the column
                                // processID
                            },
                            {
                                "data": "licensePlate"
                            },
                            {
                                "data": "dateTime"
                            },
                            {
                                "data": "status"
                            },
                            {
                                "data": "username"
                            },
                            {
                                "data": "laneId",
                                "visible": false
                            },
                            {
                                "data": "stationId",
                                "visible": false
                            },
                            {// column for view
                                // detail-update-delete
                                "data": null,
                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>"
                                + "<button class='btn btn-danger glyphicon glyphicon-trash' onclick='openDeleteModal(this)'></button>",
                            }],
                        "columnDefs": [ {
                            "searchable": false,
                            "orderable": false,
                            "targets": 0
                        } ],
                        "order": [[ 3, 'desc']],
                        "createdRow": function( row, data, dataIndex ) {
                            $(row).attr('id', data.id);
                        },
                        "scroller": true,
                        "initComplete": function() {
                            if (typeof transactionIdRedirect !== 'undefined') {
                            	if (transactionIdRedirect != null || 
                            		transactionIdRedirect != "") {
                            		
                            		jumpToPage(table, transactionIdRedirect);
                                	
                                	firebase.database().ref().child('reports').child(keyRedirect).child('status').set('read');	
								}
                			}
						}
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

            // $("#add-form").submit(function (event) {
            //     event.preventDefault();
            //     submitAddForm();
            //     clearAddForm();
            // });
        });

function jumpToPage(table, transactionIdRedirect) {
	table.page.jumpToData(transactionIdRedirect, 1);
	return highlightRow(transactionIdRedirect);
}

function highlightRow(transactionIdRedirect) {
	var selection = $("#table #" + transactionIdRedirect);
	$("tr[role='row']").removeClass("selectedRow");
	selection.addClass("selectedRow");
}

/*
 * Contain ajax call to perfom update or delete a report
 */
// perform ajax call to save report
function submitAddForm() {
    var account = {
        "username": $("#add-form-username").val(),
        "password": $("#add-form-password").val(),
        "role": $("#add-form-role").val(),
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
        "isActive": $("#add-form-isActive").val(),
        "isEnable": $("#add-form-isEnable").val()
    };
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
    var laneId = $("#update-form-laneId").val();
    if (laneId == "0"){
        laneId = null;
    }
    var transaction = {
        "id": $("#update-form-id").val(),
        "vehicle": {
            "licensePlate": $("#update-form-licensePlate").val()
        }
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../transaction/updateReport",
        data: JSON.stringify(transaction),
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
    var transaction = {
        "id": $("#delete-form-id").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../transaction/deleteReport",
        data: JSON.stringify(transaction),
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
    $("#update-form-licensePlate").val(data.licensePlate);
    curr = {
        "id": data.id,
        "vehicle": {
            "licensePlate": data.licensePlate
        }
    };
    clearErrorUpdate();
    $("#update-modal").modal('toggle');
}
// open delete confirm modal
function openDeleteModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    $("#delete-form-id").val(data.id);
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
    document.getElementById("add-form-skillName").value = "";
    $("#save").prop('disabled', false);
}

function clearErrorUpdate() {
    document.getElementById("nameErrorUpdate").innerHTML = "";
    $("#update").prop('disabled', false);
}
