var active_to_button = function (data, type, full, meta) {
    if (data == true) {
        return "<button class='label label-success' style='padding: 10px; padding-left: 16px; padding-right: 16px;' onclick = 'changeRole(this)'>Active</button>";
    } else {
        return "<button class='label label-danger' style='padding: 10px;' onclick = 'changeRole(this)'>Deactivate</button>";
    }
};

var roleName = function (data, type, full, meta) {
    switch (data) {
        case 1:
            return "Admin";
            break;
        case 2:
            return "Staff";
            break;
        case 3:
            return "Driver";
            break;
        default:
            break;
    }
};

var checkRole = function (data, type, full, meta) {
    if (data == 3) {
        return "<button class='btn btn-success glyphicon glyphicon-edit' disabled></button>";
    } else {
        return "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>";
    }
}

var checkData = function (data, type, full, meta) {
    if (data == null || data == "")
        return "N/A";
    else
        return data;

};

function changeRole(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var account = {
        "id": data.id
    };
    if (data.isActive) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/account/deactive",
            data: JSON.stringify(account)

        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/account/active",
            data: JSON.stringify(account)

        });
    }

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
                                                "data": "username"
                                            },
                                            {
                                                "data": "fullname",
                                                "render": checkData
                                            },
                                            {
                                                "data": "role",
                                                "render": roleName
                                            },
                                            {
                                                "data": "email",
                                                "render": checkData
                                            },
                                            {
                                                "data": "phone",
                                                "render": checkData
                                            },
                                            {
                                                "data": "vehicleId",
                                                "visible": false,
                                                "render": checkData
                                            },
                                            {
                                                "data": "licensePlate",
                                                "render": checkData
                                            },
                                            {
                                                "data": "vehicletypeId",
                                                "visible": false,
                                                "render": checkData
                                            },
                                            {
                                                "data": "numberId",
                                                "visible": false,
                                                "render": checkData
                                            },
                                            {
                                                "data": "isActive",
                                                "render": active_to_button
                                            },
                                            {
                                                "data": "isEnable",
                                                "render": checkData,
                                                "visible": false
                                            },
                                            {// column for view
                                                // detail-update-delete
                                                "data": "role",
//                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>",
                                                "render": checkRole
                                            }],
                                        "columnDefs": [{
                                                "searchable": false,
                                                "orderable": false,
                                                "targets": 0
                                            }]
//                                      "order": [[ 1, 'asc' ]]
                                    });
                    // generate index column
                    table.on('order.dt search.dt', function () {
                        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1;
                        });
                    }).draw();

                    // handle update form submit
                    $("#update-form").submit(function (event) {
                        event.preventDefault();
                        submitUpdateForm();
                    });

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
    var account = {
        "username": $("#add-form-username").val(),
        "password": $("#add-form-password").val(),
        "role": $("#add-form-role").val(),
        "fullname": $("#add-form-fullname").val(),
        "email": $("#add-form-email").val(),
        "phone": $("#add-form-phone").val(),
        "numberId": $("#add-form-numberId").val(),
        "vehicle": null,
        "balance": "0",
        "active": "true",
        "enable": "true"
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../account/create",
        data: JSON.stringify(account)

    });
    $("#add-modal").modal("hide");
    reloadTable();
}

var curr;
function submitUpdateForm() {
    var vehicle;
    var role = $("#update-form-role").val();
    if (role != "3") {
        vehicle = null;
    } else {
        vehicle = {
            "id": $("#update-form-vehicleId").val(),
            "licensePlate": $("#update-form-licensePlate").val(),
            "vehicletype": {
                "id": $("#update-form-typeId").val()
            }
        };
    }
    var account = {
        "id": $("#update-form-id").val(),
        "username": $("#update-form-username").val(),
        "password": $("#update-form-password").val(),
        "role": $("#update-form-role").val(),
        "fullname": $("#update-form-fullname").val(),
        "email": $("#update-form-email").val(),
        "phone": $("#update-form-phone").val(),
        "numberId": $("#update-form-numberId").val(),
        "vehicle": vehicle,
        "balance": $("#update-form-balance").val(),
        "active": $("#update-form-active").val(),
        "enable": $("#update-form-enable").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../account/update",
        data: JSON.stringify(account)      
    });
    $("#update-modal").modal("hide");
    $("#alert").show();
    reloadTable();
    clearStatus();
}
// handle delete form submit

// ajax jquery dataTables reload
function reloadTable() {
    setTimeout(function () {
        $('#table').DataTable().ajax.reload(null, false); // reload without
        // come back to the
        // first page
    }, 200); // reload the table after 0.2s
}


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
    $("#update-form-vehicleId").val(data.vehicleId);
    $("#update-form-licensePlate").val(data.licensePlate);
    $("#update-form-numberId").val(data.numberId);
    $("#update-form-typeId").val(data.vehicletypeId);
    $("#update-form-active").val(data.isActive);
    $("#update-form-enable").val(data.isEnable);
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
        "active": data.isActive,
        "enable": data.isEnable
    };
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


// set data to select tag type of vehicle from database
//$(document).ready(function () {
//    $.ajax({
//        type: "GET",
//        contentType: "application/json",
//        url: "../vehicletype/get",
//        success: function (result) {
//            $.each(JSON.parse(result), function (i, item) {
//                $('#add-form-typeId').append($('<option>', {
//                    value: item.id,
//                    text: item.name
//                }));
//            });
//            $.each(JSON.parse(result), function (i, item) {
//                $('#update-form-typeId').append($('<option>', {
//                    value: item.id,
//                    text: item.name
//                }));
//            });
//        }
//    });
//});
