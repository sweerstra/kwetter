import React, { Component } from 'react';
import './EditDetail.css';
import icons from '../../icons';

class EditDetail extends Component {
    constructor(props) {
        super(props);

        this.state = { value: '', isEditing: false };
    }

    render() {
        const { value, isEditing } = this.state;
        const { children, className, name, placeholder } = this.props;

        const element = isEditing
            ? <input className="input"
                     value={value}
                     name={name}
                     placeholder={placeholder}
                     autoFocus="true"
                     onKeyDown={this.onKeyPress}
                     onChange={this.onChange}/>
            : children;

        return (
            <div className={`${className} edit-detail`}>
                {element}
                {!isEditing && <icons.edit onClick={this.startEditing}/>}
            </div>
        );
    }

    startEditing = () => {
        console.log('start editing');
        this.setState({ isEditing: true });
    };

    onKeyPress = (e) => {
        const { value } = this.state;
        const { name } = this.props;

        if (e.keyCode === 13) {
            this.props.onEdit({ [name]: value });
            this.setState({ value, isEditing: false });
        }
    };

    onChange = (e) => {
        this.setState({ value: e.target.value });
    };
}

export default EditDetail;