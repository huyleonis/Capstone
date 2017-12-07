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
    var beacon = {
        "id": data.id
    };
    if (data.active) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/beacon/deactive",
            data: JSON.stringify(beacon),

        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/beacon/active",
            data: JSON.stringify(beacon),

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
                                                "data": null
                                            },
                                            {
                                                "data": "id",
                                                "visible": false
                                            },
                                            {
                                                "data": "uuid",
                                                "visible": false
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
                                                "render": checkData
                                            },
                                            {
                                                "data": "stationId",
                                                "render": checkData
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
	var lane = $("#add-form-laneId").val();
	if (lane == "0") {
		lane = null;
	} else {
		lane = {
            "id": $("#add-form-laneId").val()
        };
	}
    var beacon = {
        "uuid": $("#add-form-uuid").val(),
        "major": $("#add-form-major").val(),
        "minor": $("#add-form-minor").val(),
        "type": $("#add-form-type").val(),
        "lane": lane,
        "station": {
            "id": $("#add-form-stationId").val()
        },
        "active": "true"
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../beacon/create",
        data: JSON.stringify(beacon)      
    });
    $("#add-modal").modal("hide");
    $("#alert").show();
    reloadTable();
}

var curr;
function submitUpdateForm() {
	var lane = $("#update-form-laneId").val();
	if (lane == "0") {
		lane = null;
	} else {
		lane = {
            "id": $("#update-form-laneId").val()
        };
	}
	var beacon = {
        "id": $("#update-form-id").val(),
        "uuid": $("#update-form-uuid").val(),
        "major": $("#update-form-major").val(),
        "minor": $("#update-form-minor").val(),
        "type": $("#update-form-type").val(),
        "lane": lane,
        "station": {
            "id": $("#update-form-stationId").val()
        },
        "active": $("#update-form-active").val()
    };
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
    $("#update-form-uuid").val(data.uuid);
    $("#update-form-major").val(data.major);
    $("#update-form-minor").val(data.minor);
    $("#update-form-type").val(data.type);
    $("#update-form-laneId").val(data.laneId);
    $("#update-form-stationId").val(data.stationId);

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
        "active": 'true'
    };
    $("#update-modal").modal('toggle');
}

// clear input of update modal
function clearUpdateForm() {
    $("#update-form-skillName").val("");
    $("#delete-modal").modal("hide");
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
