import React from 'react';
import './Navigation.css';

const Navigation = ({ className, onSearch }) => (
    <nav className={`${className} light`}>
        <div className="nav__brand h1">
            Kwetter
        </div>
        <div className="nav__search">
            <input type="text"
                   onChange={onSearch}
                   placeholder="Search kweets..."/>
        </div>
        <div className="nav__context-menu">
            <a href="#">Registreer</a>
        </div>
        <div className="nav__context-menu">
            <a href="#">Inloggen</a>
        </div>
    </nav>
);

export default Navigation;