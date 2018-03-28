import React from 'react';
import './Kweets.css';
import PostKweet from '../PostKweet/PostKweet';
import Kweet from '../Kweet/Kweet';

const Kweets = ({ className, kweets, onKweetPost }) => (
    <div className={`${className} kweets`}>
        <div className="kweets__heading">
            <a href="#" className="h2">Kweets</a>
            <a href="#" className="h2">Timeline</a>
            <button className="btn">Post Kweet</button>
        </div>
        <PostKweet onPost={onKweetPost}/>
        {kweets.map((kweet, index) =>
            <Kweet {...kweet} key={index}/>
        )}
    </div>
);

export default Kweets;