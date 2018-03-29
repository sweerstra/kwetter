import React from 'react';
import './Users.css';

const Users = ({ children, users }) => (
    <ul className="users">
        <li>{children}</li>
        {users.map(({ profilePicture, username }, index) =>
            <li className="user" key={index}>
                <div className="user__profile-picture">
                    <img src={profilePicture}/>
                </div>
                <div className="user__username">
                    <a href="#" className="h3">@{username}</a>
                </div>
            </li>
        )}
    </ul>
);

export default Users;