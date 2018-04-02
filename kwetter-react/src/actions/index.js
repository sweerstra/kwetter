import * as types from '../constants/ActionTypes';
import Api from '../api';

export const setCurrentUser = (username) => dispatch => {
    return Api.user.getUser(username)
        .then(user => dispatch({ type: types.SET_USER, user }))
        .catch(() => dispatch({ type: types.NOT_FOUND }));
};

export const setKweetsOfUser = (username, kweetsType) => dispatch => {
    (kweetsType === 'kweets'
        ? Api.kweet.getKweets(username)
        : Api.kweet.getTimeline(username))
        .then(kweets => dispatch({ type: types.SET_KWEETS, kweets }));
};

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