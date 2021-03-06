import React from 'react';
import './Trends.css';

const Trends = ({ trends }) => (
    <ul className="trends">
        <h2 className="trends__heading">Trending</h2>
        {trends.map((trend, index) =>
            <li className="trend" key={index}>
                <a href={`/search/trend/${trend.slice(1)}`}>{trend}</a>
            </li>
        )}
    </ul>
);

export default Trends;