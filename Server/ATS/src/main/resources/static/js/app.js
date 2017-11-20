(function () {

    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyAbb_waz8o7qlzqoeaw57hKyjePecBm03o",
        authDomain: "atsapp-bf5ae.firebaseapp.com",
        databaseURL: "https://atsapp-bf5ae.firebaseio.com",
        projectId: "atsapp-bf5ae",
        storageBucket: "atsapp-bf5ae.appspot.com",
        messagingSenderId: "1029476997978"
    };
    firebase.initializeApp(config);

    // get elements
    // const preObject = document.getElementById('object');
    const ulList = document.getElementById('list');
    var reportList = [];

    // create references
    const database = firebase.database().ref();
    const dbRefObject = firebase.database().ref()
        .child('Reports')
        .orderByKey()
        .limitToLast(4);
    const dbRefList = firebase.database().ref()
        .child('Reports')
        .orderByKey()
        .limitToLast(4);

    // Sync object changes
    // dbRefObject.on('value', function (snapshot) {
    //     // preObject.innerHTML = JSON.stringify(snapshot.val(), null, 3);
    //     console.log(snapshot.val());
    //     console.log(snapshot.numChildren());
    // });

    // Sync list changes
    var count = 0;
    dbRefList.on('child_added', function (snapshot) {
        console.log("Child Added Event");
        console.log(snapshot.val());

        var report = {
            "key": snapshot.key,
            "username": snapshot.val().username,
            "transactionId": snapshot.val().transactionId,
            "isRead": snapshot.val().isRead,
            "isShow": snapshot.val().isShow
        };

        const li = document.createElement("li");
        li.id = snapshot.key;
        li.innerHTML = "<a href='/report'>Report from "
            + snapshot.val().username  + "</a>";
        ulList.appendChild(li);

        reportList = reportList.concat(report);

    });

    // function scrollToRow(id) {
    //     var selection = $( "#table" );
    //     console.log( selection );
    //     $(".dataTables_scrollBody").scrollTo(selection);
    //     $("tr[role='row']").removeClass("selectedRow");
    //     selection.addClass("selectedRow");
    // }

    function markAsRead() {
        li.style = "background-color: white";
    }

    // dbRefList.on('child_changed', function (snapshot) {
    //     console.log("Child Changed Event");
    //     console.log(snapshot.val());
    //
    //     const liChanged = document.getElementById(snapshot.key);
    //     liChanged.innerText = "Report by " + snapshot.val().username;
    //     liChanged.id = snapshot.key;
    //     ulList.appendChild(liChanged);
    // });

    // dbRefList.on('child_removed', function (snapshot) {
    //     console.log("Child Removed Event");
    //     console.log(snapshot.val());
    //
    //     const liToRemove = document.getElementById(snapshot.key);
    //     liToRemove.remove();
    // });

    // dbRefList.on('child_moved', function (snapshot) {
    //     console.log("Child Moved Event");
    //     console.log(snapshot.val());
    // });

}());