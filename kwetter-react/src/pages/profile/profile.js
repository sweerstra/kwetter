import React, { Component } from 'react';
import './profile.css';
import Navigation from '../../components/Navigation/Navigation';
import ProfileDetails from '../../components/ProfileDetails/ProfileDetails';
import ProfileDetailsEditable from '../../components/ProfileDetails/ProfileDetailsEditable';
import Kweets from '../../components/Kweets/Kweets';
import ProfileActivity from '../../components/ProfileActivity/ProfileActivity';

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            profile: {
                username: 'reactjs',
                profilePicture: 'https://pbs.twimg.com/profile_images/446356636710363136/OYIaJ1KK_bigger.png',
                bio: 'React is a declarative, efficient, and flexible JavaScript library for building user interfaces.',
                location: 'Toronto',
                website: 'facebook.github.io/react'
            },
            authenticated: true
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

        const { profile, authenticated } = this.state;

        const profileDetails = authenticated
            ? <ProfileDetailsEditable className="profile__profile-details"
                                      profile={profile}
                                      onEdit={this.onEdit}/>
            : <ProfileDetails className="profile__profile-details"
                              profile={profile}/>;

        return (
            <div className="profile">
                <Navigation className="profile__nav" onSearch={this.onSearchChange}/>
                {profileDetails}
                <Kweets className="profile__kweets"
                        kweets={kweets}
                        onKweetPost={this.onKweetPost}
                        onKweetLike={this.onKweetLike}
                        authenticated={authenticated}/>
                <ProfileActivity className="profile__profile-activity"
                                 following={users.concat(users.concat(users))}
                                 followers={users.reverse()}
                                 trends={trends}/>
            </div>
        );
    }

    onEdit = (obj) => {
        this.setState(state => ({ profile: { ...state.profile, ...obj } }));
        console.log('from on profile.edit', obj);
    };

    onSearchChange = ({ target }) => {
        console.log(target.value);
    };

    onKweetPost = (value) => {
        console.log(value);
    };

    onKweetLike = (kweet) => {
        console.log('like', kweet);
    }
}

export default Profile;