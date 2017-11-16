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
    const preObject = document.getElementById('object');
    const ulList = document.getElementById('list');

    // create references
    const database = firebase.database().ref();
    const dbRefObject = firebase.database().ref().child('TransactionErrors');
    const dbRefList = firebase.database().ref().child('TransactionErrors').orderByChild('transactionId');

    // Sync object changes
    dbRefObject.on('value', function (snapshot) {
        preObject.innerHTML = JSON.stringify(snapshot.val(), null, 3);
    });

    // Sync list changes
    dbRefList.on('child_added', function (snapshot) {
        console.log("Child Added Event");
        console.log(snapshot.val());

        const li = document.createElement("li");
        li.innerText = "Username: " + snapshot.val().username + " Transaction ID: " + snapshot.val().transactionId;
        li.id = snapshot.key;
        ulList.appendChild(li);
    });

    dbRefList.on('child_changed', function (snapshot) {
        console.log("Child Changed Event");
        console.log(snapshot.val());

        const liChanged = document.getElementById(snapshot.key);
        liChanged.innerText = "Username: " + snapshot.val().username + " Transaction ID: " + snapshot.val().transactionId;
        liChanged.id = snapshot.key;
        ulList.appendChild(liChanged);
    });

    dbRefList.on('child_removed', function (snapshot) {
        console.log("Child Removed Event");
        console.log(snapshot.val());

        const liToRemove = document.getElementById(snapshot.key);
        liToRemove.remove();
    });

    dbRefList.on('child_moved', function (snapshot) {
        console.log("Child Moved Event");
        console.log(snapshot.val());
    });

}());