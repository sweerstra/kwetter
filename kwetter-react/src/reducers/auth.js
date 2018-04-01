import { LOGIN, LOGOUT } from '../constants/ActionTypes';

const auth = (state = {
    isAuthenticated: !!localStorage.getItem('access_token'),
    userLoggedIn: JSON.parse(localStorage.getItem('logged_in'))
}, action) => {
    switch (action.type) {
        case LOGIN:
            return {
                isAuthenticated: true,
                userLoggedIn: action.user
            };

        case LOGOUT:
            return {
                isAuthenticated: false,
                userLoggedIn: null
            };

        default:
            return state;
    }
};

export default auth;