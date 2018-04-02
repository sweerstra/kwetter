import { SET_KWEETS } from '../constants/ActionTypes';

const kweets = (state = { kweets: [] }, action) => {
    switch (action.type) {
        case SET_KWEETS:
            return { kweets: action.kweets };

        default:
            return state;
    }
};

export default kweets;