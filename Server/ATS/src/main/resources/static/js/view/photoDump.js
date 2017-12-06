//function showImage() {
//     return "<a href="javascript:showImage()">show image</a>";
//}
//function setImageVisible(id, visible) {
//    var img = document.getElementById(id);
//    img.style.visibility = (visible ? 'visible' : 'hidden');
//}


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

                                        "ajax": {
                                            "url": "../transaction/getDumpToPhoto",
                                            "dataSrc": ""
                                        },
                                        "columns": [
                                            {"data": null},
                                            {"data": "photoName",
                                            },
                                            {"data": "licensePlate"},
                                            {"data": "createdTime"},
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openResolveModal(this)'></button>"
                                            }
                                        ],
                                        "columnDefs": [{
                                                "searchable": false,
                                                "orderable": false,
                                                "targets": 0
                                            }]
                                    });
                    table.on('order.dt search.dt', function () {
                        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1;
                        });
                    }).draw();
//                    // handle delete form submit
//                    $("#delete-form").submit(function (event) {
//                        event.preventDefault();
//                        submitDeleteForm();
//                    });
//                    // handle update form submit
//                    $("#resolve-modal").submit(function (event) {
//                        event.preventDefault();
//                        submitUpdateForm();
//                    })
//
//                    $("#add-form").submit(function (event) {
//                        event.preventDefault();
//                        submitAddForm();
//                        clearAddForm();
//                    });
                });
/*
 * Contain ajax call to perfom update or delete a report
 */
// perform ajax call to save report

//var curr;
//function submitUpdateForm() {
//	var account;
//	var role = $("#update-form-role").val();
//	if (role != "3") {
//		account = {
//				"id": $("#update-form-id").val(),
//		        "username": $("#update-form-username").val(),
//		        "password": $("#update-form-password").val(),
//		        "role": $("#update-form-role").val(),
//		        "fullname": $("#update-form-fullname").val(),
//		        "email": $("#update-form-email").val(),
//		        "phone": $("#update-form-phone").val(),
//		        "numberId": $("#update-form-numberId").val(),
//		        "vehicle": null,
//		        "balance": $("#update-form-balance").val(),
//		        "isActive": $("#update-form-isActive").val(),
//		        "isEnable": $("#update-form-isEnable").val()
//		    };
//	} else {
//		account = {
//				"id": $("#update-form-id").val(),
//		        "username": $("#update-form-username").val(),
//		        "password": $("#update-form-password").val(),
//		        "role": $("#update-form-role").val(),
//		        "fullname": $("#update-form-fullname").val(),
//		        "email": $("#update-form-email").val(),
//		        "phone": $("#update-form-phone").val(),
//		        "numberId": $("#update-form-numberId").val(),
//		        "vehicle": {
//		        		"id": $("#update-form-vehicleId").val(),
//		        		"licensePlate": $("#update-form-licensePlate").val(),
//		        		"vehicletype": {
//		        			"id": $("#update-form-typeId").val()
//		        		}
//		        },
//		        "balance": $("#update-form-balance").val(),
//		        "isActive": $("#update-form-isActive").val(),
//		        "isEnable": $("#update-form-isEnable").val()
//		    };
//	}
//    $.ajax({
//        type: "POST",
//        contentType: "application/json",
//        url: "../account/update",
//        data: JSON.stringify(account),
//        success: function (result) {
//            if (result == "fail") {
//                setStatus("Update fail!", "#ff0000");
//            } else {
//                setStatus("Update success!", "#00cc00");
//            }
//        },
//        error: function (result) {
//            setStatus("Something was wrong! Please check again!", "#ff0000");
//        }
//    });
//    $("#update-modal").modal("hide");
//    $("#alert").show();
//    reloadTable();
//    clearStatus();
//}
//// handle delete form submit
//function submitDeleteForm() {
//	var account = {
//			"id": $("#update-form-id").val(),
//	        "username": $("#update-form-username").val(),
//	        "password": $("#update-form-password").val(),
//	        "role": $("#update-form-role").val(),
//	        "fullname": $("#update-form-fullname").val(),
//	        "email": $("#update-form-email").val(),
//	        "phone": $("#update-form-phone").val(),
//	        "numberId": $("#update-form-numberId").val(),
//	        "vehicle": {
//	        		"id": $("#update-form-vehicleId").val(),
//	        		"licensePlate": $("#update-form-licensePlate").val(),
//	        		"vehicletype": {
//	        			"id": $("#update-form-typeId").val()
//	        		}
//	        },
//	        "balance": $("#update-form-balance").val(),
//	        "isActive": $("#update-form-isActive").val(),
//	        "isEnable": $("#update-form-isEnable").val()
//	    };
//    $.ajax({
//        type: "POST",
//        contentType: "application/json",
//        url: "../account/delete",
//        data: JSON.stringify(account),
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

    $("#update-form-licensePlate").val(data.licensePlate);
    $("#update-form-photo").val(data.photoName);
    $("#update-form-dateTime").val(data.createdTime);
    curr = {
        //"photo": "./imgs/dumps/"+ data.photo,
        "photo": img.src = "/imgs/dumps/" + data.photoName,

        "vehicle": {
            "licensePlate": data.licensePlate
        }

    };
    $("#resolve-modal").modal('toggle');
}
// open delete confirm modal
function openDeleteModal(element) {
    var data = $("#table").DataTable().row($(element).parents('tr')).data();
    $("#delete-form-skillId").val(data.skillId);
    $("#delete-modal").modal('toggle');
}

