(function() {

	// Initialize Firebase
	var config = {
		apiKey : "AIzaSyAbb_waz8o7qlzqoeaw57hKyjePecBm03o",
		authDomain : "atsapp-bf5ae.firebaseapp.com",
		databaseURL : "https://atsapp-bf5ae.firebaseio.com",
		projectId : "atsapp-bf5ae",
		storageBucket : "atsapp-bf5ae.appspot.com",
		messagingSenderId : "1029476997978"
	};
	firebase.initializeApp(config);

	// get elements
	// const preObject = document.getElementById('object');
	const ulList = document.getElementById('list');
	var reportList = [];

	// create references
	const database = firebase.database().ref();
	const dbRefObject = firebase.database().ref().child('reports');
	const dbRefList = firebase.database().ref().child('reports').orderByKey()
			.limitToLast(7);

	// Sync object changes
	// dbRefObject.on('value', function (snapshot) {
	// // preObject.innerHTML = JSON.stringify(snapshot.val(), null, 3);
	// console.log(snapshot.val());
	// console.log(snapshot.numChildren());
	// });

	// Sync list changes
	dbRefList.on('child_added', function(snapshot) {
		console.log("Child Added Event");

		var report = {
			"key" : snapshot.key,
			"username" : snapshot.val().username,
			"transactionId" : snapshot.val().transactionId,
			"status" : snapshot.val().status
		};

		const li = document.createElement("li");

		reportList = reportList.concat(report);
		reportList.map(function(data) {
			if (data.status == "unread") {
				li.style = "background-color: whitesmoke";
			} else {
				li.style = null;
			}
			li.setAttribute("class", "report");
			li.setAttribute("onclick", "scrollToRow('" + data.transactionId
					+ "', '" + data.key + "' )");
			li.innerHTML = "Report from " + data.username;
			ulList.prepend(li);
		});

	});

	// count unread notification
	dbRefObject.orderByChild('status').equalTo('unread').on(
			'value',
			function(snapshot) {
				console.log(snapshot.numChildren());
				document.getElementById('test01').innerText = snapshot
						.numChildren();
			});

	// dbRefList.on('child_changed', function (snapshot) {
	// console.log("Child Changed Event");
	// console.log(snapshot.val());
	//
	// const liChanged = document.getElementById(snapshot.key);
	// liChanged.innerText = "Report by " + snapshot.val().username;
	// liChanged.id = snapshot.key;
	// ulList.appendChild(liChanged);
	// });

	// dbRefList.on('child_removed', function (snapshot) {
	// console.log("Child Removed Event");
	// console.log(snapshot.val());
	//
	// const liToRemove = document.getElementById(snapshot.key);
	// liToRemove.remove();
	// });

}());

var reportsObj = firebase.database().ref().child('reports');

function scrollToRow(transId, key) {
	var path = window.location.pathname;
	console.log(path)
	
	if (!path.startsWith("/report")) {
		var url = window.location.protocol + "//" + 
		window.location.hostname + ":" + 
		window.location.port + "/report_" + transId + "_" + key;
		
		window.location.replace(url);
	}
	
	var selection = $("#table #" + transId);
	
	var jumpAndHighlight = {
			jumpToRow: function(table, transId) {
				table.page.jumpToData(transId, 1);
				return jumpAndHighlight;
			},
			highlightRow: function(selection) {
				$("tr[role='row']").removeClass("selectedRow");
				selection.addClass("selectedRow");
				return jumpAndHighlight;
			}
	};
	
	jumpAndHighlight.jumpToRow(table, transId).highlightRow(selection);
	
	reportsObj.child(key).child('status').set('read');
}