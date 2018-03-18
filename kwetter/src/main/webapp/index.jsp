<html>

<head>
    <link rel="stylesheet" href="./resources/css/style.css">
</head>

<body>

<h1>User Authentication</h1>

<form>
    <label>
        <input type="text" name="username" class="rounded" value="user1" placeholder="username"/>
    </label>
    <label>
        <input type="password" name="password" class="rounded" value="password" placeholder="password"/>
    </label>

    <button class="btn rounded">Login</button>
    <code id="message"></code>
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

    // remove spellcheck attribute
    document.querySelectorAll('input').forEach(input => input.setAttribute('spellcheck', false));
</script>

</body>

<style>
    body {
        color: white;
        background-color: var(--color-primary);
        margin: 50px;
    }

    main {
        display: inline-block;
        margin-left: 20px;
    }

    button, input {
        margin-bottom: 10px;
    }
</style>

</html>
