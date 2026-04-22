function getById(id) {
    var el = document.getElementById(id);
    return el && typeof el.value !== "undefined" ? el.value : "";
}

function getByName(name) {
    var el = document.querySelector('[name="' + name + '"]');
    return el && typeof el.value !== "undefined" ? el.value : "";
}

function getRegisterValuesFallback() {
    var inputs = document.querySelectorAll("input");
    if (inputs && inputs.length >= 4) {
        return {
            name: inputs[0].value || "",
            email: inputs[1].value || "",
            username: inputs[2].value || "",
            password: inputs[3].value || ""
        };
    }
    return { name: "", email: "", username: "", password: "" };
}

function login() {
    var username = getById("loginUsername") || getById("username") || getByName("username");
    var password = getById("loginPassword") || getById("password") || getByName("password");

    console.log("Login clicked. Username: " + username + ", Password: " + password);
}

function register() {
    var name = getById("regName") || getById("name") || getByName("name");
    var email = getById("regEmail") || getById("email") || getByName("email");
    var username = getById("regUsername") || getById("username") || getByName("username");
    var password = getById("regPassword") || getById("password") || getByName("password");

    if (name === "" && email === "" && username === "" && password === "") {
        var fallback = getRegisterValuesFallback();
        name = fallback.name;
        email = fallback.email;
        username = fallback.username;
        password = fallback.password;
    }

    console.log(
        "Register clicked. Name: " + name +
        ", Email: " + email +
        ", Username: " + username +
        ", Password: " + password
    );

    if (name === "" || email === "" || username === "" || password === "") {
        if (typeof alert !== "undefined") alert("All fields are mandatory");
        return;
    }

    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        if (typeof alert !== "undefined") alert("Enter a valid email address");
        return;
    }

    var usernamePattern = /^[a-zA-Z0-9]+$/;
    if (!usernamePattern.test(username)) {
        if (typeof alert !== "undefined") alert("Username should not contain special characters");
        return;
    }

    var passwordPattern = /^(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordPattern.test(password)) {
        if (typeof alert !== "undefined")
            alert("Password must be at least 8 characters, contain one uppercase letter and one number");
        return;
    }
}

if (typeof module !== "undefined") {
    module.exports = { login, register };
}

if (typeof window !== "undefined") {
    window.login = login;
    window.register = register;
}