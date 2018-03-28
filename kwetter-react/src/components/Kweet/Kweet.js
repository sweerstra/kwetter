import React from 'react';
import './Kweet.css';

const Kweet = ({ profilePicture, username, text, date, likes }) => (
    <div className="kweet">
        <div className="kweet__profile-picture">
            <img src={profilePicture}/>
        </div>
        <div className="kweet__content">
            <div className="kweet__content__details">
                <a href="#">@{username}</a>
                <span className="date">{date.toLocaleDateString()}</span>
            </div>
            <div className="kweet__content__text">
                {text}
            </div>
            <div className="kweet__content__like">
                <span className="heart">💗</span>
                <span className="like-count">{likes.toLocaleString()}</span>
            </div>
        </div>
    </div>
);

export default Kweet;