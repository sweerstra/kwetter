import React from 'react';
import './ProfileActivity.css';
import User from '../User/User';

const ProfileActivity = ({ className, users }) => (
    <ul className={`${className} profile-activity`}>
        <li>
            <h2 className="profile-activity__heading">Following</h2>
        </li>
        {users.map(user =>
            <User {...user}/>
        )}
        <li>
            <h2 className="profile-activity__heading">Followers</h2>
        </li>
        {users.reverse().map(user =>
            <User {...user}/>
        )}
    </ul>
);

export default ProfileActivity;