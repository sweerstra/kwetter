import React, { Component } from 'react';
import './login.css';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { authenticate } from '../../actions';

class Login extends Component {
    render() {
        const { isAuthenticated, userLoggedIn } = this.props;

        if (isAuthenticated) {
            return <Redirect to={`profile/${userLoggedIn.username}/kweets`}/>
        }

        return (
            <div className="login">
                <div className="login__content">
                    <h1>Kwetter Login</h1>
                    <form name="formpje" onSubmit={this.onLogin}>
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

    onLogin = (e) => {
        const { target } = e;
        e.preventDefault();

        const username = target.username.value;
        const password = target.password.value;

        this.props.onAuthenticate(username, password);
    };
}

const mapStateToProps = ({ auth }) => ({ ...auth });

const mapDispatchToProps = (dispatch) => ({
    onAuthenticate: (username, password) => dispatch(authenticate(username, password))
});

export default connect(mapStateToProps, mapDispatchToProps)(Login);