var active_to_button = function (data, type, full, meta) {
    if (data == true) {
        return "<button class='label label-success' style='padding: 10px; padding-left: 16px; padding-right: 16px;' onclick = 'changeRole(this)'>Active</button>";
    } else {
        return "<button class='label label-danger' style='padding: 10px;' onclick = 'changeRole(this)'>Deactive</button>";
    }
};

var checkData = function (data, type, full, meta) {
    if (data == null || data == "")
        return "N/A";
    else
        return data;

};

function changeRole(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var lane = {
        "id": data.id
    };
    if (data.active) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/lane/deactive",
            data: JSON.stringify(lane),

        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/lane/active",
            data: JSON.stringify(lane),

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
                                                    "../lane/get", // get
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
                                                "data": "name"
                                            },
                                            {
                                                "data": "stationId"
                                            },
                                            {
                                                "data": "active",
                                                "render": active_to_button
                                            },
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>"
                                            }],
                                        "columnDefs": [{
                                            "searchable": false,
                                            "orderable": false,
                                            "targets": 0
                                        }]
                                    });
                    // generate index column
                    table.on('order.dt search.dt', function () {
                        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1;
                        });
                    }).draw();

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
	var lane = {
	        "name": $("#add-form-name").val(),
	        "station": {
	        	"id": $("#add-form-stationId").val()
	        },
	        "active": "true"
	};
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../lane/create",
        data: JSON.stringify(lane),
        success: function (result) {
        },
    });
    $("#add-modal").modal("hide");
    $("#alert").show();
    reloadTable();
}

var curr;
function submitUpdateForm() {
	var lane = {
			"id": $("#update-form-id").val(),
	        "name": $("#update-form-name").val(),
	        "station": {
	        	"id": $("#update-form-stationId").val()
	        },
	        "active": $("#update-form-active").val()
	};
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../lane/update",
        data: JSON.stringify(lane),
    });
    $("#update-modal").modal("hide");
    $("#alert").show();
    reloadTable();
}

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
    $("#update-form-name").val(data.name);
    $("#update-form-stationId").val(data.stationId);
    $("#update-form-active").val(data.active);
    curr = {
        "id": data.id,
        "name": data.name,
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
