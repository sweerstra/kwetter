import React from 'react';
import './Kweet.css';
import icons from '../../icons';
import { Link } from 'react-router-dom';

const Kweet = ({ user: { username, profilePicture }, text, date, likes = 0, liked, onLike }) => (
    <div className="kweet">
        <div className="kweet__profile-picture">
            <img src={profilePicture}/>
        </div>
        <div className="kweet__content">
            <div className="kweet__content__details">
                <Link to={`/profile/${username}`}>@{username}</Link>
                <span className="date">{new Date(date).toLocaleDateString()}</span>
            </div>
            <div className="kweet__content__text">
                {transformText(text)}
            </div>
            <div className="kweet__content__like">
                <icons.heart onClick={onLike} liked={liked}/>
                <span className="kweet__content__like-count">{likes.toLocaleString()}</span>
            </div>
        </div>
    </div>
);

const transformText = (text) => {
    return text.split(' ').map((word, index) => {
        if (word.startsWith('#') || word.startsWith('@')) {
            return <a href={`/search/${word.slice(1)}`} key={index}>{word}</a>;
        } else {
            return ` ${word} `;
        }
    });
};

export default Kweet;