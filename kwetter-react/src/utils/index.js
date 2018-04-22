import React from 'react';

export const debounce = (fn, time) => {
    let timeout;

    return function () {
        const call = () => fn.apply(this, arguments);
        clearTimeout(timeout);
        timeout = setTimeout(call, time);
    }
};

export const transformText = (text) => {
    return text.split(' ').map((word, index) => {
        if (word.startsWith('@')) {
            return <a href={`/profile/${word.slice(1)}/kweets`} key={index}>{word}</a>;
        } else if (word.startsWith('#')) {
            return <a href={`/search/trend/${word.slice(1)}`} key={index}>{word}</a>;
        } else {
            return ` ${word} `;
        }
    });
};

export const decodeToken = (token) => {
    const [, url] = token.split('.');
    const base = url.replace('-', '+').replace('_', '/');
    return JSON.parse(atob(base));
};

export const isExpiredToken = (token) => {
    try {
        const jwt = decodeToken(token);
        const currentTime = Date.now() / 1000;
        return jwt.exp < currentTime;
    } catch (e) {
        console.error(e);
        return true;
    }
};