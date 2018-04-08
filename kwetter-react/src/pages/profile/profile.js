import React, { Component } from 'react';
import './profile.css';
import Navigation from '../../components/Navigation/Navigation';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import ProfileDetailsEditable from '../../components/ProfileDetails/ProfileDetailsEditable';
import Kweets from '../../components/Kweets/Kweets';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import {
    editSelectedUser,
    emptyFoundKweets,
    followUser,
    likeKweet,
    logout,
    postKweet,
    searchKweets,
    setFollowers,
    setFollowing,
    setKweetsOfUser,
    setSelectedUser,
    setTrends
} from '../../actions/index';
import Trends from "../../components/Trends/Trends";

class Profile extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        const { isAuthenticated, userLoggedIn, match } = this.props;
        const { username, kweetsType } = match.params;

        this.props.onSetSelectedUser(username)
            .then(({ user }) => {
                if (isAuthenticated) {
                    const selectedUserId = userLoggedIn.username !== user.username ? user.id : undefined;
                    this.props.onSetFollowing(userLoggedIn.id, selectedUserId);
                    this.props.onSetFollowers(userLoggedIn.id);
                }
            })
            .then(() => this.props.onSetKweetsOfUser(username, kweetsType));

        this.props.onSetTrends();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.match.params.kweetsType === nextProps.match.params.kweetsType) return;

        const { username, kweetsType } = nextProps.match.params;
        this.props.onSetKweetsOfUser(username, kweetsType);
    }

    render() {
        const { selectedUser, isOwnUser, isAuthenticated, userLoggedIn, isFollowing, userNotFound } = this.props;

        if (userNotFound) {
            return <Redirect to="/404"/>
        }

        const profileDetails = isAuthenticated && isOwnUser
            ? <ProfileDetailsEditable className="profile__profile-details"
                                      profile={selectedUser}
                                      onEdit={user => this.props.onEditSelectedUser(userLoggedIn.username, user)}/>
            : <ProfileDetails className="profile__profile-details"
                              profile={selectedUser}
                              isFollowing={isFollowing}
                              onFollowChange={(type, followId) => this.props.onFollowUser(type, userLoggedIn.id, followId)}/>;

        return (
            <div className="profile">
                <Navigation className="profile__nav"
                            onSearch={this.props.onSearchKweets}
                            kweetSuggestions={this.props.kweetsFound}
                            onSearchCancel={this.props.onEmptyFoundKweets}
                            userLoggedIn={userLoggedIn}
                            onLogout={this.props.onLogout}/>
                {profileDetails}
                <Kweets className="profile__kweets"
                        kweets={this.props.kweets}
                        onKweetPost={text => this.props.onPostKweet(text, userLoggedIn)}
                        onKweetLike={kweet => this.props.onLikeKweet(kweet.id, userLoggedIn.id)}
                        authenticated={isAuthenticated}
                        isOwnUser={isOwnUser}
                        kweetsType={this.props.match.params.kweetsType}/>

                <div className="profile__activity">
                    {isAuthenticated && <ProfileActivity className="profile__profile-activity"
                                                         following={this.props.following}
                                                         followers={this.props.followers}/>}

                    <Trends trends={this.props.trends}>
                        <h2 className="profile-activity__heading">Trending</h2>
                    </Trends>
                </div>
            </div>
        );
    }
}

const mapStateToProps = ({ auth, user, kweets }) => {
    const isOwnUser = auth.userLoggedIn
        && user.selectedUser
        && auth.userLoggedIn.username === user.selectedUser.username;

    return ({
        ...auth,
        ...user,
        ...kweets,
        isOwnUser
    });
};

const mapDispatchToProps = (dispatch) => ({
    onSetSelectedUser: (username) => dispatch(setSelectedUser(username)),
    onEditSelectedUser: (userame, user) => dispatch(editSelectedUser(userame, user)),
    onSetKweetsOfUser: (username, kweetsType) => dispatch(setKweetsOfUser(username, kweetsType)),
    onSetFollowing: (userId, selectedUserId) => dispatch(setFollowing(userId, selectedUserId)),
    onSetFollowers: (userId) => dispatch(setFollowers(userId)),
    onFollowUser: (followState, userId, followId) => dispatch(followUser(followState, userId, followId)),
    onPostKweet: (text, user) => dispatch(postKweet(text, user)),
    onSearchKweets: (text) => dispatch(searchKweets(text)),
    onEmptyFoundKweets: () => dispatch(emptyFoundKweets()),
    onLikeKweet: (kweetId, userId) => dispatch(likeKweet(kweetId, userId)),
    onSetTrends: (trends) => dispatch(setTrends(trends)),
    onLogout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(Profile);