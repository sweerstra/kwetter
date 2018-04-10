import React, { Component } from 'react';
import Kweets from '../components/Kweets/Kweets';
import { connect } from 'react-redux';
import { likeKweet } from '../actions/index';

class KweetsContainer extends Component {
    render() {
        const { kweets, isAuthenticated, userLoggedIn, onLikeKweet } = this.props;

        return (
            <Kweets
                kweets={kweets}
                onKweetLike={kweet => {
                    if (isAuthenticated && !kweet.liked) {
                        onLikeKweet(kweet.id, userLoggedIn.id);
                    }
                }}
            />
        );
    }
}

const mapStateToProps = ({ auth, kweets }) => {
    return ({
        ...auth,
        ...kweets
    });
};

const mapDispatchToProps = (dispatch) => ({
    onLikeKweet: (kweetId, userId) => dispatch(likeKweet(kweetId, userId))
});

export default connect(mapStateToProps, mapDispatchToProps)(KweetsContainer);