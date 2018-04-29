function validateForm() {
    var x = document.forms["common_text"]["login"].value;
    if (x == "") {
        document.getElementById("loginMessage").innerHTML.valueOf("${loginButton}");
        return false;
    }
}