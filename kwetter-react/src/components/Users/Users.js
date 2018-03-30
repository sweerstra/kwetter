import React from 'react';
import './Users.css';

const Users = ({ children, users, wrap }) => (
    <div className="users">
        <div className="users__heading">{children}</div>
        <ul className={`users__list ${wrap ? 'users__list--wrap' : 'users__list--detail'}`}>
            {users.map(({ profilePicture, username }, index) =>
                <li className="user" key={index}>
                    <div className="user__profile-picture">
                        <a href="#">
                            <img src={profilePicture}/>
                        </a>
                    </div>
                    {!wrap && <div className="user__username">
                        <a href="#" className="h3">@{username}</a>
                    </div>}
                </li>
            )}
        </ul>
    </div>
);

export default Users;