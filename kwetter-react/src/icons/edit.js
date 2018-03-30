import React from 'react';

const edit = ({ onClick }) => (
    <svg onClick={onClick} xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
         stroke="currentColor"
         strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" className="feather feather-edit"
         color="#384047">
        <polygon points="16 3 21 8 8 21 3 21 3 16 16 3"/>
    </svg>
);

export default edit;