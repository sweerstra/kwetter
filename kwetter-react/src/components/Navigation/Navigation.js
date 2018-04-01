import React from 'react';
import './Navigation.css';
import logo from '../../images/kwetter-logo.png';
import SearchBar from '../SearchBar/SearchBar';
import { Link } from 'react-router-dom';

const Navigation = ({ className, onSearch, kweetSuggestions, onSearchCancel }) => (
    <nav className={`${className} light`}>
        <div className="nav__brand h1">
            <img src={logo}/>
            Kwetter
        </div>
        <div className="nav__search">
            <SearchBar onSearch={onSearch} suggestions={kweetSuggestions} onCancel={onSearchCancel}/>
        </div>
        <div className="nav__context-menu">
            <a href="#">Registreer</a>
        </div>
        <div className="nav__context-menu">
            <Link to="/login">Inloggen</Link>
        </div>
    </nav>
);

export default Navigation;