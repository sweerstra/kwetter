import React, { Component } from 'react';
import './Profile.css';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import ProfileDetailsEditable from '../../components/ProfileDetails/ProfileDetailsEditable';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';
import PostKweet from '../../components/PostKweet/PostKweet';
import NavigationContainer from '../../containers/NavigationContainer';
import KweetsContainer from '../../containers/KweetsContainer';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import {
    addKweet,
    checkUserLikes,
    editSelectedUser,
    followUser,
    postKweet,
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

        this.state = { postKweetOpen: false };
    }

    componentDidMount() {
        const { isAuthenticated, userLoggedIn, match } = this.props;
        const { username, kweetsType } = match.params;

        this.props.onSetSelectedUser(username)
            .then(user => {
                if (isAuthenticated) {
                    const selectedUserId = userLoggedIn.username !== user.username ? user.id : undefined;
                    this.props.onSetFollowing(userLoggedIn.id, selectedUserId);
                    this.props.onSetFollowers(userLoggedIn.id);
                }

                this.props.onSetKweetsOfUser(username, kweetsType)
                    .then(() => {
                        if (isAuthenticated) {
                            this.props.onCheckUserLikes(userLoggedIn.id);
                        }
                    })
            })
            .catch(err => err);

        this.props.onSetTrends();

        if (isAuthenticated) {
            this.setUpSocketConnection(userLoggedIn.username);
        }
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.match.params.kweetsType === nextProps.match.params.kweetsType) return;

        const { username, kweetsType } = nextProps.match.params;
        this.props.onSetKweetsOfUser(username, kweetsType);
    }

    render() {
        const { postKweetOpen } = this.state;
        const { selectedUser, isOwnUser, isAuthenticated, userLoggedIn, isFollowing, userNotFound, match } = this.props;
        const isActive = (type) => match.params.kweetsType === type ? 'active' : '';

        if (userNotFound) {
            return <Redirect to="/not-found"/>
        }

        const profileDetails = isAuthenticated && isOwnUser
            ? <ProfileDetailsEditable className="profile__profile-details"
                                      profile={selectedUser}
                                      onEdit={user => this.props.onEditSelectedUser(userLoggedIn.username, user)}/>
            : <ProfileDetails className="profile__profile-details"
                              profile={selectedUser}
                              isFollowing={isFollowing}
                              isAuthenticated={isAuthenticated}
                              onFollowChange={(type, followId) => this.props.onFollowUser(type, userLoggedIn.id, followId)}/>;

        return (
            <div className="profile">
                <NavigationContainer
                    onSearchEnter={query => this.props.history.push(`/search/kweet/${query}`)}
                />

                {profileDetails}

                <div className="profile__kweets">
                    {isAuthenticated && <div className="kweets__header">
                        <a href="kweets" className={`h2 kweets__heading ${isActive('kweets')}`}>Kweets</a>
                        <a href="timeline" className={`h2 kweets__heading ${isActive('timeline')}`}>Timeline</a>
                        <a href="mentions" className={`h2 kweets__heading ${isActive('mentions')}`}>Mentions</a>
                        {!postKweetOpen && isOwnUser &&
                        <button className="kweets__header__post-kweet btn"
                                onClick={() => this.setPostKweetOpen(true)}>Post Kweet</button>}
                    </div>}

                    {postKweetOpen && <PostKweet onKweetPost={text => this.onPostKweet(text, userLoggedIn)}
                                                 onCancel={() => this.setPostKweetOpen(false)}/>}

                    <KweetsContainer kweets={this.props.kweets}/>
                </div>

                <div className="profile__activity">
                    {isAuthenticated && <ProfileActivity className="profile__profile-activity"
                                                         following={this.props.following}
                                                         followers={this.props.followers}/>}

                    <Trends trends={this.props.trends}/>
                </div>
            </div>
        );
    }

    onPostKweet = (text, user) => {
        this.setPostKweetOpen(false);
        this.props.onPostKweet(text, user);
    };

    setPostKweetOpen = (postKweetOpen) => this.setState({ postKweetOpen });

    setUpSocketConnection = (username) => {
        const socket = new WebSocket(`ws://localhost:8080/kwetter/socket/${username}`);

        socket.onopen = () => {
            console.log(`Open WebSocket connection with "${username}"`);
        };

        socket.onmessage = (message) => {
            const { match } = this.props;
            const { username: pageUsername, kweetsType } = match.params;

            if (username === pageUsername && kweetsType === 'timeline') {
                const kweet = JSON.parse(message.data);
                this.props.onAddKweet(kweet);
            }
        };
    };
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
    onAddKweet: (kweet, user) => dispatch(addKweet(kweet, user)),
    onSetSelectedUser: (username) => dispatch(setSelectedUser(username)),
    onEditSelectedUser: (userame, user) => dispatch(editSelectedUser(userame, user)),
    onSetKweetsOfUser: (username, kweetsType) => dispatch(setKweetsOfUser(username, kweetsType)),
    onSetFollowing: (userId, selectedUserId) => dispatch(setFollowing(userId, selectedUserId)),
    onSetFollowers: (userId) => dispatch(setFollowers(userId)),
    onFollowUser: (followState, userId, followId) => dispatch(followUser(followState, userId, followId)),
    onPostKweet: (text, user) => dispatch(postKweet(text, user)),
    onCheckUserLikes: (userId) => dispatch(checkUserLikes(userId)),
    onSetTrends: (trends) => dispatch(setTrends(trends)),
});

export default connect(mapStateToProps, mapDispatchToProps)(Profile);