
var table1;
function myFunction() {
    var licensePlate = $("#update-form-correct-licensePlate").val();
    var createdTime = $("#update-form-dateTime").val();
    var df = new Date(createdTime);
    table1 = $('#table1')
            .DataTable(
                    {
                        "lengthMenu": [[5, 10, 20, -1],
                            [5, 10, 20, "All"]],
                        "ajax": {
                            "url":
                                    "../transaction/getTransByLicPlateAndTime",
                            "data": {
                                    "licensePlate": licensePlate,
                                    "createdTime": df.getTime()
                            }, 
                            "dataSrc": "" // returned list
                                    // doesn't have a
                                    // header so dataSrc
                                    // =
                                    // ""
                        },
                        "columns": [// define columns for
                            // the table1
                            // data for the cell from the
                            // returned list
                            
                            {
                                "data": "id",
                                "visible": false
                                        // hide the column
                                        // processID
                            },
                            {
                                "data": "dateTime"
                            },
                            {
                                "data": "status"
                            },
                            {
                                "data": "photo",
                            },
                            {// column for view
                                // detail-update-delete
                                "data": null,
                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='submitResolve(this)'>Resolve</button>"
                            }],
                        "columnDefs": [{
                                "searchable": false,
                                "orderable": false,
                                "targets": 0
                            }]
                    });
    table1.on('order.dt search.dt', function () {
        table1.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

    // handle delete form submit
//    $("#delete-form").submit(function (event) {
//        event.preventDefault();
//        submitDeleteForm();
//    });
//    // handle update form submit
//    $("#update-form").submit(function (event) {
//        event.preventDefault();
//        submitUpdateForm();
//    })

    // $("#add-form").submit(function (event) {
    //     event.preventDefault();
    //     submitAddForm();
    //     clearAddForm();
    // });
}

function jumpToPage(table1, transactionIdRedirect) {
    table1.page.jumpToData(transactionIdRedirect, 1);
    return highlightRow(transactionIdRedirect);
}

function highlightRow(transactionIdRedirect) {
    var selection = $("#table1 #" + transactionIdRedirect);
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
function submitConfirm() {
    var transId = $("#update-form-id").val();
    var licensePlate = $("#update-form-correct-licensePlate").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "../transaction/confirmReport",
        data: {
        	"transId": transId,
        	"licensePlate": licensePlate
        },
        success: function (result) {
            if (result == "fail") {
//                setStatus("Update fail!", "#ff0000");
            } else {
//                setStatus("Update success!", "#00cc00");
            }
        },
        error: function (result) {
            setStatus("Something was wrong! Please check again!", "#ff0000");
        }
    });
    $("#resolve-modal").modal("hide");
    $("#alert").show();
    reloadTable();
//    clearStatus();
}

function submitResolve(element) {
	var data = $("#table1").DataTable().row($(element).parents('tr')).data();
    var transId = data.id;
    var transIdError = $("#update-form-id").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "../transaction/resolveReport",
        data: {
        	"transId": transId,
        	"transIdError": transIdError
        },
        success: function (result) {
            if (result == "fail") {
//                setStatus("Update fail!", "#ff0000");
            } else {
//                setStatus("Update success!", "#00cc00");
            }
        },
        error: function (result) {
            setStatus("Something was wrong! Please check again!", "#ff0000");
        }
    });
    $("#resolve-modal").modal("hide");
    $("#alert").show();
    reloadTable();
//    clearStatus();
}
//// handle delete form submit
//function submitDeleteForm() {
//    var transaction = {
//        "id": $("#delete-form-id").val()
//    };
//    $.ajax({
//        type: "POST",
//        contentType: "application/json",
//        url: "../transaction/deleteReport",
//        data: JSON.stringify(transaction),
//        success: function (result) {
//            if (result == "fail") {
//                setStatus("Delete fail!", "#ff0000");
//            } else {
//                setStatus("Delete success!", "#00cc00");
//            }
//        },
//        error: function (result) {
//            setStatus("This account does not exist! Please check again!", "#ff0000");
//        }
//    });
//    $("#delete-modal").modal("hide");
//    $("#alert").show();
//    reloadTable();
//    clearStatus();
//}
// ajax jquery dataTables reload
function reloadTable() {
    setTimeout(function () {
        $('#table').DataTable().ajax.reload(null, false); // reload without
        // come back to the
        // first page
    }, 200); // reload the table1 after 0.2s
}

// report-home.jsp's script

/*
 * Modal process for report-home.jsp
 */

//// open updateModal
//function openUpdateModal(element) {
//    var data = $("#table1").DataTable().row($(element).parents('tr')).data();
//    var img = document.getElementById("update-form-photo");
//
//    $("#update-form-id").val(data.id);
//    $("#update-form-licensePlate").val(data.licensePlate);
//    $("#update-form-photo").val(data.photo);
//    curr = {
//        "id": data.id,
//        //"photo": "./imgs/plates/"+ data.photo,
//        "photo": img.src = "/imgs/plates/" + data.photo,
//
//        "vehicle": {
//            "licensePlate": data.licensePlate
//        }
//
//    };
//    clearErrorUpdate();
//    $("#update-modal").modal('toggle');
//}
//// open delete confirm modal
//function openDeleteModal(element) {
//    var data = $("#table1").DataTable().row($(element).parents('tr')).data();
//    $("#delete-form-id").val(data.id);
//    $("#delete-modal").modal('toggle');
//}

// clear input of update modal
function clearUpdateForm() {
    $("#update-form-correct-licensePlate").val("");
    $("#delete-modal").modal("hide");
}

function a() {
	
	
}