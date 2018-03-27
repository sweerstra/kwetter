import React from 'react';
import './Kweets.css';
import Kweet from '../Kweet/Kweet';

const Kweets = ({ className, kweets }) => (
    <div className={`${className} kweets`}>
        <div className="kweets__heading">
            <h2>Kweets</h2>
        </div>
        {kweets.map((kweet, index) =>
            <Kweet {...kweet} key={index}/>
        )}
    </div>
);

export default Kweets;