import { combineReducers } from 'redux';
import auth from './auth';
import user from './user';
import kweets from './kweets';

export default combineReducers({ auth, user, kweets });