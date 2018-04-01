import * as types from '../constants/ActionTypes';
import Api from '../api';

export const authenticate = (username, password) => dispatch => {
    return Api.user.authenticate(username, password)
        .then(user => {
            dispatch({ type: types.LOGIN, user });
            localStorage.setItem('access_token', 'token');
            localStorage.setItem('logged_in', JSON.stringify(user));
            return true;
        })
        .catch(err => {
            console.log(err);
            return false;
        });
};

export const logout = () => dispatch => {
    dispatch({ type: types.LOGOUT });
    localStorage.clear();
};