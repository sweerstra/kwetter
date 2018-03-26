import React, { Component } from 'react';
import './login.css';

class Login extends Component {
    onLogin = (e) => {
        const { target } = e;
        e.preventDefault();

        const username = target.username.value;
        const password = target.password.value;

        console.log(username, password);
    }

    render() {
        return (
            <div className="login">
                <div className="login__content">
                    <h1>Kwetter Login</h1>
                    <form onSubmit={this.onLogin}>
                        <input type="text"
                               name="username"
                               placeholder="Username"
                               autoFocus="true" spellCheck="false" autoCapitalize="none"/>
                        <input type="password"
                               name="password"
                               placeholder="Password"
                               spellCheck="false" autoCapitalize="none"/>
                        <button className="btn">
                            Login
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

export default Login;