<html>
<body>

<h1>User Authentication</h1>

<form>
    <label>
        <input type="text" name="username" value="user"/>
    </label>
    <label>
        <input type="text" name="password" value="password"/>
    </label>

    <button>Login</button>
    <pre id="message"></pre>
</form>

<script>
    const message = document.getElementById('message');
    document.querySelector('form').addEventListener('submit', function (e) {
        e.preventDefault();
        message.textContent = '';

        const username = this.username.value;
        const password = this.password.value;

        const request = new XMLHttpRequest();
        request.open('POST', 'api/user/auth');
        request.setRequestHeader("Content-type", "application/json");

        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                message.textContent = request.responseText;
                console.log(request.responseText);
            } else if (request.status === 401) {
                message.textContent = '{"message":"unauthorized"}';
            }
        };

        request.send(JSON.stringify({ username, password }));
    });
</script>

</body>
</html>
