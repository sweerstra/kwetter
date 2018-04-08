import React from 'react';
import './Trends.css';

const Trends = ({ children, trends }) => (
    <ul className="trends">
        {children}
        {trends.map((trend, index) =>
            <li className="trend" key={index}>
                <a href="#">{trend}</a>
            </li>
        )}
    </ul>
);

export default Trends;