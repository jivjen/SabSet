
//const functions = require('firebase-functions');
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
var functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);



exports.updateBills = functions.database.ref('/BillsOfUsers/{ph}/{name}')
    .onUpdate((change, context) => {

        const oldData = change.before.toJSON();

        const date = oldData['date'];
        const month = date.substring(3);
        const store = oldData['storeName']
        const phg = context.params.ph;
        const billName = context.params.name;
        const newData = change.after.toJSON();
        var pro_qty = newData['pro-qty'];

        console.log("pro_qTy " + pro_qty);

        var key;
        var prods = [];
        var qties = [];
        console.log('START');

        for (key in pro_qty) {
            console.log("KEY = " + key + " VALUE = " + pro_qty[key]);
            if (pro_qty.hasOwnProperty(key)) {
                prods.push(key);
                qties.push(pro_qty[key]);
            }
        }

        if (typeof pro_qty !== 'undefined' && pro_qty.length !== 0) {
            console.log("ADDED SHIT")
            console.log('UPDATING == ' + prods);
            console.log('Prod = ' + qties);

            admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/processed').set("yes");
            admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/products').set(prods);
            admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/quantities').set(qties);
            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/products').set(prods);

            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/quantities').set(qties);

            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/processed').set("yes");

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/products').set(prods);

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/quantities').set(qties);

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/processed').set("yes");

            return admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/pro-qty').remove();
        }
        else if (newData['processed'] === "no") {
            admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/products').remove();
            admin.database().ref('BillsOfUsers/' + phg + '/' + billName + '/quantities').remove();
            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/products').remove();

            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/quantities').remove();

            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/processed').set("no");

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/products').remove();

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/quantities').remove();

            return admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/processed').set("no");


        }
        else if (newData['processed'] === "yes") {
            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/products').set(newData['products']);

            admin.database().ref('BillsSortedByStore/' + phg + '/' + store + '/' + billName + '/quantities').set(newData['quantities']);

            admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/products').set(newData['products']);

            return admin.database().ref('BillsSortedByMonth/' + phg + '/' + month + '/' + billName + '/quantities').set(newData['quantities']);

        }
        else {
            return null;
        }


    });


