import React from 'react';
import './ProfileActivity.css';
import Users from '../Users/Users';

const ProfileActivity = ({ className, following, followers }) => (
    <div className={`${className} profile-activity`}>
        {following.length > 0 && <Users users={following} wrap={false}>
            <h2 className="profile-activity__heading">Following</h2>
        </Users>}
        {followers.length > 0 && <Users users={followers}>
            <h2 className="profile-activity__heading">Followers</h2>
        </Users>}
    </div>
);

export default ProfileActivity;