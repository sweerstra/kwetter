import React from 'react';
import './NotFound.css';

const NotFound = () => (
    <div className="not-found-404">
        <h1>That user was not found</h1>
        <div className="not-found-404__continue">
            Click <a href="/login">here</a> to continue
        </div>
    </div>
);

export default NotFound;