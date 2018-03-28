import React, { Component } from 'react';
import './profile.css';
import Navigation from '../../components/Navigation/Navigation';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import Kweets from '../../components/Kweets/Kweets';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';

class Profile extends Component {
    onSearchChange = ({ target }) => {
        console.log(target.value);
    };
    onShowPostKweet = () => {
        this.setState({ showPostKweet: true });
    };
    onKweetPost = (value) => {
        console.log(value);
    };
    onKweetCancel = () => {
        this.setState({ showPostKweet: false });
    };

    constructor(props) {
        super(props);

        this.state = {
            showPostKweet: false
        };
    }

    render() {
        const kweets = [
            {
                username: 'reactjs',
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                text: 'The stars haven\'t quite aligned, so we won\'t have React Conf in early 2018 like we have in past years. TBD if we\'ll host something in the later half of the year. Maybe we\'ll catch y\'all at another conference next year though!',
                date: new Date('2017-02-12'),
                likes: 3474
            },
            {
                username: 'nodejs',
                profilePicture: 'https://pbs.twimg.com/profile_images/702185727262482432/n1JRsFeB_bigger.png',
                text: 'The Node.js Foundation board call will start today at 6pm ET / 3pm PT. We will share the YouTube link once the meeting is live.',
                date: new Date('2017-01-02'),
                likes: 31
            },
            {
                username: 'reactjs',
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                text: 'The stars haven\'t quite aligned, so we won\'t have React Conf in early 2018 like we have in past years. TBD if we\'ll host something in the later half of the year. Maybe we\'ll catch y\'all at another conference next year though!',
                date: new Date('2017-02-12'),
                likes: 3474
            },
            {
                username: 'nodejs',
                profilePicture: 'https://pbs.twimg.com/profile_images/702185727262482432/n1JRsFeB_bigger.png',
                text: 'The Node.js Foundation board call will start today at 6pm ET / 3pm PT. We will share the YouTube link once the meeting is live.',
                date: new Date('2017-01-02'),
                likes: 31
            },
            {
                username: 'reactjs',
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                text: 'The stars haven\'t quite aligned, so we won\'t have React Conf in early 2018 like we have in past years. TBD if we\'ll host something in the later half of the year. Maybe we\'ll catch y\'all at another conference next year though!',
                date: new Date('2017-02-12'),
                likes: 3474
            },
            {
                username: 'nodejs',
                profilePicture: 'https://pbs.twimg.com/profile_images/702185727262482432/n1JRsFeB_bigger.png',
                text: 'The Node.js Foundation board call will start today at 6pm ET / 3pm PT. We will share the YouTube link once the meeting is live.',
                date: new Date('2017-01-02'),
                likes: 31
            }
        ];

        const users = [
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                username: 'react.js'
            },
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/702185727262482432/n1JRsFeB_bigger.png',
                username: 'node.js'
            },
            {
                profilePicture: 'https://pbs.twimg.com/profile_images/875996174305472512/upM71pVR_bigger.jpg',
                username: 'vue.js'
            }
        ];

        const trends = [
            '#react',
            '#node',
            '#vue'
        ];

        const { showPostKweet } = this.state;

        return (
            <div className="profile">
                <Navigation className="profile__nav" onSearch={this.onSearchChange}/>
                <ProfileDetails className="profile__profile-details"/>
                <Kweets className="profile__kweets"
                        kweets={kweets}
                        showPostKweet={showPostKweet}
                        onShowPostKweet={this.onShowPostKweet}
                        onKweetPost={this.onKweetPost}
                        onKweetCancel={this.onKweetCancel}/>
                <ProfileActivity className="profile__profile-activity" users={users} trends={trends}/>
            </div>
        );
    }
}

export default Profile;