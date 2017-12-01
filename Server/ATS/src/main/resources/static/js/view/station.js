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
    var station = {
        "id": data.id
    };
    if (data.active) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/station/deactive",
            data: JSON.stringify(station)
        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/station/active",
            data: JSON.stringify(station)
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
                                                    "../station/get", // get
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
                                                "data": "location",
                                                "visible": false
                                            },
                                            {
                                                "data": "zone"
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

                    // handle update form submit
                    $("#update-form").submit(function (event) {
                        event.preventDefault();
                        submitUpdateForm();
                    }),

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
    var station = {
        "name": $("#add-form-name").val(),
        "location": $("#add-form-location").val(),
        "zone": $("#add-form-zone").val(),
        "active": "true"
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../station/create",
        data: JSON.stringify(station)
    });
    $("#add-modal").modal("hide");
    $("#alert").show();
    reloadTable();
}

var curr;
function submitUpdateForm() {
    var station = {
        "id": $("#update-form-id").val(),
        "name": $("#update-form-name").val(),
        "location": $("#update-form-location").val(),
        "zone": $("#update-form-zone").val(),
        "active": "true"
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../station/update",
        data: JSON.stringify(station)
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
    $("#update-form-location").val(data.location);
    $("#update-form-zone").val(data.zone);
    curr = {
        "id": data.id,
        "name": data.name,
        "location": data.locaton,
        "zone": data.zone,
        "active": "true"
    };
    $("#update-modal").modal('toggle');
}

