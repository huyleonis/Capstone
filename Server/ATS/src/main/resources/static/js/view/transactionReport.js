


function myFunction() {
    var licensePlate = $("#update-form-correct-licensePlate").val();
    var createdTime = $("#update-form-dateTime").val();
    var df = new Date(createdTime);
    var table1 = $('#table1')
            .DataTable(
                    {
                        "lengthMenu": [[5, 10, 20, -1],
                            [5, 10, 20, "All"]]
                        ,
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
                                "data": "photo"
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
        }       
    });
    $("#resolve-modal").modal("hide");
    reloadTable();
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
        }      
    });
    $("#resolve-modal").modal("hide");
    reloadTable();
}
// ajax jquery dataTables reload

function reloadTable() {
    setTimeout(function () {
        $('#table').DataTable().ajax.reload(null, false); // reload without
        // come back to the
        // first page
    }, 200); // reload the table1 after 0.2s
}

// clear input of update modal
function clearUpdateForm() {
    $("#update-form-correct-licensePlate").val("");
    $("#delete-modal").modal("hide");
}

function submitDismiss() {
    var transId = $("#update-form-id").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "../transaction/dismissReport",
        data: {
        	"transId": transId
        }      
    });
    $("#resolve-modal").modal("hide");
    reloadTable();
}
