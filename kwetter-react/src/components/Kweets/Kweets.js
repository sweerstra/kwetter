import React, { Component } from 'react';
import './Kweets.css';
import Kweet from '../Kweet/Kweet';

const Kweets = ({ kweets, onKweetLike, authenticated }) => (
    <div className="kweets">
        {kweets.map((kweet, index) =>
            <Kweet {...kweet} onLike={() => {
                if (authenticated && !kweet.liked) {
                    return onKweetLike(kweet);
                }
            }} key={index}/>
        )}
    </div>
);

export default Kweets;