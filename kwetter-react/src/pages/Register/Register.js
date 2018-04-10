import React, { Component } from 'react';
import './Register.css';
import icons from '../../icons';
import { debounce } from '../../utils';
import { connect } from 'react-redux';
import { getUser, register } from '../../actions';

class Register extends Component {
    constructor(props) {
        super(props);

        this.state = { usernameExists: true, passwordsMatch: false };

        this.usernameCallback = debounce(() => {
            const { username } = this.state;

            if (username) {
                getUser(username)
                    .then(() => this.setState({ usernameExists: true }))
                    .catch(() => this.setState({ usernameExists: false }));
            } else {
                this.setState({ usernameExists: true });
            }
        }, 600);
    }

    render() {
        const { usernameExists, password, confirmPassword } = this.state;
        const usernameIcon = !usernameExists ? <icons.check/> : <icons.x/>;
        const passwordsAreCorrect = password && confirmPassword && password === confirmPassword;
        const passwordsIcon = passwordsAreCorrect ? <icons.check/> : <icons.x/>;

        return (
            <div className="register">
                <div className="register__content">
                    <h1>Kwetter Register</h1>
                    <form onSubmit={this.onRegister}>
                        <div className="input-with-icon">
                            <input type="text"
                                   name="username"
                                   onChange={this.onFormChange}
                                   placeholder="Username"
                                   autoFocus="true" spellCheck="false" autoCapitalize="none"/>
                            {usernameIcon}
                        </div>
                        <input type="password"
                               name="password"
                               onChange={this.onFormChange}
                               placeholder="Password"
                               spellCheck="false" autoCapitalize="none"/>
                        <div className="input-with-icon">
                            <input type="password"
                                   name="confirmPassword"
                                   onChange={this.onFormChange}
                                   placeholder="Confirm Password"
                                   spellCheck="false" autoCapitalize="none"/>
                            {passwordsIcon}
                        </div>
                        <div className="register__content__login-check">
                            Login after registration
                            <input type="checkbox"
                                   className="checkbox"
                                   name="shouldLogin"
                                   onChange={this.onShouldLoginChange}/>
                        </div>
                        <button className="btn register__content__button"
                                disabled={usernameExists || !passwordsAreCorrect}>
                            Register
                        </button>
                    </form>
                </div>
            </div>
        );
    }

    onFormChange = (e) => {
        const { name, value } = e.target;

        if (name === 'username') {
            this.usernameCallback();
        }

        this.setState({ [name]: value });
    };

    onShouldLoginChange = (e) => {
        const { name, checked } = e.target;
        this.setState({ [name]: checked });
    };

    onRegister = (e) => {
        e.preventDefault();

        const { username, password, shouldLogin } = this.state;

        this.props.onRegister(username, password, shouldLogin)
            .then(() => this.props.history.push('/profile'));
    };
}

const mapDispatchToProps = (dispatch) => ({
    onRegister: (username, password, shouldLogin) => dispatch(register(username, password, shouldLogin))
});

export default connect(null, mapDispatchToProps)(Register);