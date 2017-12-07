
function myFunction() {
    var licensePlate = $("#update-form-correct-licensePlate").val();
    var createdTime = $("#update-form-dateTime").val();
    var df = new Date(createdTime);
    var table1 = $('#table1')
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
                                "defaultContent": "<button class='btn btn-success' onclick='submitResolve(this)'>Resolve</button>"
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

    $("#resolve-modal").submit(function (event) {
        event.preventDefault();
        submitResolve();
    });

}



var curr;
function submitConfirm() {
    var licensePlate = $("#update-form-correct-licensePlate").val();
    var laneId = 1;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "../transaction/makeManualPayment/" + licensePlate + "/" + laneId
//        data: {
//        	"transId": transId,
//        	"licensePlate": licensePlate
//        },

    });
    $("#resolve-modal").modal("hide");
    reloadTable();
}

function submitResolve(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var data1 = $("#table1").DataTable().row($(element).parents('tr')).data();
    var transId = data1.id;
    var photo = data.photoName;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "../transaction/updatePhoto",
        data: {
            "transId": transId,
            "photo": photo
        }
    });
    $("#resolve-modal").modal("hide");
    reloadTable();
}

function reloadTable() {
    setTimeout(function () {
        $('#table').DataTable().ajax.reload(null, false); // reload without
        // come back to the
        // first page
    }, 200); // reload the table1 after 0.2s
}

function clearUpdateForm() {
    $("#update-form-correct-licensePlate").val("");
    $("#delete-modal").modal("hide");
}