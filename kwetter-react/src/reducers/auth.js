import { LOGIN, LOGOUT } from '../constants/ActionTypes';
import { isExpiredToken } from '../utils/index';

const accessToken = localStorage.getItem('access_token');
const isAuthenticated = !!accessToken && !isExpiredToken(accessToken);
const userLoggedIn = isAuthenticated ? JSON.parse(localStorage.getItem('logged_in')) : null;

const auth = (state = { isAuthenticated, userLoggedIn }, action) => {
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