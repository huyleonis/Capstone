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
                                        	"url" : "../transaction/getDumpToPhoto",
                                        	"dataSrc": ""
                                        },
                                        "columns": [
                                            { "data": "photoName" },
                                            { "data": "licensePlate" },
                                            { "data": "createdTime" },
                                            {// column for view
                                                // detail-update-delete
                                                "data": null,
                                                "defaultContent": "<button class='btn btn-success glyphicon glyphicon-edit' onclick='openResolveModal(this)'></button>"
                                                        + "<button class='btn btn-danger glyphicon glyphicon-trash' onclick='openDeleteModal(this)'></button>",
                                            }
                                        ]
                                    });
//                    // handle delete form submit
//                    $("#delete-form").submit(function (event) {
//                        event.preventDefault();
//                        submitDeleteForm();
//                    });
//                    // handle update form submit
//                    $("#update-form").submit(function (event) {
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
function submitAddForm() {
	var account;
    var role = $("#add-form-role").val();
    if (role != "3") {
    	account = {
                "username": $("#add-form-username").val(),
                "password": $("#add-form-password").val(),
                "role": $("#add-form-role").val(),
                "fullname": $("#add-form-fullname").val(),
                "email": $("#add-form-email").val(),
                "phone": $("#add-form-phone").val(),
                "numberId": $("#add-form-numberId").val(),
                "vehicle": null,
                "balance": $("#add-form-balance").val(),
                "isActive": $("#add-form-isActive").val(),
                "isEnable": $("#add-form-isEnable").val()
        };
	} else {
		account = {
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
	}
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

