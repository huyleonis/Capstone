
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
                                "../report/getReportDetail", // get
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
                            {
                                "data": "photo",
                                "visible": false
                            },
                            {// column for view
                                // detail-update-delete
                                "data": null,
                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openResolveModal(this)'></button>"                            
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
function openResolveModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    var img = document.getElementById("update-form-photo");
    
    $("#update-form-id").val(data.id);
    $("#update-form-licensePlate").val(data.licensePlate);
    $("#update-form-photo").val(data.photo);
    $("#update-form-dateTime").val(data.dateTime);
    curr = {
        "id": data.id,
        //"photo": "./imgs/plates/"+ data.photo,
        "photo": img.src = "/imgs/plates/" + data.photo,
        
        "vehicle": {           
            "licensePlate": data.licensePlate
        }
        
    };
    $("#resolve-modal").modal('toggle');
}

// open delete confirm modal
function openDeleteModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    $("#delete-form-id").val(data.id);
    $("#delete-modal").modal('toggle');
}

// clear input of update modal
function clearUpdateForm() {
	 $("#update-form-correct-licensePlate").val("");
    $("#delete-modal").modal("hide");
}





