import React from 'react';
import './Kweets.css';
import Kweet from '../Kweet/Kweet';

const Kweets = ({ className, kweets }) => (
    <div className={`${className} kweets`}>
        <h2 className="kweets__heading">Kweets</h2>
        {kweets.map((kweet, index) =>
            <Kweet {...kweet} key={index}/>
        )}
    </div>
);

export default Kweets;