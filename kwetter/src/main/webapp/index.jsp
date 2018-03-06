<html>
<body>

<h1>User</h1>

<form>
    <label>
        <input type="text" name="username" value="Testuser"/>
    </label>
    <label>
        <input type="text" name="password" value="Password123"/>
    </label>

    <button>Register</button>
</form>

<script>
    document.querySelector('form').addEventListener('submit', function (e) {
        e.preventDefault();

        const username = this.username.value;
        const password = this.password.value;

        const request = new XMLHttpRequest();
        request.open('POST', 'http://localhost:8080/kwetter/api/user');
        request.send(JSON.stringify({ username, password }));
    });
</script>

</body>
</html>
