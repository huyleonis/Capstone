var active_to_button = function (data) {
    if (data == true) {
        return "<button class='label label-success' style='padding: 10px; padding-left: 16px; padding-right: 16px;' onclick = 'changeRole(this)'>Active</button>";
    } else {
        return "<button class='label label-danger' style='padding: 10px;' onclick = 'changeRole(this)'>Deactivate</button>";
    }
};


var checkData = function (data) {
    if (data == null || data == "")
        return "N/A";
    else
        return data;

};

var formatPrice = function(data){
return data = data.toLocaleString('it-IT', {style : 'currency', currency : 'VND'});
};

function changeRole(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var price = {
        "id": data.id
    };
    if (data.active) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/price/deactive",
            data: JSON.stringify(price),
        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/price/active",
            data: JSON.stringify(price),
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
                                                    "../price/get", // get
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
                                                "data": "stationName"
                                            },
                                            {
                                                "data": "stationZone"
                                            },
                                            {
                                                "data": "price",
                                                "render":  formatPrice
                                            },
                                            {
                                                "data": "fromDate"
                                            },
                                            {
                                                "data": "stationId",
                                                "visible": false
                                            },
                                            {
                                                "data": "vehicleTypeId",
                                                "visible": false
                                            },
                                            {
                                                "data": "vehicleType"
                                            },
                                            {
                                                "data": "active",
                                                "render": active_to_button
                                            },
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openUpdateModal(this)'></button>"
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
    var price = {
        "price": $("#add-form-price").val(),
        "station": {
            "id": $("#add-form-stationId").val()
        },
        "vehicleType": {
            "id": $("#add-form-vehicletypeId").val()
        },
        "fromDate": $("#add-form-fromDate").val(),
        "active": $("#add-form-active").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../price/create",
        data: JSON.stringify(price)
    });
    $("#add-modal").modal("hide");
    reloadTable();
}

var curr;
function submitUpdateForm() {
    var price = {
        "id": $("#update-form-id").val(),
        "price": $("#update-form-price").val(),
        "station": {
            "id": $("#update-form-stationId").val()
        },
        "vehicleType": {
            "id": $("#update-form-vehicletypeId").val()
        },
        "fromDate": $("#update-form-fromDate").val(),
        "active": "true"
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "../price/update",
        data: JSON.stringify(price)      
    });
    $("#update-modal").modal("hide");
    reloadTable();
}
// handle delete form submit
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
    $("#update-form-price").val(data.price);
    $("#update-form-stationId").val(data.stationId);
    $("#update-form-vehicletypeId").val(data.vehicleTypeId);
    $("#update-form-fromDate").val(data.fromDate);
    curr = {
        "id": data.id,
        "price": data.price,
        "station": {
            "id": data.stationId
        },
        "vehicletype": {
            "id": data.vehicleTypeId
        },
        "fromDate": data.fromDate,
        "active": "true"
    };
    $("#update-modal").modal('toggle');
}

// clear input of update modal
function clearUpdateForm() {
    $("#update-form-skillName").val("");
    $("#delete-modal").modal("hide");
}
