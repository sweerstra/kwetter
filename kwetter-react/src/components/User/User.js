import React from 'react';
import './User.css';

const User = ({ profilePicture, username }) => (
    <li className="user">
        <div className="user__profile-picture">
            <img src={profilePicture}/>
        </div>
        <div className="user__username">
            <a href="#" className="h3">@{username}</a>
        </div>
    </li>
);

export default User;