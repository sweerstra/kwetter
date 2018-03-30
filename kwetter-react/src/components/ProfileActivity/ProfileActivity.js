import React from 'react';
import './ProfileActivity.css';
import Users from '../Users/Users';
import Trends from '../Trends/Trends';

const ProfileActivity = ({ className, following, followers, trends }) => (
    <div className={`${className} profile-activity`}>
        <Users users={following} wrap={true}>
            <h2 className="profile-activity__heading">Following</h2>
        </Users>
        <Users users={followers}>
            <h2 className="profile-activity__heading">Followers</h2>
        </Users>
        <Trends trends={trends}>
            <h2 className="profile-activity__heading">Trending</h2>
        </Trends>
    </div>
);

export default ProfileActivity;