import React from 'react';
import './Trends.css';

const Trends = ({ children }) => (
    <ul className="trends">
        {children}
        <li className="trend">
            <a href="#">#react</a>
        </li>
        <li className="trend">
            <a href="#">#node</a>
        </li>
        <li className="trend">
            <a href="#">#vue</a>
        </li>
    </ul>
);

export default Trends;